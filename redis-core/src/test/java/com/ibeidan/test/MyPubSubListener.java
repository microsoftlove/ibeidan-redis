package com.ibeidan.test;

import redis.clients.jedis.Client;
import redis.clients.jedis.JedisPubSub;

/**
 * @author lee
 * DATE 2020/5/13 15:54
 */
public class MyPubSubListener extends JedisPubSub {

    public MyPubSubListener() {
        super();
    }

    private <T> void print(T msg){
        System.out.println(msg);
    }

    @Override
    public void onMessage(String channel, String message) {
        print("my onMessage");
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        print("my onPMessage");
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        print("my onSubscribe");
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        print("my onUnsubscribe");
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        print("my onPUnsubscribe");
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        print("my onPSubscribe");
    }

    @Override
    public void onPong(String pattern) {
        print("my onPong");
    }

    @Override
    public void unsubscribe() {
        print("my unsubscribe");
    }

    @Override
    public void unsubscribe(String... channels) {
        print("my unsubscribe");
    }

    @Override
    public void subscribe(String... channels) {
        print("my subscribe");
    }

    @Override
    public void psubscribe(String... patterns) {
        print("my psubscribe");
        super.psubscribe(patterns);
    }

    @Override
    public void punsubscribe() {
        print("my punsubscribe");
    }

    @Override
    public void punsubscribe(String... patterns) {
        print("my punsubscribe");

    }

    @Override
    public void ping() {
        print("my ping");
    }

    @Override
    public boolean isSubscribed() {
        return super.isSubscribed();
    }

    @Override
    public void proceedWithPatterns(Client client, String... patterns) {
        print("my proceedWithPatterns");
    }

    @Override
    public void proceed(Client client, String... channels) {
        print("my proceed");
    }

    @Override
    public int getSubscribedChannels() {
        return super.getSubscribedChannels();
    }
}
