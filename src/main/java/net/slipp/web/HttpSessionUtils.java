package net.slipp.web;

import javax.servlet.http.HttpSession;

import net.slipp.domain.User;

public class HttpSessionUtils {		//중복 제거를 위한 코드입니다.
	public static final String USER_SESSION_KEY = "sessionedUser";
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		
		return (User)session.getAttribute(USER_SESSION_KEY);
	}

}
