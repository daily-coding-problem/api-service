package com.dcp.api_service.v1.leetcode.repository;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class CustomProblemRepositoryImpl implements CustomProblemRepository {

	private final EntityManager entityManager;

	public CustomProblemRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Problem findRandomProblem() {
		Query countQuery = entityManager.createQuery("SELECT COUNT(p) FROM Problem p");
		long count = (long) countQuery.getSingleResult();

		if (count == 0) {
			return null;
		}

		int randomIndex = new Random().nextInt((int) count);
		Query selectQuery = entityManager.createQuery("SELECT p FROM Problem p");
		selectQuery.setFirstResult(randomIndex);
		selectQuery.setMaxResults(1);

		return (Problem) selectQuery.getSingleResult();
	}
}
