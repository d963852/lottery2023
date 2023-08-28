/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作
const install = (Vue, vm) => {

	// 参数配置对象
	const config = vm.vuex_config;

	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.api = {

		// 基础服务：登录登出、身份信息、菜单授权、切换系统、字典数据等
		lang: (params = {}) => vm.$u.get('/lang/' + params.lang),
		index: (params = {}) => vm.$u.get(config.adminPath + '/index', params),
		login: (params = {}) => vm.$u.post(config.adminPath + '/login', params),
		logout: (params = {}) => vm.$u.get(config.adminPath + '/logout', params),
		authInfo: (params = {}) => vm.$u.get(config.adminPath + '/authInfo', params),
		menuTree: (params = {}) => vm.$u.get(config.adminPath + '/menuTree', params),
		switchSys: (params = {}) => vm.$u.get(config.adminPath + '/switch/' + params.sysCode),
		dictData: (params = {}) => vm.$u.get(config.adminPath + '/sys/dictData/treeData', params),

		// 账号服务：验证码接口、忘记密码接口、注册账号接口等
		validCode: (params = {}) => vm.$u.getText('/validCode', params),
		getFpValidCode: (params = {}) => vm.$u.post('/account/getFpValidCode', params),
		savePwdByValidCode: (params = {}) => vm.$u.post('/account/savePwdByValidCode', params),
		getRegValidCode: (params = {}) => vm.$u.post('/account/getRegValidCode', params),
		saveRegByValidCode: (params = {}) => vm.$u.post('/account/saveRegByValidCode', params),

		// APP公共服务
		upgradeCheck: () => vm.$u.post('/app/upgrade/check', {
			appCode: config.appCode,
			appVersion: config.appVersion
		}),
		commentSave: (params = {}) => vm.$u.post('/app/comment/save', params),

		// 个人信息修改
		user: {
			infoSaveBase: (params = {}) => vm.$u.post(config.adminPath + '/sys/user/infoSaveBase', params),
			infoSavePwd: (params = {}) => vm.$u.post(config.adminPath + '/sys/user/infoSavePwd', params),
			infoSavePqa: (params = {}) => vm.$u.post(config.adminPath + '/sys/user/infoSavePqa', params),
		},

		// 员工用户查询
		empUser: {
			listData: (params = {}) => vm.$u.get(config.adminPath + '/sys/empUser/listData', params),
		},

		// 组织机构查询
		office: {
			treeData: (params = {}) => vm.$u.get(config.adminPath + '/sys/office/treeData', params),
		},

		// 增删改查例子
		testData: {
			form: (params = {}) => vm.$u.post(config.adminPath + '/test/testData/form', params),
			list: (params = {}) => vm.$u.post(config.adminPath + '/test/testData/listData', params),
			save: (params = {}) => vm.$u.postJson(config.adminPath + '/test/testData/save', params),
			disable: (params = {}) => vm.$u.post(config.adminPath + '/test/testData/disable', params),
			enable: (params = {}) => vm.$u.post(config.adminPath + '/test/testData/enable', params),
			delete: (params = {}) => vm.$u.post(config.adminPath + '/test/testData/delete', params),
		},

		// 文章类服务
		aricleService: {
			//获取大厅轮换图
			getIndexPictureList: (params = {}) => vm.$u.post(config.adminPath +
				'/api/article/getIndexPictureList', params),
			//获取通知标题
			getNoticeTitleList: (params = {}) => vm.$u.post(config.adminPath +
				'/api/article/getNoticeTitleList', params),
			//获取通知列表
			getNoticeList: (params = {}) => vm.$u.post(config.adminPath +
				'/api/article/getNoticeList', params),
			//获取通知清单
			getArticle: (params = {}, id) => vm.$u.post(config.adminPath +
				'/api/article/getArticle/' + id, params),
		},

		// 会员服务类
		memberService: {
			//获取会员余额
			getBalance: () => vm.$u.post(config.adminPath +
				'/api/member/getBalance', {}),
			//获取会员投注返点上限
			getRebate: () => vm.$u.post(config.adminPath +
				'/api/member/getRebate', {}),
		},

		//彩票服务类
		lotteryService: {
			// 获取最爱彩种
			findFavGameList: (params = {}) => vm.$u.post(config.adminPath +
				'/api/article/getIndexPictureList', params),
			// 获取热门彩种
			findHotGameList: (params = {
				gameGroup: 'hot'
			}) => vm.$u.post(config.adminPath +
				'/api/lottery/findGameList', params),
			// 获取彩种详情
			getGameInfo: (params = {}) => vm.$u.post(config.adminPath +
				'/api/lottery/getGameInfo', params),
			// 获取玩法清单
			findPlayMethodList: (params = {
				gemecode: ''
			}) => vm.$u.post(config.adminPath +
				'/api/lottery/findPlayMethodList', params),
			// 获取系统单注售价
			getBasePrice: (params = {}) => vm.$u.post(config.adminPath +
				'/api/lottery/getBasePrice', params),
			// 获取系统投注返点上限
			getSysMaxRebate: (params = {}) => vm.$u.post(config.adminPath +
				'/api/lottery/getSysMaxRebate', params),
			// 下注
			bet: (params = {}) => vm.$u.post(config.adminPath +
				'/api/lottery/bet', params),
		},
	};

}

export default {
	install
}