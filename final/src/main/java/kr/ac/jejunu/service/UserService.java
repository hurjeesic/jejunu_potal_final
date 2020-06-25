package kr.ac.jejunu.service;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserJpaRepository userJpaRepository;

	public User getUser(HttpSession session) {
		return (User)session.getAttribute("user");
	}

	public Optional<User> loginUser(String id, String password) {
		return userJpaRepository.findByIdAndPassword(id, password);
	}

	public boolean confirmId(String id) {
		return userJpaRepository.findById(id).isPresent();
	}

	public boolean confirmNickname(String nickname) {
		return !userJpaRepository.findByNickname(nickname).isPresent();
	}

	public void insertUser(User user) {
		userJpaRepository.save(user);
	}
}
