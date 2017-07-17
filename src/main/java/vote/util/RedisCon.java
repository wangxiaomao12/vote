package vote.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 封装了jedis连接池 其实可以尝试将redis的配置写在配置文件中，通过读取配置文件来配置
 * 
 * @author joe
 *
 */
public class RedisCon {
	// Redis服务器IP
	private static final String ADDR = "127.0.0.1";
	// Redis的端口号
	private static final int PORT = 6379;
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static final int MAX_ACTIVE = 1024;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static final int MAX_IDLE = 200;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static final int MAX_WAIT = 10000;
	private static final int TIMEOUT = 10000;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static final boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;
	private static JedisPoolConfig config = new JedisPoolConfig();
	static {
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(MAX_ACTIVE);// 这个是以前的maxActive
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, null);// 密码为空
	}

	public static Jedis getCon() {
		if (jedisPool == null) {
			throw new NullPointerException("JedisPool NullException");
		}
		return jedisPool.getResource(); // 是线程安全的
	}

	public static void close(Jedis jedis) {
		if (jedis == null) {
			throw new IllegalArgumentException("参数错误");
		}
		jedis.close();// 释放jedis资源 回连接池
	}

}
