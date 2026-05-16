package com.coach.member.dto;

import com.coach.member.entity.TargetGoal;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BodyMetricsRequest(
        @NotNull @Positive Double heightCm,
        @NotNull @Positive Double currentWeightKg,
        @NotNull String gender,
        @NotNull @Positive Integer age,
        @NotNull Boolean isLean,
        @NotNull @DecimalMin("1.2") @DecimalMax("1.9") Double activityFactor,
        @NotNull @DecimalMin("0.8") @DecimalMax("2.0") Double proteinRda,
        Double carbFactor,
        @NotNull TargetGoal targetGoal,
        @NotNull @DecimalMin("10.0") @DecimalMax("16.0") Double targetCalorieFactor,

        Double ibwKg,
        Double bmi,
        Double bmr,
        Double tdee,
        Double targetCalories,
        Double proteinGrams,
        Double carbsGrams,
        Double fatsGrams
) {}
