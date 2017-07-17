package vote.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ��װ��jedis���ӳ� ��ʵ���Գ��Խ�redis������д�������ļ��У�ͨ����ȡ�����ļ�������
 * 
 * @author joe
 *
 */
public class RedisCon {
	// Redis������IP
	private static final String ADDR = "127.0.0.1";
	// Redis�Ķ˿ں�
	private static final int PORT = 6379;
	// ��������ʵ���������Ŀ��Ĭ��ֵΪ8��
	// �����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
	private static final int MAX_ACTIVE = 1024;
	// ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8��
	private static final int MAX_IDLE = 200;
	// �ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
	private static final int MAX_WAIT = 10000;
	private static final int TIMEOUT = 10000;
	// ��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
	private static final boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;
	private static JedisPoolConfig config = new JedisPoolConfig();
	static {
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(MAX_ACTIVE);// �������ǰ��maxActive
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, null);// ����Ϊ��
	}

	public static Jedis getCon() {
		if (jedisPool == null) {
			throw new NullPointerException("JedisPool NullException");
		}
		return jedisPool.getResource(); // ���̰߳�ȫ��
	}

	public static void close(Jedis jedis) {
		if (jedis == null) {
			throw new IllegalArgumentException("��������");
		}
		jedis.close();// �ͷ�jedis��Դ �����ӳ�
	}

}
