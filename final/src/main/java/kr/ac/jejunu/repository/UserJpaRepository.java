package kr.ac.jejunu.repository;

import kr.ac.jejunu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
}
