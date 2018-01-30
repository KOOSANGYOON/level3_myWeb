package net.slipp.domain;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeRepository {
	@Autowired
	private QuestionRepository questionRepository;
//	private UserRepository userRepository;
	
	@GetMapping("/")
	public String main(HttpSession session) {
//		model.addAttribute("questions", questionRepository.findAll());
		session.setAttribute("questions", questionRepository.findAll());
//		model.addAttribute("users", userRepository.findAll());
		return "index";
	}
}
