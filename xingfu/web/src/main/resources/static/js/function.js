//开奖号码容器
function newcode(code_arr){
	var _html='';
	if(game.type==12||game.type==20||game.type==21 || game.type==54||game.type==55 || game.type==64){ //pk10开奖号码
		$.each(code_arr,function(index,value){
			_html += "<li class=''><span>"+value+"</span></li>";
		});
			$("#num").removeClass().addClass("pk10_ul").empty().html(_html).css({'width':"400px"});
			$("#num li").each(function(index){
			var num = $(this);
			num.removeClass().addClass("car" + code_arr[index]).hide();
			window.setTimeout(function(){
				num.fadeIn();
			});
		});
	}else if(game.type==6||game.type==18||game.type==19||game.type==24||game.type==25||game.type==34){ //K3开奖号码
		$.each(code_arr,function(index,value){
			_html += "<li><span></span></li>";
		});
			$("#num").empty().html(_html).css({'width':"210px"});
			$("#num li").each(function(index){
			var num = $(this).children('span');
			setTimeout(function(){
				num.animate({top: "39px"},100, function() {
					num.html('<p>'+code_arr[0]+'</p><p>'+code_arr[1]+'</p><p>'+code_arr[2]+'</p>');
					num.css("top","-195px");
					num.animate({top: "39px"}, 600, function(){ 
						num.html(code_arr[index]);
						num.css("top","-39px");
						num.animate({top: "0px"}, 100);
					});
				});
			});
		});
	}else{
		$.each(code_arr,function(index,value){
			_html += "<li><span>-</span></li>";
		});
		$("#num").empty().html(_html).css({'width':"350px"});//开奖号码
		$("#num li").each(function(index){
			var num = $(this).children('span');
			setTimeout(function(){
				num.animate({top: "39px"},100, function() {
					num.html('<p>'+code_arr[0]+'</p><p>'+code_arr[1]+'</p><p>'+code_arr[2]+'</p><p>'+code_arr[3]+'</p><p>'+code_arr[4]+'</p>');
					num.css("top","-195px");
					num.animate({top: "39px"}, 600, function(){ 
						num.html(code_arr[index]);
						num.css("top","-39px");
						num.animate({top: "0px"}, 100);
					});
				});
			});
		});
	}
}
function codeplay(code_arr){
	var _html='';
	if(game.type==12|| game.type==16|| game.type==26|| game.type==27|| game.type==28|| game.type==29||game.type==33|| game.type==20||game.type==21||code_arr.length==54){//正在开奖中彩球大小
		if(code_arr.length==12||code_arr.length==20||code_arr.length==21||code_arr.length==54||game.type==55 || game.type==64){//pk10 正在开奖中
			var code_arr=strCut(code_arr, 2);
			$.each(code_arr,function(index,value){
				_html += "<li class=''><span></span></li>";
			});
			$("#num").addClass("pk10_ul").empty().html(_html)
			$("#num li").each(function(index){
				var num = $(this);
				num.removeClass().addClass("car" + code_arr[index]).hide();
				window.setTimeout(function(){
					num.fadeIn();
				},100+index*200);
			});
		}else{
			var code_arr=code_arr.split('');
			$.each(code_arr,function(index,value){
				_html += "<li><span>-</span></li>";
			});
			$("#num").removeClass().addClass("pk10_ul_line").empty().html(_html);

			$("#num").empty().html(_html).css({'width': "350px"});//pk10 正在开奖中
			$("#num li").each(function(index){
				var num = $(this);
				num.html('<span>' + code_arr[index] + '</span>');
			});
		}
	}else if(game.type==4 || game.type==5 || game.type==10 || game.type==11 || game.type==22 || game.type==23){// 11x5正在开奖中
		if(code_arr.length==10){
			var code_arr=strCut(code_arr, 2);
		}else{
			var code_arr=code_arr.split('');
		}
		$.each(code_arr,function(index,value){
			_html += "<li><span>-</span></li>";
		});
		$("#num").empty().html(_html).css({'width': "350px"});
		$("#num li").each(function(index){
			var num = $(this);
			num.html('<span>' + code_arr[index] + '</span>');
		});
	}else if(game.type==14 ||  game.type==32){
		if(code_arr.length==14){
			var code_arr=strCut(code_arr, 2);
		}else{
			var code_arr=code_arr.split('');
		}
		if(code_arr.length==7){
			code_arr.splice(6,0,'-');
			$.each(code_arr,function(index,value){
				_html += "<li><span>-</span></li>";
			});
			$("#num").addClass("lhc").empty().html(_html).css({'width': "350px"});
		}else{
			$.each(code_arr,function(index,value){
				_html += "<li><span>-</span></li>";
			});
			$("#num").removeClass("lhc").empty().html(_html).css({'width': "350px"});
		}
		$("#num li").each(function(index){
			var num = $(this);
			if(code_arr.length==8){
				var color='';
				var a=['01','02','07','08','12','13','18','19','23','24','29','30','34','35','40','45','46'];
				var b=['03','04','09','10','14','15','20','25','26','31','36','37','41','42','47','48'];
				var c=['05','06','11','16','17','21','22','27','28','32','33','38','39','43','44','49'];
				if(in_array(code_arr[index], a)){
					color='red';
				}else if(in_array(code_arr[index], b)){
					color='blue';
				}else if(in_array(code_arr[index], c)){
					color='green';
				}else{
					color='and';
				}
				num.removeClass().addClass(color);
			}
			num.html('<span>' + code_arr[index] + '</span>');
		});
	}else{
		var code_arr=code_arr.split('');
		$.each(code_arr,function(index,value){
			_html += "<li><span>-</span></li>";
		});
		$("#num").empty().html(_html).css({'width': "350px"});
		$("#num li").each(function(index){
			var num = $(this);
			num.html('<span>' + code_arr[index] + '</span>');
		});
	}
}

function in_array(needle,haystack) {
	var n=haystack.length;
	for(var i=0;i<n;i++){
	  if(haystack[i]==needle) return true;
	}
	return false;
}
var T,S,KT,KS,TIPS;
function gameKanJiangDataC(diffTime, actionNo){
	//console.info("diffTime",diffTime);
	//console.info("actionNo",actionNo);
	var thisNo=$('#current_issue').html();
	TIPS='本期['+thisNo+']已截至投注';
	if(diffTime<=0){
		if(game.type==12|| game.type==20|| game.type==21|| game.type==54||game.type==55 || game.type==64) $('#num').removeClass("pk10_ul");
		codeplay('准备开奖中');
		$('#btnaddBet').unbind('click');
		$('#btnaddBet').bind('click', function(){
			layer.msg(TIPS);
		});
		if(S){
			layer.msg('当期投注已截止，请进入下一期投注');
		}
		S=false;
		KS=true;
		if(KT) clearTimeout(KT);
		$('.lottery_history_issue span').text($('#current_issue').text());
		setKJWaiting(kjTime);
	}else{
		if(actionNo) $('#current_issue').html(actionNo);
		var m=Math.floor(diffTime % 60), s=(diffTime---m)/60, h=0;
		if(s<10){
			s="0"+s;
		}
		if(m<10){
			m="0"+m;
		}
		var mx=m, sx=s, hx=h;
		if(sx>60){
			hx=Math.floor(sx/60);
			sx=sx-hx*60;
			if(hx<10){$('#s1s').html(0);$('#s1x').html(0);$('#s2s').html(hx);$('#s2x').html(hx);}else{hx=hx.toString().split('');$('#s1s').html(hx['0']);$('#s1x').html(hx['0']);$('#s2s').html(hx['1']);$('#s2x').html(hx['1']);}
			if(sx<10){$('#f1s').html(0);$('#f1x').html(0);$('#f2s').html(sx);$('#f2x').html(sx);}else{sx=sx.toString().split('');$('#f1s').html(sx['0']);$('#f1x').html(sx['0']);$('#f2s').html(sx['1']);$('#f2x').html(sx['1']);}
			mx=mx.toString().split('');$('#m1s').html(mx['0']);$('#m1x').html(mx['0']);$('#m2s').html(mx['1']);$('#m2x').html(mx['1']);
		}else{
			$('#s1s').html(0);$('#s1x').html(0);$('#s2s').html(0);$('#s2x').html(0);
			sx=sx.toString().split('');$('#f1s').html(sx['0']);$('#f1x').html(sx['0']);$('#f2s').html(sx['1']);$('#f2x').html(sx['1']);
			mx=mx.toString().split('');$('#m1s').html(mx['0']);$('#m1x').html(mx['0']);$('#m2s').html(mx['1']);$('#m2x').html(mx['1']);
		}
		if(S && h==0 && ((m==30 && s==0) || (m==0 && s==4))){
			getcorrtime();
		}
		if(S && h==0 && m==5 && s==0){
			playVoice('/public/sound/fengdan.mp3', 'stop-time-voice');
		}
		if(h==0 && m==0 && s==0){
			loadKjData();
			$('.fqzhBox :checkbox[name=zhuiHao]').removeData()[0].checked=false;
			$('#ischeck').removeClass('check').addClass('uncheck');
			gameCalcAmount();
		}else{
			if($.browser.msie){
				T=setTimeout(function(){
					gameKanJiangDataC(diffTime);
				}, 1000);
			}else{
				T=setTimeout(gameKanJiangDataC, 1000, diffTime);
			}
		}
    }
}
function setKJWaiting(kjDiffTime){
	//console.info("kjDiffTime",kjDiffTime);
	var mm=Math.floor(kjDiffTime % 60), ss=(kjDiffTime---mm)/60;
	$('#posttime').html('封单剩余时间');
	if(ss<10){
		ss="0"+ss;
	}
	if(mm<10){
		mm="0"+mm;
	}
	var mmx=mm, ssx=ss, hhx;
	if(ssx>60){
		hhx=Math.floor(ssx/60);
		ssx=ssx-hhx*60;
		if(hhx<10){$('#s1s').html(0);$('#s1x').html(0);$('#s2s').html(hhx);$('#s2x').html(hhx);}else{hhx=hhx.toString().split('');$('#s1s').html(hhx['0']);$('#s1x').html(hhx['0']);$('#s2s').html(hhx['1']);$('#s2x').html(hhx['1']);}
		if(ssx<10){$('#f1s').html(0);$('#f1x').html(0);$('#f2s').html(ssx);$('#f2x').html(ssx);}else{ssx=ssx.toString().split('');$('#f1s').html(ssx['0']);$('#f1x').html(ssx['0']);$('#f2s').html(ssx['1']);$('#f2x').html(ssx['1']);}
		mmx=mmx.toString().split('');$('#m1s').html(mmx['0']);$('#m1x').html(mmx['0']);$('#m2s').html(mmx['1']);$('#m2x').html(mmx['1']);
	}else{
		$('#s1s').html(0);$('#s1x').html(0);$('#s2s').html(0);$('#s2x').html(0);
		ssx=ssx.toString().split('');$('#f1s').html(ssx['0']);$('#f1x').html(ssx['0']);$('#f2s').html(ssx['1']);$('#f2x').html(ssx['1']);
		mmx=mmx.toString().split('');$('#m1s').html(mmx['0']);$('#m1x').html(mmx['0']);$('#m2s').html(mmx['1']);$('#m2x').html(mmx['1']);
	}
	if(Math.floor(mm)==0 && Math.floor(ss)==0){
		KS=false;
		Stage();
	}else{
		if($.browser.msie){
			KT=setTimeout(function(){
				setKJWaiting(kjDiffTime);
			}, 1000);
		}else{
			KT=setTimeout(setKJWaiting, 1000, kjDiffTime);
		}
	}
}

