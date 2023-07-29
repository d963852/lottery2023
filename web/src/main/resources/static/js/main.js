function setCookie(name,value,expire,path) {
	var curdate=new Date();
	var cookie=name+"="+encodeURIComponent(value)+"; ";
	if(expire!=undefined||expire==0){
		if(expire==-1){
			expire=366*86400*1000;//保存366天
		}else{
			expire=parseInt(expire);
		}
		curdate.setTime(curdate.getTime()+expire);
		cookie+="expires="+curdate.toUTCString()+"; ";
	}
	path = path || "/";
	cookie += "path=" + path;
	document.cookie=cookie;
}

function getCookie(name) {
    var re = "(?:; )?" + encodeURIComponent(name) + "=([^;]*);?";
    re = new RegExp(re);
    if (re.test(document.cookie)) {
        return decodeURIComponent(RegExp.$1);
    }
    return '';
}
//用于Air的方法
function airAction(uname,nicename){
	//alert(uname);
}
function chagetheme(){
	setCookie('skins','sincai');
}
//密码验证(6－16位数字和字母，不能只是数字，或者只是字母，不能连续三位相同)
function validateUserPss(str) {
	var patrn = /^[0-9a-zA-Z]{6,16}$/;
	if( !patrn.exec(str) ) {
		return false;
	}
	patrn = /^\d+$/;
	if( patrn.exec(str) ) {
		return false;
	}
	patrn = /^[a-zA-Z]+$/;
	if( patrn.exec(str) ) {
		return false;
	}
	patrn = /(.)\1{2,}/;
	if( patrn.exec(str) ) {
		return false;
	}
	return true;
}
//密码验证(6－16位数字和字母，不能只是数字，或者只是字母，不能连续三位相同)
function validateUserPss( str ) {
	var patrn = /^[0-9a-zA-Z]{6,16}$/g;
	if( !patrn.exec(str) ) {
		return false;
	}
	patrn = /^\d+$/g;
	if( patrn.exec(str) ) {
		return false;
	}
	patrn = /^[a-zA-Z]+$/g;
	if( patrn.exec(str) ) {
		return false;
	}
	return true;
}
//呢称验证
function validateNickName( str )
{
	var patrn = /^.{2,10}$/g;
	if( patrn.exec(str) ) {
		return true;
	} else {
		return false;
	}
}
//onkeyup:根据用户输入的资金做检测并自动转换中文大写金额(用于充值和提现)
//obj:检测对象元素，chineseid:要显示中文大小写金额的ID，maxnum：最大能输入金额
function checkWithdraw(obj,chineseid,maxnum) {
	obj.value = formatFloat(obj.value);
	if( parseFloat(obj.value) > parseFloat(maxnum) ) {
		layer.msg("输入金额超出了可用余额");
		obj.value = maxnum;
	}
	$("#"+chineseid).html( changeMoneyToChinese(obj.value) );
}
function checkemailWithdraw( obj,chineseid,maxnum ){
	obj.value = formatFloat(obj.value);
	if( parseFloat(obj.value) > parseFloat(maxnum) ){
		layer.msg("您的充值金额已经超出规定限额");
		obj.value = maxnum;
	}
	$("#"+chineseid).html( changeMoneyToChinese(obj.value) );
}
//格式化浮点数形式(只能输入正浮点数，且小数点后只能跟四位,总体数值不能大于999999999999999共15位:数值999兆)
function formatFloat( num ) {
	num = num.replace(/^[^\d]/g,'');
	num = num.replace(/[^\d.]/g,'');
	num = num.replace(/\.{2,}/g,'.');
	num = num.replace(/^[0].*/g,'');
	num = num.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	if( num.indexOf(".") != -1 ) {
		var data = num.split('.');
		num = (data[0].substr(0,15))+'.'+(data[1].substr(0,2));
	} else {
		num = num.substr(0,15);
	}
	return num;
}

function show_no(id) {
	$("#code_"+id).show("slow");
}

function show_nocode(id) {
	$("#ncode_"+id).show("slow");
}

function close_no(id) {
	$("#code_"+id).hide("slow");
}

