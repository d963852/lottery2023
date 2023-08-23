/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
const config = {

	// 产品名称
	productName: 'JeeSite Mobile',

	// 公司名称
	companyName: 'ThinkGem',

	// 产品版本号
	productVersion: 'V4.3.2',

	// 版本检查标识
	appCode: 'android',

	// 内部版本号码
	appVersion: 1,

	// 管理基础路径
	adminPath: '/a',

	//服务器地址
	serverHost: '127.0.0.1:8023',

	// 全大小单双清
	selectNumber10: {
		quan: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
		da: [5, 6, 7, 8, 9],
		xiao: [0, 1, 2, 3, 4],
		dan: [1, 3, 5, 7, 9],
		shuang: [0, 2, 4, 6, 8],
		qing: [],
	}

}

// 设置后台接口服务的基础地址
config.baseUrl = 'http://' + config.serverHost;
config.serverUrl = config.baseUrl + config.adminPath;

// 建议：打开下面注释，方便根据环境，自动设定服务地址
if (process.env.NODE_ENV === 'development') {
	// config.baseUrl = '/../js'; // 代理模式 vue.config.js 中找到 devServer 设置的地址
	config.baseUrl = 'http://127.0.0.1:8023';
}

export default config;