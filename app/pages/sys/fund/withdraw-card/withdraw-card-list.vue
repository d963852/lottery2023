<template>
	<view class="wrap">
		<!-- 余额组件 -->
		<lo-balance :showBtn="true"></lo-balance>

		<!-- 语言切换组件 -->
		<js-lang ref="jslang" :showBtn="true"></js-lang>

		<!-- 顶部提示 -->
		<view>
			<u-alert-tips type="warning" :description="topAlertDescription" :show-icon="true"
				:close-able="true"></u-alert-tips>
		</view>

		<!-- 列表 -->
		<view class="contentList" v-for="(item, index) in list" :key="index"
			@click="navTo('/pages/sys/fund/withdraw-card/withdraw-card-form?id='+item.id)">
			<view class="u-flex u-row-between u-p-20">
				<view>
					<view>
						<text class="text-bold u-p-r-30 u-font-30">{{ item.cardType }}</text>
					</view>
					<view class="u-m-t-10 info" v-if="item.bankName">
						<text class="u-p-r-10">{{ item.bankName }}</text>
					</view>
					<view class="u-m-t-10 info" v-if="item.bankAccountName">
						<text class="u-p-r-10">{{ item.bankAccountName }}</text>
					</view>
					<view class="u-m-t-10 info">
						<text class="u-p-r-10">{{ item.bankAccount }}</text>
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

		<!-- 新增 -->
		<view class="btn-plus" @click="navTo('/pages/sys/fund/withdraw-card/withdraw-card-form')">
			<u-icon name="plus-circle-fill" size="90" color="#fa3534"></u-icon>
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
				topAlertTitle: '寻隐者不遇',
				topAlertDescription: '松下问童子，言师采药去。只在此山中，云深不知处。',
				topAlertShow: true,
			};
		},
		onLoad() {
			this.loadList();
		},
		onShow() {},
		methods: {
			loadMore() {
				this.loadStatus = "loading";
				setTimeout(() => {
					this.query.pageNo += 1;
					this.loadList();
				}, 100);
			},
			async loadList() {
				let res = await this.$u.api.withdrawCardService.findList(this.query);
				if (res.success) {
					console.info(res);
					if (!res.data || res.data.length == 0) {
						this.loadStatus = "nomore";
						return;
					}
					this.list = this.list.concat(res.data);
					this.count = res.data.count;
					this.query.pageNo = res.data.pageNo;
					this.query.pageSize = res.data.pageSize;
					this.loadStatus = "loadmore";
				}
			},
			navTo(url) {
				uni.navigateTo({
					url: url
				});
			},
			form(index) {
				uni.navigateTo({
					url: url
				});
			}

		}
	};
</script>
<style lang="scss">
	page {
		background-color: #f8f8f8;
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
</style>