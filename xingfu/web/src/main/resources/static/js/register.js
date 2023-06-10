/*
// 註冊Ajax請求
// 香港寰宇科技
*/
	$('form[target=ajax]').live('submit', function(){
		var data	= [], 
		$this		= $(this),
		self		= this,
		onajax		= window[$this.attr('onajax')],
		call		= window[$this.attr('call')];
		//羅盤判斷
		if(typeof call!='function'){
			call=function(){}
		}
		if('function'==typeof onajax){
			try{
				if(onajax.call(this)===false) return false;
			}catch(err){
				call.call(self, err);
				return false;
			}
		}
		//判斷用戶名
		$(':input[name]', this).each(function(){
			var $this=$(this),
			value=$this.data('value'),
			name=$this.attr('name');
			if($this.is(':radio, :checkbox') && this.checked==false) return true;
			if(value===undefined) value=this.value;
			data.push({name:name, value:value});
		});
		//登錄提交
		$.ajax({
			url:$this.attr('action'),
			async:true,
			data:data,
			type:$this.attr('method')||'get',
			dataType:$this.attr('dataType')||'json',
			headers:{"x-form-call":1},
			error:function(xhr, textStatus, errThrow){
				call.call(self, errThrow||textStatus);
			},
			success:function(data, textStatus, xhr, headers){
				var errorMessage=xhr.getResponseHeader('X-Error-Message');
				if(errorMessage){
					call.call(self, decodeURIComponent(errorMessage), data);
				}else{
					call.call(self, null, data);
				}
			}
		});
		return false;
	});
function UserLoginSet(){
	if(!this.username.value){layer.msg("請輸入賬號");return false;}
}
function UserLogin(err, data){
	if(err){
		layer.msg(err);
		$("#vcode").trigger("click");
	}else{
		location='/';
	}
}
//在線客服
function zxkf(){
	 layer.open({
	  type: 2,
	  area: ['504px', '536px'],
	  zIndex:9999,
	  fix: false, 
	  top:0,
	  title:'在線客服',
	  scrollbar: false,
	  content:'/User/Service'
	});
	  return false;
}
//註冊檢查
function MemberAdd(){
	var type=$('[name=type]:checked',this).val();
	if(!this.username.value){layer.msg("請輸入用戶名");return false;}
	if(!/^\w{5,16}$/.test(this.username.value)){layer.msg("用戶名由5到16位的字母或數字組成");return false;}
	if(!this.password.value){layer.msg("請輸入登錄密碼");return false;}
	if(this.password.value.length<6){layer.msg("登錄密碼至少6位數");return false;}
	if(!this.cpasswd.value){layer.msg("請確認登錄密碼");return false;}
	if(document.getElementById('cpasswd').value!=this.password.value){layer.msg("兩次輸入的登錄密碼不壹樣");return false;}
}
function UserRegister(err, data){
	if(err){
		layer.msg(err);
		$("#vcode").trigger("click");
	}else{
		layer.open({
                content:'恭喜，賬戶成功創建',
				timeout: 2,
                btn:"馬上登入",
                yes:function(){
                    window.location='/User/Login';
                }
            })
	}
}
function AgentApply(){
	if(!this.name.value){layer.msg("輸入平臺名稱");return false;}
	if(!this.num.value){layer.msg("輸入團隊人數");return false;}
	if(!this.qq.value){layer.msg("輸入聯系QQ");return false;}
	if(this.qq.value.length<5){layer.msg("QQ號碼至少為5位數");return false;}
}
function AgentApplySet(err, data){
	if(err){
		layer.msg(err);
		$("#vcode").trigger("click");
	}else{
		layer.msg(data);
	}
}