package vote.dao;

import vote.bean.User;
import vote.util.JedisTemplate;

public class UserDao {
	private JedisTemplate template = JedisTemplate.getInstance();
	private static UserDao userDao = new UserDao();

	private UserDao() {

	}

	public static UserDao getInstance() {
		return userDao;
	}

	public User loginByNameAndPassword(User user) {
		String userName = template.getHash("user_List_name_id", user.getUserName());
		if (userName == null) {
			return null;
		} else {
			String password = template.getHash(userName, "password");
			if (user.getPassword().equals(password)) {
				user.setUserId(template.getHash(userName, "id"));
				return user;
			} else {
				return null;
			}
		}
	}
}