function Stage(){
	$.getJSON('/Index/Stage/'+game.type, function(data){
		if(data && data.lastNo && data.thisNo){
			BettingRefresh();
			$('#btnaddBet').unbind('click');
			$('#btnaddBet').bind('click',CodesAdd);
			$('#posttime').html('投注剩余时间');
			$('#current_issue').html(data.thisNo.actionNo);
			$('.lottery_history_issue span').html(data.lastNo.actionNo);
			S=true;
			if(T) clearTimeout(T);
			kjTime=parseInt(data.kjdTime);
			gameKanJiangDataC(data.diffTime-kjTime, data.thisNo.actionNo);
			loadKjData();
		}
	});
}
function getcorrtime(){
	$.getJSON('/Index/Stage/'+game.type, function(data){
		if(data && data.lastNo && data.thisNo){
			if(T) clearTimeout(T);
			kjTime=parseInt(data.kjdTime);
			gameKanJiangDataC(data.diffTime-kjTime, data.thisNo.actionNo);
		}
	});
}
var  moveno;
function setKjing(){
	if(!KS){
		$('#kaijiang #kjsay').html('<em class="kjtips">正在开奖中</em>');
		$('#kaijiang #kjsay').show();
		$('.wjkjData p').hide();
		$('.wjkjData ul').show();
	}
	var ctype=$('.kj-hao').attr('ctype');
	var cnum=$('.kj-hao').attr('cnum'),num;
		cnum=parseInt(cnum);
	$(".kj-hao").find("li").attr("flag", "move");
		if(ctype=='g1'){
			moveno = window.setInterval(function () {
				$.each($(".kj-hao").find("li"), function (i, n) {
					if ($(this).attr("flag") == "move") {
						num=Math.floor((cnum-1) * Math.random() + 1);
						if(num<10) num='0'+num;
						$(this).html(num);
					}
				})
			}, 40);
		}else if(ctype=='g2'){  //快3
			moveno = window.setInterval(function () {
				$.each($(".kj-hao").find("li"), function (i, n) {
					if ($(this).attr("flag") == "move") {
						$(this).attr("class", "gr_ks gr_ksm" + Math.floor(6 * Math.random() + 1));
					}
				})
			}, 70);
		}else if(ctype=='g3'){ //11选5
			moveno = window.setInterval(function () {
				$.each($(".kj-hao").find("li"), function (i, n) {
					if ($(this).attr("flag") == "move") {
						$(this).attr("class", "gr_s gr_s0" + Math.floor(8 * Math.random() + 1));
					}
				})
			}, 40);
		}else if(ctype=='pk10'){ //pk10
			moveno = window.setInterval(function () {
				$.each($(".kj-hao").find("li"), function (i, n) {
					if ($(this).attr("flag") == "move") {
						$(this).attr("class", "ball2 ball_0" + Math.floor(4 * Math.random() + 1));
					}
				})
			}, 40);
		}else{
			 moveno = window.setInterval(function () {
				$.each($(".kj-hao").find("li"), function (i, n) {
					if ($(this).attr("flag") == "move") {
						$(this).attr("class", "gr_s gr_s" + Math.floor(10 * Math.random()));
					}
				})
			}, 40);
		}
}
function loadKjData(){
	var type=$('#kaijiang').attr('type');
	$.ajax('/Index/Gdata/'+type,{
		dataType:'json',
		cache:false,
		error:function(){
			setTimeout(loadKjData, 1000);
		},
		success:function(data, textStatus, xhr){
			if(!data){
				if(!KS) codeplay('正在开奖中');
				setTimeout(loadKjData, 1000);//动画时间
			}else{
				try{
					if(type==12||type==20||type==21|| game.type==54||game.type==55 || game.type==64) $('#num').addClass("pk10_ul");//PK10
					var hao=data.data.split(',');
					$('.lottery_history_issue span').html(data.actionNo);
					newcode(hao);
					freshKaiJiangData(data.actionNo, hao);
					Betting(game.type,data.actionNo)
					$('#CodesBet').unbind('click');
					$('#CodesBet').bind('click',gamePostCode);
					if((typeof $('#samfea').dialog("isOpen")=='object') || $('#samfea').dialog('isOpen')){
						$('#samfea').dialog('close');
					}
					S=true;
					KS=true;
					if(type==28||type==29||type==33){
						$('.pk10_ul_line').removeClass().addClass('k8').css({"width":"400px"});
					}else{
						$('.pk10_ul_line').removeClass().addClass('klsf').css({"width":"400px"});
					}
					if(KT) clearTimeout(KT);
					if(T) clearTimeout(T);
					kjTime=parseInt(data.kjdTime);
					gameKanJiangDataC(data.diffTime-kjTime, data.thisNo);
					playVoice('/public/sound/kaijiang.mp3', 'kai-jian-voice');
					BettingRefresh();
					HistoryRefresh();
					reloadMemberInfo();
				}catch(err){
					setTimeout(loadKjData, 1000);
				}
			}
		}
	});
}
function freshKaiJiangData(type){
	$('#historylot');
}
function msg(){
	$('.msg-num').load('/Index/Msg');
}
function Betting(type,actionNo){
	if(type && actionNo){
		$.getJSON('/Prompt/Betting/'+type+'/'+actionNo, function(tip){
			if(tip){
				var ofset =$("#mainiframe",parent.document).offset();
				return window.layer.open({
								skin: 'layui-layer-lan',
								type: 1,
								title: '消息提示',
								shade: 0,
								area: ['350px', '150px'],
								offset: 'rb',
								content: '<div class="tipsrb">'+ tip.message +'</div>'
						});
		  }
		})
	}
}
function PasswdSet(){
	if(!this.oldpassword.value){layer.msg("请输入原登入密码");return false;}
	if(this.oldpassword.value.length<6){layer.msg("原登入密码至少6位");return false;}
	if(!this.newpassword.value){layer.msg("请输入新登入密码");return false;}
	if(this.newpassword.value.length<6){layer.msg("新登入密码至少6位");return false;}
	if(!this.qrpassword.value){layer.msg("请确认新登入密码");return false;}
	if(this.qrpassword.value.length<6){layer.msg("新登入密码至少6位");return false;}
	var confirmpwd=$(':password.confirm', this).val();
	if(confirmpwd!=this.newpassword.value){layer.msg("两次输入密码不一样");return false;}
	return true;
}
function SapitalPasswdNew(){
	if(!this.newpassword.value){layer.msg("请设置新密码");return false;}
	if(this.newpassword.value.length<6){layer.msg("密码至少6位");return false;}
	var confirmpwd=$(':password.confirm', this).val();
	if(confirmpwd!=this.newpassword.value){layer.msg("两次输入密码不一样");return false;}
	return true;
}
function SapitalPasswdSet(){
	if(!this.oldpassword.value){layer.msg("请输入原资金密码");return false;}
	if(this.oldpassword.value.length<6){layer.msg("原资金密码至少6位");return false;}
	if(this.oldpassword.value.length>13){layer.msg("原资金密码至多13位");return false;}
	if(!this.newpassword.value){layer.msg("请输入新资金密码");return false;}
	if(this.newpassword.value.length<6){layer.msg("新资金密码至少6位");return false;}
	if(!this.qrpassword.value){layer.msg("请确认新资金密码");return false;}
	if(this.qrpassword.value.length<6){layer.msg("新资金密码至少6位");return false;}
	var confirmpwd=$(':password.confirm', this).val();
	if(confirmpwd!=this.newpassword.value){layer.msg("两次输入资金密码不一样");return false;}
	return true;
}
//新增会员
function MemberAdd(){
	var type=$('[name=type]:checked',this).val();
	if(!this.username.value){layer.msg("请输入会员帐号");return false;}
	if(!/^\w{5,16}$/.test(this.username.value)){layer.msg("用户名由5到16位的字母或数字组成");return false;}
	if(!this.password.value){layer.msg("请输入登入密码");return false;}
	if(this.password.value.length<6){layer.msg("登入密码至少6位数");return false;}
	if(!this.cpasswd.value){layer.msg("请确认登入密码");return false;}
	if(document.getElementById('cpasswd').value!=this.password.value){layer.msg("两次输入的登入密码不一样");return false;}
	if(!this.fanDian.value){layer.msg("请输入返点");return false;}
	if(parseFloat(this.fanDian.value)<0){layer.msg("返点不能小于0%"); return false;}
	if(parseFloat(this.fanDian.value)>parseFloat($(this.fanDian).attr('max'))){layer.msg('返点不能大于'+$(this.fanDian).attr('max')); return false;}
	var fanDianDiff= $(this.fanDian).attr('fanDianDiff');
	if((this.fanDian.value*1000) % (fanDianDiff*1000)){layer.msg('返点只能是'+fanDianDiff+'%的倍数');return false;}
}
function Dinding(){
	if(this.account && !this.account.value){layer.msg("请填写收款账号");return false;}
	if(this.username && !this.username.value){layer.msg("请填写开户姓名");return false;}
	if(this.countname && !this.countname.value){layer.msg("请填写开户地址");return false;}
	if(this.coinPassword && !this.coinPassword.value){layer.msg("请输入资金密码");return false;}
	if(this.coinPassword && this.coinPassword.value.length<6){layer.msg("资金密码至少6位");return false;}
	return true;
}
function safeBeforcare(){
	if(!this.care.value){layer.msg("备注不能为空");return false;}
	return true;
}
function safeBefornickname(){
	if(!this.nickname.value){layer.msg("昵称不能为空");return false;}
	return true;
}
function BonusDaySet(){
	if(!this.coinpwd.value){layer.msg("请输入资金密码");return false;}
	if(parseFloat(this.amount.value)>=1){layer.msg("团队当前为盈利，申请失败"); return false;}
	if(parseFloat(this.issue.value)>=parseFloat(this.bonus.value)){layer.msg("累积分红大于亏损分红，申请失败"); return false;}
	if(parseFloat(this.money.value)<=100){layer.msg("本次分红低于100，申请失败"); return false;}
	return true;
}
function MsgTips(err, data){
	if(err){
		layer.msg(err);
	}else if(data){
		layer.msg(''+ data +'');
	}else{
		layer.msg('成功');
	}
}
function teamCopyTip(text){
	if(text){
		layer.msg("复制成功");
		}
}

