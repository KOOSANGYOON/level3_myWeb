package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
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
		return "profile";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable long id, Model model) {
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "updateForm";
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
		return "form";
	}
	
	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "list";
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
		return "profile";
	}
	
	@GetMapping("/user/login")
	public String login(String userId, String password, Model model) {
		System.out.println(userId + " " + password);
		return "login";
	}
}
