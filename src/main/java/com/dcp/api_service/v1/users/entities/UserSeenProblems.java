package com.dcp.api_service.v1.users.entities;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "user_seen_problems")
@Data
public class UserSeenProblems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "problem_id", nullable = false)
	private Problem problem;

	private Timestamp problemSentAt;   // The timestamp when the problem was sent.
	private Timestamp solutionSentAt;  // The timestamp when the solution was sent (for premium users).

	@Column(nullable = false)
	private String status;  // "PROBLEM_SENT", "SOLUTION_SENT"
}