function nclose_no(id) {
	$("#ncode_"+id).hide("slow");
}
//自动转换数字金额为大小写中文字符,返回大小写中文字符串，最大处理到999兆
function changeMoneyToChinese( money ) {
	var cnNums	= new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");	//汉字的数字
	var cnIntRadice = new Array("","拾","佰","仟");	//基本单位
	var cnIntUnits = new Array("","万","亿","兆");	//对应整数部分扩展单位
	var cnDecUnits = new Array("角","分","毫","厘");	//对应小数部分单位
	var cnInteger = "整";	//整数金额时后面跟的字符
	var cnIntLast = "元";	//整型完以后的单位
	var maxNum = 999999999999999.9999;	//最大处理的数字
	var IntegerNum;		//金额整数部分
	var DecimalNum;		//金额小数部分
	var ChineseStr="";	//输出的中文金额字符串
	var parts;		//分离金额后用的数组，预定义
	if( money == "" ){
		return "";
	}
	money = parseFloat(money);
	if( money >= maxNum ){
		layer.msg('超出最大处理数字');
		return "";
	}
	if( money == 0 ){
		ChineseStr = cnNums[0]+cnIntLast+cnInteger;
		return ChineseStr;
	}
	money = money.toString(); //转换为字符串
	if( money.indexOf(".") == -1 ){
		IntegerNum = money;
		DecimalNum = '';
	}else{
		parts = money.split(".");
		IntegerNum = parts[0];
		DecimalNum = parts[1].substr(0,4);
	}
	if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换
		zeroCount = 0;
		IntLen = IntegerNum.length;
		for( i=0;i<IntLen;i++ ){
			n = IntegerNum.substr(i,1);
			p = IntLen - i - 1;
			q = p / 4;
            m = p % 4;
			if( n == "0" ){
				zeroCount++;
			}else{
				if( zeroCount > 0 ){
					ChineseStr += cnNums[0];
				}
				zeroCount = 0;	//归零
				ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
			}
			if( m==0 && zeroCount<4 ){
				ChineseStr += cnIntUnits[q];
			}
		}
		ChineseStr += cnIntLast;
	//整型部分处理完毕
	}
	if( DecimalNum!= '' ) {//小数部分
		decLen = DecimalNum.length;
		for( i=0; i<decLen; i++ ) {
			n = DecimalNum.substr(i,1);
			if( n != '0' ) {
				ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
			}
		}
	}
	if( ChineseStr == '' ) {
		ChineseStr += cnNums[0]+cnIntLast+cnInteger;
	} else if( DecimalNum == '' ) {
		ChineseStr += cnInteger;
	}
	return ChineseStr;
}

function moneyFormat(num) {
	var sign = Number(num) < 0 ? "-" : "";
    num = num.toString();
	if( num.indexOf(".") == -1 ) {
		num = "" + num + ".0000";
	}
	var data = num.split('.');
	data[0] = data[0].toString().replace(/[^\d]/g,"").substr(0,15);
	data[0] = Number(data[0]).toString();
	var newnum = [];
	for( var i=data[0].length; i>0; i-=3 ) {
		newnum.unshift(data[0].substring(i,i-3));
	}
	data[0] = newnum.join(",");
	data[1] = data[1].toString().substr(0,4);
	return sign+""+data[0] + "." + data[1];
}
//返点数值输入检查
function clearNoNum(obj){
    var myregexp = /\d+\.?\d?/;
    var match = myregexp.exec(obj.value);
    if (match != null) {
        obj.value = match[0];
    } else {
        obj.value = "";
    }
}
//判断是否IE浏览器
function fnCheckIe(){
	var broswer = navigator.userAgent;
	var ver = parseInt(broswer.substr(broswer.indexOf("MSIE")+5,3));
	if(ver <= 8){
		return true;
	}else{
		return false;
	}
}

$(function(){
	var moveIn = false;
	var moveSeeIn = false;
	$(document).on('mouseover','.task_div',function(){
		moveIn = true;
	});
	$(document).on('mouseout','.task_div',function(){
		moveIn = false;
	});
	$(document).on('mouseover','.seeMore',function(){
		moveSeeIn = true;
	});
	$(document).on('mouseout','.seeMore',function(){
		moveSeeIn = false;
	});
	$(document).click(function(){
		if(!moveIn){
			$('.task_div').hide('slow');
		}
		if(!moveSeeIn){
			$('.seeMore').hide();
		}
	});
	$(document).on("click",".seeMore",function(){
		if(moveIn){
			$('.task_div').hide('slow');
		}
		if(moveSeeIn){
			$('.seeMore').hide();
		}
	});
})

