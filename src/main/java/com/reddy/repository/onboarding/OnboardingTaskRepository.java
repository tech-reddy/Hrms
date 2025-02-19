package com.reddy.repository.onboarding;

import com.reddy.model.onboarding.OnboardingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingTaskRepository extends JpaRepository<OnboardingTask, Long> {
}
