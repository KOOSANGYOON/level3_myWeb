package net.slipp.web;

import javax.servlet.http.HttpSession;

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
import net.slipp.domain.User;

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
	public String qnaSubmit(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		
		System.out.println("question : " + newQuestion);
		
		return "redirect:/";
	}
	
	@GetMapping("/questions/{id}")
	public String readQuestion(@PathVariable long id, Model model) {
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "/qna/show";
	}
}
