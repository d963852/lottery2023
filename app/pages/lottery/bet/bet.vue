<template>
	<view class="wrap">
		<!-- 自定义标题栏 -->
		<!-- 		<u-navbar back-text="返回" title="剑未配妥，出门已是江湖" :background="titleBackground"
		 title-color="#fff" back-icon-color="#fff" back-text-style="navbarTitleFontColor"></u-navbar> -->

		<!-- 余额组件 -->
		<lo-balance :showBtn="true"></lo-balance>

		<!-- 语言切换组件 -->
		<js-lang :title="navbarTitle" ref="jslang" :showBtn="true"></js-lang>



		<!-- <scroll-view class="scroll-list" scroll-y="true"> -->
		<!-- 当前在售 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				<u-icon name="tags" class="u-m-r-10"></u-icon>
				{{ $t('betPage.current') }}
				<text class="red-bold u-p-l-10 u-p-r-10">{{ gameInfo.currentIssueNumber }}</text>
			</view>
			<view>
				<u-icon name="clock" class="u-m-r-10"></u-icon>
				<u-count-down color="#fa3534" :timestamp="countDownTimestamp" separator="colon" separator-size="28"
					separator-color="#606266" @change="countDownChange" @end="countDownEnd"></u-count-down>
			</view>
		</view>
		<u-gap height="5"></u-gap>

		<!-- 开奖时间 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				<u-icon name="bell" class="u-m-r-10"></u-icon>
				{{ $t('betPage.nextLottery') }}：<text class="red-bold">{{ currentIssueEndTime }}</text>
			</view>
			<view>
				<u-icon name="volume" class="u-m-r-10"></u-icon>
				{{ gameInfo.gameDesc }}
			</view>
		</view>
		<u-gap height="5"></u-gap>

		<!-- 上期开奖 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				<u-icon name="star" class="u-m-r-10"></u-icon>
				{{ gameInfo.lastIssueNumber }} &nbsp;{{ $t('betPage.lastIssueWinningNumbers') }}
			</view>
			<view @click="showLotteryHistory">
				{{ $t('betPage.history') }}
				<u-icon name="arrow-right"></u-icon>
			</view>
		</view>
		<view style="background: #fff; min-height: 100rpx;">
			<view class="u-flex u-row-center u-p-20" v-show="lastIssueLotteryNumberVisible">
				<view class="lastLotteryNumberCss" v-for="(item, index) in lastIssueLotteryNumberList" :key="index">
					<u-count-to :start-val="0" :end-val="item" font-size=40 color="#fa3534"></u-count-to>
				</view>
			</view>
			<view class="u-flex u-row-center u-p-20 lastLotteryNumberLoadingCss"
				v-show="!lastIssueLotteryNumberVisible">
				<u-loading color="red" class="u-m-r-15"></u-loading>{{ $t('betPage.loadingWinningNumber') }}
			</view>
		</view>
		<u-gap height="5"></u-gap>

		<!-- 玩法组选择 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view @click="showPlayGroupSelecter">
				<u-icon name="bookmark" class="u-m-r-10"></u-icon>
				<text class="red-bold">
					{{ currentPlayMethodGroup.groupName }} - {{ currentPlayMethod.playMethodName }}
				</text>
			</view>
			<u-select v-model="playMethodSelecterVisible" mode="mutil-column-auto" :list="playGroupList"
				@confirm="confirmPlayMethod"></u-select>
			<!-- 玩法说明 -->
			<u-popup v-model="playMethodHelpVisible" :closeable=true mode="bottom" length="60%">
				<view class="u-p-t-60 u-p-30">
					<view class="u-text-center u-font-40 red-bold">
						{{ currentPlayMethodGroup.groupName }} - {{ currentPlayMethod.playMethodName }}
					</view>
					<u-line margin="30rpx" />
					<view class="u-font-25 u-m-t-15 u-font-40">
						{{ this.currentPlayMethod.info }}
					</view>
					<u-line margin="30rpx" />
					<view class="u-font-25 u-m-t-15 u-font-40">
						{{ this.currentPlayMethod.example }}
					</view>
				</view>
			</u-popup>
			<view @click="showPlayMethodHelp" class="u-text-right">
				<u-icon name="question-circle" class="u-m-r-10"></u-icon>
				{{ $t('betPage.playMethodHelp') }}
			</view>
		</view>

		<!-- 奖金/返点 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				奖金{{ this.currentBet.bonusAmount }} 元
			</view>
			<view class="u-flex-4 u-p-l-30  u-p-r-30">
				<u-slider v-model="rebateAndBonusSliderValue" step="1" min="0" max="100" active-color="#fa3534"
					@end="calcRebateAndBonus"></u-slider>
			</view>
			<view class="u-text-right">
				{{ this.currentBet.rebate }}% 返点
			</view>
		</view>
		<u-gap height="15"></u-gap>

		<!-- 选号 -->
		<view style="border: 1rpx solid #888;">
			<!-- 玩法组件 -->
			<view>
				<component :is="currentPlayMethodComponent" v-on:monitorBetNumber="monitorBetNumber"></component>
			</view>

			<!-- 模式、倍数  -->
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<view @click="showMultiplierSelecter">
					<u-icon name="rewind-right" class="u-m-r-10"></u-icon>
					{{ $t('betPage.multiple') }}：<text
						class="red-bold u-p-l-10 u-p-r-10 u-m-r-10 cricle-border">{{ currentBet.betMultiplier }}</text>

					<u-select v-model="multiplierSelecterVisible" :list="multiplierList"
						@confirm="changeMultiplier"></u-select>
				</view>
				<view>
					<u-icon name="integral" class="u-m-r-10"></u-icon>
					{{ $t('betPage.betAmount') }}
					<text class="red-bold u-p-l-10">{{ currentBet.finalCount }}</text>
				</view>
			</view>
			<u-gap height="5"></u-gap>

			<!-- 注数、金额 -->
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<view @click="showUnitSelecter">
					<u-icon name="red-packet" class="u-m-r-10"></u-icon>
					{{ $t('betPage.unit') }}：<text
						class="red-bold u-p-l-10 u-p-r-10 cricle-border">{{ currentBet.betUnit.label }}</text>
					<u-select v-model="unitListSelecterVisible" :list="unitList" @confirm="changeUnit"></u-select>
				</view>
				<view>
					<u-icon name="rmb" class="u-m-r-10"></u-icon>
					{{ $t('betPage.amount') }}
					<text class="red-bold u-p-l-10 u-p-r-10">{{ currentFinalBetAmount }}</text> 元
				</view>
			</view>
			<u-gap height="5"></u-gap>

			<!-- 添加选号 -->
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<!-- 添加到投注列表按钮 -->
				<u-button type="primary" @click="addToBetList" :disabled="addToBetListBtnDisabled">
					<u-icon name="plus" class="u-m-r-10"></u-icon>
					{{ $t('betPage.addToBetList') }}
				</u-button>
				<!-- 直接投注按钮 -->
				<u-button type="success" @click="directBet" :disabled="betBtnDisabled">
					<u-icon name="checkbox-mark" class="u-m-r-10"></u-icon>
					{{ $t('betPage.bet') }}
				</u-button>
			</view>
			<u-gap height="5"></u-gap>
		</view>

		<u-gap height="15"></u-gap>

		<!-- 投注列表 -->
		<view style="border: 1rpx solid #2979ff;">
			<view class="u-flex u-p-20">
				<view class="u-flex-6 bet-list-title u-text-left">
					{{ $t('betPage.betList') }}
				</view>
				<view class="u-flex-6 u-text-right" @click="navTo('/pages/lottery/bet-history/bet-history')">
					{{ $t('betPage.betHistory') }} <u-icon name="arrow-right"></u-icon>
				</view>
			</view>
			<view v-show="isShowBetList" style="background: #fff;">
				<view class="u-flex u-row-between u-p-20">
					<view class="u-flex-2">
						<text>投注数</text>
					</view>
					<view class="u-flex-3">
						奖金
					</view>
					<view class="u-flex-2">
						返点
					</view>
					<view class="u-flex-3">
						投注金额
					</view>
					<view class="u-flex-1 u-text-right">
						删除
					</view>
				</view>
				<view class="u-flex u-row-between u-p-20" v-for="(item, index) in betList" :key="index">
					<view class="u-flex-2" @click="showBet(index)">
						<text>{{ item.finalCount }} </text>
					</view>
					<view class="u-flex-3" @click="showBet(index)">
						{{ item.bonusAmount }}{{ $t('betPage.currencyUnit') }}
					</view>
					<view class="u-flex-2" @click="showBet(index)">
						{{ item.rebate }}%
					</view>
					<view class="u-flex-3" @click="showBet(index)">
						{{ item.betAmount }} {{ $t('betPage.currencyUnit') }}
					</view>
					<view class="u-flex-1 u-text-right">
						<u-icon name="close-circle" class="red-bold" @click="removeFromBetList(index)"></u-icon>
					</view>
				</view>
			</view>
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<view>
					{{ $t('betPage.sumAmount') }}
					<text class="red-bold u-p-l-10 u-p-r-10"
						style="font-size: 50rpx;">{{ this.betListTotalAmount }}</text>元
				</view>
				<view class="u-p-r-15">
					<u-button type="primary" :disabled="betListBtnDisabled" @click="betListBet">
						<u-icon name="checkbox-mark" class="u-m-r-10"></u-icon>
						{{ $t('betPage.betNow') }}
					</u-button>
				</view>
			</view>
			<u-gap height="15"></u-gap>
		</view>

		<!-- 投注详情 -->
		<view>
			<u-popup v-model="betListDetailVisible" :closeable=true mode="bottom" length="70%">
				<view class="u-p-t-60 u-p-30 u-font-33">
					<view class="u-text-center red-bold">
						投注详情
					</view>
					<u-line margin="30rpx" />
					<view>
						游戏：
						<text class="red-bold u-p-l-10 u-p-r-10">
							{{ gameInfo.gameName }}
						</text>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
					<view>
						玩法：
						<text class="red-bold u-p-l-10 u-p-r-10">
							{{ betListDetailInfo.playMethodGroupName }} - {{ betListDetailInfo.playMethodName }}
						</text>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							奖金
							<text class="red-bold u-p-l-10 u-p-r-10">{{ betListDetailInfo.bonusAmount }}</text>元
						</view>
						<view class="u-m-l-10 u-m-r-10">
							/
						</view>
						<view>
							返点
							<text class="red-bold u-p-l-10 u-p-r-10">{{ betListDetailInfo.rebate }} %</text>
						</view>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
					<view>
						投注号
					</view>
					<view>
						<view v-for="(item, index) in betListDetailInfo.betNumberObj" :key="index">
							左第{{ index+1 }}位 <text class="red-bold u-p-l-10 u-p-r-10">{{ item.join(",") }}</text>
						</view>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							倍数
							<text class="red-bold u-p-l-10 u-p-r-10">{{ betListDetailInfo.betMultiplier }}</text> 倍
						</view>
						<view class="u-m-l-10 u-m-r-10">
							/
						</view>
						<view>
							总注数
							<text class="red-bold u-p-l-10 u-p-r-10">{{ betListDetailInfo.finalCount }} </text> 注
						</view>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							货币单位
							<text class="red-bold u-p-l-10 u-p-r-10">{{ betListDetailInfo.betUnit.label }}</text>
						</view>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							投注金额
							<text class="red-bold u-p-l-10 u-p-r-10">{{ betListDetailInfo.betAmount }} </text> 元
						</view>
					</view>
					<u-line margin="30rpx" border-style="dashed"></u-line>
				</view>
			</u-popup>
		</view>

		<!-- 余额不足提示 -->
		<view>
			<u-popup v-model="needRechargeAlertVisible" :closeable=true mode="bottom" length="50%">
				<view class="u-p-t-60 u-p-30">
					<view class="u-text-center u-font-40 red-bold">
						您的余额不足
					</view>
					<u-line margin="30rpx" />
					<view class="u-font-25 u-m-t-15 u-font-40">
						您当前的余额 {{ this.balance }} 元
					</view>
					<u-line margin="30rpx" />
					<view class="u-font-25 u-m-t-15 u-font-40">
						需要的金额 {{ this.needAmount }} 元
					</view>
					<u-line margin="30rpx" />
					<view class="u-font-25 u-m-t-15 u-font-40">
						还需充值 {{ this.needRechargeAmount }} 元
					</view>
					<u-line margin="30rpx" />
					<view class="u-font-25 u-m-t-15">
						<u-button type="primary  u-font-40">
							<u-icon name="rmb-circle" class="u-m-r-10"></u-icon>
							{{ $t('去充值') }}
						</u-button>
					</view>
				</view>
			</u-popup>
		</view>

		<!-- 开奖历史简单版 -->
		<view>
			<u-popup v-model="lotteryHistoryVisible" :closeable=true mode="bottom" length="70%">
				<view class="u-p-t-60 u-p-30">
					<view>
						<view class="u-flex u-row-between u-p-20" style="background: #fff;">
							<view class="red-bold">
								期号
							</view>
							<view class="red-bold">
								开奖结果
							</view>
						</view>
						<u-line margin="10rpx" border-style="dashed"></u-line>
					</view>
					<view v-for="(item,index) in lotteryHistoryData" :key="index">
						<view class="u-flex u-row-between u-p-20" style="background: #fff;">
							<view>
								{{ item.issueNum }}
							</view>
							<view>
								{{ item.lotteryNum }}
							</view>
						</view>
						<u-line margin="10rpx" border-style="dashed"></u-line>
					</view>
				</view>
			</u-popup>
		</view>

		<!-- 顶部弹出错误提醒 -->
		<view>
			<u-top-tips ref="uTips"></u-top-tips>
		</view>
		<!-- </scroll-view> -->
	</view>
