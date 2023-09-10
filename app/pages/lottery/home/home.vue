<template>
	<view class="wrap">
		<!-- 余额组件 -->
		<lo-balance :showBtn="true"></lo-balance>

		<!-- 语言切换组件 -->
		<js-lang ref="jslang" :showBtn="true"></js-lang>

		<!-- 轮播图 -->
		<u-swiper :height="300" :list="imgList" :title="false" @click="imgListClick"></u-swiper>

		<!-- 功能栏 -->
		<u-grid :col="4">
			<u-grid-item>
				<u-icon name="red-packet" :style="{ color: '#CC3333' }" :size="60"></u-icon>
				<view class="grid-text">{{ $t('lottery.actionList.recharge') }}</view>
			</u-grid-item>
			<u-grid-item @click="navTo('/pages/lottery/bet-history/bet-history')">
				<u-icon name="clock" :style="{ color: '#339933' }" :size="60"></u-icon>
				<view class="grid-text">{{ $t('lottery.actionList.bettinghistory') }}</view>
			</u-grid-item>
			<u-grid-item @click="navTo('/pages/sys/fund/log/fund-log')">
				<u-icon name="list-dot" :style="{ color: '#CC6633' }" :size="60"></u-icon>
				<view class="grid-text">{{ $t('lottery.actionList.accountDetails') }}</view>
			</u-grid-item>
			<u-grid-item>
				<u-icon name="server-man" :style="{ color: '#3366CC' }" :size="60"></u-icon>
				<view class="grid-text">{{ $t('lottery.actionList.customerService') }}</view>
			</u-grid-item>
		</u-grid>
		<u-gap height="5" bg-color="#eee"></u-gap>
		<!-- 通知栏 -->
		<u-notice-bar :more-icon="true" type="error" mode="vertical" :list="noticeList"
			@click="noticeListClick"></u-notice-bar>
		<u-gap height="5" bg-color="#eee"></u-gap>

		<!-- 喜报 -->
		<!-- <view class="gameInfoGroup">
			<view class="image" @click="navTo('info')">
				<image src="@/static/common/img/happyNotice.png"></image>
			</view>
			<view class="info">
				<u-notice-bar :more-icon="true" type="none" mode="vertical" :list="noticeList"
					@click="noticeListClick"></u-notice-bar>
			</view>
		</view> -->



		<!-- 热门游戏列表 -->
		<!-- <u-card :title="hotGameList.menuName" :index="hotGameList.menuCode" full="true">
			<view slot="body">
				<u-row gutter="16">
					<u-col span="6" v-for="(item, index) in hotGameListData" :key="item.menuCode">
						<view class="game">
							<view class="gameInfoGroup">
								<view class="image" @click="navTo('info')">
									<image :src="item.menuIcon"></image>
								</view>
								<view class="info">
									<view class="gameName">{{ item.menuName }}</view>
									<view class="gameDes">
										<u-icon name="volume-fill" style="margin-right: 10rpx;"></u-icon>
										{{ item.menuDes }}
									</view>
								</view>
							</view>
						</view>
					</u-col>
				</u-row>
			</view>
		</u-card> -->
		<u-section :title="$t('lottery.hotGameListTitle')" :sub-title="$t('lottery.allGames')" font-size="30"
			class="u-p-20"></u-section>
		<u-grid :col="3">
			<u-grid-item v-for="(item, index) in hotGameList" :key="item.menuCode"
				@click="navToBet(item.menuCode,item.menuName)">
				<view class="gameIcon">
					<image :src="item.menuIcon"></image>
				</view>
				<view class="gameName">{{ item.menuName }}</view>
				<view class="gameDes">{{ item.menuDes }}</view>
			</u-grid-item>
		</u-grid>
	</view>
</template>
<script>
	/**
	 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
	 */
	export default {
		data() {
			return {

				imgList: [{
						image: '/static/common/img/banner/01.svg',
						newsUrl: ''
					},
					{
						image: '/static/common/img/banner/02.svg',
						newsUrl: ''
					},
					{
						image: '/static/common/img/banner/03.svg',
						newsUrl: ''
					}
				],

				noticeList: [
					this.$t("lottery.noticeListDefault")
				],

				todoCount: 0,

				hotGameList: [{
					menuCode: 'loading01',
					menuName: this.$t('common.loading'),
					menuIcon: 'star-fill',
					menuColor: '#919328',
					url: '',
				}, {
					menuCode: 'loading02',
					menuName: this.$t('common.loading'),
					menuIcon: 'star-fill',
					menuColor: '#919328',
					url: '',
				}, {
					menuCode: 'loading03',
					menuName: this.$t('common.loading'),
					menuIcon: 'star-fill',
					menuColor: '#919328',
					url: '',
				}],
			};
		},
		computed: {},
		onLoad() {
			//this.refreshCount();
			this.getIndexPictureList();
			this.getNoticeTitleList();
			this.findHotGameList();
		},
		onShow() {
			this.refreshCount();
		},
		methods: {
			navTo(url) {
				uni.navigateTo({
					url: url
				});
			},
			navToBet(gameCode, gameName) {
				uni.navigateTo({
					url: '/pages/lottery/bet/bet?gameCode=' + gameCode + '&gameName=' + gameName,
				});
			},
			refreshCount() {
				this.todoCount = 3;
			},
			imgListClick(index) {
				console.log(this.imgList[index].newsUrl);
			},
			itemClick(index) {
				console.log(index);
			},
			noticeListClick(index) {
				this.navTo('/pages/sys/msg/index')
			},
			getIndexPictureList() {
				//获取轮换图新闻图片和id
				let that = this;
				this.$u.api.aricleService.getIndexPictureList().then(res => {
					if (res.success) {
						let newArray = res.data.map(function(item, index) {
							return {
								image: that.vuex_config.baseUrl + item.imgUrl,
								newsUrl: item.id
							};
						});
						that.imgList = newArray;
					}
				});
			},
			getNoticeTitleList() {
				// 获取最新5条通知
				let that = this;
				this.$u.api.aricleService.getNoticeTitleList().then(res => {
					if (res.success) {
						let newArray = res.data.map(function(item, index) {
							return item.title;
						});
						that.noticeList = newArray;
					}
				});
			},
			async findHotGameList() {
				let that = this;
				// 最热游戏推荐
				let res = await this.$u.api.gameService.findListByType({
					gameType: 'hot'
				});
				if (res.success) {
					// console.info(res.data);
					let newArray = res.data.map(function(item, index) {
						return {
							menuCode: item.gameCode,
							menuName: item.gameName,
							menuIcon: that.vuex_config.baseUrl + item.imgUrl,
							menuColor: '#919328',
							menuDes: item.gameDesc,
							url: '#',
						};
					});
					this.hotGameList = newArray;
				} else {
					this.$u.toast(this.$t('error.notFound'));
				}
			},


		}
	};
</script>
<style lang="scss">
	@import 'index.scss';

	page {
		background-color: #ffffff;
	}

	.grid-text {
		// font-weight: bold;
	}

	u-grid-item {
		color: red;
	}
</style>