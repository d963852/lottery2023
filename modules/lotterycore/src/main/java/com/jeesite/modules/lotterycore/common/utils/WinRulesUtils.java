package com.jeesite.modules.lotterycore.common.utils;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中奖规则
 */
public class WinRulesUtils {
    private static final WinRulesUtils WIN_RULES_UTILS = new WinRulesUtils();
    private static final String NUMBER_0_9 = "\\d+";

    /**
     * 入口方法，通过反射调用计算方法
     *
     * @param betNumber     投注号码
     * @param winningNumber 开奖号码
     * @param extBetNumber  附加位数，附加位数，一般用在任选位数上，个数用1,十位用2，百位用4...多位存储时可以用其和表示。,0一般是不保存位数用
     * @param winRuleFun    要调用的中奖规则算法
     * @return
     */
    public static int calcWinningCount(String winRuleFun, String betNumber, String winningNumber, String extBetNumber) throws Exception {
        if (extBetNumber == null) {
            extBetNumber = "";
        }
        // 根据winRuleFun调用方法
        try {
            return (int) ReflectUtil.invoke(WIN_RULES_UTILS, winRuleFun, betNumber, winningNumber, extBetNumber);
        } catch (UtilException e) {
            e.printStackTrace();
            throw new BizException(BizError.valueOf(e.getMessage()));
        }
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

    /**
     * 笛卡尔积
     */
    private static <T> List<List<T>> getDescartes(List<List<T>> list) {
        List<List<T>> returnList = new ArrayList<>();
        descartesRecursive(list, 0, returnList, new ArrayList<T>());
        return returnList;
    }

    /**
     * 递归实现笛卡尔积
     * 原理：从原始list的0开始依次遍历到最后
     *
     * @param originalList 原始list
     * @param position     当前递归在原始list的position
     * @param returnList   返回结果
     * @param cacheList    临时保存的list
     */
    private static <T> void descartesRecursive(List<List<T>> originalList, int position, List<List<T>> returnList, List<T> cacheList) {
        List<T> originalItemList = originalList.get(position);
        for (int i = 0; i < originalItemList.size(); i++) {
            //最后一个复用cacheList，节省内存
            List<T> childCacheList = (i == originalItemList.size() - 1) ? cacheList : new ArrayList<>(cacheList);
            childCacheList.add(originalItemList.get(i));
            if (position == originalList.size() - 1) {//遍历到最后退出递归
                returnList.add(childCacheList);
                continue;
            }
            descartesRecursive(originalList, position + 1, returnList, childCacheList);
        }
    }


    /**
     * 复式
     * 投注列表：1,5|2,9|3，笛卡尔乘积展开后，包含1,2,3则中奖
     *
     * @return 返回中奖注数
     * @params betNumber 投注列表：1,5|2,9|3
     * @params winningNumber 开奖号码：1,2,3
     */
    public static int fs(String betNumber, String winningNumber) {
//        List<List<String>> listData = new ArrayList<>();
//        // 按照|拆分投注号码
//        List<String> betNumberList = Arrays.asList(StringUtils.reverse(betNumber).split("\\|"));
//        for (String betNumberString: betNumberList) {
//            // 按照，拆分每个投注号码
//            listData.add(Arrays.asList(betNumberString.split(",")));
//        }
//        // 做笛卡尔积，获取全集列表
//        List<List<String>> descartesList = getDescartes(listData);
//        // 将全集列表转换为list,然后在遍历出没有一致的即可
//        List<String> resultList = ListUtil.toList();
//        for (List<String> innerList: descartesList) {
//            resultList.add(StringUtils.join(innerList,","));
//        }

        List<String> listData = getDescartes(Arrays.stream(betNumber.split("\\|"))
                .map(it -> Arrays.asList(it.split(","))).collect(Collectors.toList()))
                .stream().map(it -> StringUtils.join(it, ",")).collect(Collectors.toList());
        // 在全集列表中查找开奖号码一致的
        int[] ints = ListUtil.indexOfAll(listData, winningNumber::equals);
        return ints.length;
    }


    // 五星复式
    public static int dxwf5f(String betNumber, String winningNumber, String extBetNumber) {
        // 5,6,7,8,9|7|2|6,0|2,1
        if (!ReUtil.isMatch(NUMBER_0_9, StrUtil.removeAll(betNumber, '|', ','))) {
            throw new BizException(BizError.投注数字内容超出规则范围);
        }
        return fs(betNumber, winningNumber);
    }


    public static void main(String[] args) {
//        System.out.println(ssc5xfs("5,6,7,8,9|7|2|6,0|2,1"));
        try {
            System.out.println(calcWinningCount("dxwf5f", "1,6,8|2,8,6|1,6|2,3,7|1,3,5", "1,6,5,5,9", ""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
