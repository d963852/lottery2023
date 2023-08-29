package com.jeesite.modules.lotterycore.constants;

import com.jeesite.common.config.Global;

public class Constant {

    public static final String 操作人_系统自动 = "系统自动";

    public static final String 下级账号查询范围_所有账号 = "10";
    public static final String 下级账号查询范围_指定账号及直接下级 = "20";

    public static final String 投注返点ID = "BETTING_REBATE_ID";

    public static final String 返点结算期号ID = "REBATE_SETTLEMENT_ISSUE_ID";

    public static final String 登录提示_登录成功 = "登录成功";
    public static final String 登录提示_不是管理员无法登陆后台 = "该账号不是管理员,无法登陆到后台";
    public static final String 登录提示_用户名或密码不正确 = "用户名或密码不正确";
    public static final String 登录提示_用户名不存在 = "用户名不存在";

    public static final String 登录状态_成功 = "1";
    public static final String 登录状态_失败 = "0";

    public static final String 投注订单状态_未开奖 = "1";
    public static final String 投注订单状态_未中奖 = "2";
    public static final String 投注订单状态_已中奖 = "3";
    public static final String 投注订单状态_已撤单 = "4";

    public static final String 追号订单状态_进行中 = "1";
    public static final String 追号订单状态_已完成 = "2";
    public static final String 追号订单状态_已取消 = "3";

    public static final String 账号类型_管理员 = "admin";
    public static final String 账号类型_代理 = "agent";
    public static final String 账号类型_会员 = "member";

    public static final String 账号状态_启用 = "1";
    public static final String 账号状态_禁用 = "2";

    public static final String 游戏状态_启用 = "1";
    public static final String 游戏状态_禁用 = "2";
    public static final String 游戏状态_维护中 = "3";

    public static final String 期号状态_未开奖 = "1";
    public static final String 期号状态_已开奖 = "2";
    public static final String 期号状态_已结算 = "3";
    public static final String 期号状态_已作废 = "4";

    public static final String 游戏玩法状态_启用 = "1";
    public static final String 游戏玩法状态_禁用 = "0";

    public static final String 当前开奖期号ID = "CURRENT_LOTTERY_ISSUE_ID";

    public static final Integer 充值订单默认有效时长 = 10;
    public static final String 充值订单_已支付订单单号 = "RECHARGE_ORDER_PAID_ORDER_NO";
    public static final String 充值订单状态_待支付 = "1";
    public static final String 充值订单状态_已支付 = "2";
    public static final String 充值订单状态_已结算 = "3";
    public static final String 充值订单状态_超时取消 = "4";
    public static final String 充值订单状态_人工取消 = "5";

    public static final String 账变日志类型_入账_账号充值 = "入账_账号充值";
    public static final String 账变日志类型_入账_代充值 = "入账_代充值";
    public static final String 账变日志类型_入账_充值优惠 = "入账_充值优惠";
    public static final String 账变日志类型_入账_投注返点 = "入账_投注返点";
    public static final String 账变日志类型_入账_下家投注返点 = "入账_下家投注返点";
    public static final String 账变日志类型_入账_提现失败返还冻结资金 = "入账_提现失败返还冻结资金";
    public static final String 账变日志类型_入账_撤单返还 = "入账_撤单返还";
    public static final String 账变日志类型_入账_中奖派奖 = "入账_中奖派奖";
    public static final String 账变日志类型_入账_工资 = "入账_工资";
    public static final String 账变日志类型_入账_分红 = "入账_分红";
    public static final String 账变日志类型_入账_活动礼金 = "入账_活动礼金";
    public static final String 账变日志类型_管理员手动账变 = "管理员手动账变";
    public static final String 账变日志类型_出账_投注扣款 = "出账_投注扣款";
    public static final String 账变日志类型_出账_提现扣款 = "出账_提现扣款";
    public static final String 账变日志类型_出账_申请提现资金冻结 = "出账_申请提现资金冻结";

    public static final String 充提日志订单类型_充值 = "1";
    public static final String 充提日志订单类型_提现 = "2";

    public static final String 提现记录状态_发起提现 = "1";
    public static final String 提现记录状态_审核通过 = "2";
    public static final String 提现记录状态_审核不通过 = "3";
    public static final String 提现记录状态_已到账 = "4";

    public static final String 赔率模式_固定赔率 = "1";
    public static final String 赔率模式_不固定赔率 = "2";

    public static final double 系统_会员_分红上限 = Double.parseDouble(Global.getConfig("lottery.member.bonus", "15"));
    public static final double 系统_会员_工资上限 = Double.parseDouble(Global.getConfig("lottery.member.wage", "2.5"));
    public static final double 系统_会员_返点上限 = Double.parseDouble(Global.getConfig("lottery.member.rebate", "15"));
    public static final double 系统_会员_赔率上限 = Double.parseDouble(Global.getConfig("lottery.member.odds", "2"));
    public static final double 系统_单注销售单价 = Double.parseDouble(Global.getConfig("lottery.sys.basePrice", "2.00"));


    public static final String 游戏_奇趣分分彩 = "QIQFFC";
    public static final String 游戏_腾讯分分彩 = "TXFFC";
    public static final String 游戏_腾讯2分彩 = "TX2FCS";
    public static final String 游戏_西贡五分彩 = "XG300";
    public static final String 游戏_河内分分彩 = "HN60";
    public static final String 游戏_河内五分彩 = "HN300";
    public static final String 游戏_体彩排列3 = "TCPL3";
    public static final String 游戏_福彩3D = "FC3D";
    public static final String 游戏_幸运飞艇 = "XIYFT";
    public static final String 游戏_经典重庆时时彩 = "JDCQSSC";
    public static final String 游戏_重庆分分彩 = "CQFFC";
    public static final String 游戏_重庆五分彩 = "CQ5FC";
    public static final String 游戏_山东11选5 = "SD11X5";
    public static final String 游戏_上海11选5 = "SH11X5";
    public static final String 游戏_江苏快三 = "JSKS";
    public static final String 游戏_北京PK10 = "BJPK10";
    public static final String 游戏_新疆时时彩 = "XJSSC";
    public static final String 游戏_欢乐生肖 = "CQSSC";


    public static final String 消息主题_同步开奖数据 = "SYNC_LOTTERY_NUMBER";

    public static final String 消息主题_开奖 = "LOTTERY_DRAW";

}
