<template>
	<view class="wrap">
		<!-- 余额组件 -->
		<lo-balance :showBtn="true"></lo-balance>

		<!-- 语言切换组件 -->
		<js-lang ref="jslang" :showBtn="true"></js-lang>

		<view class="header">
			<view class="userinfo">
				<view class="image" @click="navTo('info')">
					<image :src="avatarUrl"></image>
				</view>
				<view class="info">
					<view class="username">{{ vuex_user.userName || $t('login.noLogin') }}</view>
				</view>
			</view>
			<view class="logout"><u-button type="error" shape="circle" size="mini"
					@click="logout">{{$t('login.logoutButton')}}</u-button></view>
		</view>


		<view class="u-p-t-10 u-p-b-20">
			<!-- 团队管理 -->
			<view class="u-m-t-20">
				<u-cell-group>
					
				</u-cell-group>
			</view>

			<!-- 个人数据 -->
			<view class="u-m-t-20">
				<u-cell-group>
					<u-cell-item icon="kefu-ermai" :iconSize="iconSize" :iconStyle="{ color: '#a571fd' }" title="投注记录"
						@click="navTo('comment')"></u-cell-item>
					<u-cell-item icon="clock" :iconSize="iconSize" :iconStyle="{ color: '#ff6f27' }" title="账变记录"
						@click="upgrade()"></u-cell-item>
					<u-cell-item icon="clock" :iconSize="iconSize" :iconStyle="{ color: '#ff6f27' }" title="盈亏报表"
						@click="upgrade()"></u-cell-item>
					<u-cell-item icon="clock" :iconSize="iconSize" :iconStyle="{ color: '#ff6f27' }" title="充值记录"
						@click="upgrade()"></u-cell-item>
					<u-cell-item icon="clock" :iconSize="iconSize" :iconStyle="{ color: '#ff6f27' }" title="提现记录"
						@click="upgrade()"></u-cell-item>
				</u-cell-group>
			</view>

			<!-- 个人管理 -->
			<view class="u-m-t-20">
				<u-cell-group>
					<u-cell-item icon="lock" :iconSize="iconSize" :iconStyle="{ color: '#1bca6a' }" title="登录密码"
						@click="navTo('pwd')"></u-cell-item>
					<u-cell-item icon="lock" :iconSize="iconSize" :iconStyle="{ color: '#1bca6a' }" title="资金密码"
						@click="navTo('pwd')"></u-cell-item>
					<u-cell-item icon="lock" :iconSize="iconSize" :iconStyle="{ color: '#1bca6a' }" title="提款信息"
						@click="navTo('/pages/sys/fund/withdraw-card/withdraw-card-list')"></u-cell-item>
					<u-cell-item icon="lock" :iconSize="iconSize" :iconStyle="{ color: '#1bca6a' }" title="个人信息"
						@click="navTo('pwd')"></u-cell-item>
					<u-cell-item icon="lock" :iconSize="iconSize" :iconStyle="{ color: '#1bca6a' }" title="系统公告"
						@click="navTo('pwd')"></u-cell-item>
				</u-cell-group>
			</view>
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
				iconSize: 38
			};
		},
		computed: {
			avatarUrl() {
				let url = this.vuex_user.avatarUrl || '/ctxPath/static/images/user1.jpg';
				url = url.replace('/ctxPath/', this.vuex_config.baseUrl + '/');
				return url + '?t=' + new Date().getTime();
			}
		},
		methods: {
			navTo(url) {
				uni.navigateTo({
					url: url
				});
			},
			logout() {
				this.$u.api.logout().then(res => {
					this.$u.toast(res.message);
					if (res.result == 'true') {
						let self = this;
						setTimeout(() => {
							uni.reLaunch({
								url: '/pages/sys/login/index'
							});
						}, 500);
					}
				});
			},
			upgrade() {
				// #ifdef APP-PLUS
				this.$u.api.upgradeCheck().then(res => {
					if (res.result == 'true') {
						uni.showModal({
							title: '提示',
							content: res.message + '是否下载更新？',
							showCancel: true,
							success: function(res2) {
								if (res2.confirm) {
									plus.runtime.openURL(res.data.apkUrl);
								}
							}
						});
					} else {
						this.$u.toast(res.message);
					}
				});
				// #endif
				// #ifndef APP-PLUS
				this.$u.toast('小程序端或H5端无需检查更新')
				// #endif
			}
		}
	};
</script>
<style lang="scss">
	@import 'index.scss';

	page {
		background-color: #f8f8f8;
	}
</style>