package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserJpaRepository userJpaRepository;

	// 테스트용
	@GetMapping("/index")
	public User index() {
		return userJpaRepository.findById(1).get();
	}

	@GetMapping("/login")
	public ModelAndView showLogin() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:/index");
		if (!userJpaRepository.findByIdAndPassword(user.getId(), user.getPassword()).isPresent()) {
			modelAndView.setViewName("login");
			modelAndView.addObject("error", "아이디 또는 비밀번호를 다시 확인해주세요.");
		}

		return modelAndView;
	}
}
