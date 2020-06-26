package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/login")
	public ModelAndView showLogin() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView login(HttpSession session, @ModelAttribute User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:/index");
		Optional<User> loginUser = userService.loginUser(user.getId(), user.getPassword());
		if (!loginUser.isPresent()) {
			modelAndView.setViewName("login");
			modelAndView.addObject("id", user.getId());
			modelAndView.addObject("msg", "아이디 또는 비밀번호를 다시 확인해주세요.");
		}
		else {
			session.setAttribute("user", loginUser.get());
		}

		return modelAndView;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();

		return new ModelAndView("redirect:/login");
	}

	@PostMapping("/user/register")
	public ModelAndView insertUser(@ModelAttribute User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:../login");

		userService.insertUser(user);
		modelAndView.addObject("msg", "가입되었습니다.");

		return modelAndView;
	}

	@GetMapping("/user/update")
	public ModelAndView showUpdatedUser(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("updatedUser");

		modelAndView.addObject("user", session.getAttribute("user"));

		return modelAndView;
	}

	@PostMapping("/user/update")
	public ModelAndView updateUser(HttpSession session, @ModelAttribute User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/update");

		session.setAttribute("user", userService.updateUser(userService.getUser(session), user));

		return modelAndView;
	}

	@GetMapping("/user/delete")
	public ModelAndView deleteUser(HttpSession session, @RequestParam String password) {
		ModelAndView modelAndView;

		if (userService.deleteUser(userService.getUser(session), password)) {
			modelAndView = new ModelAndView("redirect:../login");
			session.invalidate();
		}
		else {
			modelAndView = new ModelAndView("redirect:/user/update");
			modelAndView.addObject("msg", "비밀번호가 틀렸습니다.");
		}

		return modelAndView;
	}

	@GetMapping("/user/id/confirm")
	@ResponseBody
	public boolean confirmId(@RequestParam String id) {
		return userService.confirmId(id);
	}

	@GetMapping("/user/nickname/confirm")
	@ResponseBody
	public boolean confirmNickname(HttpSession session, @RequestParam String nickname) {
		return userService.confirmNickname(userService.getUser(session), nickname);
	}
}
