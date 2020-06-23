package kr.ac.jejunu.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

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

	@Column(name = "time", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar time;

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

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "complete", columnDefinition = "boolean default false")
	private Boolean complete;
}
