package shu.quan.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author shu_quan
 * @version 1.0
 * @introduction : 单向发送消息
 * @createtime 2021/10/19 22:34
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception{
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("oneway_producer_group");
        // 设置VIP通道
        producer.setVipChannelEnabled(true);
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.18.100:9876;192.168.18.101:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 8; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("broker-a" /* Topic */,
                    "TagAA" /* Tag */,
                    ("Hello  Single way RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        //Wait for sending to complete
        Thread.sleep(5000);
        producer.shutdown();
    }
}