$(function() {
	//导航链接
	$("span.mail").click(function() {
		window.open('/Member/Information', '_self')
	});
	$("span.btn-binding").click(function() {
		window.open('/Member/Dinding', '_self')
	});
	$("div.home").click(function() {
		window.open('/', '_self')
	});
	$("div.team,li.mine").click(function() {
		window.open('/Member/Index', '_self')
	});
	$("div.jinding").click(function() {
		window.open('/Agent/Index', '_self')
	});

	//广告弹窗
	$("li.video,span.detail,span.resize").click(function() {
		common_open_layer('960px', '640px', false, true, 'https://cmxv2z2b.com?sh=AT78W');
	});

	$("._1OpRJUrdnhc8Is86dxwPtA .lite1").click(function() {
		common_open_layer('960px', '640px', false, true, '/Index/View/66');
	});

	//彩票链接
	//var info = [["/Game/Cqssc", "怀旧重庆彩"], ["/Game/Hs2fc", "多彩腾讯分分彩"], ["/Game/Txffc", "奇趣腾讯分分彩"], ["/Game/Bx90m", "河内一分彩"], ["/Game/Qt5fc", "河内五分彩"], ["/Game/Tjssc", "重庆欢乐生肖"], ["/Game/Xjssc", "新疆时时彩"], ["/Game/Bjsc", "北京赛车"], ["/Game/Xyft", "幸运飞艇"], ["/Game/Sh11", "上海11选5"], ["/Game/Sd11", "山东11选5"], ["/Game/Jsk3", "江苏快三"], ["/Game/Fc3d", "福彩3D"], ["/Game/Pl3", "排列3"], ["/Game/Twbg", "重庆分分彩"], ["/Game/Jnd5fc", "西贡5分彩"], ["/Game/Jxssc", "腾讯二分彩"], ["/Game/Bx5fc", "重庆五分彩"]];
	// $("li.menu-item,div.hot-lottery-item,div.reconmmend-item-box").click(function() {
	// 	var url = "";
	// 	var name = $(this).text().replace(/^\s\s*/, '').replace(/\s\s*$/, '')
	// 	for (var i = 0; i < info.length; i++) {
	// 		if (name.indexOf(info[i][1]) == 0) {
	// 			url = info[i][0];
	// 			break
	// 		}
	// 	} 
	// 	if (url != "") {
	// 		window.open(url, '_self')
	// 	}
	// });

	//电游链接
	$(".menus .menu-item .actions button").click(function() {
		var button = $(this);
		if (button.hasClass('start')) {
			common_info_hint('请先进行充值后，在进入游戏。');
		}
		if (button.hasClass('transfer')) {
			common_open_layer('500px', '350px', '资金转账', false, '/Index/Transfer/' + Base64.encode(button.attr('name')));
		}
	});

	//滚动导航
	var bannerItems = $('.swiper-container .swiper-slide');
	if (bannerItems.length > 1) {
		if (window.Swiper) {
			var mySwiper = new Swiper('.swiper-container', {
				loop: true,
				grabCursor: true,
				paginationClickable: true,
				autoplay: 3000,
				autoplayDisableOnInteraction: false,
				speed: 300
			});
			$('#prev_btn').on('click',function(e) {
				e.preventDefault();
				mySwiper.swipePrev()
			});
			$('#next_btn').on('click',function(e) {
				e.preventDefault();
				mySwiper.swipeNext()
			})
		}
	} else {
		$(".controller,.swiper-pagination").hide()
	}

	//滚动公告
	var num = $("#notice-box").find("div").length;
	if (num > 1) {
		setInterval(function() {
			$('#notice-box').animate({
				marginTop: "-26px"
			},
			500,
			function() {
				$(this).css({
					marginTop: "0"
				}).find("div:first").appendTo(this)
			})
		},
		3000)
	}
});

//公用提示弹窗
function common_open_layer(width, height, title, close, url) {
    layer.open({
        title: title,
        type: 2,
        area: [width, height],
        fixed: true,
        shade: 0.45,
        shadeClose: close,
        closeBtn: title ? 1 : 0,
        resize: false,
        content: url
    });
}

//公用信息提示(自动关闭)
function common_info_hint(info) {
    var index = layer.load(2, {time: 5 * 1000});
    setTimeout(function() {
        layer.close(index);
        layer.alert(info, {
            time: 3 * 1000,
            success: function(layero, index) {
                var timeNum = this.time / 1000,
                setText = function(start) {
                    layer.title((start ? timeNum : --timeNum) + ' 秒后自动关闭', index);
                };
                setText(!0);
                this.timer = setInterval(setText, 1000);
                if (timeNum <= 0) clearInterval(this.timer);
            },
            end: function(){
                clearInterval(this.timer);
            }
        });
    }, 1000);
}

