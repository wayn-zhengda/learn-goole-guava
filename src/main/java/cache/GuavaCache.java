package cache;

import com.google.common.cache.*;
import org.junit.Test;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 使用guava的本地缓存功能
 * 什么时候使用本地的缓存，什么时候使用redis缓存？
 * 为什么要用guava而不用concurrentMap呢？
 */
public class GuavaCache {

    @Test
    public void testCache(){
        //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存.相当于操作redis时的getCache
        LoadingCache<Integer,Object> localCache
                // 这个地方是个单例，这个单例存在并发的问题么？
                = CacheBuilder.newBuilder()
                // 设置可以同时写内存的线程数量
                .concurrencyLevel(1024)
                // 设置过期时间，写入缓存之后开始计时。.expireAfterAccess()使用之后计时
                .expireAfterWrite(1000L, TimeUnit.SECONDS)
                // 设置初始容器大小，默认16
                .initialCapacity(16)
                // 设置容器最大容量，超过会按照LRU（最近虽少使用算法来移除缓存项）
                .maximumSize(1021)
                // 设置统计key的命中率
                .recordStats()
                // 缓存被移除时，添加通知
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                })
                // build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(
                        new CacheLoader<Integer, Object>() {
                            @Override
                            public Object load(Integer key) throws Exception {
                                System.out.println("load student " + key);
                                Entity entity = new Entity();
                                entity.setId(key);
                                entity.setAge(99);
                                entity.setName("tttt");
                                return entity;
                            }
                        }
                );
        for (int i = 0; i < 200; i++) {
            try {
                Entity entity = (Entity)localCache.get(1);
                System.out.println("cache stats:");
                //最后打印缓存的命中率等 情况
                System.out.println(localCache.stats().toString());
                //CacheStats{hitCount=199, missCount=1, loadSuccessCount=1, loadExceptionCount=0, totalLoadTime=3629712, evictionCount=0}
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

    }



}
