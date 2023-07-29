$('.game-btn a[href]').on('click', function(){
    let $this=$(this);
    if($this.is('.current')) return false;
    console.info("$this.attr('href'):",$this.attr('href'));
    $('#playMethodListRegion').load($this.attr('href'), function(){
        $this.closest('.game-btn').find('.current').removeClass('current');
        $this.closest('div').addClass('current');
        $('.game-btn2 a[href]:first').trigger('click');
    });
    return false;
});



// function loadPlayTips(playedid){
// 	$('#game-helptips').load('/Game/Help/'+playedid);
// }

/*
$(function(){
	$('input.code').on('click', function(){
		var $this=$(this);
		if($this.is('.checked')){
			$this.removeClass('checked');
		}else{
			$this.addClass('checked');
		}
		gameCalcAmount();
	});
	// if($.browser.msie){
	// 	$('a, :button, :radio, :checkbox').on('focus', function(){
	// 		this.blur();
	// 	});
	// }
	$('input.action').on('click', function(){
		var $this=$(this),
		call=$this.attr('action'),
		pp=$this.parent();
		$this.addClass("on").siblings(".action").removeClass("on");
		if(call && $.isFunction(call=window[call])){
			call.call(this, pp);
		}else if($this.is('.all')){
			$('input.code',pp).addClass('checked');
		}else if($this.is('.large')){
			$('input.code.max',pp).addClass('checked');
			$('input.code.min',pp).removeClass('checked');
		}else if($this.is('.small')){
			$('input.code.min',pp).addClass('checked');
			$('input.code.max',pp).removeClass('checked');
		}else if($this.is('.odd')){
			$('input.code.d',pp).addClass('checked');
			$('input.code.s',pp).removeClass('checked');
		}else if($this.is('.even')){
			$('input.code.s',pp).addClass('checked');
			$('input.code.d',pp).removeClass('checked');
		}else if($this.is('.none')){
			$('input.code',pp).removeClass('checked');
		}
	});


	$('td.code-list').on('click', function(){

		var data=$(this).parent().data('code');
		layer.open({
		  type: 1,
		  title:'投注号码',
		  area: ['420px', '240px'], //宽高
		  content:'<div style="width:100%;height:100%;word-wrap:break-word;color:#828282;font-size:20px;">'+data.actionData+'</div>'
		});
		//displayCodeList(data);
	});




	$('.touzhu-cont .del').on('click', function(){
		$(this).closest('tr').remove();
		$('.touzhu-bottom :checkbox[name=zhuiHao]').removeData()[0].checked=false;
		gameCalcAmount();
	});
	$('#CodesBet').unbind('click');
	$('#CodesBet').bind('click',gamePostCode);
	$("#basic_slider").each(function(){
		var $this=$(this),
		onslide, attr={};
		['value', 'min', 'max', 'step'].forEach(function(p){
			if(!isNaN(value=parseFloat($this.attr(p)))){
				attr[p]=value;
			}
		});
		if(onslide=$this.attr('slideCallBack')){
			if(typeof window[onslide]=='function'){
				attr.slide=function(event, ui){
					window[onslide].call(this, ui.value);
				}
			}
		}
		$this.slider().find('.ui-slider-handle').remove()
	});
	$('.fandian-box input').click(function(){
		var $slider=$('#basic_sliders'),
		min=parseFloat($slider.attr('min')),
		max=parseFloat($slider.attr('max')),
		value=$slider.slider('option', 'value');

		value+=parseFloat($(this).attr('step'));
		if(value<min) value=min;
		if(value>max) value=max;

		$slider.slider('option', 'value', value);
		gameSetFanDian.call($slider[0], value);
	});
	$('#textarea-code').on('keypress', function(event){
		event.keyCode=event.keyCode||event.charCode;
		return !!(
			event.ctrlKey
			|| event.altKey
			|| event.shiftKey
			|| event.keyCode==13
			|| event.keyCode==8
			|| (event.keyCode>=48
			&& event.keyCode<=57)
		);
	}).on('keyup', gameMsgAutoTip);
	$('#textarea-code').on('change', function(){
		var str=$(this).val();
		if(/[a-zA-Z]+/.test(str)){
			layer.msg('投注号码不能含有字母字符');
			$(this).val('');
		}
	});
	$('.pp :button, :radio[name=danwei]').on('click', gameMsgAutoTip);
	$('#beishu').on('change', gameMsgAutoTip);
	$('.surbeishu').on('click', function(){
		var newVal=parseInt($('#beishu').val())-1;
		if(newVal<1) newVal=1;
		$('#beishu').val(newVal);
		gameMsgAutoTip();
	});
	$('.addbeishu').on('click', function(){
		var newVal=parseInt($('#beishu').val())+1;
		$('#beishu').val(newVal);
		gameMsgAutoTip();
	});
	$('.touzhu-bottom :checkbox[name=zhuiHao]')
	.click(function(){
		var bet=$('#lt_cf_content tr.lottery'),zhlen=bet.length;
		len=bet.length
		if(len==0){
			layer.msg('请添加一组选号');
			return false;
		}else if(len>1){
			layer.msg('只能针对一组选号发起追号');
			return false;
		}
		setGameZhuiHao(bet.data('code'));
		return false;
	});
	$('.touzhu-bottom :checkbox[name=fpEnable]')
	.click(function(){
		var bet=$('.lt_cf_contentt tbody tr'),
		len=bet.length,
		amount=parseInt($('#all-amount').text());
		if($(this).attr("checked")){
			if(len==0){
				layer.msg('请选1投注');
				return false;
			}
			amount*=2;
			$('#all-amount').text(amount.round(2));
		}else{
			amount/=2;
			$('#all-amount').text(amount.round(2));
			}
		return true;
	});
	$('.zhui-hao-modal thead :checkbox').on('change', function(){
		$(this).closest('table').find('tbody :checkbox').prop('checked', this.checked).trigger('change');
	});
	$('.zhui-hao-modal tbody :checkbox').on('change', function(){
		var $this=$(this),
		$ui=$this.closest('div[rel]'),
		data=$ui.data(),
		amount=$ui.attr('rel') * Math.abs($this.closest('tr').find('.beishu').val()),
		$show=$ui.closest('.zhui-hao-modal').find('.ui-dialog-title');
		if(!data.count){
			data.count=0;
			data.amount=0;
		}
		if(this.checked){
			data.count++;
			data.amount+=amount;
		}else{
			data.count--;
			data.amount-=amount;
		}
		$('.qs', $show).text(data.count);
		$('.amount', $show).text(Math.round(data.amount*100)/100);
		$this.closest('tr').find('.amount').text(Math.round(amount*100)/100);
		$this.data('amount', amount);
	});
	$('.zhui-hao-modal tbody .beishu').on('change', function(e){
		var $this=$(this);
		var re=/^[1-9][0-9]*$/;
		if(!re.test($this.val())){layer.msg('倍数只能为正整数');$this.val(1);return;}
		if(!$this.closest('tr').find(':checkbox')[0].checked) return ;

		var $ui=$this.closest('div[rel]'),
		data=$ui.data(),
		$checkbox=$this.closest('tr').find(':checkbox'),
		_amount=Math.abs($checkbox.data('amount'));
		amount=$ui.attr('rel') * Math.abs($this.val()),
		$show=$ui.closest('.zhui-hao-modal').find('.ui-dialog-title');
		data.amount+=amount-_amount;
		$checkbox.data('amount', amount);
		$('.qs', $show).text(data.count);
		$('.amount', $show).text(Math.round(data.amount*100)/100);
		$this.closest('tr').find('.amount').text(Math.round(amount*100)/100);
	});
	$('.game-btn a[href]').on('click', function(){
		var $this=$(this);
		if($this.is('.current')) return false;
		$('.game-btn2').load($this.attr('href'), function(){
			$this.closest('.game-btn').find('.current').removeClass('current');
			$this.closest('div').addClass('current');
			$('.game-btn2 a[href]:first').trigger('click');
		});
		return false;
	});
	$('.game-btn2 a[href]').on('click', function(){
		var $this=$(this);
		loadPlayTips($this.attr('data_id'));
		$('#num-select').load($this.attr('href'), function(){
			$this.closest('.game-btn2').find('.current').removeClass('current');
			$this.parent().addClass('current');
		});
		return false;
	});
	$('.showhelp .showeg').on("mouseover",function(){
		var $action=$(this).attr('action');
		var ps = $(this).position();
		$('#'+$action+'s_div').siblings('.game_eg').hide();
		$('#'+$action+'s_div').css({top:ps.top + 20,left:ps.left + 20}).fadeIn(100);
	})
	$('.showhelp .showeg').on("mouseout",function(){
		$('#game-helptips').find('.game_eg').hide();
	})
	$('.kjabtn').on('click', function(){
		var $this=$(this);
		$this.closest('.kaijiangall').find('ul').load($this.attr('src'), function(){
			$('.kjabtn.current').removeClass('current');
			$this.addClass('current');
		});
	});
	$('.jiang').on('click', function(){
		var $this=$(this);
		if($this.is('.current')) return true;
		$('.jiang.current').removeClass('current');
		$this.addClass('current');
		return true;
	})
	.find('select').on('change', function(){
		$('.zj-cont tbody').load(this.value);
	});
	$('.dantuo :radio').on('click', function(){
		var $dom=$(this).closest('.dantuo');
		if(this.value){
			$dom.next().hide().next().show();
		}else{
			$dom.next().show().next().hide();
		}
	});
	$('.dmtm :input.code').on('click',function(event){
		var $this=$(this),
		$dom=$this.closest('.dmtm');
		if($('.code.checked[value=' + this.value +']', $dom).not(this).length==1){
			$this.removeClass('checked');
			layer.msg('选择胆码不能与拖码相同');
			return false;
		}
	});
	$('.zhixu115 :input.code').on('click',function(event){
		var $this=$(this);
		if(!$this.is('.checked')) return false;

		var $dom=$this.closest('.zhixu115');
		$('.code.checked[value=' + this.value +']', $dom).removeClass('checked');
		$this.addClass('checked');
	});
	if(getVoiceStatus()=='off'){
		$('#voice').removeClass('voice-on').addClass('voice-off').attr('title', '声音关闭，点击开启');
	}
});
var MaxZjCount={
	ds:function(){
		var zjCount=0,t=1,s;
		$.each(this.split('|').sort(), function(){
			if(s==String(this)){
				t++;
			}else{
				s=this;
				if(t>zjCount) zjCount=t;
				t=1;
			}
		});
		if(t>zjCount) zjCount=t;

		return zjCount;
	},
	fs:function(){
		return 1;
	},
	dxds:function(){
		var d=this.split(',').map(function(v){
			return v
			.replace('单','双')
			.replace('大', '小')
			.split("").sort().join('')
			.replace(/双{2,}/,'双')
			.replace(/小{2,}/,'小')
			.length;
		});
		return d[0] * d[1];
	},
	dd5x:function(){
		return this.split(',').filter(function(v){return v!='-'}).length;
	},
	bdd:function(){
		return this.length>3?3:this.length;
	}
}


*/