function dataAddCode(){
	$('form', this).trigger('submit');
}
function defaultCloseModal(){
	$(this).dialog('destroy');
}
//修改会员
function UserModifySet(){
	if(!this.fanDian.value.match(/^[\d\.\%]{1,4}$/)) throw('请正确设置返点');
	if(parseFloat(this.fanDian.value)>parseFloat($(this.fanDian).attr('max'))) throw('返点不能大于或等于'+$(this.fanDian).attr('max'));
	if(parseFloat(this.fanDian.value)<parseFloat($(this.fanDian).attr('min'))) throw('返点不能小于'+$(this.fanDian).attr('min'));
	if(parseFloat(this.fanDian.value)<parseFloat($(this.fanDian).attr('val'))) throw('返点不能小于'+$(this.fanDian).attr('val'));
	var fanDianDiff= $(this.fanDian).attr('fanDianDiff');
	if((this.fanDian.value*1000) % (fanDianDiff*1000)) throw('返点只能是'+fanDianDiff+'%的倍数');
}
//用户充值
function UserRechargeSet(){
	if(this.coin.value<=0) throw('充值金额必须大于0');
	if(!this.safepass.value){layer.msg("请输入密码");return false;}
}
function userDataSubmitCode(err, data){
	if(err){
		layer.msg(err);
	}else{
		layer.msg("修改成功");
		$(this).parent().dialog('destroy');
		reload();
	}
}
//新增注册链接前
function LinkAdd(){
	var type=$('[name=type]:checked',this).val();
	//if(!this.username.value){layer.msg("请输入会员帐号");return false;}
	if(!this.fanDian.value){layer.msg("请输入返点");return false;}
	if(!this.fanDian.value.match(/^[\d\.\%]{1,4}$/)){layer.msg("请正确设置返点");return false;}
	if(parseFloat(this.fanDian.value)<0){layer.msg("返点不能小于0%");return false;}
	if(parseFloat(this.fanDian.value)>parseFloat($(this.fanDian).attr('max'))){layer.msg('返点不能大于'+$(this.fanDian).attr('max')); return false;}
	var fanDianDiff= $(this.fanDian).attr('fanDianDiff');
	if((this.fanDian.value*1000) % (fanDianDiff*1000)){layer.msg("返点只能是'+fanDianDiff+'%的倍数");return false;}
}
function uniqueSelect(parent){
	var $this=$(this),$unique=parent.closest('.unique'),
	fun=function(i,c){
		return $('input.code.checked[value='+this.value+']').length?'':'checked';
	};
	if($this.is('.all')){
		$('input.code',parent).addClass(fun);
	}else if($this.is('.large')){
		$('input.code.max',parent).addClass(fun);
		$('input.code.min',parent).removeClass('checked');
	}else if($this.is('.small')){
		$('input.code.min',parent).addClass(fun);
		$('input.code.max',parent).removeClass('checked');
	}else if($this.is('.odd')){
		$('input.code.d',parent).addClass(fun);
		$('input.code.s',parent).removeClass('checked');
	}else if($this.is('.even')){
		$('input.code.s',parent).addClass(fun);
		$('input.code.d',parent).removeClass('checked');
	}else if($this.is('.none')){
		$('input.code',parent).removeClass('checked');
	}
}
/*
function reload(){
	location.reload();
}
*/
function reloadMemberInfo(){
	$.ajax({
		type : 'GET',
		url  : '/Member/Balance',
		timeout : 10000,
		success : function(data){
			autoRefresh = true;
			if(data == "error") {
				$("#ExhibitionMoney").html("正在读取资金");
				return false;
			}else{
				if(isNaN(data)){
					layer.confirm('登入超时或已过期，请重新登入',
					function(index)
					{
					location.href="/User/Login";
					});
				}else{
					$("#ExhibitionMoney").html(moneyFormat(data).substr(0,14));
					return true;
				}
			}
		},
		error: function() {
			$("#ExhibitionMoney").html("正在读取资金");
		},
		complete:function(XHR,textStatus){
			XHR = null;
		}
	}); 
}
function randomSelectCode(len, codes){
	var i,selectCode=[], codesLen=codes.length;
	for(i=0; i<len; i++){
		selectCode[i]=Math.floor(Math.random()*codesLen);
	}
	return selectCode;
}

function setGameZhuiHao(data){
	var fpcount=1,$feipan=$(':checkbox[name=fpEnable]'); 
	if($feipan.prop('checked')) fpcount=2;
	$.get('/Index/Zmodal', function(html){
		$(html).dialog({
			title:'追号期数：<span class="qs">0</span>　总金额：<span class="amount">0.00</span>元',
			minWidth:600,
			height:400,
			modal:true,
			stack:false,
			dialogClass:'zhui-hao-modal',
			buttons:{
				"确定追号":function(){
					var data=[];
					$('tbody :checkbox:checked', this).each(function(){
						var $this=$(this),
						$tr=$this.closest('tr');
						data.push([$('td:eq(1)', $tr).text(), $('.beishu', $tr).val(), $('td:eq(4)', $tr).text()].join('|'));
					});
					
					if(!data.length){
						layer.msg('追号至少选一期');
						return false;
					}
					
					$('.fqzhBox :checkbox[name=zhuiHao]').data({
						zhuiHao:data.join(';'),
						zhuiHaoMode:$(this).closest('.zhui-hao-modal').find(':checkbox[name=zhuiHaoMode]:first')[0].checked?1:0
					})[0].checked=true;
					$( this ).dialog( "destroy" );
					gameCalcAmount();
				},
				"取消追号":function(){
					$('.fqzhBox :checkbox[name=zhuiHao]').removeData()[0].checked=false;
					$( this ).dialog( "destroy" );
					gameCalcAmount();
				}
			},
			open:function(event, ui){
				var $this=$(this),
				price=Math.round(data.mode * data.actionNum * fpcount * 100)/100;
				$this.attr('rel', price);
				$this.attr('src', '/Index/ZmodalQs/'+game.type+'/'+price+'/');
				
				$('.tr-cont', this).load($this.attr('src')+10);
				$this.closest('.zhui-hao-modal').find('select:first').change(function(){
					$('tbody', $this).load($this.attr('src')+this.value);
				});
			}
		});
	})
}
function doZhuiHaoCount(){
	var count=0, amount=0;
	$('tbody tr :checkbox', this).each(function(i, v){
		
	});
}
function displayCodeList(opts){
	$('<div>').append(
		$('<textarea class="code-tip-box" style="height: 200px;width: 100%;display: block;margin: 15px auto;resize:none;text-align: center;background: url(/images/nsc/logo.png?v=1.16.10.1) no-repeat right 5px top 136px;" class="code-tip-box" readonly></textarea>')
		.append(opts.actionData)
	).dialog({title:'投注号码'});
}
function gameMsgAutoTip(){
	var obj,$game=$('#num-select .pp'),
	calcFun=$game.attr('action');
	if(calcFun && (calcFun=window[calcFun]) && (typeof calcFun=='function')){
		try{
			obj=calcFun.call($game);
			if($.isArray(obj)){
				o={actionNum:0};
				obj.forEach(function(v,i){
					o.actionNum+=v.actionNum;
				});
				obj=o;
			}
			$('#game-tip-dom').text(obj.actionNum+'注,'+(gameGetMode()*gameGetBeiShu()*obj.actionNum).round(1)+'元');
		}catch(err){
			$('#game-tip-dom').text(err);
		}
	}
}
$(function(){
	$(':radio[name=danwei]').live("click",function(){
		var value=$(this).val();
		if($(this).attr("checked")){
			$.cookie('mode', value, { expires: 7, path: '/'});
		}
	})	
	$("#basic_slider").live("mouseover",function(){
		$.cookie('fanDian', gameGetFanDian(), { expires: 7, path: '/'});
	})	
	$('.changebg a').live("click",function(){
		var img=$(this).attr("rel");
		$.cookie('pagepg', img, { expires: 7, path: '/'});
		location.reload();
		return false;
	})	
	
})
/**
 * 设置cookie
 */
