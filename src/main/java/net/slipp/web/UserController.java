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
@RequestMapping("/")
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
		
		if (!password.equals(user.getPassword())) {
			System.out.println("========== Login FAILED... password was wrong! =============");
			return "redirect:/user/loginForm";
		}
		System.out.println("========== Login Success!! User is " + user + " =============");
		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
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
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable long id, Model model) {
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	@PutMapping("/users/{id}")
	public String update(@PathVariable long id, User newUser) {
		System.out.println("asfsleiflsaijflsijfalsijflisj");
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
