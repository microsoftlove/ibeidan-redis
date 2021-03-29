package com.ibeidan.client;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.dynamic.RedisCommandFactory;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lee
 * DATE 2021/3/24 11:21
 *
 * 1.集群模式客户端初始化
 * 2.拓扑刷新机制
 * 3.结合序列化操作
 * 4.封装相应的工具方法
 *
 * 客户端封装
 * 获取连接
 * 进行集群命令操作
 *
 */
public class BdLettuceInit {

    private volatile static RedisClusterClient clusterClient ;
    private volatile static StatefulRedisClusterConnection connection;

    private final static Object syncLock = new Object();

    private BdLettuceInit(){}

    public static List<RedisURI> getNodes(){
        List<RedisURI> nodes = new ArrayList<>();
        String nodesStr ="192.168.10.114:6400,192.168.10.114:6410,192.168.10.114:6420,192.168.10.125:6400,192.168.10.125:6410,192.168.10.125:6420";
        String[] parts = nodesStr.split( ",");
        for (int i = 0; i < parts.length; i++) {
            String[] ipPort = parts[i].split(":");
            RedisURI node1 = RedisURI.create(ipPort[0], Integer.valueOf(ipPort[1]));
            node1.setPassword("A_c45844981a42776b36df1bbb55005d@E");
            nodes.add(node1);
        }
        return nodes;
    }

    public static StatefulRedisClusterConnection getConnection(){
        if (connection == null ){
            synchronized (syncLock){
                if (connection == null) {
                    ClientResources clientResources = DefaultClientResources.create();
                    clusterClient = RedisClusterClient.create(clientResources,getNodes());
                    ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                            .enableAdaptiveRefreshTrigger()
                            .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30l))
                            .build();
                    clusterClient.setOptions(ClusterClientOptions.builder()
                            .topologyRefreshOptions(topologyRefreshOptions)
                            .build());
                   // connection = clusterClient.connect();
                    connection = clusterClient.connect(new ByteArrayCodec());
                    //JVM 停止或重启时，关闭连接池释放掉连接(跟数据库连接池类似)
                    closeConnection();
                }
            }
        }
        return connection;
    }

    /**
     * @author libeibei
     * 2021/3/26 11:05
     * JVM 停止或重启时，关闭连接池释放掉连接(跟数据库连接池类似)
     **/
    private static void closeConnection(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    if (clusterClient != null) {
                        clusterClient.shutdown(0, 10, TimeUnit.MILLISECONDS);
                        //log.info("Closing jerseyClient successfully,jerseyClient={}",client);
                        System.out.println("shutdown clusterClient");
                    }
                    if (connection != null) {
                        connection.close();
                        System.out.println("shutdown connection");
                    }
                } catch (Exception e) {
                   // log.error("Error while closing the jerseyClient,error ={}", Throwables.getStackTraceAsString(e));
                }
            }
        });
    }





   public static void initConfig(){
        System.out.println(getNodes());
     ClientResources clientResources = DefaultClientResources.create();
    RedisClusterClient clusterClient = RedisClusterClient.create(clientResources,getNodes());
    //    RedisCommandFactory redisCommandFactory = new RedisCommandFactory()

    ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
            .enableAdaptiveRefreshTrigger()
            .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30l))
            .build();
    clusterClient.setOptions(ClusterClientOptions.builder()
            .topologyRefreshOptions(topologyRefreshOptions)
            .build());

    StatefulRedisClusterConnection connection = clusterClient.connect();
    RedisFuture<String> future = connection.async().setex("lee",100*60,"test");
    try {
        String f =  future.get(200, TimeUnit.MILLISECONDS);
        System.out.println(f);

        RedisFuture redisFuture=  connection.async().get("lee");
        String result = (String) redisFuture.get(1,TimeUnit.SECONDS);
        System.out.println(result);
        Thread.sleep(100000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } catch (TimeoutException e) {
        e.printStackTrace();
    }
   }


   public static void poolInit() throws InterruptedException {
       RedisClusterClient redisClusterClient = RedisClusterClient.create(getNodes());

       GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool = ConnectionPoolSupport.createGenericObjectPool(
               redisClusterClient::connect, new GenericObjectPoolConfig());

       StatefulRedisClusterConnection<String, String> connection2 = null;
       try {
          // connection = pool.borrowObject();
           for (int i = 0; i < 10; i++) {
              connection2 = pool.borrowObject();
               System.out.println(connection2);
               RedisAdvancedClusterCommands<String, String> sync = connection2.sync();
               String result =  sync.get("lee");
               System.out.println(result);
               sync.ping();
               pool.returnObject(connection2);


               RedisAdvancedClusterCommands<String, String> sync3 = connection2.sync();
               String result4 =  sync3.get("lee");
               System.out.println(result4);
        }
           connection2 = pool.borrowObject();
           System.out.println("=="+connection2);
       } catch (Exception e) {
           e.printStackTrace();
       }


       //final CompletableFuture<StatefulRedisClusterConnection<String, String>> acquire = pool.acquire();

       Thread.sleep(100000);
   }
}