$(function(){
	//切换模式
	$('.danwei').live("click",function(){
		var value=$(this).attr('value');
		$.cookie('mode', value, { expires: 7, path: '/'});
		$(this).addClass('dwon').siblings('b').removeClass('dwon');
		gameMsgAutoTip();
	})	
	$("#basic_slider").live("mouseover",function(){
		$.cookie('fanDian', gameGetFanDian(), { expires: 7, path: '/'});
	})
})
function gameActionRemoveCode(isSelected){
	$('#lt_cf_content tr.lottery').remove();
	$('.fqzhBox :checkbox[name=zhuiHao]').removeData()[0].checked=false;
	gameCalcAmount();
}
function gameAddCode(code){
	wait();
	var actionNo=$.parseJSON($.ajax('/Game/CheckBuy',{async:false}).responseText);
	destroyWait();
	if(actionNo){
		layer.msg('本期投注已截止，请在下一期投注');
		return false;
	}
	if($.isArray(code)){
		for(var i=0; i<code.length; i++) gameAddCode(code[i]);
		return;
	}
	if(code.actionNum==0) throw('号码不正确');
	try{
		code=$.extend({
			fanDian: gameGetFanDian(),
			bonusProp:gameGetPl(code),
			mode: gameGetMode(),
			beiShu: gameGetBeiShu(),
			orderId: (new Date())-2147486647
		}, code);
		var rand=~~(Math.random()*89999999+10000000).toString();
		var weiShu=0, wei='',
		modeName={'2':'元', '0.2':'角', '0.02':'分', '0.002':'厘'},
		amount=code.mode * code.beiShu * code.actionNum,
		$wei=$('#wei-shu'),
		playedName=code.playedName||$('.game-cont .current').text(),
		weiCount=parseInt($wei.attr('length'));
		if(code.playedName) delete code.playedName;
		delete code.isZ6;
		delete code.undefined;
		delete code.isH;
		if($wei.length){
			if($(':checked', $wei).length!=weiCount) throw('请选择'+weiCount+'位置！');
			$(':checked', $wei).each(function(){
				weiShu|=parseInt(this.value);
			});
		}
		code.weiShu=weiShu;
		if(weiShu){
			var w={16:'万', 8:'千', 4:'百', 2:'十',1:'个'}
			for(var p in w){
				if(weiShu & p) wei+=w[p];
			}
			wei+=':';
		}
		$('#num-select input:hidden').each(function(){
			if($(this).attr('name'))
				code[$(this).attr('name')]=this.value;
		});
		$('<tr class="lottery lotteryBg">').data('code', code)
		.append(
			$('<th class="code-list">').append('['+playedName+'] '+wei+(code.actionData.length>37?(code.actionData.substr(0,15)+'...<a href="#" onclick="div_slow_show('+rand+');return(false);" class="orange">详情</a><div id="div_slow_id_'+rand+'" class="more" style="display:none;"><a class="close" href="#" onclick="div_slow_hide('+rand+');return(false);">[关闭]</a><textarea class="code" readonly="readonly">'+code.actionData+'</textarea></div>'):code.actionData))
		)
		.append(
			$('<td>').append('模式:'+parseFloat(code.bonusProp))
		)
		.append(
			$('<td class="orange">').append(''+modeName[code.mode]+'')
		)
		.append(
			$('<td>').data('value', code.actionNum).append(code.actionNum+'注')
		)
		.append(
			$('<td>').append(code.beiShu+'倍')
		)
		.append(
			$('<td class="orange">').data('value', Math.round(amount*1000)/1000).append(Math.round(amount*1000)/1000+"元")
		)
		.append(
			$('<td class="delitem">').append('<a href="javascript:void(0);" class="del"></a>')
		)
		.appendTo('#lt_cf_content');
		$('#textarea-code').val("");
		gameCalcAmount();
		$('.tz-buytype :checkbox[name=zhuiHao]').removeData()[0].checked=false;
		$('.num-table :button.checked').removeClass('checked');
	}catch(err){
		layer.msg(err);
	}
}
//删除事件委托
$(document).ready(function(){
	$('body').on('click','td.delitem',function(){
		$(this).parent('tr').remove();
	})
})
//添加投注
function gamePostCode(){
	var code=[],
	zhuiHao,
	data={},
	is_combine=0,		// 存放共同信息
	amount=0,
	nums=0,
	$zhuiHao=$('.fqzhBox :checkbox[name=zhuiHao]');
	$('.lotteryList .lottery').each(function(){
		code.push($(this).data('code'));
	});
	if(code==""){
		layer.msg('请添加选号进行投注');
		return false;
	}
	$('.fqzhBox :checkbox:checked').each(function(){
		data[$(this).attr('name')]=this.value;
	});
	if($('.fqzhBox :checkbox[name=zhuiHao]')[0].checked){
		var $dom=$('.fqzhBox :checkbox[name=zhuiHao]');
		zhuiHao=$dom.data('zhuiHao');
		data.zhuiHao=1;
		data.zhuiHaoMode=$dom.data('zhuiHaoMode');
	}
	wait();
	var actionNo=$.parseJSON($.ajax('/Game/getNo/'+game.type,{async:false}).responseText);
	destroyWait();
	if(!actionNo){
		layer.msg('获取投注期号出错!');
		return false;
	}
	if($zhuiHao.prop('checked')){
		var zhdata=$('#lt_cf_content tbody tr.lottery').data('code');
		$zhuiHao.data('zhuiHao').split(';').forEach(function(v){
			nums+=parseInt(v.split('|')[1]);
		});
		amount=Math.round(zhdata.mode*zhdata.actionNum*nums*1000)/1000;
		var tipString='<div class="title">你确定追号 '+nums+ ' 期？</div><div class="floatarea">';
		$('#lt_cf_content tr.lottery').each(function(){
			var $this=$(this);
			var scode=$('th:eq(0)', $this).text();
			if(scode.length>37){
				scode=scode.substr(0,36)+' ......';
			}
			tipString+="<p><span>"+$('td:eq(1)', $this).text()+"</span><b>"+scode+"</b></p>";
		});
	}else{
		var tipString='<div class="title">确定投注 '+actionNo['actionNo']+ ' 期？</div><div class="floatarea">';
		$('#lt_cf_content tr.lottery').each(function(){
			var $this=$(this);
			var scode=$('th:eq(0)', $this).text();
			amount+=Math.round(parseFloat($('td:eq(4)', $this).text())*1000)/1000;
			if(scode.length>37){
				scode=scode.substr(0,36)+' ......';
			}
			tipString+="<p><span>"+$('td:eq(1)', $this).text()+"</span><b>"+scode+"</b></p>";
		});
	}
	tipString+='</div>';
	tipString+='<div class="totleNum"><span class="numlabel">总金额:</span> <span>'+amount+' 元</span></div>';
    $.confirm(tipString,function(){
		data['type']=game.type;
		data['actionNo']=actionNo.actionNo;
		data['kjTime']=actionNo.actionTime;
		 if($('.is_combine').prop('checked') == true){
            is_combine = 1;
			}
		wait();
			$.ajax('/Game/PostCode', {
				data:{
					code:code,
					para:data,
					zhuiHao:zhuiHao,
					is_combine:is_combine
				},
				type:'post',
				dataType:'json',
				error:function(xhr, textStatus, errorThrown){
					gamePostedCode(errorThrown||textStatus);
				},
				success:function(data, textStatus, xhr){
					destroyWait();
					gamePostedCode(null, data);
					if(data) layer.msg(data);
					//setTimeout(function(){location.reload();},2000);//投注提交后2秒刷新页面
					$("#cannel_chckbox").attr("checked",false);//提交初始化
				},
				complete:function(xhr, textStatus){
					var errorMessage=xhr.getResponseHeader('X-Error-Message');
					if(errorMessage) gamePostedCode(decodeURIComponent(errorMessage));
				}
			});
		});
		$('.fqzhBox :checkbox[name=zhuiHao]').removeData()[0].checked=false;
		$('#lt_trace_if').removeClass('check').addClass('uncheck');
}
//提交检测
function gamePostedCode(err, data){
	if(err){
		layer.msg(err);
	}else{
		gameActionRemoveCode();
		reloadMemberInfo();
		gameCalcAmount();
		BettingRefresh();
	}
}
function gameCalcAmount(){
	var nums=0, amount=0, $zhuiHao=$('.fqzhBox :checkbox[name=zhuiHao]'), i=0;
	if($zhuiHao.prop('checked')){
		var data=$('#lt_cf_content tbody tr.lottery').data('code');
		$zhuiHao.data('zhuiHao').split(';').forEach(function(v){
			nums+=parseInt(v.split('|')[1]);
		});
		amount=Math.round(data.mode*data.actionNum*nums*1000)/1000;
		i=1;
	}else{
		$('#lt_cf_content tbody tr.lottery').each(function(){
			var $this=$(this);
			nums+=$('td:eq(2)', $this).data('value');
			amount+=$('td:eq(4)', $this).data('value');
			i+=1;
		});
	}
	amount=Math.round(amount*1000)/1000;
	$('#lt_cf_count').text(i);
	$('#lt_cf_nums').text(nums);
	$('#lt_cf_money').text(amount);
}
//添加投注
function CodesAdd(){
	if($('#AgentOn'))
	{
		if(parseInt($('#AgentOn').val())>0){
			layer.msg('代理不能参与投注');
			return false;
		}
	}
	var modeName={'2.000':'元','0.200':'角','0.020':'分','0.002':'厘'},
	$mode=$('.danwei.dwon'),
	$slider=$("#basic_slider"),
	fanDian=gameGetFanDian(),
	modeFanDian=$mode.data().maxFanDian,
	userFanDian=$slider.attr('fan-dian'),
	mode=$mode.attr("value");
	if(userFanDian-fanDian> modeFanDian){
		var pl=$('#fandian-value').data(),
		_amount=(pl.maxpl-pl.minpl)/$slider.attr('game-fan-dian')*modeFanDian+(pl.minpl-0);
		layer.msg(modeName[mode]+'模式最大奖金只能为'+_amount.toFixed(2));
		return false;
	}
	// 单笔中奖限额
	var maxZjAmount=$slider.data().betZjAmount;
	if(maxZjAmount){
		if($('#fandian-value').data('pl') * mode/2 * ($('#beishu').val()||1) > maxZjAmount){
			layer.msg('单笔中奖奖金不能超过'+maxZjAmount+'元');
			return false;
		}
	}
	var obj,$game=$('#num-select .pp'),
	calcFun=$game.attr('action');
	if(calcFun && (calcFun=window[calcFun]) && (typeof calcFun=='function')){
		try{
			obj=calcFun.call($game);
			var maxBetCount=$slider.data().betCount;
			if(maxBetCount && obj.actionNum>maxBetCount){
				layer.msg('单笔投注注数最大不能超过'+maxBetCount+'注');
				return false;
			}
			if(typeof obj!='object'){
				throw('未知出错');
			}else{
				gameAddCode(obj);
			
				$game.find('input.action').removeClass('on');
			}
		}catch(err){
			layer.msg(err);
		}
	}
}
//一键投注
function CodesAddBet(){
	if($('#AgentOn'))
	{
		if(parseInt($('#AgentOn').val())>0){
			layer.msg('代理不能买单');
			return false;
		}
	}
	var modeName={'2.000':'元','0.200':'角','0.020':'分','0.002':'厘'},
	$mode=$('.danwei.dwon'),
	$slider=$("#basic_slider"),
	fanDian=gameGetFanDian(),
	modeFanDian=$mode.data().maxFanDian,
	userFanDian=$slider.attr('fan-dian'),
	mode=$mode.attr("value");
	if(userFanDian-fanDian> modeFanDian){
		var pl=$('#fandian-value').data(),
		_amount=(pl.maxpl-pl.minpl)/$slider.attr('game-fan-dian')*modeFanDian+(pl.minpl-0);
		layer.msg(modeName[mode]+'模式最大奖金只能为'+_amount.toFixed(2));
		return false;
	}
	// 单笔中奖限额
	var maxZjAmount=$slider.data().betZjAmount;
	if(maxZjAmount){
		if($('#fandian-value').data('pl') * mode/2 * ($('#beishu').val()||1) > maxZjAmount){
			layer.msg('单笔中奖奖金不能超过'+maxZjAmount+'元');
			return false;
		}
	}
	var obj,$game=$('#num-select .pp'),
	calcFun=$game.attr('action');
	if(calcFun && (calcFun=window[calcFun]) && (typeof calcFun=='function')){
		try{
			obj=calcFun.call($game);
			var maxBetCount=$slider.data().betCount;
			if(maxBetCount && obj.actionNum>maxBetCount){
				layer.msg('单笔投注注数最大不能超过'+maxBetCount+'注');
				return false;
			}
			if(typeof obj!='object'){
				throw('未知出错');
			}else{
				gameAddCode(obj);
				$game.find('input.action').removeClass('on');
			}
		}catch(err){
			//layer.msg(err);
		}
	}
gamePostCode();
}
//撤单函数
function confirmCancel(){
	var obj=$(this);
	var tipString='<span class="ui-wjicon-confirm"></span>是否确定撤单？';
		var wjDialog=$('#samfea').html(tipString).dialog({
		title:'温馨提示',
		resizable: false,
		width:450,
		minHeight:180,
		modal: true,
		buttons: {
		"确定": function() {
			$( this ).dialog( "close" );
			obj.attr("onajax","");
			obj.click();
			
		},
		"取消": function() {
			$( this ).dialog( "close" );
			
		}
		
		}
		});//dialog end	
    return false;
}
//刷新开奖记录
function HistoryRefresh(err, msg){
	if(err){
		layer.msg(err);
	}else{
		if(game.type==12||game.type==20||game.type==21||game.type==26||game.type==28||game.type==29||game.type==33||game.type==54||game.type==55 || game.type==64){  //PK10
		$('#HistoryRefresh').load('/Game/HistoryRefresh2/'+game.type, reloadMemberInfo);
	}else
		$('#HistoryRefresh').load('/Game/HistoryRefresh/'+game.type, reloadMemberInfo);
	}
}
//刷新投注页订单信息
function BettingRefresh(err, msg){
	if(err){
		layer.msg(err);
	}else{
		$('#BettingRefresh').load('/Game/BettingRefresh/'+game.type, reloadMemberInfo);
	}
}
function BettingDelete(err, data){
	if(err){
		layer.msg(err);
	}else{
		layer.msg(''+ data +'');
		$('#BettingRefresh').load('/Game/BettingRefresh/'+game.type, reloadMemberInfo);
	}
}
//设置反点
function gameSetFanDian(value){
	var $dom=$("#fandian-value"),
	gameFanDian=parseFloat($("#basic_slider").attr('game-fan-dian')),
	myFanDian=parseFloat($("#basic_slider").attr('fan-dian')),
	pl=parseFloat($dom.data('maxpl')),
	minPl=parseFloat($dom.data('minpl')),
	str=(pl-minPl)/gameFanDian*myFanDian+minPl-(pl-minPl)*value/gameFanDian;
	str=str.round(2);	
	if(pl==minPl){
		$("#basic_slider").hide();
	}else{
		$("#basic_slider").show();
	}
	$("#basic_slider").slider('option', 'value', value);
	$dom.data('pl', str);
	$dom.text(str);
	$('#fandian-value2').text(value.round(1)+'%')
}
var FANDIAN=0;
function gameSetPl(value, flag){
	var FANDIAN=0;
	var $dom=$("#basic_slider");
	$('#fandian-value').data('maxpl', value.bonusProp);
	$('#fandian-value').data('minpl', value.bonusPropBase);
	var $slider=$("#basic_slider").closest('.fandian-box');
	if(flag){
		$('.fandian-k').css('visibility','hidden');
	}else{
		$('.fandian-k').css('visibility','visible');
	}
		FANDIAN=FANDIAN||gameGetFanDian();
		gameSetFanDian(FANDIAN);
}
function gameGetFanDian(){
	var $dom=$("#fandian-value"),
	pl=parseFloat($dom.data('maxpl')),
	minPl=parseFloat($dom.data('minpl'));
	var value=$("#basic_slider").slider('option', 'value');
	if(pl==minPl){
		value=0;
	}
	return value;
}
function gameGetPl(code){
	var $dom=$('#num-select .pp');
	if($dom.is('[action=tzSscHhzxInput]')){
		if(code.isZ6){
			var set={
				bonusProp:parseFloat($dom.attr('z6max')),
				bonusPropBase:parseFloat($dom.attr('z6min'))
			};
		}else{
			var set={
				bonusProp:parseFloat($dom.attr('z3max')),
				bonusPropBase:parseFloat($dom.attr('z3min'))
			};
		}
		gameSetPl(set, true);
	}
	return $('#fandian-value').data('pl');
}
// 读取模式
function gameGetMode(){
	return parseFloat($('#game-dom .danwei.dwon').attr('value')||1);
}
function gameGetBeiShu(){
	var txt=$('#beishu').val();
	if(!txt) return 1;
	var re=/^[1-9][0-9]*$/;
	if(!re.test(txt)){
		throw('倍数只能大于1且为正整数');
		$('#beishu').val(1);
	}
	if(isNaN(txt=parseInt(txt))) throw('倍数设置不正确');
	return txt;
}
function DescartesAlgorithm(){
	var i,j,a=[],b=[],c=[];
	if(arguments.length==1){
		if(!$.isArray(arguments[0])){
			return [arguments[0]];
		}else{
			return arguments[0];
		}
	}
	if(arguments.length>2){
		for(i=0;i<arguments.length-1;i++) a[i]=arguments[i];
		b=arguments[i];
		return arguments.callee(arguments.callee.apply(null, a), b);
	}
	if($.isArray(arguments[0])){
		a=arguments[0];
	}else{
		a=[arguments[0]];
	}
	if($.isArray(arguments[1])){
		b=arguments[1];
	}else{
		b=[arguments[1]];
	}
	for(i=0; i<a.length; i++){
		for(j=0; j<b.length; j++){
			if($.isArray(a[i])){
				c.push(a[i].concat(b[j]));
			}else{
				c.push([a[i],b[j]]);
			}
		}
	}
	return c;
}
/* 组合算法*/
function combine(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0) return r.push(t);
		for (var i = 0, l = a.length; i <= l - n; i++) {
			f(t.concat(a[i]), a.slice(i + 1), n - 1);
		}
	})([], arr, num);
	return r;
}
/* 排列算法*/
function permutation(arr, num){
	var r=[];
	(function f(t,a,n){
		if (n==0) return r.push(t);
		for (var i=0,l=a.length; i<l; i++){
			f(t.concat(a[i]), a.slice(0,i).concat(a.slice(i+1)), n-1);
		}
	})([],arr,num);
	return r;
}

