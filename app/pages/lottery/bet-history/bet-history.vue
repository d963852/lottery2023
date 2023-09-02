<template>
	<view class="wrap">
		<!-- 余额组件 -->
		<lo-balance :showBtn="true"></lo-balance>

		<!-- 语言切换组件 -->
		<js-lang ref="jslang" :showBtn="true"></js-lang>

		<!-- 搜索-->
		<view class="search">
			<u-search v-model="keywords" @custom="search" @search="search"></u-search>
		</view>

		<!-- 列表 -->
		<view class="contentList" v-for="(item, index) in list" :key="index" @click="showDetail(index)">
			<view class="u-flex u-row-between u-p-20" :class="getStatusCss(item.bizStatus)">
				<view>
					<view>
						<text class="text-bold u-p-r-30 u-font-30">{{ item.gameName }}</text>
						<text class="u-p-r-20">第 {{ item.issue }} 期</text>
					</view>
					<view class="u-m-t-10 info">
						<text class="u-p-r-20">{{ item.betTime }}</text>
						<text class="u-p-r-20">/</text>
						<text class="u-p-r-20">{{ item.totalBetCount }} 注</text>
						<text class="u-p-r-20">/</text>
						<text class="u-p-r-10">返点 {{ item.rebate }}%</text>

					</view>
					<view class="u-m-t-10">
						<text class="u-p-r-20">{{ item.bizStatus }}</text>
						<text class="u-p-r-20" v-if="item.winAmount>=0">/</text>
						<text class="u-p-r-20" v-if="item.winAmount>=0">奖金 {{ item.winAmount }} 元</text>
					</view>
				</view>
				<view>
					<u-icon name="arrow-right"></u-icon>
				</view>
			</view>
			<u-line color="#aaa" border-style="dashed" margin="10rpx 5rpx" />
		</view>

		<!-- 加载更多 -->
		<view class="loadmore u-m-t-30" @click="loadMore">
			<u-loadmore :status="loadStatus"></u-loadmore>
		</view>

		<!-- 投注详情 -->
		<view>
			<u-popup v-model="detailVisible" :closeable=true mode="bottom" length="70%">
				<view class="u-p-t-60 u-p-30 u-font-33">
					<view class="u-text-center red-bold">
						投注详情
					</view>
					<u-line margin="20rpx" />
					<view>
						游戏：
						<text class="red-bold u-p-l-10 u-p-r-10">
							{{ detailInfo.gameName }}
						</text>
					</view>
					<u-line margin="20rpx" border-style="dashed"></u-line>
					<view>
						玩法：
						<text class="red-bold u-p-l-10 u-p-r-10">
							{{ detailInfo.playMethodGroup }} - {{ detailInfo.playMethod }}
						</text>
					</view>
					<u-line margin="20rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							奖金
							<text class="red-bold u-p-l-10 u-p-r-10">{{ detailInfo.bonusAmount }}</text>元
						</view>
						<view class="u-m-l-10 u-m-r-10">
							/
						</view>
						<view>
							返点
							<text class="red-bold u-p-l-10 u-p-r-10">{{ detailInfo.rebate }} %</text>
						</view>
					</view>
					<u-line margin="20rpx" border-style="dashed"></u-line>
					<view>
						投注号
					</view>
					<view>
						<view v-for="(item, index) in detailInfo.betNumberObj" :key="index">
							左第{{ index+1 }}位 <text class="red-bold u-p-l-10 u-p-r-10">{{ item.join(",") }}</text>
						</view>
					</view>
					<u-line margin="20rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							倍数
							<text class="red-bold u-p-l-10 u-p-r-10">{{ detailInfo.betMultiple }}</text> 倍
						</view>
						<view class="u-m-l-10 u-m-r-10">
							/
						</view>
						<view>
							总注数
							<text class="red-bold u-p-l-10 u-p-r-10">{{ detailInfo.totalBetCount }} </text> 注
						</view>
					</view>
					<u-line margin="20rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							货币单位
							<text class="red-bold u-p-l-10 u-p-r-10">{{ detailInfo.betUnit }}</text>
						</view>
					</view>
					<u-line margin="20rpx" border-style="dashed"></u-line>
					<view class="u-flex u-row-left" style="background: #fff;">
						<view>
							投注金额
							<text class="red-bold u-p-l-10 u-p-r-10">{{ detailInfo.betAmount }} </text> 元
						</view>
					</view>
					<view v-if="canCancleOrder(detailInfo.bizStatus)">
						<u-line margin="20rpx" border-style="dashed"></u-line>
						<u-button type="warning u-font-40" @click="cancelOrder(detailInfo.id)">
							<u-icon name="rmb-circle" class="u-m-r-10"></u-icon>
							{{ $t('撤单') }}
						</u-button>
					</view>
				</view>
			</u-popup>
		</view>

		<view class="bottom-space">
		</view>
	</view>
