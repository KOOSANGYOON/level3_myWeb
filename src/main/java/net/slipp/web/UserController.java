package net.slipp.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	private List<User> users = new ArrayList<User> ();
	
	@PostMapping("/create")
	public String create(User user) {
		System.out.println("user : " + user);
		users.add(user);
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/form")
	public String form() {
		return "form";
	}
	
	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "list";
	}
	
	@GetMapping("/user/profile")
	public String profile(User user, Model model) {
		System.out.println("take user : " + user);
		for (User userInList : users) {
			if (user.getUserId().equals(userInList.getUserId())) {
				model.addAttribute("name", user.getName());
				model.addAttribute("email", user.getEmail());
			}
		}
		System.out.println("take user second : " + user);
		return "profile";
	}
	
	@GetMapping("/user/login")
	public String login(String userId, String password, Model model) {
		System.out.println(userId + " " + password);
		return "login";
	}
	
	@PostMapping("/user/login/success")
	public String loginSuccess(String userId, String password, Model model) {
		model.addAttribute("userId", userId);
		model.addAttribute("password", password);
		return "loginSuccess";
	}

}