//计算注数算法集
function tzAllSelect(){
	var code=[], len=1,codeLen=parseInt(this.attr('length')), delimiter=this.attr('delimiter')||"";
	if(this.has('.checked').length!=codeLen) throw('请选'+codeLen+'位数字');
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			len*=$code.length;
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join(delimiter);
		}
	});
	return {actionData:code.join(','), actionNum:len};
}
/* 排列组选2  除去对子和豹子*/
function tzDesAlgorSelect(){
	var code=[], len=1,codeLen=parseInt(this.attr('length')), delimiter=this.attr('delimiter')||"";
	if(this.has('.checked').length!=codeLen) throw('请选'+codeLen+'位数字');
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join(delimiter);
		}
	});
	code=code.join(',');
	len=DescartesAlgorithm.apply(null, code.split(",").map(function(v){return v.split(delimiter)}))
	.map(function(v){ return v.join(','); })
	.filter(function(v){ return (!isRepeat(v.split(","))) })
	.length;
	return {actionData:code, actionNum:len};
}
  function isRepeat(arr){ 
         var hash = {};  
         for(var i in arr) {  
             if(hash[arr[i]])  
                  return true;  
             hash[arr[i]] = true;  
         }  
         return false;  
    }  
/*大小单双选号*/
function tzDXDS(){
	var code=[], len=1,codeLen=2;
	if(this.has('.checked').length!=codeLen) throw('请选'+codeLen+'种玩法');
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			len*=$code.length;
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join("");
			
		}
	});
	return {actionData:code.join(','), actionNum:len};
}

