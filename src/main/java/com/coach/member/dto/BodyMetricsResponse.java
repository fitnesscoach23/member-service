package com.coach.member.dto;

import com.coach.member.entity.TargetGoal;

import java.time.Instant;
import java.util.UUID;

public record BodyMetricsResponse(
        UUID id,
        UUID memberId,
        Double heightCm,
        Double currentWeightKg,
        String gender,
        Integer age,
        Boolean isLean,
        Double activityFactor,
        Double proteinRda,
        Double carbFactor,
        TargetGoal targetGoal,
        Double targetCalorieFactor,
        Double ibwKg,
        Double bmi,
        Double bmr,
        Double tdee,
        Double targetCalories,
        Double proteinGrams,
        Double carbsGrams,
        Double fatsGrams,
        Instant createdAt,
        Instant updatedAt
) {}
