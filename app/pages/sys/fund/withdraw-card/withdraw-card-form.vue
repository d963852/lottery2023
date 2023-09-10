<template>
	<view class="wrap">
		<u-form class="form" :model="model" :rules="rules" ref="uForm" label-position="left">
			<!-- 卡类型 -->
			<u-form-item label="提款渠道" prop="cardType" label-width="180">
				<u-input v-model="model.cardType" type="select" placeholder="请选择"
					@click="withdrawCardTypeSelecterVisible = true" />
				<u-select v-model="withdrawCardTypeSelecterVisible" mode="single-column" :list="withdrawCardTypeList"
					@confirm="withdrawCardTypeConfirm"></u-select>
			</u-form-item>

			<view v-show="bankVisible">
				<!-- 开户银行 -->
				<u-form-item label="所属银行" prop="bankAccount" label-width="180">
					<u-input v-model="model.bankName" type="select" placeholder="请选择"
						@click="bankNameSelecterVisible = true" />
					<u-select v-model="bankNameSelecterVisible" mode="single-column" :list="bankNameList"
						@confirm="bankNameSeleterConfirm"></u-select>
				</u-form-item>
				<u-form-item label="开户人姓名" prop="bankAccountName" label-width="180">
					<u-input v-model="model.bankAccountName" placeholder="请输入" type="text" :border="false" />
				</u-form-item>
			</view>

			<!-- 账号，根据不同类型显示不同输入组件 -->
			<view>
				<u-form-item :label="bankAccountLabel()" prop="bankAccount" label-width="180">
					<u-input v-model="model.bankAccount" placeholder="请输入" :type="bankAccountType()" :border="false"
						:auto-height="true" />
				</u-form-item>
			</view>

		</u-form>
		<view class="form-footer">
			<u-button class="btn" type="primary" @click="submit" :disabled="btnDisabled">保存</u-button>
			<u-button class="btn" type="warning" @click="del" :disabled="btnDisabled">删除</u-button>
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
				model: {
					id: '',
					cardType: '', // 卡类型（U/支付宝/微信/银行卡/闪付/其他）
					bankName: '', // 开户行名称
					bankAccount: '', // 账号
					bankAccountName: '', // 账户名
					bankAddress: '', // 开户行地址
					subBankName: '', // 支行名称
				},
				rules: {
					bankAccount: [{
						required: true,
						message: '请输入此项',
						trigger: ['blur'],
					}]
				},
				bankVisible: false, // 是否显示银行相关输入组件
				withdrawCardTypeList: [], //提现渠道列表
				withdrawCardTypeSelecterVisible: false,
				bankNameList: [],
				bankNameSelecterVisible: false,
				btnDisabled:false,
			};
		},
		async onLoad(params) {
			//根据id获取提现卡
			if (params.id) {
				this.$u.api.withdrawCardService.get({
					id: params.id
				}).then(res => {
					Object.assign(this.model, res.data);
					if (this.model.cardType == "银行卡") {
						this.bankVisible = true;
					}

				});
			};

			// 获取提现卡类型列表
			let withdrawCardTypeRes = await this.$u.api.dictService.findList({
				dictType: 'lottery_withdraw_card_type',
			});
			// console.info('lottery_withdraw_card_type', withdrawCardTypeRes);
			if (withdrawCardTypeRes.success) {
				this.withdrawCardTypeList = withdrawCardTypeRes.data;
				if (!this.model.cardType) {
					this.model.cardType = this.withdrawCardTypeList[0].value;
				}
			};

			// 获取银行列表
			let bankNameRes = await this.$u.api.dictService.findList({
				dictType: 'lottery_sys_bank_list',
			});
			// console.info('lottery_sys_bank_list', bankNameRes);
			if (bankNameRes.success) {
				this.bankNameList = bankNameRes.data;
			};
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			bankAccountLabel() {
				if (this.model.cardType == "USDT-TRC20") {
					return "USDT-TRC20地址";
				} else if (this.model.cardType == "银行卡") {
					return "卡号";
				} else {
					return "账号";
				}
			},
			bankAccountType() {
				if (this.model.cardType == "USDT-TRC20") {
					return "textarea";
				} else {
					return "text";
				}
			},
			withdrawCardTypeConfirm(e) {
				let cardType = e[0].label;
				this.model.cardType = cardType;
				if (cardType == "银行卡") {
					this.bankVisible = true;
				} else {
					this.bankVisible = false;
				}
			},
			bankNameSeleterConfirm(e) {
				let bankName = e[0].label;
				this.model.bankName = bankName;
			},
			submit() {
				this.btnDisabled = true;
				this.$refs.uForm.validate(valid => {
					if (valid) {
						this.$u.api.withdrawCardService.save(this.model).then(res => {
							if (res.success) {
								this.$u.toast(this.$t('msg.actionSuccess'), 2500);
								this.navTo('/pages/sys/fund/withdraw-card/withdraw-card-list');
							} else {
								this.$u.toast(res.message);
							}
						});
					} else {
						this.$u.toast('您填写的信息有误，请根据提示修正。');
					}
				});
				this.btnDisabled = false;
			},
			async del() {
				this.btnDisabled = true;
				let res = await this.$u.api.withdrawCardService.delete({
					id: this.model.id
				});
				if (res.success) {
					this.$u.toast(this.$t('msg.actionSuccess'), 2500);
					this.navTo('/pages/sys/fund/withdraw-card/withdraw-card-list');
				} else {
					this.$u.toast(res.message);
				}
				this.btnDisabled = false;
			},
			navTo(url) {
				uni.navigateTo({
					url: url
				});
			}
		}
	};
</script>
<style lang="scss">

</style>