</template>
<script>
	import utils from '@/common/utils.js';

	export default {
		data() {
			return {
				navbarTitle: '', //标题栏
				gameName: '', //游戏名称
				balance: 0, //用户余额
				gameInfo: {
					lastIssueLotteryNumber: "正在开奖中",
				},
				nextGameInfo: {}, //下期游戏
				gameCode: '', //游戏code
				currentPlayMethodGroup: '', //当前玩法组
				currentPlayMethod: '', //当前玩法
				playMethodSelecterVisible: false, //玩法选择是否可见
				playGroupList: [], //玩法选择组数据
				playMethodHelpVisible: false, //玩法说明是否可见
				currentPlayMethodComponent: null, //当前玩法页面
				multiplierSelecterVisible: false, //倍数选择是否可见
				needRechargeAlertVisible: false, //余额不足提醒
				needAmount: 0, //需要的金额
				needRechargeAmount: 0, //需充值金额
				betListDetailVisible: false, //投注列表详情是否可见
				betListDetailInfo: { //投注列表详情里要显示的数据
					betNumber: {},
					betCount: 0,
					finalCount: 0,
					betMultiplier: 1,
					betUnit: {
						value: 1,
						label: '元',
					},
					betAmount: 0.00, //投注金额
					rebate: 0, // 投注返点
					bonusAmount: 0, // 投注奖金金额
					rebateAmount: 0, //投注返点金额
				},
				multiplierList: [{ // 倍数选择列表
						value: 1,
						label: '1'
					},
					{
						value: 2,
						label: '2'
					},
					{
						value: 5,
						label: '5'
					},
					{
						value: 10,
						label: '10'
					},
					{
						value: 20,
						label: '20'
					},
					{
						value: 50,
						label: '50'
					},
					{
						value: 100,
						label: '100'
					},
					{
						value: 500,
						label: '500'
					},
					{
						value: 1000,
						label: '1000'
					}
				],
				unitListSelecterVisible: false, //货币单位选择是否可见
				unitList: [{ //货币选择列表
						value: 1,
						label: '元'
					},
					{
						value: 0.1,
						label: '角'
					},
					{
						value: 0.01,
						label: '分'
					},
					{
						value: 0.001,
						label: '厘'
					}
				],
				isShowBetList: true, //是否显示投注列表（在有投注加入列表后才显示）
				betList: [], //投注列表数据
				currentBet: { //当前投注数据
					betNumber: {},
					betCount: 0,
					finalCount: 0,
					betMultiplier: 1,
					betUnit: {
						value: 1,
						label: '元',
					},
					betAmount: 0.00, //投注金额
					rebate: 0, // 投注返点
					bonusAmount: 0, // 投注奖金金额
					rebateAmount: 0, //投注返点金额
				},
				basePrice: 2.00, // 彩票每注基准售价，默认2元，进入页面后从服务器更新
				canBet: true, //是否可投注，用于控制按钮是否可点，当截止投注前5秒不可投注
				waitForBetRequestDisable: false, //点击投注后，禁用添加列表、投注按钮

				rebateAndBonusSliderValue: 0, //投注返点slider值
				// playMethodMaxBonus: 0, //玩法最大奖金
				// playMethodMinBonus: 0, //玩法最小奖金
				memberRebate: 0, //客户投注返点上限
				sysMaxRebate: 0, //系统投注返点上限

				lotteryHistoryVisible: false, //底部开奖历史是否可见
				lotteryHistoryData: [], //底部开奖历史数据
			};
		},
		computed: {
			// 当前投注总金额
			currentFinalBetAmount() {
				let amount = this.currentBet.finalCount * this.basePrice * this.currentBet.betUnit.value;
				if (!isNaN(parseFloat(amount)) && isFinite(amount)) {
					return amount;
				}
				return 0;
			},
			// 是否显示投注列表
			bitListVisible() {
				if (this.betList && this.betList.length > 0) {
					return true;
				} else {
					return false;
				}
			},
			// 添加至投注列表按钮是否disable
			addToBetListBtnDisabled() {
				if (!isNaN(parseFloat(this.currentFinalBetAmount)) && //投注金额是数字
					isFinite(this.currentFinalBetAmount) && //投注金额是数字
					this.currentFinalBetAmount > 0 && // 投注金额大于0
					!this.waitForBetRequestDisable //没有在等投注结果
				) {
					return false;
				}
				return true;
			},
			// 直接投注按钮是否disable
			betBtnDisabled() {
				if (!isNaN(parseFloat(this.currentFinalBetAmount)) && //投注金额是数字
					isFinite(this.currentFinalBetAmount) && //投注金额是数字
					this.currentFinalBetAmount > 0 && // 投注金额大于0
					this.canBet && //没有封盘
					!this.waitForBetRequestDisable //没有在等投注结果
				) {
					return false;
				}
				return true;
			},
			// 计算投注列表总金额
			betListTotalAmount() {
				if (this.betList && this.betList.length > 0) {
					let sumAmonut = 0;
					this.betList.map(function(item, index) {
						return sumAmonut += item.betAmount;
					});
					return sumAmonut;
				} else {
					return 0;
				}
			},
			// 投注列表中的立即投注按钮是否disable
			betListBtnDisabled() {
				if (!isNaN(parseFloat(this.betListTotalAmount)) && //列表总投注金额是数字
					isFinite(this.betListTotalAmount) && //列表总投注金额是数字
					this.betListTotalAmount > 0 && // 列表总投注金额大于0
					this.canBet && //没有封盘
					!this.waitForBetRequestDisable //没有在等投注结果
				) {
					return false;
				}
				return true;
			},
			// 倒计时
			countDownTimestamp() {
				if (this.gameInfo.currentIssueEndTime != undefined) {
					//console.info('this.gameInfo.currentIssueEndTime', this.gameInfo.currentIssueEndTime);
					let startTime = new Date(this.gameInfo.currentIssueEndTime); // 结束时间
					let endTime = new Date(); // 开始时间
					let ts = startTime - endTime;
					// console.info(ts);
					return (ts + 1) / 1000;
				} else {
					return 0;
				}
			},
			// 当前投注截止时间
			currentIssueEndTime() {
				return utils.formatDateTime(new Date(this.gameInfo.currentIssueEndTime), "M-d HH:mm:ss");
			},
			// 上期开奖结果
			lastIssueLotteryNumberList() {
				// return ['08','08','08','08','08','08','08','08','08','08'];
				// console.info("开始计算list", this.gameInfo.lastIssueLotteryNumber);
				if (this.gameInfo.lastIssueLotteryNumber &&
					this.gameInfo.lastIssueLotteryNumber.length >= 1 &&
					this.gameInfo.lastIssueLotteryNumber != "正在开奖中") {
					return this.gameInfo.lastIssueLotteryNumber.split(',');
				} else {
					return false;
				}
			},
			// 是否显示上期开奖结果
			lastIssueLotteryNumberVisible() {
				// console.info("开始计算vis", this.gameInfo.lastIssueLotteryNumber);
				if (this.gameInfo.lastIssueLotteryNumber &&
					this.gameInfo.lastIssueLotteryNumber.length >= 1 &&
					this.gameInfo.lastIssueLotteryNumber != "正在开奖中") {
					return true;
				} else {
					return false;
				}
			},
		},
		components: {},
		async onLoad(param) {
			if (param) {
				this.gameCode = param.gameCode;
				this.gameName = param.gameName;
				this.navbarTitle = param.gameName;
			}
			// 获取最新销售基准单价
			await this.$u.api.sysConfigService.getConfig({
				configName: 'lottery.sys.basePrice'
			}).then(res => {
				if (res.success) {
					this.basePrice = res.data;
				}
			});
			// 获取最新系统投注返点上限
			await this.$u.api.sysConfigService.getConfig({
				configName: 'lottery.member.rebate'
			}).then(res => {
				if (res.success) {
					this.sysMaxRebate = res.data;
				}
				// console.info('sysMaxRebate',this.sysMaxRebate);
			});
			// 获取用户投注返点上限
			await this.$u.api.memberService.getRebate().then(res => {
				if (res.success) {
					this.memberRebate = res.data;
				}
				// console.info('memberRebate',this.memberRebate);
			});
			// 获取游戏信息
			if (param) {
				await this.getGameInfo(param.gameCode);
				await this.loadComponent(param.gameCode);
			}
		},
		onShow() {},
		created() {},
		methods: {
			backToIndex() {
				uni.switchTab({
					url: '/pages/lottery/home/home'
				})
			},
			async getBalance() {
				let res = await this.$u.api.memberService.getBalance();
				// console.info(res);
				if (res.success) {
					this.balance = res.data;
				} else {
					this.$u.toast(this.$t('error.serverDisconnected'));
				}
			},
			// 获取游戏基本信息
			getGameInfo(gameCode) {
				this.$u.api.gameService.getByCode({
					gameCode: gameCode
				}).then(res => {
					// console.info(res.data);
					if (res.success) {
						this.gameInfo = res.data;
						this.$forceUpdate();
						// 如果未开奖，获取开奖信息
						if (!this.gameInfo.lastIssueLotteryNumber) {
							this.gameInfo.lastIssueLotteryNumber = "正在开奖中";
							//未取到上期开奖结果，继续取
							this.getLastIssueLotteryNumber();
						}
					} else {
						this.$u.toast(this.$t('error.notFound'));
					}
				});
			},
			// 切换玩法组件
			confirmPlayMethod(e) {
				console.log('e', e);
				this.currentPlayMethodComponent = resolve => require(['@/components/lo-playmethod/' + e[1].extra
						.playMethodTpl +
						'.vue'
					],
					resolve);
				this.calcRebateAndBonus();
			},
			// 显示玩法选择select
			showPlayGroupSelecter() {
				this.playMethodSelecterVisible = true;
			},
			// 显示投注倍数select
			showMultiplierSelecter() {
				this.multiplierSelecterVisible = true;
			},
			// 显示玩法帮助
			showPlayMethodHelp() {
				this.playMethodHelpVisible = true;
			},
			// 选中投注倍数后计算注数
			changeMultiplier(e) {
				this.currentBet.betMultiplier = e[0].value;
				if (!isNaN(parseFloat(this.currentBet.betCount)) && isFinite(this.currentBet.betCount)) {
					this.currentBet.finalCount = this.currentBet.betCount * this.currentBet.betMultiplier;
				}
			},
			// 显示投注单位select
			showUnitSelecter() {
				this.unitListSelecterVisible = true;
			},
			// 选中投注单位后变更投注单位
			changeUnit(e) {
				this.currentBet.betUnit = e[0];
			},
			// 根据游戏code取得游戏类型，显示玩法组件
			async loadComponent(gameCode) {
				let that = this;
				this.$u.api.playMethodService.findListByGameCode({
					gameCode: gameCode
				}).then(res => {
					if (res.success) {
						// console.info(res.data);
						let newArray = res.data.map(function(item, index) {
							return {
								value: item.id,
								label: item.groupName,
								extra: item,
								children: item.playMethodList.map(function(item2, index2) {
									return {
										value: item2.id,
										label: item2.playMethodName,
										extra: item2,
									}
								}),
							};
						});
						that.playGroupList = newArray;
						that.currentPlayMethodGroup = that.playGroupList[0].extra;
						that.currentPlayMethod = that.playGroupList[0].children[0].extra;
						// 计算玩法对应的奖金和返点
						this.calcRebateAndBonus();
						// console.info("currentPlayMethodGroup",that.currentPlayMethodGroup);
						// console.info("currentPlayMethod",that.currentPlayMethod);
						// 默认显示第一个玩法组里的第一个组件
						let playMethodtTpl = that.currentPlayMethod.playMethodTpl;

						that.currentPlayMethodComponent = resolve => require(['@/components/lo-playmethod/' +
								playMethodtTpl + '.vue'
							],
							resolve);
						this.$forceUpdate();
					} else {
						this.$u.toast(this.$t('error.notFound'));
					}
				});

			},
			// 监视从子组件中返回的投注数字
			monitorBetNumber(betInfo) {
				if (betInfo.error) {
					this.currentBet.finalCount = betInfo.error;
				} else {
					// console.info('接收到子组件回传', betInfo);
					// 计算投注注数
					this.currentBet.betNumber = betInfo.betNumber;
					this.currentBet.betNumberObj = betInfo.betNumberObj;
					this.currentBet.betCount = betInfo.betCount;
					this.currentBet.finalCount = betInfo.betCount * this.currentBet.betMultiplier;
				}
			},
			// 倒计时处理
			countDownChange(timestamp) {
				if (timestamp <= 6 && timestamp >= 5) {
					// 5秒终止本期投注
					this.$u.toast(this.$t('msg.stopBet'), 7000);
					this.canBet = false;
				}
			},
			// 倒计时结束后，刷新游戏信息
			countDownEnd() {
				this.updateCurrentIssue();
			},
			// 刷新游戏信息
			updateCurrentIssue() {
				setTimeout(() => {
					this.$u.api.gameService.getByCode({
						gameCode: this.gameCode
					}).then(res => {
						// console.info("res", res);
						if (res.success) {
							this.nextGameInfo = res.data;
							if (this.nextGameInfo.currentIssueNumber == this.gameInfo.currentIssueNumber) {
								this.updateCurrentIssue();
							} else {
								// console.info("数据更新");
								this.gameInfo = this.nextGameInfo;
								this.canBet = true;
								if (!this.nextGameInfo.lastIssueLotteryNumber) {
									this.gameInfo.lastIssueLotteryNumber = "正在开奖中";
									//未取到上期开奖结果，继续取
									this.getLastIssueLotteryNumber();
								}
							}
						} else {
							this.updateCurrentIssue();
						}
					});
				}, 1000);
			},
			// 获取上期开奖结果
			getLastIssueLotteryNumber() {
				setTimeout(() => {
					this.$u.api.gameService.getByCode({
						gameCode: this.gameCode
					}).then(res => {
						if (res.success) {
							if (res.data.lastIssueLotteryNumber &&
								res.data.lastIssueNumber == this.gameInfo.lastIssueNumber) {
								this.gameInfo = res.data;
								// console.info("this.gameInfo.lastIssueLotteryNumber", this.gameInfo
								// 	.lastIssueLotteryNumber);
								this.$forceUpdate();
							} else {
								this.getLastIssueLotteryNumber();
							}
						} else {
							this.getLastIssueLotteryNumber();
						}
					});
				}, 500);
			},
			/**
			 * 投注返点和奖金计算
			 * 若系统投注返点上限15，客户投注返点上限不能超过15
			 * 投注时允许客户选择奖金和返点比例，返点是投注就给（相当于打折），奖金是中奖才给
			 * 中奖奖金金额=(玩法最大奖金-玩法最小奖金)*(客户投注返点上限-客户选择返点)/系统投注返点上限+玩法最小奖金
			 * 投注返点金额=投注金额*客户选择返点
			 */
			calcRebateAndBonus() {
				// 客户选择的返点
				let selectRebate = this.memberRebate * this.rebateAndBonusSliderValue / 100;
				// 当前玩法的中奖奖金
				let bonusDiff = this.currentPlayMethod.maxBonus - this.currentPlayMethod.minBonus;
				let rebateDiff = this.memberRebate - selectRebate;
				let bonusAdd = bonusDiff * rebateDiff / this.sysMaxRebate;
				let bonus = bonusAdd + this.currentPlayMethod.minBonus;
				this.currentBet.bonusAmount = bonus; //奖金
				this.currentBet.rebate = selectRebate; // 返点率(还需要除以100)
				this.currentBet.rebateAmount = selectRebate / 100 * this.currentBet.betAmount; //返点金额=返点率*投注金额

				// console.log('this.currentPlayMethod', this.currentPlayMethod);

				// console.log('this.memberRebate', this.memberRebate);
				// console.log('selectRebate', selectRebate);
				// console.log('rebateDiff', rebateDiff);

				// console.log('this.currentPlayMethod.maxBonus', this.currentPlayMethod.maxBonus);
				// console.log('this.currentPlayMethod.minBonus', this.currentPlayMethod.minBonus);
				// console.log('bonusDiff', bonusDiff);

				// console.log('this.sysMaxRebate', this.sysMaxRebate);
				// console.log('bonusAdd', bonusAdd);
				// console.log('bonus', bonus);

				// console.log('this.currentBet.bonusAmount', this.currentBet.bonusAmount);
				// console.log('this.currentBet.rebate', this.currentBet.rebate);
				// console.log('this.currentBet.rebateAmount ', this.currentBet.rebateAmount);
			},
			// 添加到投注列表及直接投注之前，计算当前投注的信息
			async validateBalance(amount) {
				this.needAmount = amount;
				// 取最新余额，判断余额是否足够
				let res = await this.$u.api.memberService.getBalance(this.vuex_user.id);
				if (res.success) {
					this.balance = res.data;
					if (this.balance < amount) {
						//余额不够，给出提示
						this.needRechargeAmount = (this.needAmount - this.balance).toFixed(2);
						this.needRechargeAlertVisible = true;
						return false;
					}
				} else {
					this.$u.toast(this.$t('error.serverDisconnected'));
					return false;
				}
				return true;
			},
			// 将当前投注添加到投注列表
			async addToBetList() {
				this.currentBet.betAmount = this.currentFinalBetAmount;
				this.currentBet.playMethodId = this.currentPlayMethod.id;
				this.currentBet.playMethodName = this.currentPlayMethod.playMethodName;
				this.currentBet.playMethodGroupName = this.currentPlayMethodGroup.groupName;
				// 检查余额是否足够
				let validated = await this.validateBalance(this.currentBet.betAmount);
				if (validated) {
					let theBet = utils.deepClone(this.currentBet);
					this.betList.unshift(theBet);
					console.info('betList', this.betList);
				}
			},
			// 从投注列表中删除指定的投注
			removeFromBetList(index) {
				this.betList.splice(index, 1);
			},
			// 直接投注
			async directBet() {
				this.currentBet.betAmount = this.currentFinalBetAmount;
				this.currentBet.playMethodId = this.currentPlayMethod.id;
				// 检查余额是否足够
				let validated = await this.validateBalance(this.currentBet.betAmount);
				if (validated) {
					// 投注
					let theBet = utils.deepClone(this.currentBet);
					this.bet([theBet]);
				}
			},
			// 列表投注
			async betListBet() {
				// 检查余额是否足够
				let validated = await this.validateBalance(this.betListTotalAmount);
				if (validated) {
					// 投注
					this.bet(this.betList);
				}
			},
			// 投注
			async bet(betList) {
				// 添加当前销售游戏代码和期号
				let betRequest = {
					gameCode: this.gameInfo.gameCode,
					issue: this.gameInfo.currentIssueNumber,
					betList: betList,
				};
				// 投注
				this.waitForBetRequestDisable = true; // 禁用添加列表、投注按钮
				let betResult = await this.$u.api.betService.bet({
					betRequest: JSON.stringify(betRequest),
				});
				if (betResult.success) {
					// 投注成功，更新余额
					this.$refs.uTips.show({
						title: this.$t('betPage.betSuccess'),
						type: 'success',
						duration: '3000'
					})
					this.$refs.jslang.balance = betResult.data;
					this.waitForBetRequestDisable = false; // 启用添加列表、投注按钮
				} else {
					this.$refs.uTips.show({
						title: '投注失败，原因：' + betResult.message,
						type: 'warning',
						duration: '3000'
					})
					this.waitForBetRequestDisable = false; // 启用添加列表、投注按钮
				}
			},
			showBet(index) {
				console.info(index);
				this.betListDetailInfo = this.betList[index];
				this.betListDetailVisible = true;
			},
			navTo(url) {
				console.info('url', url);
				uni.navigateTo({
					url: url,
				});
			},
			async showLotteryHistory() {
				let res = await this.$u.api.issueService.findIssueHistoryList({
					gameCode: this.gameInfo.gameCode
				});
				console.info(res.data);
				if (res.success) {
					this.lotteryHistoryData = res.data;
					this.lotteryHistoryVisible = true;
				} else {
					this.$u.toast(this.$t('error.serverDisconnected'));
					return;
				}
			}
		},
		beforeDestroy() {},
	};
</script>
<style lang="scss">
	page {
		background-color: #f8f8f8;
	}

	.bet-list-title {
		color: #2979ff;
		font-weight: bold;
		font-size: 32rpx;
	}

	.lastLotteryNumberCss {
		text-align: center;
		-moz-border-radius: 120px;
		border-radius: 120px;
		padding: 0rpx 15rpx;
		margin: 0rpx 5rpx;
		border: 1px solid #fa3534;
		background-color: #fff;
	}

	.lastLotteryNumberLoadingCss {
		padding: 0rpx 15rpx;
		margin: 0rpx 5rpx;
		color: #fa3534;
		background-color: #fff;
		font-size: 40rpx;
		letter-spacing: 8rpx;
		font-weight: bold;
	}
</style>