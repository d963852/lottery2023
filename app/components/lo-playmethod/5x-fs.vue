<template>
	<view>
		<!-- 位选 -->
		<view>
			<view v-for="(item, index) in panelData" :key="item.id">
				<view class="u-flex u-p-20" style="background: #fff;">
					<view class="u-flex-4">
						<text class="u-font-18 u-p-l-20 u-main-color red-bold">{{ item.title }}</text>
					</view>
					<view class=" u-flex u-row-between u-flex-nowrap u-flex-8">
						<view @click.stop="quickSelectNumber(item.id,'quan')">全</view>
						<view @click.stop="quickSelectNumber(item.id,'da')">大</view>
						<view @click.stop="quickSelectNumber(item.id,'xiao')">小</view>
						<view @click.stop="quickSelectNumber(item.id,'dan')">单</view>
						<view @click.stop="quickSelectNumber(item.id,'shuang')">双</view>
						<view @click.stop="quickSelectNumber(item.id,'qing')">清</view>
					</view>
				</view>
				<u-gap height="3"></u-gap>
				<view class="u-p-20" style="background: #fff;">
					<view class="u-flex u-row-around u-flex-nowrap u-m-b-25">
						<view class="select-number" v-for="(number, index) in item.numberRowOne"
							@click.stop="selectNumber(item.id,number)" :key="item.id+number"
							:class="{ 'select-number-active': isSelected(item.id,number) }">
							{{ number }}
						</view>
					</view>
					<view class="u-flex u-row-around u-flex-nowrap">
						<view class="select-number" v-for="(number, index) in item.numberRowTow"
							@click.stop="selectNumber(item.id,number)" :key="item.id+number"
							:class="{ 'select-number-active': isSelected(item.id,number) }">
							{{ number }}
						</view>
					</view>
				</view>
				<u-gap height="15"></u-gap>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				panelData: [{
						id: "wan",
						title: '万位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "qian",
						title: '千位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "bai",
						title: '百位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "shi",
						title: '十位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "ge",
						title: '个位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
				],
				selectedNumber: {
					wan: [],
					qian: [],
					bai: [],
					shi: [],
					ge: [],
				},
			};
		},
		methods: {
			selectNumber(position, number) {
				// 选中/反选单个数字
				let numberIndex = this.selectedNumber[position].indexOf(number);
				if (numberIndex == -1) {
					this.selectedNumber[position].push(number);
				} else {
					this.selectedNumber[position].splice(numberIndex, 1);
				}
			},
			quickSelectNumber(position, action) {
				// 选中指定类型的数字
				// 全
				if (action == "quan") {
					this.selectedNumber[position] = this.vuex_config.selectNumber10.quan;
				} else if (action == "da") {
					this.selectedNumber[position] = this.vuex_config.selectNumber10.da;
				} else if (action == "xiao") {
					this.selectedNumber[position] = this.vuex_config.selectNumber10.xiao;
				} else if (action == "dan") {
					this.selectedNumber[position] = this.vuex_config.selectNumber10.dan;
				} else if (action == "shuang") {
					this.selectedNumber[position] = this.vuex_config.selectNumber10.shuang;
				} else if (action == "qing") {
					this.selectedNumber[position] = this.vuex_config.selectNumber10.qing;
				}
				console.info(this.selectedNumber);
			},
			isSelected(position, number) {
				if (this.selectedNumber[position].indexOf(number) != -1) {
					return true;
				}
				return false;
			}
		}
	};
</script>

<style>
	.font-weight-bold {
		font-weight: bold;

	}

	.select-number {
		font-size: 55rpx;
		text-align: center;
		-moz-border-radius: 120px;
		border-radius: 120px;
		padding: 0rpx 22rpx;
		color: #555;
		border: 1px solid #888;
		background-color: #eee;
	}

	.select-number-active {
		background-color: #FF9999;
	}
</style>