<template>
	<view class="wrap">
		<!-- 语言切换和余额组件 -->
		<js-lang :title="gameName" :showBtn="true"></js-lang>

		<!-- 当前在售 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				<u-icon name="tags" class="u-m-r-10"></u-icon>
				当前在售
				<text class="red-bold u-p-l-10 u-p-r-10">20230523-1365</text>
			</view>
			<view>
				<u-icon name="clock" class="u-m-r-10"></u-icon>
				<u-count-down color="#fa3534" :timestamp="300" separator="zh" separator-size="28"
					separator-color="#606266"></u-count-down>
			</view>
		</view>
		<u-gap height="5"></u-gap>

		<!-- 彩种详情 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				<u-icon name="bell" class="u-m-r-10"></u-icon>
				开奖时间：23:37:48
			</view>
			<view>
				<u-icon name="volume" class="u-m-r-10"></u-icon>
				每期30分钟 全天1440期
			</view>
		</view>
		<u-gap height="5"></u-gap>

		<!-- 上期开奖 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view>
				<u-icon name="star" class="u-m-r-10"></u-icon>
				上期开奖
			</view>
			<view>
				<u-tag class="red-bold u-margin-left-10" text="08,08,08,08,08,08,08,08,08,08" shape="circle"
					mode="plain" type="error" />
			</view>
			<view>
				往期开奖
				<u-icon name="arrow-right"></u-icon>
			</view>
		</view>
		<u-gap height="5"></u-gap>

		<!-- 玩法组选择 -->
		<view class="u-flex u-row-between u-p-20" style="background: #fff;">
			<view @click="showPlayGroupSelect">
				<u-icon name="bookmark" class="u-m-r-10"></u-icon>
				<text class="red-bold">{{ playGroup }} - {{ playMethod }}</text>
			</view>
			<view>
				<u-number-box v-model="value" :min="0.0" :max="1.0" :step="0.1"></u-number-box>
			</view>
			<view>
				<u-select v-model="showSelect" mode="mutil-column-auto" :list="playGroupList"
					@confirm="confirmPlayMethod"></u-select>
			</view>
		</view>
		<u-gap height="15"></u-gap>

		<!-- 选号 -->
		<view style="border: 1rpx solid #2979ff;">
			<!-- 玩法 -->
			<view>
				<component :is="currentPlayMethod"></component>
			</view>

			<!-- 模式、倍数  -->
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<view @click="showMultiplierSelecter">
					<u-icon name="rewind-right" class="u-m-r-10"></u-icon>
					倍数：<text class="red-bold u-p-l-10 u-p-r-10 u-m-r-10 cricle-border">{{ multiplier }}</text> 倍
					<u-select v-model="showMultiplierList" :list="multiplierList"
						@confirm="changeMultiplier"></u-select>
				</view>
				<view>
					<u-icon name="integral" class="u-m-r-10"></u-icon>
					注数
					<text class="red-bold u-p-l-10 u-p-r-10">100000</text>注
				</view>
			</view>
			<u-gap height="5"></u-gap>

			<!-- 注数、金额 -->
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<view @click="showUnitSelecter">
					<u-icon name="red-packet" class="u-m-r-10"></u-icon>
					投注单位：<text class="red-bold u-p-l-10 u-p-r-10 cricle-border">{{ unit.label }}</text>
					<u-select v-model="showUnitList" :list="unitList" @confirm="changeUnit"></u-select>
				</view>
				<view>
					<u-icon name="rmb" class="u-m-r-10"></u-icon>
					金额
					<text class="red-bold u-p-l-10 u-p-r-10">200000000.00</text> 元
				</view>
			</view>
			<u-gap height="5"></u-gap>

			<!-- 添加选号 -->
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<u-button type="primary">
					<u-icon name="plus" class="u-m-r-10"></u-icon>
					添加投注列表
				</u-button>
				<u-button type="success">
					<u-icon name="checkbox-mark" class="u-m-r-10"></u-icon>
					一键投注
				</u-button>
			</view>
			<u-gap height="5"></u-gap>
		</view>

		<u-gap height="15"></u-gap>

		<!-- 投注列表 -->
		<view style="border: 1rpx solid #19be6b;">
			<view class="u-flex u-row-center u-p-20 bet-list-title">
				投注列表
			</view>
			<view v-show="isShowBetList" style="background: #fff;">
				<view class="u-flex u-row-between u-p-20" v-for="(item, index) in betList" :key="index"
					@click="showBet(item.menuCode,item.menuName)">
					<view class="u-flex-3">
						<text>{{ item.betCount }}注</text>
					</view>
					<view class="u-flex-2">
						{{ item.betMultiplier }}倍
					</view>
					<view class="u-flex-2">
						{{ item.betUnit.label }}模式
					</view>
					<view class="u-flex-3">
						<text>{{ item.betAmount }}元</text>
					</view>
					<view class="u-flex-1 u-text-right">
						<u-icon name="close-circle" class="red-bold"></u-icon>
					</view>
				</view>
			</view>
			<view class="u-flex u-row-between u-p-20" style="background: #fff;">
				<view>
					总计
					<text class="red-bold u-p-l-10 u-p-r-10" style="font-size: 50rpx;">20000.00</text>元
				</view>
				<view class="u-p-r-15">
					<u-button type="success">
						<u-icon name="checkbox-mark" class="u-m-r-10"></u-icon>
						立即投注
					</u-button>
				</view>
			</view>
			<u-gap height="15"></u-gap>
		</view>

	</view>