/*大小单双选号1*/
function tzDXDS1(){
	var code=[], len=1,codeLen=1;
	if(this.has('.checked').length<codeLen) throw('请选'+codeLen+'种玩法');
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			len*=$code.length;
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join("");
			
		}
	});
	return {actionData:code.join(','), actionNum:len};
}
/*大小单双选号*/
function tzDXDSq3h3(){
	var code=[], len=1,codeLen=3;
	if(this.has('.checked').length!=codeLen) throw('请选'+codeLen+'种玩法');
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			len*=$code.length;
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join("");
		}
	});
	return {actionData:code.join(','), actionNum:len};
}
/*趣味选号*/
function qwwf(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	if(this.has('.checked').length!=codeLen) throw('请选'+codeLen+'位数字');
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			len*=$code.length;
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join("");
		}
	});
	return {actionData:code.join(','), actionNum:len};
}
/*五星定位胆选号*/
function tz5xDwei(){
	var code=[], len=0, delimiter=this.attr('delimiter')||"";
	this.each(function(i){
		var $code=$('input.code.checked', this);
		if($code.length==0){
			code[i]='-';
		}else{
			len+=$code.length;
			code[i]=[];
			$code.each(function(){
				code[i].push(this.value);
			});
			code[i]=code[i].join(delimiter);
		}
	});
	if(!len) throw('至少选一个号码');
	return {actionData:code.join(','), actionNum:len};
}
/*不定胆选号*/
function tz5xBDwei(){
	var code="", len=0, $code=$('input.code.checked', this);
	len=$code.length;
	if(!len) throw('至少选一个号码');
	$code.each(function(){
		code+=this.value;
	});
	return {actionData:code, actionNum:len};
}
/* 时时彩录入式投注*/
function tzSscInput(){
	var codeLen=parseInt(this.attr('length')),
	codes=[],
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
	codes=codes.map(function(code){
		return code.split("").join(',')
	});
	return {actionData:codes.join('|'), actionNum:codes.length}
}

/* 时时彩录入式投注*/
function ssc2xzxds(){
	var codeLen=parseInt(this.attr('length')),
	codes=[],
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
	codes=codes.map(function(code){
		return code.split("").join(',')
	});
	codes2=filterArray(codes);
	//if(codes2.toString()!=codes.toString()) samfea("系统已自动过滤重复号码");
	return {actionData:codes2.join('|'), actionNum:codes2.length}
}

/* 时时彩录入式投注*/
function ssc2xzxds(){
	var codeLen=parseInt(this.attr('length')),
	codes=[],
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
	codes=codes.map(function(code){
		return code.split("").sort().join(',');
	});
	codes2=filterArray(codes);
	return {actionData:codes2.join('|'), actionNum:codes2.length}
}
/*11选5录入式投注*/
function tz11x5Input(){
	var codeLen=parseInt(this.attr('length'))*2,
	codes=[],
	ncode,
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
	codes=codes.map(function(code){
		code=code.split("");
		ncode="";
		code.forEach(function(v,i){
			if(i % 2==0 && ncode){	
				 ncode+=","+v;
			}else{ 
				 ncode+=v;
			}
		});
		return ncode;
	});
	return {actionData:codes.join('|'), actionNum:codes.length}
}
function tz11x5Inputrxds(){
	var codeLen=parseInt(this.attr('length'))*2,codes=[],ncode,
	str=$('#textarea-code',this).val().replace(/[^\d]/g,''),codeLen2=codeLen;
	if(codeLen2!=2) codeLen2=2;
	var info=['01','02','03','04','05','06','07','08','09','10','11'];
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入长度不正确');
	}
	var tcodes = codes.join(''),tlen = tcodes.length/codeLen2,arTemp=[];
	for (var i=0;i<tlen;i++) {
		arTemp.push(tcodes.substr(i*codeLen2,codeLen2));
	}
	var code = [],hsTemp = {},reTemp = [],j=codeLen/2;
	if(codeLen==2 && isRepeat(arTemp))  throw('存在重复输入的数据');
	for(var i=0;i<arTemp.length;i++){
		if(info.indexOf(arTemp[i])==-1) throw('输入有误，号码范围01-11');
		if (!hsTemp[arTemp[i]]) {
			hsTemp[arTemp[i]] = true;
		}else{
			reTemp.push(arTemp[i]);
		}
		j-=1;
		if(j==0){j=codeLen/2;hsTemp=[];}
	}
	if(reTemp.length > 0) throw('存在重复输入的数据：' + reTemp.join(','));
	codes=codes.map(function(code){
		code=code.split("");
		ncode="";
		code.forEach(function(v,i){
			if(i % 2==0 && ncode){	
				 ncode+=","+v;
			}else{ 
				 ncode+=v;
			}
		});
		return ncode;
	});
	if(isRepeat(codes)) throw('存在重复的注数');
	return {actionData:codes.join('|'), actionNum:codes.length}
}
/*时时彩录入式组选投注*/
function tzSscZuInput(){
	var codeLen=parseInt(this.attr('length')),
	codes=[];
	$('#textarea-code',this).val().split(/[\r\n]/).forEach(function(str){
		if(str.length && str.length % codeLen == 0){
			if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
			codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
		}else{
			throw('输入号码不正确');
		}
	});
	codes.forEach(function(code){
		if((new RegExp("^(\\d)\\1{"+(codeLen-1)+"}$")).test(code)) throw('组选不能为豹子');
	});
	codes=codes.map(function(code){
		return code.split("").join(',')
	});
	return {actionData:codes.join('|'), actionNum:codes.length}
}
/*时时彩录入式选位数投注*/
function tzSscWeiInput(){
	var codeLen=parseInt(this.attr('length')),
	codes=[],weiShu=[],
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if($('#wei-shu :checked',this).length!=codeLen) throw('请选择'+codeLen+'个位置');
	$('#wei-shu :checkbox',this).each(function(i){
		if(!this.checked) weiShu.push(i);
	});
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
	codes=codes.map(function(code){
		code=code.split("");
		weiShu.forEach(function(v,i){
			code.splice(v, 0, '-');
		});
		return code.join(',');
	});
	return {actionData:codes.join('|'), actionNum:codes.length}
}
/*11选5录入式选位数投注*/
function tz11x5WeiInput(){
	var codeLen=parseInt(this.attr('length')),
	codes=[],weiShu=[],ncode,
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if($('#wei-shu :checked',this).length!=codeLen) throw('请选择'+codeLen+'个位置');
	$('#wei-shu :checkbox',this).each(function(i){
		if(!this.checked) weiShu.push(i);
	});
	codeLen*=2;
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
		codes=codes.map(function(code){
		code=code.split("");
		ncode="";
		code.forEach(function(v,i){
			if(i % 2==0 && ncode){	
				 ncode+=","+v;
			}else{ 
				 ncode+=v;
			}
		});
		ncode=ncode.split(",");
		weiShu.forEach(function(v,i){
			ncode.splice(v, 0, '-');
		});
		return ncode;
	});
	return {actionData:codes.join('|'), actionNum:codes.length}
}
/*时时彩录入式组选位数投注*/
function tzSscZuWeiInput(){
	var codeLen=parseInt(this.attr('length')),
	codes=[],weiShu=[],
	str=$('#textarea-code',this).val().replace(/[^\d]/g,'');
	if($('#wei-shu :checked',this).length!=codeLen) throw('请选择'+codeLen+'个位置');
	$('#wei-shu :checkbox',this).each(function(i){
		if(!this.checked) weiShu.push(i);
	});
	if(str.length && str.length % codeLen == 0){
		if(/[^\d]/.test(str)) throw('投注有错，不能有数字以外的字符。');
		codes=codes.concat(str.match(new RegExp('\\d{'+codeLen+'}', 'g')));
	}else{
		throw('输入号码不正确');
	}
	codes.forEach(function(code){
		if((new RegExp("^(\\d)\\1{"+(codeLen-1)+"}$")).test(code)) throw('组选不能为豹子');
	});
	codes=codes.map(function(code){
		code=code.split("");
		weiShu.forEach(function(v,i){
			code.splice(v, 0, '-');
		});
		return code.join(',');
	});
	return {actionData:codes.join('|'), actionNum:codes.length};
}
/*组合组选*/
function tzCombineSelect(){
	var codeLen=parseInt(this.attr('length')),
	codes='', $select=$('.checked'),len;
	if($select.length<codeLen) throw('请选'+codeLen+'位数');
	$select.each(function(){
		codes+=this.value;
	});
	len=combine(codes.split(""), codeLen).length;
	return {actionData:codes, actionNum:len};
}
function ssc_z3_r6(){
	var codeLen=parseInt(this.attr('length')),
	codes='', $select=$('.checked'),len;
	var $num=$('#num',this).html();
	if($select.length<codeLen) throw('请选'+codeLen+'位数');
	$select.each(function(){
		codes+=this.value;
	});
	len=combine(codes.split(""), codeLen).length*$num;
	return {actionData:codes, actionNum:len};
}
/*排列组选*/
function tzPermutationSelect(){
	var codeLen=parseInt(this.attr('length')),
	codes='', $select=$('.checked'),len;
	if($select.length<codeLen) throw('请选'+codeLen+'位数');
	$select.each(function(){
		codes+=this.value;
	});
	len=permutation(codes.split(""), codeLen).length;
	return {actionData:codes, actionNum:len};
}
/*混合组选录入式投注*/
function tzSscHhzxInput(){
	var codeList=$('#textarea-code').val(),	
	played=this.attr('played'),	
	z3=[],
	z6=[];
	var o={"前":[16,17],"中":[289,290],"后":[19,20],"任选":[22,23],"混":[59,60]};
	if(played=='任选' && $('#wei-shu :checked',this).length!=3) throw('请选3位数');
	codeList=codeList.replace(/[^\d]/gm,'');
	if(codeList.length==0) throw('请输入号码');
	if(codeList.length % 3) throw('输入号码不正确');
	codeList.replace(/[^\d]/gm,'').match(/\d{3}/g).forEach(function(code){
		var reg=/(\d)(.*)\1/;
		if(/(\d)\1{2}/.test(code)){
			throw('组选不能为豹子');
		}else if(reg.test(code)){
			z3.push(code);
		}else{
			z6.push(code);
		}
	});
	if(z3.length && z6.length){
		return [{playedId:o[played][0], playedName:played+'三组三', actionData:z3.join(','), actionNum:z3.length, isZ6:false},
				{playedId:o[played][1], playedName:played+'三组六', actionData:z6.join(','), actionNum:z6.length, isZ6:true}];
	}else if(z3.length){
		return {playedId:o[played][0], playedName:played+'三组三', actionData:z3.join(','), actionNum:z3.length, isZ6:false};
	}else if(z6.length){
		return {playedId:o[played][1], playedName:played+'三组六', actionData:z6.join(','), actionNum:z6.length, isZ6:true};
	}
}

