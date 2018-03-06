package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		return true;
	}
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, Model model, HttpSession session) {
		try {
			User loginUser = HttpSessionUtils.getUserFromSession(session);
			Question question = questionRepository.findOne(questionId);
			Answer answer = new Answer(loginUser, question, contents);
			hasPermission(session, question);
			answerRepository.save(answer);
			return String.format("redirect:/questions/%d", questionId);
		}catch (IllegalStateException e){
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		
//		if (!HttpSessionUtils.isLoginUser(session)) {		//기존의 answer 구현코드.
//			return "redirect:/user/loginForm";
//		}
//		
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//		Question question = questionRepository.findOne(questionId);
//		Answer answer = new Answer(loginUser, question, contents);
//		answerRepository.save(answer);
//		return String.format("redirect:/questions/%d", questionId);
	}

}
