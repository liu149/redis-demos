package com.examples.distributeLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

public class JedisLock {
    private  Jedis jedis;
    private SetParams setParams;
    public JedisLock() {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        this.jedis = jedisPool.getResource();
        this.setParams = new SetParams();
        this.setParams.nx().ex(2);
    }

    public boolean lock() {
        for(;;) {
            if("OK".equals(jedis.set("lock", "1", setParams))){
                return true;
            }else{
                try {
                    Thread.yield();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean unlock() {
       return jedis.del("lock") == 1L;
    }
}
