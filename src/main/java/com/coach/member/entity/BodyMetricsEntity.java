package com.coach.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "body_metrics",
        uniqueConstraints = @UniqueConstraint(columnNames = "member_id")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyMetricsEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID memberId;

    @Column(nullable = false)
    private Double heightCm;
    @Column(nullable = false)
    private Double currentWeightKg;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Boolean isLean;
    @Column(nullable = false)
    private Double activityFactor;
    @Column(nullable = false)
    private Double proteinRda;
    @Column(nullable = false)
    private Double carbFactor;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TargetGoal targetGoal;
    @Column(nullable = false)
    private Double targetCalorieFactor;

    @Column(nullable = false)
    private Double ibwKg;
    @Column(nullable = false)
    private Double bmi;
    @Column(nullable = false)
    private Double bmr;
    @Column(nullable = false)
    private Double tdee;
    @Column(nullable = false)
    private Double targetCalories;
    @Column(nullable = false)
    private Double proteinGrams;
    @Column(nullable = false)
    private Double carbsGrams;
    @Column(nullable = false)
    private Double fatsGrams;

    @Column(nullable = false)
    private Instant createdAt;
    private Instant updatedAt;
}
