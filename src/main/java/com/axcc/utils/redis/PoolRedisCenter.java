package com.axcc.utils.redis;

import com.axcc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.PostConstruct;

/**
 * 公共共享Redis链接池
 *
 */
@Component
public class PoolRedisCenter {
    private static final Logger logger = LoggerFactory.getLogger(PoolRedisCenter.class);

    private static PoolRedisCenter redisCenter;

    /**
     * 公用redis集群 共享资源池
     */
    @Autowired
    private ShardedJedisPool jedisPool;

    /**
     * @Autowired注解在非Controller中其他层注入为null，所以需要下面的配置
     * 关于@PostConstruct：被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次
     */
    @PostConstruct
    public void init() {
        redisCenter = this;
        redisCenter.jedisPool = this.jedisPool;
    }

    /**
     * 从公用redis集群获取resource，用于之后的具体的redis操作
     *
     * @return
     */
    public static ShardedJedis getJedis() {
        try {
            if (redisCenter.jedisPool != null) {
                ShardedJedis resource = redisCenter.jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Get redis server from shared redis pool error: ", e);
            return null;
        }
    }

    /**
     * 归还redis链接
     *
     * @param jedis
     */
    public static void returnJedis(final ShardedJedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 归还坏redis链接
     *
     * @param jedis
     */
    public static void returnBrokenJedis(final ShardedJedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    /**
     * 内部接口也是回调接口，只定义抽象方法
     *
     */
    public interface JedisCallback {
        Object execute(ShardedJedis jedis) throws Exception;
    }

    /**
     * 具体动作 并自动返回连接 需要具体实现
     *
     * @param callback
     * @return
     */
    public Object getResult(JedisCallback callback) {
        ShardedJedis jedis = getJedis();
        try {
            return callback.execute(jedis);
        } catch (Exception e) {
            logger.error("", e);
            returnBrokenJedis(jedis);
            throw new RuntimeException("Redis getResult exception", e);
        } finally {
            if (jedis != null)
                returnJedis(jedis);
        }
    }

}
