package com.jeesite.test;

import com.jeesite.modules.Application;
import com.xxl.mq.client.message.XxlMqMessage;
import com.xxl.mq.client.producer.XxlMqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TaskTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void tt() {
        String topic = "SYNC_LOTTERY_NUM_QIQFFC";
        String data = "时间戳:" + System.currentTimeMillis();
        XxlMqProducer.produce(new XxlMqMessage(topic, data));
    }

}
