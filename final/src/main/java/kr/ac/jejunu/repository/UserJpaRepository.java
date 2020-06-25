package kr.ac.jejunu.repository;

import kr.ac.jejunu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
	Optional<User> findByIdAndPassword(String id, String password);
	Optional<User> findById(String id);
	Optional<User> findByNickname(String nickname);
}
