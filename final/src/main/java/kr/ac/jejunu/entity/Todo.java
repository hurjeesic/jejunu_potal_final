package kr.ac.jejunu.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "todoinfo")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer no;

	@JoinColumn(name = "userinfo", nullable = false)
	@ManyToOne(targetEntity = User.class)
	private User user;

	@Column(name = "created_time")
	@CreatedDate
	private Calendar createdTime;

	@Column(name = "modified_time")
	@LastModifiedDate
	private Calendar modifiedTime;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "complete", columnDefinition = "boolean default false")
	private Boolean complete;
}