/*十一选五任选玩法投注*/
function tz11x5Select(){
	var code=[], len=1,codeLen=parseInt(this.attr('length')),sType=!!$('.dantuo :radio:checked').val();
	if(sType){
		var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
		if(dLen==0){
			throw('至少选一位胆码');
		}else if(dLen>=codeLen){
			throw('最多只能选择'+(codeLen-1)+'个胆码');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			len=combine(tCode, codeLen-dCode.length).length;
			return {actionData:'('+dCode.join(' ')+')'+tCode.join(' '), actionNum:len};
		}
	}else{
		$(':input:visible.code.checked').each(function(i,o){
			code[i]=o.value;
		});
		if(code.length<codeLen) throw('至少选择'+codeLen+'位数');
		return {actionData:code.join(' '), actionNum:combine(code, codeLen).length};
	}
}

function lhc_2z2(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen<2){
			throw('至少选2位数');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			len=combine(dCode, codeLen).length;
			return {actionData:dCode.join(' '), actionNum:len};
		}
}

function lhc_3z3(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen<3){
			throw('至少选3位数');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			len=combine(dCode, codeLen).length;
			return {actionData:dCode.join(' '), actionNum:len};
		}
}

function lhctmdx(){
	var code=[],len=1,codeLen=parseInt(this.attr('length'));
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen!=1){
			throw('请选择一种形态');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			return {actionData:dCode.join(' '), actionNum:len};
		}
}

function lhc_5bz(){
	var code=[],len=1,codeLen=parseInt(this.attr('length'));
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen!=5){
			throw('请选择5个号码');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			return {actionData:dCode.join(' '), actionNum:len};
		}
}

function lhc_7bz(){
	var code=[],len=1,codeLen=parseInt(this.attr('length'));
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen!=7){
			throw('请选择7个号码');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			return {actionData:dCode.join(' '), actionNum:len};
		}
}

function ssc_5z_120(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen<5){
			throw('至少选5位数');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			len=combine(dCode, codeLen).length;
			return {actionData:dCode.join(','), actionNum:len};
		}
}

