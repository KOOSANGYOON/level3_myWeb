package net.slipp.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/user/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/user/login")
	public String login(String userId, String password, Model model, HttpSession session) {
		System.out.println(userId + " " + password);
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			System.out.println("========== Login FAILED... user ID didn't exist =============");
			return "redirect:/user/loginForm";
		}
		
		if (!user.matchPassword(password)) {
			System.out.println("========== Login FAILED... password was wrong! =============");
			return "redirect:/user/loginForm";
		}
		System.out.println("========== Login Success!! User is " + user + " =============");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		System.out.println("======== Success to LOGOUT!! ========");
		
		return "redirect:/";
	}
	
	@PostMapping("/users")
	public String create(User user) {
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/user/list";
	}
	
	@GetMapping("/users/{userId}")
	public String seeUser(@PathVariable String userId, Model model) {
		for (User user : userRepository.findAll()) {
			if (userId.equals(user.getUserId())) {
				model.addAttribute("user", user);
			}
		}
		return "/user/profile";
	}
	
	@GetMapping("/user/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/user/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
//		if (!sessionedUser.matchId(id)) {
//			throw new IllegalStateException("본인의 정보만 수정할 수 있습니다.");
//		}
		
		User user = userRepository.findOne(sessionedUser.getId());
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/users/{id}")
	public String update(@PathVariable Long id, User newUser) {
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/form")
	public String form() {
		return "/user/form";
	}
	
	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/user/profile")
	public String profile(@PathVariable User user, Model model) {
		System.out.println("take user : " + user);
		for (User userInList : userRepository.findAll()) {
			if (user.getUserId().equals(userInList.getUserId())) {
				model.addAttribute("name", user.getName());
				model.addAttribute("email", user.getEmail());
			}
		}
		System.out.println("take user second : " + user);
		return "/user/profile";
	}
}
