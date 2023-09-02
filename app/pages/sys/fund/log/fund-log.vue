<template>
	<view class="wrap">
		<!-- 余额组件 -->
		<lo-balance :showBtn="true"></lo-balance>

		<!-- 语言切换组件 -->
		<js-lang ref="jslang" :showBtn="true"></js-lang>

		<!-- 列表部分 -->
		<!-- <view class="search">
			<u-search v-model="keywords" @custom="search" @search="search"></u-search>
		</view> -->
		<view class="u-p-20">
			<view class="contentList" v-for="(item, index) in list" :key="index">
				<view class="u-flex u-row-between u-p-20" :class="getStatusCss(item.changeAmount)">
					<view>
						<view>
							<text class="text-bold u-p-r-30 u-font-30">{{ item.changeType }}</text>
							<text class="u-p-r-20">/</text>
							<text class="u-p-r-20">金额 {{ item.changeAmount }} 元</text>
						</view>
						<view class="u-m-t-10 info">
							<text class="u-p-r-20">{{ item.changeTime }}</text>
							<text class="u-p-r-20">/</text>
							<text class="u-p-r-20">余额 {{ item.balance }} 元</text>
						</view>
						<view class="u-m-t-10 info">
							<text class="u-p-r-10">{{ item.remarks }}</text>
						</view>
					</view>
					<view>
						<u-icon name="arrow-right"></u-icon>
					</view>
				</view>
				<u-line color="#aaa" border-style="dashed" margin="10rpx 5rpx" />
			</view>
		</view>

		<view class="loadmore u-m-t-30" @click="loadMore">
			<u-loadmore :status="loadStatus"></u-loadmore>
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
				navbarTitle: '',
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
				let res = await this.$u.api.memberService.findFundLogList(this.query);
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
				} else {}
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
				if (e > 0) {
					return "in";
				} else {
					return "out";
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

	.in {
		background-color: $u-type-success-light;
	}

	.out {
		background-color: $u-type-error-light;
	}
</style>