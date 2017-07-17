package vote.util;

public class InitSystem {
	private static JedisTemplate template = JedisTemplate.getInstance();

	public static void main(String[] args) {
		template.putHash("user_List_name_id", "123", "user:1");
		
		template.putHash("user:1", "id", "1");
		template.putHash("user:1", "password", "123");
		template.putHash("user:1", "name", "123");
		
	}
}
