package com.reddy.service.onboarding;

import com.reddy.model.onboarding.OnboardingTask;
import com.reddy.repository.onboarding.OnboardingTaskRepository;

import java.util.List;

public class OnboardingTaskServiceImp implements OnboardingTaskService{
    private final OnboardingTaskRepository onboardingTaskRepository;
    public OnboardingTaskServiceImp(OnboardingTaskRepository onboardingTaskRepository) {
        this.onboardingTaskRepository = onboardingTaskRepository;
    }
    @Override
    public List<OnboardingTask> getAllOnboardingTasks() {
        return onboardingTaskRepository.findAll();
    }

    @Override
    public OnboardingTask getOnboardingTaskById(Long id) {
        return onboardingTaskRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public OnboardingTask createOnboardingTask(OnboardingTask onboardingTask) {
        return onboardingTaskRepository.save(onboardingTask);
    }

    @Override
    public OnboardingTask updateOnboardingTask(OnboardingTask onboardingTask) {
        return onboardingTaskRepository.save(onboardingTask);
    }

    @Override
    public void deleteOnboardingTask(Long id) {
        onboardingTaskRepository.deleteById(id);
    }
}
