package net.slipp.web;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;

	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		
		return Result.ok();
	}
	
	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		return true;
	}
	
	@GetMapping("/qnaform")
	public String qnaform() {
		return "/qna/qnaform";
	}
	
	@PostMapping("")
	public String qnaSubmit(String title, String contents, Model model, HttpSession session) {
		try {
			User sessionUser = HttpSessionUtils.getUserFromSession(session);
			Question newQuestion = new Question(sessionUser, title, contents);
			hasPermission(session, newQuestion);
			questionRepository.save(newQuestion);
			return "redirect:/";
		}catch (IllegalStateException e){
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}

//	@PostMapping("")		//기존에 짰던 qnasubmit 코드
//	public String qnaSubmit(String title, String contents, HttpSession session) {
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "redirect:/user/loginForm"; // redirect 하지 않으면, PostMappnig -> GetMapping 해야하는데, 이 부분을 구현해놓지 않았으므로,
//												// 405 에러가 뜬다.
//		}
//
//		User sessionUser = HttpSessionUtils.getUserFromSession(session);
//		Question newQuestion = new Question(sessionUser, title, contents);
//		questionRepository.save(newQuestion);
//
//		System.out.println("question : " + newQuestion);
//
//		return "redirect:/";
//	}

	@GetMapping("/{id}")
	public String readQuestion(@PathVariable long id, Model model) {
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}

		model.addAttribute("question", question);
		return "/qna/updateForm";
	}


	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}

		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}

		questionRepository.delete(id);
		return "redirect:/";
	}
}
