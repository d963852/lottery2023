import cn.hutool.core.util.RandomUtil;
import com.jeesite.modules.TaskApplication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TaskApplication.class)
public class TaskTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void tt() {
        List<String> resultList = new ArrayList<>();
        String rs = "";
        for (int i = 0; i < 5; i++) {
            resultList.add(String.valueOf(RandomUtil.randomInt(0, 9)));
        }
        if (resultList.size() > 0) {
            rs = String.join(",",resultList);
        }
        logger.info(rs);
    }

}