//编码加密解密
var Base64 = {
    // 转码表
    table : [
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O' ,'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'
    ],
    UTF16ToUTF8 : function(str) {
        var res = [], len = str.length;
        for (var i = 0; i < len; i++) {
            var code = str.charCodeAt(i);
            if (code > 0x0000 && code <= 0x007F) {
                // 单字节，这里并不考虑0x0000，因为它是空字节
                // U+00000000 – U+0000007F     0xxxxxxx
                res.push(str.charAt(i));
            } else if (code >= 0x0080 && code <= 0x07FF) {
                // 双字节
                // U+00000080 – U+000007FF     110xxxxx 10xxxxxx
                // 110xxxxx
                var byte1 = 0xC0 | ((code >> 6) & 0x1F);
                // 10xxxxxx
                var byte2 = 0x80 | (code & 0x3F);
                res.push(
                    String.fromCharCode(byte1),
                    String.fromCharCode(byte2)
                );
            } else if (code >= 0x0800 && code <= 0xFFFF) {
                // 三字节
                // U+00000800 – U+0000FFFF     1110xxxx 10xxxxxx 10xxxxxx
                // 1110xxxx
                var byte1 = 0xE0 | ((code >> 12) & 0x0F);
                // 10xxxxxx
                var byte2 = 0x80 | ((code >> 6) & 0x3F);
                // 10xxxxxx
                var byte3 = 0x80 | (code & 0x3F);
                res.push(
                    String.fromCharCode(byte1),
                    String.fromCharCode(byte2),
                    String.fromCharCode(byte3)
                );
            } else if (code >= 0x00010000 && code <= 0x001FFFFF) {
                // 四字节
                // U+00010000 – U+001FFFFF     11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
            } else if (code >= 0x00200000 && code <= 0x03FFFFFF) {
                // 五字节
                // U+00200000 – U+03FFFFFF     111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
            } else /** if (code >= 0x04000000 && code <= 0x7FFFFFFF)*/ {
                // 六字节
                // U+04000000 – U+7FFFFFFF     1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
            }
        }
        return res.join('');
    },
    UTF8ToUTF16 : function(str) {
        var res = [], len = str.length;
        var i = 0;
        for (var i = 0; i < len; i++) {
            var code = str.charCodeAt(i);
            // 对第一个字节进行判断
            if (((code >> 7) & 0xFF) == 0x0) {
                // 单字节
                // 0xxxxxxx
                res.push(str.charAt(i));
            } else if (((code >> 5) & 0xFF) == 0x6) {
                // 双字节
                // 110xxxxx 10xxxxxx
                var code2 = str.charCodeAt(++i);
                var byte1 = (code & 0x1F) << 6;
                var byte2 = code2 & 0x3F;
                var utf16 = byte1 | byte2;
                res.push(Sting.fromCharCode(utf16));
            } else if (((code >> 4) & 0xFF) == 0xE) {
                // 三字节
                // 1110xxxx 10xxxxxx 10xxxxxx
                var code2 = str.charCodeAt(++i);
                var code3 = str.charCodeAt(++i);
                var byte1 = (code << 4) | ((code2 >> 2) & 0x0F);
                var byte2 = ((code2 & 0x03) << 6) | (code3 & 0x3F);
                var utf16 = ((byte1 & 0x00FF) << 8) | byte2
                res.push(String.fromCharCode(utf16));
            } else if (((code >> 3) & 0xFF) == 0x1E) {
                // 四字节
                // 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
            } else if (((code >> 2) & 0xFF) == 0x3E) {
                // 五字节
                // 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
            } else /** if (((code >> 1) & 0xFF) == 0x7E)*/ {
                // 六字节
                // 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
            }
        }
        return res.join('');
    },
    encode : function(str) {
        if (!str) {
            return '';
        }
        var utf8 = this.UTF16ToUTF8(str); // 转成UTF8
        var i = 0; // 遍历索引
        var len = utf8.length;
        var res = [];
        while (i < len) {
            var c1 = utf8.charCodeAt(i++) & 0xFF;
            res.push(this.table[c1 >> 2]);
            // 需要补2个=
            if (i == len) {
                res.push(this.table[(c1 & 0x3) << 4]);
                res.push('==');
                break;
            }
            var c2 = utf8.charCodeAt(i++);
            // 需要补1个=
            if (i == len) {
                res.push(this.table[((c1 & 0x3) << 4) | ((c2 >> 4) & 0x0F)]);
                res.push(this.table[(c2 & 0x0F) << 2]);
                res.push('=');
                break;
            }
            var c3 = utf8.charCodeAt(i++);
            res.push(this.table[((c1 & 0x3) << 4) | ((c2 >> 4) & 0x0F)]);
            res.push(this.table[((c2 & 0x0F) << 2) | ((c3 & 0xC0) >> 6)]);
            res.push(this.table[c3 & 0x3F]);
        }
        return res.join('');
    },
    decode : function(str) {
        if (!str) {
            return '';
        }
        var len = str.length;
        var i   = 0;
        var res = [];
        while (i < len) {
            code1 = this.table.indexOf(str.charAt(i++));
            code2 = this.table.indexOf(str.charAt(i++));
            code3 = this.table.indexOf(str.charAt(i++));
            code4 = this.table.indexOf(str.charAt(i++));
            c1 = (code1 << 2) | (code2 >> 4);
            c2 = ((code2 & 0xF) << 4) | (code3 >> 2);
            c3 = ((code3 & 0x3) << 6) | code4;
            res.push(String.fromCharCode(c1));
            if (code3 != 64) {
                res.push(String.fromCharCode(c2));
            }
            if (code4 != 64) {
                res.push(String.fromCharCode(c3));
            }
        }
        return this.UTF8ToUTF16(res.join(''));
    }
};
