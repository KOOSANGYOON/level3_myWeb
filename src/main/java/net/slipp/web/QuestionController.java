package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import net.slipp.domain.UserRepository;
//import net.slipp.domain.User;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;

@Controller
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
//	private UserRepository userRepository;
	
	
	
	@GetMapping("/qnaform")
	public String qnaform() {
		return "/qna/qnaform";
	}
	
	@PostMapping("/questions")
	public String qnaSubmit(Question question, Model model) {
		questionRepository.save(question);
		System.out.println("question : " + question);
		
		return "redirect:/";
	}
	
	@GetMapping("/questions/{id}")
	public String readQuestion(@PathVariable long id, Model model) {
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "/qna/show";
	}
}
