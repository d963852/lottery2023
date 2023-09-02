<template>
	<u-sticky>
		<view class="u-flex u-row-right u-text-right">
			<u-tag class="u-margin-left-10 lo-balance" :text="calcBalance" type="error" shape="circle"/>
		</view>
	</u-sticky>
</template>
<script>
	/**
	 * 余额组件
	 * @property {String} title 顶部导航的标题 i18n 编码
	 * @property {Boolean} showBtn 是否显示语言切换按钮
	 * @example <js-lang title="login.title" :showBtn="true"></js-lang>
	 * @description Copyright (c) 2013-Now http://jeesite.com All rights reserved.
	 * @author ThinkGem
	 * @version 2021-3-11
	 */
	export default {
		data() {
			return {
				balance: 0.00,
				timer: null,
			}
		},
		props: {},
		computed: {
			calcBalance() {
				return '¥ ' + this.balance;
			}
		},
		created() {
			this.getBalance();
			// 每30秒取一次余额
			this.timer = setInterval(() => {
				this.getBalance();
			}, 30 * 1000);
		},
		methods: {
			//获取 用户余额
			async getBalance() {
				let res = await this.$u.api.memberService.getBalance(this.vuex_user.id);
				// console.info(res);
				if (res.success) {
					this.balance = res.data;
				} else {
					this.$u.toast(this.$t('error.serverDisconnected'));
				}
			},
		},
		beforeDestroy() {
			clearInterval(this.timer);
			this.timer = null;
		},
	}
</script>
<style lang="scss" scoped>
	.lo-balance {
		position: absolute;
		z-index: 10000;
		top: -68rpx;
		right: 90rpx;
	}
</style>