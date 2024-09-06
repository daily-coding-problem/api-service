package com.dcp.api_service.v1.leetcode.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "problems", schema = "leetcode")
public class Problem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Integer questionId;
	private String title;
	private String slug;
	private String content;
	private String difficulty;

	@ElementCollection
	private List<String> topics;

	@ElementCollection
	private List<String> companies;

	@ElementCollection
	private List<String> hints;

	private String link;

	private String solution;
}
