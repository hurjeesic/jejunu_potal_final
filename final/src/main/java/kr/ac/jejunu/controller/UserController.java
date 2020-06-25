package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
			modelAndView.addObject("msg", "아이디 또는 비밀번호를 다시 확인해주세요.");
		}
		else {
			session.setAttribute("user", loginUser.get());
		}

		return modelAndView;
	}

	@PostMapping("/user/register")
	public ModelAndView insertUser(@ModelAttribute User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:../login");

		userJpaRepository.save(user);
		modelAndView.addObject("msg", "가입되었습니다.");

		return modelAndView;
	}

	@GetMapping("/user/id/confirm")
	@ResponseBody
	public boolean confirmId(@RequestParam String id) {
		return userJpaRepository.findById(id).isPresent();
	}

	@GetMapping("/user/nickname/confirm")
	@ResponseBody
	public boolean confirmNickname(@RequestParam String nickname) {
		return userJpaRepository.findByNickname(nickname).isPresent();
	}
}
