package org.seckill.service;

import javax.annotation.Resource;

import org.seckill.enums.Fun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
@Service("redisService")
public class RedisService {
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public String set(final String key, final String value) throws Exception{
		return  execute(new Fun<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return  e.set(key,value);
			}
		});
	}
	public String get(final String key) throws Exception{
		return execute(new Fun<String, ShardedJedis>() {
			
			@Override
			public String callback(ShardedJedis e) {
				
				return  e.get(key);
			}
		});
	}
	private <T> T execute(Fun<T,ShardedJedis> fun){
		ShardedJedis shardedJedis=null;
		try {
			shardedJedis=shardedJedisPool.getResource();
			return fun.callback(shardedJedis);
		
		}finally {
			shardedJedisPool.close();
		}
	}
}
