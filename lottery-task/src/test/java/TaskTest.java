import com.jeesite.modules.TaskApplication;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterytask.issue.GenerateIssueTask;
import com.xxl.mq.client.message.XxlMqMessage;
import com.xxl.mq.client.producer.XxlMqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskApplication.class)
public class TaskTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GenerateIssueTask generateIssueTask;

    @Autowired
    private GameService gameService;

    @Autowired
    private IssueService issueService;

    @Test
    public void tt() {
        for (int i = 0; i < 100; i++) {
            String topic = "SYNC_LOTTERY_NUM_QIQFFC" + i;
            XxlMqProducer.produce(new XxlMqMessage(topic, topic));
        }
    }

    @Test
    public void makeTodayIssues() {
        Date day = new Date();
        List<Game> gameList = gameService.findList(new Game());
        for (Game game : gameList) {
            try {
                issueService.makeIssue(day, day, game.getId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }


}
