package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
//	private boolean hasPermission(HttpSession session, Question question) {
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			throw new IllegalStateException("로그인이 필요합니다.");
//		}
//		return true;
//	}
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
//		try {		//내가 구현해본 부분.
//			User loginUser = HttpSessionUtils.getUserFromSession(session);
//			Question question = questionRepository.findOne(questionId);
//			Answer answer = new Answer(loginUser, question, contents);
//			hasPermission(session, question);
//			return answerRepository.save(answer);
//		}catch (IllegalStateException e){
//			model.addAttribute("errorMessage", e.getMessage());
//			return null;
//		}
		
//		if (!HttpSessionUtils.isLoginUser(session)) {		//기존의 answer 구현코드.
//			return "redirect:/user/loginForm";
//		}
//		
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//		Question question = questionRepository.findOne(questionId);
//		Answer answer = new Answer(loginUser, question, contents);
//		answerRepository.save(answer);
//		return String.format("redirect:/questions/%d", questionId);
		
		if (!HttpSessionUtils.isLoginUser(session)) {		//기존의 answer 구현코드. 수정1
			return null;
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		return answerRepository.save(answer);
	}

}
