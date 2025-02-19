package com.reddy.service.onboarding;

import com.reddy.model.onboarding.OnboardingTask;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OnboardingTaskService {
    List<OnboardingTask> getAllOnboardingTasks();
    OnboardingTask getOnboardingTaskById(Long id);
    OnboardingTask createOnboardingTask(OnboardingTask onboardingTask);
    OnboardingTask updateOnboardingTask(OnboardingTask onboardingTask);
    void deleteOnboardingTask(Long id);
}