</template>
<script>
	/**
	 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
	 */
	export default {
		data() {
			return {
				keywords: '',
				query: {
					pageNo: 1,
					pageSize: 20
				},
				list: [],
				count: 0,
				loadStatus: 'loadmore',
				options: [{
					text: '删除',
					style: {
						background: '#dd524d'
					}
				}],
				detailVisible: false,
				detailInfo: {
					bizStatus: '',
				},
			};
		},
		onLoad() {
			this.loadList();
		},
		onShow() {
			if (uni.getStorageSync('refreshList') === true) {
				uni.removeStorageSync('refreshList');
				this.search('');
			}
		},
		computed: {

		},
		methods: {
			loadMore() {
				this.loadStatus = "loading";
				setTimeout(() => {
					this.query.pageNo += 1;
					this.loadList();
				}, 100);
			},
			async loadList() {
				let res = await this.$u.api.lotteryService.findBetHistoyList(this.query);
				if (res.success) {
					if (!res.data.list || res.data.list.length == 0) {
						this.loadStatus = "nomore";
						return;
					}
					this.list = this.list.concat(res.data.list);
					this.count = res.data.count;
					this.query.pageNo = res.data.pageNo;
					this.query.pageSize = res.data.pageSize;
					this.loadStatus = "loadmore";
				}
			},
			search(value) {
				this.list = [];
				this.query.pageNo = 0;
				this.query.testInput = value;
				this.loadList();
			},
			navTo(url) {
				uni.navigateTo({
					url: url
				});
			},
			getStatusCss(e) {
				if (e == "未开奖") {
					return "betting";
				}
				if (e == "已派奖") {
					return "win";
				}
				if (e == "未中奖") {
					return "notWin";
				}
				if (e == "已撤单") {
					return "canceled";
				}
				return "";
			},
			showDetail(index) {
				this.detailInfo = this.list[index];
				let betNumber = this.list[index].betNumber;
				let betNumberList = betNumber.split("|").map(function(it) {
					return it.split(',');
				});

				this.detailInfo.betNumberObj = betNumberList;
				this.detailVisible = true;
			},
			canCancleOrder(bizStatus) {
				if (bizStatus == "未开奖") {
					return true;
				}
				return false;
			},
			// 撤单
			async cancelOrder(orderId) {
				console.info('orderId', orderId);
				let res = await this.$u.api.lotteryService.cancelBetOrder({
					orderId: orderId
				});
				if (res.success) {
					this.$u.toast(this.$t('msg.cancelBetOrderOk'));
				} else {
					this.$u.toast(this.$t('msg.cancelBetOrderError') + res.message);
					return;
				}
			},

		}
	};
</script>
<style lang="scss">
	page {
		background-color: #fff;
	}

	.btn-plus {
		position: absolute;
		bottom: 50rpx;
		right: 50rpx;
		z-index: 1;
		opacity: 0.6;
	}

	.btn-plus:hover {
		opacity: 1;
	}


	.info {
		color: #888;
	}

	.betting {
		background-color: $u-type-primary-light;
	}

	.win {
		background-color: $u-type-error-light;
	}

	.notWin {
		background-color: #fff;
	}

	.canceled {
		background-color: #EEEEEE;
		color: #AAAAAA;
	}
</style>