package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ShardedJedisPool shardedJedisPool;
	
	public  RedisDao() {
		
	}
	public  RedisDao(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool=shardedJedisPool;
	}

	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	public Seckill getSeckill(long seckillId) {

		try {
			 ShardedJedis jedis = shardedJedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				byte[] bytes = jedis.get(key.getBytes());
				Seckill seckill=schema.newMessage();
				if (bytes != null) {
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	public String putSeckill(Seckill seckill) {
		try {
			 ShardedJedis jedis = shardedJedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer
						.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				String result = jedis.setex(key.getBytes(), 60*60, bytes);
				return result;
			} finally {
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}
}
