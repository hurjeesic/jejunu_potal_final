package kr.ac.jejunu.repository;

import kr.ac.jejunu.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<Todo, Integer> {

}
