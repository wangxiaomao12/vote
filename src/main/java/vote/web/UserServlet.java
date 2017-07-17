package vote.web;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import vote.bean.User;
import vote.bean.Vote;
import vote.service.UserService;

public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if (StringUtils.isEmpty(action)) {
			resp.sendRedirect("common/error.jsp");
		}
		switch (action) {// JDK新特性 String可用于Switch语句
		case "login":
			login(req, resp);
			break;

		case "pubVote":
			pubVote(req, resp);
			break;

		case "index":
			index(req, resp);
			break;
		default:
			break;
		}
	}

	private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pageString = req.getParameter("page");
		int page;
		if (StringUtils.isEmpty(pageString)) {
			page = 1;
		} else {
			page = Integer.parseInt(pageString);
		}
		List<Vote> voteList = userService.getIndexVote(page);
		req.setAttribute("voteList", voteList);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void pubVote(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String voteContent = req.getParameter("vote");
		String voteTitle = req.getParameter("title");
		if (StringUtils.isEmpty(voteContent) || StringUtils.isEmpty(voteTitle)) {
			resp.getWriter().write("isblank");
			return;
		}
		Vote vote = new Vote();
		vote.setAuthor(((User) req.getSession().getAttribute("user")).getUserId());
		vote.setContent(voteContent);
		vote.setId(UUID.randomUUID().toString());
		vote.setTime(System.currentTimeMillis());
		vote.setTitle(voteTitle);
		vote.setVotes(0);
		if (userService.publishVote(vote)) {
			resp.sendRedirect("first.jsp");
		} else {
			resp.getWriter().write("error");
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			resp.getWriter().write("isblank");
			return;
		}
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		User currentUser = userService.login(user);
		if (currentUser != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", currentUser);
			resp.sendRedirect("first.jsp");
		} else {
			resp.getWriter().write("error");
		}
	}

}
