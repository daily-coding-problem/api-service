package com.dcp.api_service.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class StudyPlanProblemId implements Serializable {
	private Long studyPlanId;
	private Long problemId;
}
