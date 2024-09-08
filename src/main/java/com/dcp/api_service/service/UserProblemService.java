package com.dcp.api_service.service;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import com.dcp.api_service.v1.leetcode.entities.StudyPlanProblem;
import com.dcp.api_service.v1.leetcode.repository.StudyPlanProblemRepository;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.entities.UserSeenProblems;
import com.dcp.api_service.v1.users.entities.UserStudyPlan;
import com.dcp.api_service.v1.users.repository.UserSeenProblemsRepository;
import com.dcp.api_service.v1.users.repository.UserStudyPlanRepository;
import com.dcp.api_service.utilities.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserProblemService {
	private final UserSeenProblemsRepository userSeenProblemsRepository;
	private final StudyPlanProblemRepository studyPlanProblemRepository;
	private final UserStudyPlanRepository userStudyPlanRepository;

	private final EmailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(UserProblemService.class);

	public UserProblemService(UserSeenProblemsRepository userSeenProblemsRepository, StudyPlanProblemRepository studyPlanProblemRepository,
	                      UserStudyPlanRepository userStudyPlanRepository, EmailService emailService) {
		this.userSeenProblemsRepository = userSeenProblemsRepository;
		this.studyPlanProblemRepository = studyPlanProblemRepository;
		this.userStudyPlanRepository = userStudyPlanRepository;
		this.emailService = emailService;
	}

	public void processUserProblem(User user) {
		Problem nextProblem = getNextUnseenOrPendingProblem(user);

		if (nextProblem == null) {
			logger.info("No unseen or pending problems left for user: {}", user.getEmail());
			return;
		}

		Optional<UserSeenProblems> userSeenProblemOpt = userSeenProblemsRepository.findByUserIdAndProblemId(user.getId(), nextProblem.getId());
		boolean userHasSeenProblem = userSeenProblemOpt.isPresent();

		if (!userHasSeenProblem) {
			logger.info("Sending problem {} to user: {}", nextProblem.getTitle(), user.getEmail());
			emailService.sendProblem(user, nextProblem);
			markProblemAsSeen(user, nextProblem, "PROBLEM_SENT");
			return;
		}

		UserSeenProblems userSeenProblem = userSeenProblemOpt.get(); // Get the actual object

		if (user.isPremium() && "PROBLEM_SENT".equals(userSeenProblem.getStatus()) &&
			DateUtil.isOneDayLater(userSeenProblem.getProblemSentAt())) {
			logger.info("Sending solution for problem {} to premium user: {}", nextProblem.getTitle(), user.getEmail());
			emailService.sendSolution(user, nextProblem);
			markProblemAsSeen(user, nextProblem, "SOLUTION_SENT");
			return;
		}

		logger.info("No further actions required for user: {}", user.getEmail());
	}

	private Problem getNextUnseenOrPendingProblem(User user) {
		// Iterate over all study plans of the user
		for (UserStudyPlan userStudyPlan : user.getStudyPlans()) {
			if (userStudyPlan.getFinishedAt() != null) {
				logger.debug("Skipping finished study plan: {}", userStudyPlan.getStudyPlanId());
				continue; // Skip finished study plans
			}

			// Fetch all problems associated with the current study plan
			List<StudyPlanProblem> problems = studyPlanProblemRepository.findByStudyPlanId(userStudyPlan.getStudyPlanId());

			for (StudyPlanProblem studyPlanProblem : problems) {
				Problem problem = studyPlanProblem.getProblem();
				Optional<UserSeenProblems> seenProblemOpt = userSeenProblemsRepository.findByUserIdAndProblemId(user.getId(), problem.getId());

				// If the problem has not been seen, return it
				if (seenProblemOpt.isEmpty()) {
					logger.debug("Found unseen problem: {} for user: {}", problem.getTitle(), user.getEmail());
					return problem;
				}

				UserSeenProblems seenProblem = seenProblemOpt.get(); // Get the actual UserSeenProblems object

				// Check if the user is premium and if the problem is pending
				if (user.isPremium() && "PROBLEM_SENT".equals(seenProblem.getStatus()) && DateUtil.isOneDayLater(seenProblem.getProblemSentAt())) {
					logger.debug("Found pending problem: {} for premium user: {}", problem.getTitle(), user.getEmail());
					return problem;
				}
			}

			// If all problems in this study plan have been seen and the solutions have been sent, mark it as finished
			markStudyPlanAsFinished(userStudyPlan);
			logger.info("Marked study plan {} as finished for user: {}", userStudyPlan.getStudyPlanId(), user.getEmail());
		}

		// Return null if no unseen or pending problems are found
		logger.info("No unseen or pending problems found for user: {}", user.getEmail());
		return null;
	}

	private void markStudyPlanAsFinished(UserStudyPlan userStudyPlan) {
		userStudyPlan.setFinishedAt(new java.sql.Timestamp(System.currentTimeMillis()));
		userStudyPlanRepository.save(userStudyPlan);
		logger.info("Study plan {} marked as finished.", userStudyPlan.getStudyPlanId());
	}

	private void markProblemAsSeen(User user, Problem problem, String status) {
		Optional<UserSeenProblems> seenProblemOpt = userSeenProblemsRepository.findByUserIdAndProblemId(user.getId(), problem.getId());

		UserSeenProblems seenProblem = seenProblemOpt.orElseGet(() -> {
			// Create a new record if this is the first time the user sees the problem.
			UserSeenProblems newSeenProblem = new UserSeenProblems();
			newSeenProblem.setUser(user);
			newSeenProblem.setProblem(problem);
			newSeenProblem.setStatus(status);

			if ("PROBLEM_SENT".equals(status)) {
				newSeenProblem.setProblemSentAt(new Timestamp(System.currentTimeMillis()));
			}

			return newSeenProblem;
		});

		// Update the status for existing records
		seenProblem.setStatus(status);

		// Set the solution sent time if the status is "SOLUTION_SENT"
		if ("SOLUTION_SENT".equals(status)) {
			seenProblem.setSolutionSentAt(new Timestamp(System.currentTimeMillis()));
		}

		userSeenProblemsRepository.save(seenProblem); // Save the new or updated record
	}

}
