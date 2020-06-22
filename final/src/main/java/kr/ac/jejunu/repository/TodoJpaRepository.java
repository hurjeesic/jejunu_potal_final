package kr.ac.jejunu.repository;

import kr.ac.jejunu.entity.Todo;
import kr.ac.jejunu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoJpaRepository extends JpaRepository<Todo, Integer> {
	List<Todo> findAllByUser(User user);
}
