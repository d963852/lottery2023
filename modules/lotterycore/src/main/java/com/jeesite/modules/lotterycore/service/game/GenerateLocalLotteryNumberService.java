package com.jeesite.modules.lotterycore.service.game;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.service.BaseService;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 彩票期号Service
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class GenerateLocalLotteryNumberService extends BaseService {

    @Autowired
    private GameService gameService;

    /**
     * 生成本地开奖数据
     *
     * @param issue 要开奖的游戏期号
     */
    public String generateLocalLotteryNumber(@NotNull Issue issue) throws BizException {
        Game gameSC = new Game();
        gameSC.setGameCode(issue.getGameCode());
        List<Game> gameList = gameService.findList(gameSC);
        if (gameList.size() < 1) {
            throw new BizException(BizError.参数异常.getCode(), "没有找到对应的游戏");
        }
        gameSC = gameList.get(0);

        List<String> resultList = new ArrayList<>();
        String lotteryNumberResult = "";
        // 在开奖号码列表中随机取数，注意否可重复
        if(StrUtil.isEmpty(gameSC.getLotteryNumberList())){
            throw new BizException(BizError.参数异常.getCode(), "没有开奖号码列表");
        }
        String[] codeList = gameSC.getLotteryNumberList().split(",");
        boolean canRepeat = "1".equals(gameSC.getLotteryNumberRepeat());
        while (resultList.size() < gameSC.getLotteryNumberCount()) {
            // 从开奖号码清单中获取一个随机号码
            String number = codeList[RandomUtil.randomInt(0,codeList.length)];
            if(!canRepeat){
                // 判断是否有重复的
                if(resultList.contains(number)){
                    continue;
                }
            }
            resultList.add(number);
        }
        if (resultList.size() > 0) {
            lotteryNumberResult = String.join(",", resultList);
        }
        return lotteryNumberResult;
    }

}