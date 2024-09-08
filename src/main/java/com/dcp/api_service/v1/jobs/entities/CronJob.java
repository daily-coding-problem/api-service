package com.dcp.api_service.v1.jobs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "cron_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CronJob {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;
	private String schedule;
	private Boolean enabled;
	private Timestamp lastRunAt;
	private Timestamp nextRunAt;
}
