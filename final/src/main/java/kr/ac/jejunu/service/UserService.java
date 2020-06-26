package kr.ac.jejunu.service;

import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.TodoJpaRepository;
import kr.ac.jejunu.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.lang.ref.PhantomReference;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserJpaRepository userJpaRepository;
	private final TodoJpaRepository todoJpaRepository;

	public User getUser(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return (User)session.getAttribute("user");
		}

		return null;
	}

	public User updateUser(User original, User user) {
		user.setNo(original.getNo());
		user.setId(original.getId());

		return userJpaRepository.save(user);
	}

	public boolean deleteUser(User user, String password) {
		if (user.getPassword().equals(password)) {
			todoJpaRepository.deleteAllByUser(user);
			userJpaRepository.delete(user);

			return true;
		}

		return false;
	}

	public Optional<User> loginUser(String id, String password) {
		return userJpaRepository.findByIdAndPassword(id, password);
	}

	public boolean confirmId(String id) {
		return !userJpaRepository.findById(id).isPresent();
	}

	public boolean confirmNickname(User user, String nickname) {
		return (user != null && user.getNickname().equals(nickname)) || !userJpaRepository.findByNickname(nickname).isPresent();
	}

	public void insertUser(User user) {
		userJpaRepository.save(user);
	}
}
