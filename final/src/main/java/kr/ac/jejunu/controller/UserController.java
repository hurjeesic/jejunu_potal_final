package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserJpaRepository userJpaRepository;

	@GetMapping("/index")
	public User index() {

		return userJpaRepository.findById(1).get();
	}
}
