package com.ibeidan.client;

import com.ibeidan.utils.pojo.ProtoStuffUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lee
 * DATE 2021/3/29 10:21
 */
public class BdLettuceUtils {

   /* public static String setex(String key,String obj,long seconds){
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = BdLettuceInit.getConnection().async();
        RedisFuture<String> future =  asyncCommands.setex(key,seconds,obj);
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }*/

   private  static RedisAdvancedClusterAsyncCommands<byte[], byte[]> asyncCommands(){
       RedisAdvancedClusterAsyncCommands<byte[], byte[]> asyncCommands = BdLettuceInit.getConnection().async();
       return asyncCommands;
   }

    public static <T> String setex(String key,T t,long seconds){
        byte[] bytes = ProtoStuffUtils.serialize(t);
        RedisFuture<String> future =  asyncCommands().setex(key.getBytes(),seconds,bytes);
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String setexSync(String key, T t,long seconds){
        byte[] bytes = ProtoStuffUtils.serialize(t);
        RedisAdvancedClusterCommands<byte[], byte[]> asyncCommands = BdLettuceInit.getConnection().sync();
        return asyncCommands.setex(key.getBytes(),seconds,bytes);
    }

    public static  byte[] getKeyByte(String key){

        RedisFuture<byte[]> future =  asyncCommands().get(key.getBytes());
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getItem(String key,Class<T> tClass){
        RedisFuture<byte[]> future = asyncCommands().get(key.getBytes());
        byte[] valueBytes = new byte[0];
        try {
            valueBytes = future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return ProtoStuffUtils.deserialize(valueBytes, tClass);
    }

    public static String getString(String key){
        RedisFuture<byte[]> future = asyncCommands().get(key.getBytes());
        byte[] valueBytes = new byte[0];
        try {
            valueBytes = future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return ProtoStuffUtils.deserialize(valueBytes, String.class);
    }

    public static Long del(String key){
        RedisFuture<Long> future = asyncCommands().del(key.getBytes());
        try {
             return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public static long ttl(String key){
        RedisFuture<Long> future = asyncCommands().ttl(key.getBytes());
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return -2L;
    }



  /*  public static String getKeyString(String key){
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = BdLettuceInit.getConnection().async();
        RedisFuture<String> future =  asyncCommands.get(key);
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*public static <T> T getKey(String key){
        RedisAdvancedClusterAsyncCommands<String, T> asyncCommands = BdLettuceInit.getConnection().async();
        RedisFuture<T> future =  asyncCommands.get(key);
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
