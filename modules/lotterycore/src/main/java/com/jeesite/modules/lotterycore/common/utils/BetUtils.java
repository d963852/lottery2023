package com.jeesite.modules.lotterycore.common.utils;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;

/**
 * 投注数计算
 */
public class BetUtils {
    private static final BetUtils BETUTILS = new BetUtils();
    private static final String NUMBER_0_9 = "\\d+";

    /**
     * 入口方法，通过反射调用计算方法
     *
     * @param betNumber
     * @param betCountFun
     * @return
     */
    public static int calcBetCount(String betNumber, String betCountFun) throws Exception {
        // 根据betCountFun调用方法
//        return (int) BETUTILS.getClass().getMethod(betCountFun, new Class[]{String.class}).invoke(BETUTILS, betNumber);
        return (int) ReflectUtil.invoke(BETUTILS, betCountFun, betNumber);
    }

    /**
     * 求排列数 A(n,m) n>m
     *
     * @param n
     * @param m
     * @return
     */
    public static int A(int n, int m) {
        int result = 1;
        // 循环m次,如A(6,2)需要循环2次，6*5
        for (int i = m; i > 0; i--) {
            result *= n;
            n--;// 下一次减一
        }
        return result;
    }

    /**
     * 求组合数
     *
     * @param n
     * @param m
     * @return
     */
    public static int C(int n, int m) {
        // 应用组合数的互补率简化计算量
        m = m > (n - m) ? (n - m) : m;
        // 分子的排列数
        int son = A(n, m);
        // 分母的排列数
        int mother = A(m, m);
        return son / mother;
    }

    // 五星复式
    // 从万位、千位、百位、十位、个位中选择一个5位数号码组成一注，所选号码与开奖号码全部相同，且顺序一致，即为中奖。
    public static int ssc5xfs(String betNumber) {
        // 5,6,7,8,9|7|2|6,0|2,1
        if (!ReUtil.isMatch(NUMBER_0_9, StrUtil.removeAll(betNumber, '|', ','))) {
            throw new BizException(BizError.投注数字内容超出规则范围);
        }
        int betCount = 1;
        String[] betNumberList = betNumber.split("\\|");
        for (String position : betNumberList) {
            String[] numbers = position.split(",");
            betCount *= numbers.length;
        }
        return betCount;
    }

    public static void main(String[] args) {
//        System.out.println(ssc5xfs("5,6,7,8,9|7|2|6,0|2,1"));
        try {
            System.out.println(calcBetCount("5,6,7,8,9|7|2|6,0|2,1", "ssc5xfs"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
