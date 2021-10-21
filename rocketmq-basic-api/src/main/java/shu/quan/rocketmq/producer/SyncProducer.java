package shu.quan.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * @author liujq
 * @version 1.0.0
 * @description 出现broker-b主从数据同步问题，从节点不可用。设置brokerIP, 重启服务即可。
 * @date 2021/10/19 13:12
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("producer_group");
        // 设置VIP通道
        producer.setVipChannelEnabled(true);
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.18.100:9876;192.168.18.101:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("basic" /* Topic */,
                    "Tag-shu" /* Tag */,
                    ("broker-b  SLAVE NOT AVAILABLE " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            String msgId = sendResult.getMsgId();
            int queueId = sendResult.getMessageQueue().getQueueId();
            System.out.println("发送结果:" + sendResult);
//            System.out.printf("%s%n", sendResult);
            TimeUnit.SECONDS.sleep(1);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
