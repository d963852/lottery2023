/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import com.jeesite.common.tests.BaseSpringContextTests;
import com.jeesite.modules.TaskApplication;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.dao.GameDao;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.service.GameService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest(classes = TaskApplication.class)
public class MakeSyncClass extends BaseSpringContextTests {

    @Autowired
    private GameService gameService;

    @Test
    public void makeSyncClassFile() {
        List<Game> gameList = gameService.findList(new Game());
        for (Game game : gameList) {
            createFile(game);
        }
    }

    private void createFile(Game game) {
        //将列表写入文件，覆盖模式，编码为UTF-8
        String path = "C:\\Sync\\Sync" + game.getGameCode() + "LotteryNumTask.java";
        //path指定路径下的文件如不存在，则创建
        try {
            List<String> list = new ArrayList<>();
            list.add("package com.jeesite.modules.lotterytask.issue.syncLotteryNumberTasks;");
            list.add("");
            list.add("import com.jeesite.modules.lotterycore.constants.Constant;");
            list.add("import com.jeesite.modules.lotterytask.issue.BaseSyncLotteryNumTask;");
            list.add("import org.apache.rocketmq.spring.annotation.ConsumeMode;");
            list.add("import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;");
            list.add("import org.apache.rocketmq.spring.annotation.SelectorType;");
            list.add("import org.springframework.stereotype.Component;");
            list.add("");
            list.add("/**");
            list.add(" * 【" + game.getGameName() + "】开奖数据同步类");
            list.add(" * 由MakeSyncClass自动生成");
            list.add(" */");
            list.add("@Component");
            list.add("@RocketMQMessageListener(");
            list.add("  consumeMode = ConsumeMode.CONCURRENTLY,");
            list.add("  topic = \"SYNC_LOTTERY_NUMBER\",");
            list.add("  consumerGroup = \"LotteryConsumerGroup_" + game.getGameCode() + "\",");
            list.add("  selectorType = SelectorType.TAG,");
            list.add("  selectorExpression = \"" + game.getGameCode() + "\"");
            list.add(")");
            list.add("public class Sync" + game.getGameCode() + "LotteryNumTask extends BaseSyncLotteryNumTask {");
            list.add("  public String GAME_CODE = \"" + game.getGameCode() + "\";");
            list.add("}");
            FileUtil.writeUtf8Lines(list, path);
        } catch (IORuntimeException e) {
            //抛出一个运行时异常(直接停止掉程序)
            throw new RuntimeException("运行时异常", e);
        }
    }

}
