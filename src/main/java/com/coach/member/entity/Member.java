package com.coach.member.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String coachEmail;

    @Column(nullable = false)
    private String fullName;

    private String email;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private Integer age;
    private String country;
    private String stateCityProvince;
    private Integer heightCm;
    private Integer currentWeightKg;
    private String mainTrainingGoal;
    private String goal;
    private String previousWeightLoss;
    private String weightRegain;
    private String priorTrainingExperience;
    private String dailyTrainingCommitmentHours;
    private String preferredWorkoutTiming;
    private Integer daysPerWeekTrain;
    private String personalTrainingBefore;
    private String goalReward;
    private String additionalInfo;
    private String alcoholConsumption;
    private String smokingHabits;
    private String supplementsPast;
    private String steroidUsage;
    private Integer stressLevel;
    private String sleepHours;
    private String pastSportsActivity;
    private String foodPreference;
    private String activityLevel;
    @Column(columnDefinition = "TEXT")
    private String typicalDay;
    private String currentDietPlan;
    private String favoriteFoods;
    private String foodAllergies;
    private String medicalCondition;
    private String injuries;
    private String medicalConditionsDetailed;
    private String pushUp;
    private String squat;
    private String rowBandDumbbell;
    private String overheadPressDumbbell;
    private String hipHingeRdl;
    private String frontView;
    private String sideView;
    private String backView;
    private String notes;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column(nullable = false)
    private Instant createdAt;
    private Instant updatedAt;
}
