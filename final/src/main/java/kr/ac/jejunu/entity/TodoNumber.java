package kr.ac.jejunu.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TodoNumber {
	Integer total;
	Integer process;
	Integer complete;
}
