<template>
	<view>
		<!-- 位选 -->
		<view>
			<view v-for="(item, index) in panelData" :key="item.id">
				<view class="u-flex u-p-20" style="background: #fff;">
					<view class="u-flex-4">
						<text class="u-font-18 u-p-l-20 u-main-color red-bold">{{ item.title }}</text>
					</view>
					<view class=" u-flex u-row-between u-flex-nowrap u-flex-8 u-p-r-20">
						<view @click.stop="quickSelectNumber(item.id,'quan')">全</view>
						<view @click.stop="quickSelectNumber(item.id,'da')">大</view>
						<view @click.stop="quickSelectNumber(item.id,'xiao')">小</view>
						<view @click.stop="quickSelectNumber(item.id,'dan')">单</view>
						<view @click.stop="quickSelectNumber(item.id,'shuang')">双</view>
						<view @click.stop="quickSelectNumber(item.id,'qing')">清</view>
					</view>
				</view>
				<u-gap height="5"></u-gap>
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
						id: "p05",
						title: '万位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "p04",
						title: '千位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "p03",
						title: '百位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "p02",
						title: '十位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
					{
						id: "p01",
						title: '个位',
						numberRowOne: [0, 1, 2, 3, 4],
						numberRowTow: [5, 6, 7, 8, 9],
					},
				],
				selectedNumber: {
					p05: [],
					p04: [],
					p03: [],
					p02: [],
					p01: [],
				},
			};
		},
		computed: {},
		watch: {
			selectedNumber: {
				handler: function(newVal, oldVal) {
					// 监听selectedNumber，如果全部选择完毕，回传给父组件
					for (let key in newVal) {
						if (newVal[key].length < 1) {
							this.$emit('monitorBetNumber', {
								error: "请选择5位数字",
							});
							return;
						}
					}

					// 计算投注注数
					let betCount = 1;
					for (let key in newVal) {
						betCount *= newVal[key].length;
					}

					// 将选择结果反馈到上级组件
					// 选中号码拼接为 x,x,x|x,x,x|…… 格式
					let arr = Object.values(newVal).join("|");

					this.$emit('monitorBetNumber', {
						success: true,
						betNumber: arr,
						betCount: betCount,
					});
				},
				deep: true,
			}
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