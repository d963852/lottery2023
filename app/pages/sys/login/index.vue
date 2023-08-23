<template>
	<view class="wrap">
		<!-- <js-lang title="login.title" :showBtn="true"></js-lang> -->
		<view class="logo">
			<image src="/static/lo/login/logo.png"></image>
		</view>
		<view class="list">
			<view class="list-call">
				<u-icon class="u-icon" size="40" name="account"></u-icon>
				<input class="u-input" type="text" v-model="username" maxlength="32"
					:placeholder="$t('login.placeholderAccount')" />
				<u-checkbox v-model="remember" active-color="#69cbff">{{$t('login.autoLogin')}}</u-checkbox>
			</view>
			<view class="list-call">
				<u-icon class="u-icon" size="40" name="lock"></u-icon>
				<input class="u-input" type="text" v-model="password" maxlength="32"
					:placeholder="$t('login.placeholderPassword')" :password="!showPassword" />
				<image class="u-icon-right"
					:src="'/static/jeesite/login/eye_' + (showPassword ? 'open' : 'close') + '.png'"
					@click="showPass()"></image>
			</view>
			<view class="list-call" v-if="isValidCodeLogin">
				<u-icon class="u-icon" size="40" name="coupon"></u-icon>
				<input class="u-input" type="text" v-model="validCode" maxlength="4"
				 :placeholder="$t('login.placeholderValidCode')" />
				<u-image class="img-valid-code" width="200rpx" height="90rpx" :src="imgValidCodeSrc"
					@click="refreshImgValidCode()"></u-image>
			</view>
		</view>
		<view class="button" hover-class="button-hover" @click="submit()"><text>{{$t('login.loginButton')}}</text>
		</view>
	</view>
</template>
<script>
	/**
	 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
	 */
	import base64 from '@/common/base64.js';
	export default {
		data() {
			return {
				username: 'hy802',
				password: '123456',
				showPassword: false,
				remember: true,
				isValidCodeLogin: true,
				validCode: '',
				imgValidCodeSrc: null,
				baseUrl: ''
			};
		},
		onLoad() {
			this.refreshImgValidCode(null);
		},
		methods: {
			showPass() {
				this.showPassword = !this.showPassword;
			},
			refreshImgValidCode(e) {
				if (this.vuex_token == '') {
					this.$u.api.index().then(res => {
						this.imgValidCodeSrc = this.vuex_config.baseUrl + '/validCode?__sid=' +
							res.sessionid + '&t=' + new Date().getTime();
					});
				} else {
					this.imgValidCodeSrc = this.vuex_config.baseUrl + '/validCode?__sid=' +
						this.vuex_token + '&t=' + new Date().getTime();
				}
				this.validCode = '';
			},
			submit() {
				if (this.username.length == 0) {
					this.$u.toast('请输入账号');
					return;
				}
				if (this.password.length == 0) {
					this.$u.toast('请输入密码');
					return;
				}
				this.$u.api.login({
						username: base64.btoa(this.username),
						password: base64.btoa(this.password),
						validCode: base64.btoa(this.validCode),
						param_deviceType: 'mobileApp',
						param_remember: this.remember
					})
					.then(res => {
						this.$u.toast(res.message || '未连接服务器');
						if (res.result == 'true') {
							setTimeout(() => {
								uni.reLaunch({
									url: '/pages/lottery/index'
								});
							}, 500);
						}
						if (res.isValidCodeLogin) {
							this.isValidCodeLogin = true;
							this.refreshImgValidCode();
						}
					});
			},
		}
	};
</script>
<style lang="scss">
	@import 'index.scss';

	.logo {
		width: 260rpx;
		height: 260rpx;
		background: rgba(59, 121, 235, 1);
		box-shadow: 0rpx 5rpx 20rpx 5rpx rgba(45, 127, 235, 0.5);
		border-radius: 50%;
		margin: -50% auto -80rpx auto;
	}

	.logo image {
		width: 260rpx;
		height: 260rpx;
		border-radius: 50%;
	}

	.base-url js-select {
		width: 100%;
	}

	.button {
		margin: 30rpx auto 0;
	}

	.footer {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		text-align: center;
		color: #46afff;
		height: 40rpx;
		line-height: 40rpx;
		font-size: 35rpx;
		margin-top: 60rpx;
	}

	.footer text {
		font-size: 30rpx;
		margin-left: 25rpx;
		margin-right: 25rpx;
	}

	.oauth2 {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		margin: 55rpx 100rpx;

		image {
			height: 100rpx;
			width: 100rpx;
		}
	}

</style>