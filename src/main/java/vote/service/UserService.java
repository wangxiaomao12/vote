package vote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import vote.bean.User;
import vote.bean.Vote;
import vote.dao.UserDao;
import vote.util.JedisTemplate;

public class UserService {
	private UserDao userDao = UserDao.getInstance();
	private JedisTemplate template = JedisTemplate.getInstance();
	private static UserService userService = new UserService();

	private UserService() {

	}

	public static UserService getInstance() {
		return userService;
	}

	public User login(User user) {
		return userDao.loginByNameAndPassword(user);
	}

	public boolean publishVote(Vote vote) {
		String voteHash = "vote:" + vote.getId(); // jvm会自动优化这里
		template.putHash(voteHash, "author", vote.getAuthor());
		template.putHash(voteHash, "content", vote.getContent());
		template.putHash(voteHash, "id", vote.getId());
		template.putHash(voteHash, "title", vote.getTitle());
		template.putHash(voteHash, "time", vote.getTime() + "");
		template.putHash(voteHash, "votes", vote.getVotes() + "");
		template.zadd("vote_list", voteHash, vote.getTime());//这个vote_list使用的是 发布时间从大大小的顺序
		return true;
	}

	public List<Vote> getIndexVote(int page) {
		int size = 10;
		Set<String> voteSet = template.ZRevRange("vote_list", (page - 1) * size, size);
		List<Vote> voteList = new ArrayList<>();
		for (String voteHash : voteSet) {
			Vote vote = new Vote();
			vote.setId(template.getHash(voteHash, "id"));
			vote.setAuthor(template.getHash(voteHash, "author"));
			vote.setContent(template.getHash(voteHash, "content"));
			vote.setTime(Long.parseLong(template.getHash(voteHash, "time")));
			vote.setTitle(template.getHash(voteHash, "title"));
			vote.setVotes(Integer.parseInt(template.getHash(voteHash, "votes")));
			voteList.add(vote);
		}
		return voteList;
	}
}
