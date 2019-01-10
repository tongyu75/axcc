package com.axcc.utils.redis;

import org.apache.commons.lang.Validate;
import redis.clients.jedis.ShardedJedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis的操作集合类
 */
public class RedisUtils {

    public static String get(String key) {
        String ret = null;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            ret = jedis.get(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return ret;
    }

    public static String set(String key, String value) {
        String ret = "";
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            ret = jedis.set(key, value);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return ret;
    }

    public static String set(String key, String value, int seconds) {
        String ret = "";
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            ret = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return ret;
       
    }

    public static String set(byte[] key, byte[] value) {
        String ret = "";
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            ret = jedis.set(key, value);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return ret;
    }

    /**
     * 从缓存中移除数据
     */
    public static void remove(String key) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            jedis.del(key.getBytes());
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
    }

    /**
     * 获取缓存数据
     */
    public static Map<String, String> hmget(String key, String... fields) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            if (fields == null || fields.length == 0) {
                return jedis.hgetAll(key);
            }
            List<String> values = jedis.hmget(key, fields);
            Map<String, String> result = new HashMap<String, String>();
            for (int i = 0; i < fields.length; i++) {
                result.put(fields[i], values.get(i));
            }
            return result;
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }

    /**
     * 设置缓存数据
     */
    public static String hmset(String key, Map<String, String> hash) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }

    /**
     * 删除缓存数据
     */
    public static long hdel(String key, String... fields) {
        long result = 0l;
        Validate.notEmpty(fields);
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            result = jedis.hdel(key, fields);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return result;
    }

    /**
     * 设置缓存数据
     */
    public static String hset(String key, String field, String value) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return String.valueOf(jedis.hset(key, field, value));
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }

    /**
     * Hash操作：获取整个Hash
     *
     * @param key key
     * @return Hash
     */
    public static Map<String, String> hgetAll(String key) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }
    
    /**
     * 返回哈希表 key 中域的数量。
     *
     * @param key key
     * @return Hash
     */
    public static long hlen(String key) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.hlen(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return 0L;
    }


    /**
     * 获取缓存数据
     */
    public static String hget(String key, String field) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.hget(key, field);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }
    
    /**
     * <br>描述:返回哈希表 key 中，一个或多个给定域的值。
     * <br>Author:cuixc
     * <br>Date:2018年10月26日
     * @param key
     * @param fields
     * @return
     */
    public static List<String> hmget2(String key, String... fields) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }
    
    /**
     * 返回哈希表 key 中的所有域。
     */
    public static Set<String> hkeys(String key) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.hkeys(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }

    /**
     * 键操作：设置key的过期时间
     *
     * @param key key
     * @param seconds 过期时间，单位秒
     */
    public static void expire(String key, int seconds) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
    }

    /* *
     * @Author  wangcunlei
     * @Date  2018-5-8 9:14
     * @Description  redis 计数
     * @Params  [key]
     * @Return  java.lang.Long
     */
    public static Long incr(String key){
        Long retVal = 0l;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            retVal = jedis.incr(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return retVal;
    }

    public static boolean exists(String key){
        boolean retVal = false;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            retVal = jedis.exists(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return retVal;
    }

    public static Long sadd(byte[] key, byte[]... members) {
        Long retVal = 0l;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            retVal = jedis.sadd(key, members);
        } catch (Exception e) {
            e.printStackTrace();
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return retVal;
    }

    public static Long srem(byte[] key, byte[]... members) {
        Long retVal = 0l;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            retVal = jedis.srem(key, members);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return retVal;
    }

    public static Long srem(String key,  String... members) {
        Long retVal = 0l;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            retVal = jedis.srem(key, members);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return retVal;
    }
    public static byte[] get(byte[] key) {
        byte[] retValue = null;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            retValue = jedis.get(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return retValue;
    }
    
    /**
   	 * 将一个或多个值 value 插入到列表 key 的表头
   	 * @param key
   	 * @return
   	 */
   	public static void lpush(String key, String value) {
   		ShardedJedis jedis = null;
   		boolean releaseBrokenJedis = false;
   		try {
   			jedis = PoolRedisCenter.getJedis();
   			jedis.lpush(key, value);
   		} catch (Exception e) {
   			if (jedis != null) {
   				PoolRedisCenter.returnBrokenJedis(jedis);
   				releaseBrokenJedis = true;
   			}
   		} finally {
   			if (!releaseBrokenJedis)
   				PoolRedisCenter.returnJedis(jedis);
   		}
   	}
   	/**
   	 * 阻塞。取队列的值 （右出）
   	 * 
   	 * @param key
   	 * @return
   	 */
   	public static Object blockPop(String key, int timeouts) {
   		ShardedJedis jedis = null;
   		boolean releaseBrokenJedis = false;
   		Object result = null;
   		List<String> bitsList = null;
   		try {
   			jedis = PoolRedisCenter.getJedis();
   			bitsList = jedis.brpop(timeouts, key);
				if (bitsList != null && !bitsList.isEmpty()) {
					if (bitsList.size() == 2) {
						result = bitsList.get(1);
					}
				}
   		} catch (Exception e) {
   			if (jedis != null) {
   				PoolRedisCenter.returnBrokenJedis(jedis);
   				releaseBrokenJedis = true;
   			}
   		} finally {
   			if (!releaseBrokenJedis)
   				PoolRedisCenter.returnJedis(jedis);
   		}
   		return result;

   	}

   	
   	/**
   	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
   	 * @param key
   	 * @return
   	 */
   	public static Object getSet(String key,String val) {
   		String ret = null;
           ShardedJedis jedis = null;
           boolean releaseBrokenJedis = false;
           try {
               jedis = PoolRedisCenter.getJedis();
               ret = jedis.getSet(key,val);
           } catch (Exception e) {
               if (jedis != null) {
                   PoolRedisCenter.returnBrokenJedis(jedis);
                   releaseBrokenJedis = true;
               }
           } finally {
               if (!releaseBrokenJedis) {
                   PoolRedisCenter.returnJedis(jedis);
               }
           }
           return ret;

   	}
   	
   	/**
   	 * 删除给定的一个或多个 key 。
   	 * @param key
   	 * @return
   	 */
   	public static long del(String key) {
           ShardedJedis jedis = null;
           boolean releaseBrokenJedis = false;
           try {
               jedis = PoolRedisCenter.getJedis();
               return jedis.del(key);
           } catch (Exception e) {
               if (jedis != null) {
                   PoolRedisCenter.returnBrokenJedis(jedis);
                   releaseBrokenJedis = true;
               }
           } finally {
               if (!releaseBrokenJedis) {
                   PoolRedisCenter.returnJedis(jedis);
               }
           }
           return 0L;

   	}

    /**
     * Set操作：添加Set
     *
     * @param key key
     * @param members members
     */
    public static Long sadd(String key, String... members) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.sadd(key, members);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }

    /**
     * Set操作：获取set中元素个数
     *
     * @param key key
     * @return 个数
     */
    public static Long scard(String key) {
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            return jedis.scard(key);
        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {
            if (!releaseBrokenJedis) {
                PoolRedisCenter.returnJedis(jedis);
            }
        }
        return null;
    }

    public static long setnx(String key,String lock){
        long result = 0l;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            result =  jedis.setnx(key,lock);


        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {

            if (!releaseBrokenJedis)
                PoolRedisCenter.returnJedis(jedis);


        }
        return result;
    }

    public static String setex(String key, int seconds, String value){
        String result = null;
        ShardedJedis jedis = null;
        boolean releaseBrokenJedis = false;
        try {
            jedis = PoolRedisCenter.getJedis();
            result =  jedis.setex(key, seconds, value);


        } catch (Exception e) {
            if (jedis != null) {
                PoolRedisCenter.returnBrokenJedis(jedis);
                releaseBrokenJedis = true;
            }
        } finally {

            if (!releaseBrokenJedis)
                PoolRedisCenter.returnJedis(jedis);
        }
        return result;
    }
}
