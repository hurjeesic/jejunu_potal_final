package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserJpaRepository userJpaRepository;

	@GetMapping("/login")
	public ModelAndView showLogin() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, @ModelAttribute User user) {
		HttpSession session = request.getSession();
		ModelAndView modelAndView = new ModelAndView("redirect:index");
		String id = user.getId();
		Optional<User> loginUser = userJpaRepository.findByIdAndPassword(user.getId(), user.getPassword());
		if (!loginUser.isPresent()) {
			modelAndView.setViewName("login");
			modelAndView.addObject("id", id);
			modelAndView.addObject("error", "아이디 또는 비밀번호를 다시 확인해주세요.");
		}
		else {
			session.setAttribute("user", loginUser.get());
		}

		return modelAndView;
	}
}
