package com.ibeidan.test;

import org.junit.Test;
import redis.clients.jedis.JedisPubSub;



/**
 * @author lee
 * DATE 2020/5/13 15:00
 */
public class PublishSubscribeCommands extends AbstractRedisTest {

    private Long result ;

    private String channel1 = "channel1";

    /**
     * todo 时间复杂度： O(N+M)，其中 N 是频道 channel 的订阅者数量，
     * 而 M 则是使用模式订阅(subscribed patterns)的客户端的数量。
     * 将信息 message 发送到指定的频道 channel 。
     * 返回值
     * 接收到信息 message 的订阅者数量。
     **/
    @Test
    public void publish () {
        result = jedis.publish(channel1,"I am libeibei !");
        print(result);
    }

    /**
     * todo 时间复杂度： O(N)，其中 N 是订阅的频道的数量。
     * 订阅给定的一个或多个频道的信息。
     * 返回值
     * 接收到的信息
     **/
    @Test
    public void subscribe(){
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                print(channel+"==" + message);
                unsubscribe(channel);
            }
            @Override
            public void onSubscribe(String channel, int subscribedChannels) {

                print("onSubscribe");
            }

            @Override
            public void unsubscribe(String... channels) {
                print("cancel sub");
                super.unsubscribe(channels);
            }
        },channel1);
    }

    /**
     * todo 时间复杂度： O(N)， N 是订阅的模式的数量。
     * 订阅一个或多个符合给定模式的频道。
     *
     * 每个模式以 * 作为匹配符，比如 it* 匹配所有以 it 开头的频道
     * ( it.news 、 it.blog 、 it.tweets 等等)，
     * news.* 匹配所有以 news. 开头的频道
     * ( news.it 、 news.global.today 等等)，诸如此类。
     * 返回值
     * 接收到的信息
     **/
    @Test
    public void psubscribe(){
        jedis.psubscribe(new JedisPubSub() {

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                print(pattern+ " === "+channel + " == "+message);
                punsubscribe("chan*");
            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                print("onPSubscribe");
            }

            @Override
            public void punsubscribe(String... patterns) {
                print("punsubscribe");
                super.punsubscribe(patterns);
            }
        },"chan*");
    }


    /**
     * 取消订阅
     **/
    @Test
    public void unsubscribe() {
        MyPubSubListener listener = new MyPubSubListener();
        result = jedis.publish(channel1,"my test");
         listener.unsubscribe(channel1);

    }

    @Test
    public void pubsub(){
        result = jedis.pubsubNumPat();
        print(result);
    }





}