jQuery.extend( jQuery.easing,  {
    easeOutBounce: function (x, t, b, c, d) {
        if ((t/=d) < (1/2.75)) {
            return c*(7.5625*t*t) + b;
        } else if (t < (2/2.75)) {
            return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
        } else if (t < (2.5/2.75)) {
            return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
        } else {
            return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
        }
    },
});
//即将推出
function SoonTips(){
  layer.msg('即将推出，感谢关注');
}
//请先登录
function UserLogin(){
  layer.msg('请先登录');
}
//开奖记录
function kjHistory(){
  layer.msg('右上角走势K线查看更多');
}
//活动结束
function ActivityEnd(){
  layer.msg('该活动已结束');
}
//分红弹层
function BonusTip(){
  layer.msg('团队亏损才可分红');
}
//试玩
function TestUser(){
  layer.msg('试玩用户无权限');
}
//积分兑换
function Integral(num){
	layer.open({
	  type: 2,
	  area: ['360px', '374px'],
	  title:'积分兑换',
	  scrollbar: false,
	  content:'/Activity/IntegralId/'+num
	});
	return false;
}
//积分兑换实物
function IntegralSw(num){
	layer.open({
	  type: 2,
	  area: ['360px', '536px'],
	  title:'实物兑换',
	  scrollbar: false,
	  content:'/Activity/IntegralSw/'+num
	});
	return false;
}
//充值
function Recharge(){
	layer.open({
	  type: 2,
	  area: ['700px', '550px'],
	  title:'账户充值',
	  scrollbar: false,
	  content:'/Finance/Recharge'
	});
	return false;
}

//充值
function Recharge2(usernname){
	layer.open({
	  offset: '100px',
	  type: 2,
	  area: ['90%', '80%'],
	  title:'账户充值',
	  scrollbar: false,
	  resize:false,
	  content:'../recharge/gameIndex.html?username='+usernname
	});
	return false;
}

//提现
function Withdrawals(){
	layer.open({
	  offset: '100px',
	  type: 2,
	  area: ['460px', '440px'],
	  title:'账户提现',
	  scrollbar: false,
	  resize:false,
	  content:'/Finance/Withdrawals'
	});
	return false;
}
//公告
function Notice(){
	layer.open({
	  offset: '100px',
	  type: 2,
	  area: ['980px', '563px'],
	  title:'平台公告',
	  scrollbar: false,
	  resize:false,
	  content:'/Index/Info'
	});
	return false;
}
//备注
function Remarks(){
	layer.open({
	  type: 2,
	  area: ['460px', '160px'],
	  title:'网站备注',
	  scrollbar: false,
	  resize:false,
	  content:'/Member/Remarks'
	});
	return false;
}
//昵称
function Nickname(){
	layer.open({
	  type: 2,
	  area: ['460px', '160px'],
	  title:'昵称修改',
	  scrollbar: false,
	  resize:false,
	  content:'/Member/Nickname'
	});
	return false;
}
//资金转换
function Accounts(){
	layer.open({
	  type: 2,
	  area: ['360px', '400px'],
	  title:'资金转换',
	  scrollbar: false,
	  content:'/Finance/Accounts'
	});
	return false;
}
//在线会员
function MemberOnline(){
	layer.open({
	  type: 2,
	  area: ['500px', '450px'],
	  title:'在线会员',
	  scrollbar: false,
	  content:'/Agent/MemberOnline'
	});
	return false;
}
//退出帐号
function Logout(){
layer.confirm('是否确定退出账户？',{offset: '30%'},
        function(index)
        {
           window.location.href='/User/Logout';
		   layer.close(index);
        });
}
function zxkf(){
	 layer.open({
	  offset: '100px',
	  type: 2,
	  	  area: ['980px', '565px'],
	  zIndex:9999,
	  fix: false,
	  top:0,
	  title:'在线客服',
	  scrollbar: false,
	  resize:false,
	  content:'/User/Service'
	});
	return false;
}
//平台公告
function View(num){
	layer.open({
	  offset: '100px',
	  type: 2,
	  area: ['800px', '570px'],
	  title:'平台公告',
	  scrollbar: false,
	  resize:false,
	  content:'/Index/View/'+num
	});
	return false;
}
//契约签订
function Deed(num){
	layer.open({
	  type: 2,
	  area: ['360px', '374px'],
	  title:'契约签订',
	  scrollbar: false,
	  content:'/Agent/Deed/'+num
	});
	return false;
}
//亏损分红
function BonusDay(){
	layer.open({
	  type: 2,
	  area: ['360px', '428px'],
	  title:'亏损分红',
	  scrollbar: false,
	  content:'/Agent/BonusDay'
	});
	return false;
}
/*动画隐藏*/
function GameFlash() {
    var mScbti = document.getElementById('game_flash_false');
    var mScbsp = document.getElementById('game_flash_true');
    if(mScbti.sp == '0'){
        mScbsp.style.display = 'block';
        mScbti.innerHTML = "隐藏动画";
        mScbti.sp = '1';
    }else{
        mScbsp.style.display = 'none';
        mScbti.innerHTML = "显示动画";
        mScbti.sp = '0';
    }
}