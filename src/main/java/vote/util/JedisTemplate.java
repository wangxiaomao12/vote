package vote.util;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * redis操作模板类 (未使用 泛型 和 序列化)
 * 
 * @author joe
 *
 */
public class JedisTemplate {
	// 饿汉式 单例模式
	private final static JedisTemplate template = new JedisTemplate();

	public static JedisTemplate getInstance() {
		return template;
	}

	private JedisTemplate() {// 私有构造函数
	}

	/**
	 * put key - value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putKey(String key, String value) {
		Jedis jedis = RedisCon.getCon();
		String result = jedis.set(key, value);
		RedisCon.close(jedis);
		return result.equals("OK");
	}

	/**
	 * put key - value 带过期时间
	 * 
	 * @param key
	 * @param value
	 * @param time
	 * @return
	 */
	public boolean putKeyWithExpire(String key, String value, int seconds) {
		Jedis jedis = RedisCon.getCon();
		String result = jedis.setex(key, seconds, value);
		RedisCon.close(jedis);
		return result.equals("OK");
	}

	/**
	 * get value by key
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.get(key);
		}
	}

	/**
	 * put hash key - value
	 * 
	 * @param hash
	 * @param key
	 * @param value
	 * @return 重复存放的时候会返回0，但是值也会被覆盖
	 */
	public long putHash(String hash, String key, String value) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.hset(hash, key, value);// 重复存放时候 返回0，但是值也会被覆盖
		}

	}

	/**
	 * get hash key
	 * 
	 * @param hash
	 * @param key
	 * @return
	 */
	public String getHash(String hash, String key) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.hget(hash, key);
		}
	}

	/**
	 * rput list
	 * 
	 * @param list
	 * @param value
	 * @return 返回的是list的长度
	 */
	public long rputList(String list, String value) {
		try (Jedis jedis = RedisCon.getCon();) { // 使用 jdk7 语法糖
			return jedis.rpush(list, value); // 返回的是list的长度
		}
	}

	/**
	 * lrange start - start+size
	 * 
	 * @param list
	 * @param start
	 * @param size
	 * @return
	 */
	public List<String> lrangeList(String list, int start, int size) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.lrange(list, start, start + size - 1);
		}
	}

	/**
	 * 向 zset 中添加元素
	 * 
	 * @param zset
	 * @param member
	 * @param score
	 * @return 重复添加返回0
	 */
	public long zadd(String zset, String member, double score) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.zadd(zset, score, member);
		}
	}

	/**
	 * 查询 zset 范围 start szie 默认的是从小到达的顺序
	 * 
	 * @param zset
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> zrange(String zset, int start, int size) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.zrange(zset, start, start + size - 1);
		}
	}

	/**
	 * 安装 从大到小的顺序
	 * 获取元素
	 * @param zset
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> ZRevRange(String zset, int start, int size) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.zrevrange(zset, start, start + size - 1);
		}
	}

}
