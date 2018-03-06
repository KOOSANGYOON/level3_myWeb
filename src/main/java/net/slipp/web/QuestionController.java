package net.slipp.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/qnaform")
	public String qnaform() {
		return "/qna/qnaform";
	}
	
	@PostMapping("")
	public String qnaSubmit(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/user/loginForm";		//redirect 하지 않으면, PostMappnig -> GetMapping 해야하는데, 이 부분을 구현해놓지 않았으므로, 405 에러가 뜬다.
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		
		System.out.println("question : " + newQuestion);
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String readQuestion(@PathVariable long id, Model model) {
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "/qna/show";
	}
}
