package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import net.slipp.domain.User;
//import net.slipp.domain.UserRepository;
//import net.slipp.domain.Question;
//import net.slipp.domain.UserRepository;
import net.slipp.domain.QuestionRepository;

@Controller
public class HomeRepository {
	@Autowired
	private QuestionRepository questionRepository;
//	private UserRepository userRepository;
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
//		model.addAttribute("users", userRepository.findAll());
		return "index";
	}
}
