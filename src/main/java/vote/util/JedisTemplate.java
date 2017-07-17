package vote.util;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * redis����ģ���� (δʹ�� ���� �� ���л�)
 * 
 * @author joe
 *
 */
public class JedisTemplate {
	// ����ʽ ����ģʽ
	private final static JedisTemplate template = new JedisTemplate();

	public static JedisTemplate getInstance() {
		return template;
	}

	private JedisTemplate() {// ˽�й��캯��
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
	 * put key - value ������ʱ��
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
	 * @return �ظ���ŵ�ʱ��᷵��0������ֵҲ�ᱻ����
	 */
	public long putHash(String hash, String key, String value) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.hset(hash, key, value);// �ظ����ʱ�� ����0������ֵҲ�ᱻ����
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
	 * @return ���ص���list�ĳ���
	 */
	public long rputList(String list, String value) {
		try (Jedis jedis = RedisCon.getCon();) { // ʹ�� jdk7 �﷨��
			return jedis.rpush(list, value); // ���ص���list�ĳ���
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
	 * �� zset �����Ԫ��
	 * 
	 * @param zset
	 * @param member
	 * @param score
	 * @return �ظ���ӷ���0
	 */
	public long zadd(String zset, String member, double score) {
		try (Jedis jedis = RedisCon.getCon();) {
			return jedis.zadd(zset, score, member);
		}
	}

	/**
	 * ��ѯ zset ��Χ start szie Ĭ�ϵ��Ǵ�С�����˳��
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
	 * ��װ �Ӵ�С��˳��
	 * ��ȡԪ��
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