</template>
<script>
	//import playMethods from "@/components/lo-playmethod/";
	/**
	 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
	 */
	export default {
		data() {
			return {
				gameName: '',
				gameInfo: {},
				gameCode: '',
				playGroup: '五星玩法',
				playMethod: '五星复式',
				value: 0,
				showSelect: false,
				playGroupList: [],
				currentPlayMethod: null,
				showMultiplierList: false,
				multiplierList: [{
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
				multiplier: 1,
				showUnitList: false,
				unitList: [{
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
				unit: {
					value: 1,
					label: '元'
				},
				isShowBetList: true,
				betList: [{
					betCount: 100000,
					betMultiplier: 1000,
					betUnit: {
						value: 1,
						label: '元',
					},
					betAmount: 200000000.00,
				}, {
					betCount: 500,
					betMultiplier: 1,
					betUnit: {
						value: 1,
						label: '元',
					},
					betAmount: 1000.00,
				}],
			};
		},
		computed: {},
		components: {},
		onLoad(param) {
			// 获取游戏信息
			if (param) {
				this.getGameInfo(param.gameCode);
				this.loadComponent(param.gameCode);
				this.gameName = param.gameName;
			}
		},
		onShow() {},
		created() {},
		methods: {
			getGameInfo(gameCode) {
				this.$u.api.lotteryService.getGameInfo({
					gameCode: gameCode
				}).then(res => {
					if (res.success) {
						this.gameInfo = res.data;
						this.$forceUpdate();
					} else {
						this.$u.toast(this.$t('error.notFound'));
					}
				});
			},

			confirmPlayMethod(e) {
				this.currentPlayMethod = resolve => require(['@/components/lo-playmethod/' + e[1].extra + '.vue'],
					resolve);
			},
			showPlayGroupSelect() {
				this.showSelect = true;
			},

			showMultiplierSelecter() {
				this.showMultiplierList = true;
			},
			changeMultiplier(e) {
				this.multiplier = e[0].value;
			},

			showUnitSelecter() {
				this.showUnitList = true;
			},
			changeUnit(e) {
				this.unit = e[0];
			},
			async loadComponent(gameCode) {
				let that = this;
				this.$u.api.lotteryService.findPlayMethodList({
					gameCode: gameCode
				}).then(res => {
					if (res.success) {
						console.info(res.data);
						let newArray = res.data.map(function(item, index) {
							return {
								value: item.id,
								label: item.groupName,
								children: item.playMethodList.map(function(item2, index2) {
									return {
										value: item2.id,
										label: item2.playMethodName,
										extra: item2.playMethodTpl,
									}
								}),
							};
						});
						that.playGroupList = newArray;
						let playMethodtTpl = that.playGroupList[0].children[0].extra;
						that.currentPlayMethod = resolve => require(['@/components/lo-playmethod/' +
								playMethodtTpl + '.vue'
							],
							resolve);
					} else {
						this.$u.toast(this.$t('error.notFound'));
					}
				});

			},

		}
	};
</script>
<style lang="scss">
	@import 'index.scss';

	page {
		background-color: #f8f8f8;
	}

	.bet-list-title {
		background: #fff;
		color: #19be6b;
		font-weight: bold;
		font-size: 32rpx;
	}
</style>