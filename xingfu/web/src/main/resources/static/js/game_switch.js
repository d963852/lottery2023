(function () {
    var HomeCommon = {
        init: function () {
            this.eventBind();
        }
        , eventBind: function () {
            var _this = this, f;
            if (location.hash === "#lottery") {
                return $("#game_switch").show();
            }
            //彩票大厅二级菜单效果
            $('[data-item="lot_hall"],#game_switch').unbind('mouseover').on({
                mouseover: function (e) {
                    $('#game_switch').css('display', 'block');
                },
                mouseleave: function (e) {
                    $('#game_switch').hide();
                }
            });
            $('#game_switch').unbind('mousemove').on('mousemove', function (e) {
                e.preventDefault();
                $('#game_switch').css('display', 'block');
            });
            $('#lot_hall,#game_switch').unbind('mouseleave').on('mouseleave', function (e) {
                e.preventDefault();
                $('#game_switch').css('display', 'none');
            });
        }
    }
    HomeCommon.init();
})();
// UserMenu 海东青 20170925
$(function(){
$(".user_menu").click(function(){
$(this).next(".user_menu_info").slideToggle(200).siblings(".user_menu_info").slideUp(200);
})
})