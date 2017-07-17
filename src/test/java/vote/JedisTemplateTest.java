package vote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vote.util.JedisTemplate;

public class JedisTemplateTest {
	private JedisTemplate template = null;

	@Before
	public void before() {
		template = JedisTemplate.getInstance();
	}

	@Test
	public void test() {
		Assert.assertTrue(template.putKey("hello", "world"));
	}

	@Test
	public void test2() {
		Assert.assertTrue(template.putKeyWithExpire("hello", "world", 3));
	}
	
	@Test
	public void test3() {
		Assert.assertEquals(1l, template.putHash("hash", "key", "value1"));
	}
	
	@Test
	public void test4() {
		Assert.assertEquals("value1", template.getHash("hash", "key"));
	}
	
	@Test
	public void test5() {
		Assert.assertEquals(1l,template.rputList("list", "a"));
	}
	
	@Test
	public void test6() {
		Assert.assertEquals(3,template.lrangeList("list", 0, 3).size());
	}

	@Test
	public void test7() {
		Assert.assertEquals(1l,template.zadd("zset", "member3", 0.1));
	}
	
	@Test
	public void test8() {
		Assert.assertEquals(1,template.zrange("zset", 0, 1).size());
	}
}
