<template>
	<view class="wrap">
		<js-lang title="msg.title"></js-lang>
		<u-modal v-model="showTheArticle" :title="theArticle.title" :title-style="{color: 'red'}" width="80%"
			:mask-close-able="true">
			<view class="slot-content">
				<u-line color="red" length="97%" margin="15rpx" />
				<view class="u-text-center view u-font-10">发布时间：{{theArticle.pubDate}}</view>
				<u-parse className="article-content" :content="theArticle.content"></u-parse>
			</view>
		</u-modal>
		<view class="item" v-for="(item, index) in noticeList" :key="item.id">
			<u-card :title="item.title" :subTitle="item.pubDate" :index="item.id" @click="showArticle">
				<view class="" slot="body">
					<view slot="">
						{{item.des}}
					</view>
				</view>
			</u-card>
		</view>
	</view>
</template>
<script>
	import uParse from '@/components/u-parse/u-parse.vue';
	/**
	 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
	 */
	export default {
		components: {
			uParse
		},
		data() {
			return {
				showTheArticle: false,
				noticeList: [{
					id: 'a-3',
					title: '【紧急通知】DNS被劫持处理方案！！',
					pubDate: '2099-09-09',
					des: '近期有不明网站使用非法手段劫持本平台的域名，本平台不会和其他平台合并，请大家不要向任何平台提供您的账号和密码，平台也不会向您索要您的账号密码。',
				}, ],
				theArticle: {
					id: 'a-3',
					title: '【紧急通知】DNS被劫持处理方案！！',
					pubDate: '2099-09-09',
					content: '',
				},
			};
		},
		onLoad() {
			this.getNoticeList();
		},
		methods: {
			navTo(url) {
				uni.navigateTo({
					url: url
				});
			},
			getNoticeList() {
				// 获取通知，最多显示10条
				let that = this;
				this.$u.api.aricleService.getNoticeList().then(res => {
					console.info(res);
					if (res.success) {
						let newArray = res.data.map(function(item, index) {
							return {
								id: item.id,
								title: item.title,
								des: item.des,
								pubDate: item.pubDate
							};
						});
						that.noticeList = newArray;
					}
				});
			},
			showArticle(index) {
				// 获取文章详情
				let that = this;
				this.$u.api.aricleService.getArticle({}, index).then(res => {
					console.info(res);
					if (res.success) {
						that.theArticle = res.data;
						that.showTheArticle = true;
					} else {
						that.$u.toast(that.$t('error.notFound'));
					}
				});
			},

		}
	};
</script>
<style lang="scss">
	page {
		background-color: #f8f8f8;
	}

	.article-content {
		padding: 30rpx;
	}
</style>