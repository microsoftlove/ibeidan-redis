/*
package com.ibeidan.client;

*/
/**
 * @author lee
 * @DATE 2021/3/26 14:16
 *//*

public class beizhu {


    private String passwd = "xxxxx";
    private String ip = "10.0.0.204";
    ;

    @Bean
    @Scope("singleton")
    public ClientResources clientResources() {
        final DefaultClientResources defaultClientResources = DefaultClientResources.builder()
                .ioThreadPoolSize(4)
                .computationThreadPoolSize(4)
                .build();

        return defaultClientResources;
    }

    @Bean(destroyMethod = "shutdown")
    @Scope("singleton")
    public RedisClusterClient clusterClient(ClientResources clientResources) {
        final String ip = "10.0.0.204";
        final String passwd = "dingXiang123";
        final RedisURI redisURI1 = RedisURI.Builder.redis(ip, 7001).withPassword(passwd).build();
        final RedisURI redisURI2 = RedisURI.Builder.redis(ip, 7002).withPassword(passwd).build();
        final RedisURI redisURI3 = RedisURI.Builder.redis(ip, 7003).withPassword(passwd).build();
        final RedisURI redisURI4 = RedisURI.Builder.redis(ip, 7004).withPassword(passwd).build();
        final RedisURI redisURI5 = RedisURI.Builder.redis(ip, 7005).withPassword(passwd).build();
        final RedisURI redisURI6 = RedisURI.Builder.redis(ip, 7006).withPassword(passwd).build();
        RedisClusterClient clusterClient = null;
        try {
            final List<RedisURI> redisURIS = Arrays.asList(redisURI1, redisURI2, redisURI3, redisURI4, redisURI5, redisURI6);
            clusterClient = RedisClusterClient.create(clientResources, redisURIS);
            ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                    .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT, ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
                    //连接池refresh超时时间
                    .adaptiveRefreshTriggersTimeout(Duration.ofMinutes(3))
                    .build();


            clusterClient.setOptions(ClusterClientOptions.builder()
                    .topologyRefreshOptions(topologyRefreshOptions)
                    .autoReconnect(true)
                    .pingBeforeActivateConnection(true)
                    .build());

            final RedisAdvancedClusterAsyncCommands<String, String> async = clusterClient.connect().async();
            final RedisFuture<String> set = async.set("aa", "aaaaa");
            set.get();
            log.info("客户端初始化成功");
            return clusterClient;
        } catch (Exception e) {
            log.error("lettce客户端初始化失败，{}", e);
            if (clusterClient != null) {
                clusterClient.shutdown();
            }
        }

        return null;
    }

    */
/**
     * 初始化异步的 Cluter 模式链接池
     *
     * @param clusterClient
     * @return
     *//*

    @Bean()
    @DependsOn("clusterClient")
    @Scope("singleton")
    public BoundedAsyncPool<StatefulRedisClusterConnection<String, String>> lettucePool(RedisClusterClient clusterClient) {
        final BoundedPoolConfig.Builder builder = BoundedPoolConfig.builder();
        builder.minIdle(9);
        final BoundedPoolConfig boundedPoolConfig = builder.build();
        final BoundedAsyncPool<StatefulRedisClusterConnection<String, String>> lettucePool = AsyncConnectionPoolSupport.createBoundedObjectPool(
                () -> clusterClient.connectAsync(StringCodec.UTF8)
                , boundedPoolConfig
        );


        log.info("连接池初始化成功");
        return lettucePool;
    }

    */
/**
     * 从连接池获取链接
     *
     * @param lettucePool
     *//*

    @Bean
    @DependsOn("lettucePool")
    public CompletableFuture<StatefulRedisClusterConnection<String, String>> clusterAsync(BoundedAsyncPool<StatefulRedisClusterConnection<String, String>> lettucePool) {

        final CompletableFuture<StatefulRedisClusterConnection<String, String>> acquire = lettucePool.acquire();
        return acquire;
    }
}
*/