function ssczx60(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var c;var anum=0;var bnum=0;var d;
	var sele_count= new Array('0','0','0','1','4','10','20','35','56','84');
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length;
		if(dLen==0){
			throw('至少选一位二重号');
		}else if(tLen<3){
			throw('至少选三位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			num=Sames(dCode,tCode);
		    if(tLen-1>=0){c=tLen-1;}else{c=0;}
	        if(num-1>=0){if(dLen-num==0){anum=sele_count[c]*dLen;}if(dLen-num>0){anum=sele_count[tLen]*(dLen-num)+sele_count[c]*num;}}else{anum=sele_count[tLen]*dLen;}
			len=parseInt(anum);
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssczx30(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var d;
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length;
		if(dLen<2){
			throw('至少选两位二重号');
		}else if(tLen<1){
			throw('至少选一位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			for (i=0;i<dLen-1;i++){d=i+1;for (j=d;j<dLen;j++){for (c=0;c<tLen;c++){if(dCode[i]-tCode[c]!=0 && dCode[j]-tCode[c]!=0){bnum=bnum+1;}}}}
			len=bnum;
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssczx20(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var d;
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length;
		if(dLen<1){
			throw('至少选一位三重号');
		}else if(tLen<2){
			throw('至少选两位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			for (i=0;i<tLen-1;i++){d=i+1;for (j=d;j<tLen;j++){for (c=0;c<dLen;c++){if(tCode[i]-dCode[c]!=0 && tCode[j]-dCode[c]!=0){bnum=bnum+1;}}}}
			len=bnum;
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssczx10(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var c;var d;
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length;
		if(dLen<1){
			throw('至少选一位三重号');
		}else if(tLen<1){
			throw('至少选一位二重号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			for (i=0;i<dLen;i++){for (j=0;j<tLen;j++){if(dCode[i]-tCode[j]!=0){bnum=bnum+1;}}}
			len=bnum;
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssczx5(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var c;var d;
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length;
		if(dLen<1){
			throw('至少选一位四重号');
		}else if(tLen<1){
			throw('至少选一位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			for (i=0;i<dLen;i++){for (j=0;j<tLen;j++){if(dCode[i]-tCode[j]!=0){bnum=bnum+1;}}}
			len=bnum;
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssczx24(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var sele_count= new Array('0','0','0','1','5','15','35','70','126','210');
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen<4){
			throw('至少选择四位！');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			var endnum=0;var num=dCode.length-1;endnum=parseInt(sele_count[num]);
			len=endnum;
			return {actionData:dCode.join(','), actionNum:len};
		}
}
function ssczx12(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var a;var b;var c;
	var anum=0;var bnum=0;var c;var d;
	var sele_count= new Array('0','1','3','6','10','15','21','28','36');
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length;
		if(dLen<1){
			throw('至少选一位二重号');
		}else if(tLen<2){
			throw('至少选两位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			num=Sames(dCode,tCode);  
            if(tLen-1>=0){c=tLen-1;}else{c=0;}
	        if(tLen-2>=0){d=tLen-2;}else{d=0;} 
	        if(num-1>=0){
		    if(dCode.length-num==0){c=tLen-2;anum=sele_count[c]*dCode.length;}
		    if(dCode.length-num>0){c=tLen-2;anum=sele_count[c]*num;anum=anum+sele_count[tLen-1]*(dCode.length-num);}
	        }else{if(tLen-1>=0){c=tLen-1;}else{c=0;}anum=sele_count[c]*dCode.length;}
	        endnum=parseInt(anum);
			len=endnum;
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssczx6(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var sele_count= new Array('0','0','1','3','6','10','15','21','28','36','45');
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen<2){
			throw('至少选择两位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			var endnum=sele_count[dLen];
			len=endnum;
			return {actionData:dCode.join(','), actionNum:len};
		}
}
function ssczx4(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var a;var b;var c;var d_arr=new Array();
	var anum=0;var bnum=0;var d;
	var sele_count= new Array('0','1','2','3','4','5','6','7','8','9');
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length; 
		if(dLen<1){
			throw('至少选一位三重号');
		}else if(tLen<1){
			throw('至少选一位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
		    for(var e=0;e<dCode.length;e++){
		    var this_num=dCode[e];
		    d_arr=drop_array_lines(tCode,this_num); 
		    endnum+=d_arr.length;
	        }
			len=endnum;
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:len};
		}
}
function ssch3zxhz(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var sele_count= new Array('1','2','2','4','5','6','8','10','11','13','14','14','15','15','14','14','13','11','10','8','6','5','4','2','2','1');
	var endnum=0;var num;

	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
		    for (i=0;i<dCode.length;i++){num=dCode[i]-1;endnum=endnum+parseInt(sele_count[num]);} 
			len=endnum;
			return {actionData:dCode.join(','), actionNum:len};
		}
}
function ssch3ts(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));

	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			len=dLen;
			return {actionData:dCode.join(','), actionNum:len};
		}
}
function ssch3kd(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
    var sele_count= new Array('10','54','96','126','144','150','144','126','96','54');
	var endnum=0;var num;
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			for(i=0;i<dCode.length;i++){num=dCode[i];if(num-1>=-1){endnum=endnum+parseInt(sele_count[num]);}}
			len=endnum;
			return {actionData:dCode.join(','), actionNum:len};
		}
}

function sscq3qw2x(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var a;var b;var c;var d_arr=new Array();
	var anum=0;var bnum=0;var c;var d;
	var sele_count= new Array('0','1','2','3','4','5','6','7','8','9');
	var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
	    tLen=$('.code.checked', $t).length; 
		if(dLen<1){
			throw('至少选一位三重号');
		}else if(tLen<1){
			throw('至少选一位单号');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
		    for(var e=0;e<dCode.length;e++){
		    var this_num=dCode[e];
		    d_arr=drop_array_lines(tCode,this_num); 
		    endnum+=d_arr.length;
	        }
			return {actionData:dCode.join('')+','+tCode.join(''), actionNum:endnum};
		}
}

function ssc2xh2zxbd(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
    var endnum=0;var num=0;var a;var b;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var c;var d;var alist= new Array;var blist= new Array
	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			var endnum=0;var num=0;var a;var b;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var c;var d;var alist= new Array;var blist= new Array 
	        for (j=0;j<10;j++){for (c=j;c<10;c++){if(j-c!=0){if(dCode-c==0 || dCode-j==0){bnum=bnum+1;}}}} 
			return {actionData:dCode.join(','), actionNum:bnum};
		}
}

function zxhz3d(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var sele_count= new Array('1','3','6','10','15','21','28','36','45','55','63','69','73','75','75','73','69','63','55','45','36','28','21','15','10','6','3','1');
	var endnum=0;var num;

	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
		    for (i=0;i<dCode.length;i++){num=dCode[i];endnum=endnum+parseInt(sele_count[num]);} 
			len=endnum;
			return {actionData:dCode.join(','), actionNum:len};
		}
}

function zuxhz3d(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var sele_count= new Array('1','2','2','4','5','6','8','10','11','13','14','14','15','15','14','14','13','11','10','8','6','5','4','2','2','1');
	var endnum=0;var num;

	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
		    for (i=0;i<dCode.length;i++){num=dCode[i]-1;endnum=endnum+parseInt(sele_count[num]);} 
			len=endnum;
			return {actionData:dCode.join(','), actionNum:len};
		}
}

function sscq2zhixhz(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var a;var b;var c;var anum=0;var bnum=0;var cnum=0;var bnum=0;var d;var alist= new Array;var blist= new Array;

	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
		    alist=dCode;a=dLen;
			for (i=0;i<a;i++){for (j=0;j<10;j++){for (c=0;c<10;c++){if(j+c-alist[i]==0){bnum=bnum+1;}}}}
			return {actionData:dCode.join(','), actionNum:bnum};
		}
}

function sscqh2zhuxhz(){
	var code=[], len=1,codeLen=parseInt(this.attr('length'));
	var endnum=0;var num=0;var a;var b;var c;var d;var anum=0;var bnum=0;var cnum=0;var alist= new Array;var blist= new Array;

	var $d=$(this).filter(':visible:first'),
		dLen=$('.code.checked', $d).length;
        
		if(dLen<1){
			throw('至少选择一位！');
		}else{
			var dCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
		    alist=dCode;a=dLen;
			for (i=0;i<a;i++){b=alist[i];for (j=0;j<10;j++){for (c=j;c<10;c++){if(j-c!=0){if(b-j-c==0){bnum=bnum+1;}}}}}
			return {actionData:dCode.join(','), actionNum:bnum};
		}
}

/*快乐十分任选玩法投注*/
function tzKLSFSelect(){
	var code=[], len=1,codeLen=parseInt(this.attr('length')),sType=!!$('.dantuo :radio:checked').val();
	if(sType){
		var $d=$(this).filter(':visible:first'),
		$t=$d.next(),
		dLen=$('.code.checked', $d).length;
		
		if(dLen==0){
			throw('至少选一位胆码');
		}else if(dLen>=codeLen){
			throw('最多只能选择'+(codeLen-1)+'个胆码');
		}else{
			var dCode=[],tCode=[];
			$('.code.checked', $d).each(function(i,o){
				dCode[i]=o.value;
			});
			$('.code.checked', $t).each(function(i,o){
				tCode[i]=o.value;
			});
			len=combine(tCode, codeLen-dCode.length).length;
			return {actionData:'('+dCode.join(' ')+')'+tCode.join(' '), actionNum:len};
		}
	}else{
		$(':input:visible.code.checked').each(function(i,o){
			code[i]=o.value;
		});
		if(code.length<codeLen) throw('至少选择'+codeLen+'位数');
		return {actionData:code.join(' '), actionNum:combine(code, codeLen).length};
	}
}
function GetRandomNum(Min,Max)
{   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
}
function Sames(a,b){
	var num=0;
	for (i=0;i<a.length;i++)
	{   var zt=0;
		for (j=0;j<b.length;j++)
		{
			if(a[i]-b[j]==0){
				zt=1;
			}
		}
		if(zt==1){
			num+=1; 
		}
	}
	return num;
}
function drop_array_lines(arr,num){
	var drop_arr=new Array();
	for(o=0;o<arr.length;o++){
		if(parseInt(arr[o],10)-parseInt(num,10)==0){ 
			 
		}else{
			drop_arr.push(arr[o]); 
		}
	}
	return drop_arr;
}
function samfea(tips) {			
	 layer.open({
		title: ['提示', 'font-size:18px;'],
		content: '<div style="text-align: center;padding: 20px 0px;font-size: 20px;">'+ tips +'</div>',
		area: ['450px', '200px'],//宽 长
		timeout: 2,
		onyes: true,
		btn:"确定3",
		btnAlign: 'c' //按钮居中
	});
		
}

function Combination(c, b) {
    b = parseInt(b);
    c = parseInt(c);
    if (b < 0 || c < 0) {
        return false
    }
    if (b == 0 || c == 0) {
        return 1
    }
    if (b > c) {
        return 0
    }
    if (b > c / 2) {
        b = c - b
    }
    var a = 0;
    for (i = c; i >= (c - b + 1) ; i--) {
        a += Math.log(i)
    }
    for (i = b; i >= 1; i--) {
        a -= Math.log(i)
    }
    a = Math.exp(a);
    return Math.round(a)
}
function strCut(str, len){
	var strlen = str.length;
	if(strlen == 0) return false;
	var j = Math.ceil(strlen / len);
	var arr = Array();
	for(var i=0; i<j; i++)
		arr[i] = str.substr(i*len, len)
	return arr;
}

function filterArray(arrs){
    var k=0,n=arrs.length; 
	var arr = new Array(); 
    for(var i=0;i<n;i++)
    {
        for(var j=i+1;j<n;j++)
        {
            if(arrs[i]==arrs[j])
            {
                arrs[i]=null;
                break;
            }
        }
    }    
    for(var i=0;i<n;i++)
    {
        if(arrs[i])
        {
            arr[k++]=arrs[i]; // arr.push(this[i]);
        }
    } 
    return arr;
}
function scoreBeforeSwapGood(){
	if(!this.address.value) throw('请填写邮寄地址');
	if(!this.mobile.value) throw('请填写收件电话');
	if(!this.coinpwd.value) throw('请填写资金密码');
}
function IntegralSet(){
	if(!this.number.value) throw('请填写兑换数量');
	if(isNaN(this.number.value)) throw('兑换数量必须为整数');
	if(!this.coinpwd.value) throw('请填写资金密码');
}
function IntegralSwSet(){
	if(!this.number.value) throw('请填写兑换数量');
	if(isNaN(this.number.value)) throw('兑换数量必须为整数');
	if(!this.contacts.value) throw('请填写联系人');
	if(!this.mobile.value) throw('请填写联系手机');
	if(!this.address.value) throw('请填写收货地址');
	if(!this.coinpwd.value) throw('请填写资金密码');
}
//消息加载
function MessageNew(){
	location.href="/Member/Information";
}
function wait(){
  layer.msg('数据提交中，请稍后....',{time: 2});
}
function destroyWait(){
	$.unblockUI({fadeInTime: 0, fadeOutTime: 0});
}

function defaultModalCloase(event, ui){
	$(this).dialog('destroy');
}
function dataAddCode(){
	$('form', this).trigger('submit');
}

function delLink(err, data){
	if(err){
		samfea(err,"err");
	}else{
		layer.open({
                content:'注册链接删除成功',
                btn:['确定'],
                yes:function(){
				window.location.reload(); 
                }
            })
	}
}
//base64加密
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(
　　-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
　　-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
　　-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
　　52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
　　-1,　0,　1,　2,　3,  4,　5,　6,　7,　8,　9, 10, 11, 12, 13, 14,
　　15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
　　-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
　　41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
function base64_encode(str) {
　　var out, i, len;
　　var c1, c2, c3;
　　len = str.length;
　　i = 0;
　　out = "";
　　while(i < len) {
 c1 = str.charCodeAt(i++) & 0xff;
 if(i == len)
 {
　　 out += base64EncodeChars.charAt(c1 >> 2);
　　 out += base64EncodeChars.charAt((c1 & 0x3) << 4);
　　 out += "==";
　　 break;
 }
 c2 = str.charCodeAt(i++);
 if(i == len)
 {
　　 out += base64EncodeChars.charAt(c1 >> 2);
　　 out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
　　 out += base64EncodeChars.charAt((c2 & 0xF) << 2);
　　 out += "=";
　　 break;
 }
 c3 = str.charCodeAt(i++);
 out += base64EncodeChars.charAt(c1 >> 2);
 out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
 out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
 out += base64EncodeChars.charAt(c3 & 0x3F);
　　}
　　return out;
}
function sendok(){
	var ycode=[];
	$('#lt_cf_content .lottery').each(function(){
		ycode.push($(this).data('code'));
	});
	if(ycode.length>0){
		$('#lt_sendok').removeClass('sendBtnDisabled');
		$('#lt_sendok').bind('click',gamePostCode);
	}else{
		$('#lt_sendok').addClass('sendBtnDisabled');
		$('#lt_sendok').unbind('click');
	}
}
function BetInfo(num){
	layer.open({
	  type: 2,
	  area: ['450px', '650px'],
	  zIndex:1888,
	  title:'订单详情',
	  scrollbar: false,
	  resize:false,
	  content:'/Member/BetInfo/'+num
	});
	return false;
}