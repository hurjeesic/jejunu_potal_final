package kr.ac.jejunu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "userinfo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	private Integer no;

	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nickname", unique = true, nullable = false)
	private String nickname;
}
