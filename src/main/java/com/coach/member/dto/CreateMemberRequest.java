package com.coach.member.dto;

import com.coach.member.entity.MemberStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateMemberRequest(
        @NotBlank String fullName,
        String email,
        String phone,
        String gender,
        @JsonAlias("dob") LocalDate dateOfBirth,
        Integer age,
        String country,
        String stateCityProvince,
        Integer heightCm,
        Integer currentWeightKg,
        String mainTrainingGoal,
        String goal,
        String previousWeightLoss,
        String weightRegain,
        String priorTrainingExperience,
        String dailyTrainingCommitmentHours,
        String preferredWorkoutTiming,
        Integer daysPerWeekTrain,
        String personalTrainingBefore,
        String goalReward,
        String additionalInfo,
        String alcoholConsumption,
        String smokingHabits,
        String supplementsPast,
        String steroidUsage,
        Integer stressLevel,
        String sleepHours,
        String pastSportsActivity,
        String foodPreference,
        String activityLevel,
        String typicalDay,
        String currentDietPlan,
        String favoriteFoods,
        String foodAllergies,
        String medicalCondition,
        String injuries,
        String medicalConditionsDetailed,
        String pushUp,
        String squat,
        String rowBandDumbbell,
        String overheadPressDumbbell,
        String hipHingeRdl,
        String frontView,
        String sideView,
        String backView,
        MemberStatus status,
        String notes
) {}
