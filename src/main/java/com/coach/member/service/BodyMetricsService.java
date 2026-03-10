package com.coach.member.service;

import com.coach.member.dto.BodyMetricsRequest;
import com.coach.member.dto.BodyMetricsResponse;
import com.coach.member.entity.BodyMetricsEntity;
import com.coach.member.entity.Member;
import com.coach.member.entity.TargetGoal;
import com.coach.member.repository.BodyMetricsRepository;
import com.coach.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BodyMetricsService {

    private static final double DEFAULT_CARB_FACTOR = 3.5;

    private final BodyMetricsRepository repository;
    private final MemberRepository memberRepository;

    public BodyMetricsResponse getBodyMetrics(String coachEmail, UUID memberId) {
        loadMember(coachEmail, memberId);
        BodyMetricsEntity entity = repository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Body metrics not found"));
        return toResponse(entity);
    }

    public BodyMetricsResponse upsertBodyMetrics(String coachEmail, UUID memberId, BodyMetricsRequest req) {
        loadMember(coachEmail, memberId);
        BodyMetricsEntity entity = repository.findByMemberId(memberId)
                .orElseGet(() -> BodyMetricsEntity.builder()
                        .memberId(memberId)
                        .createdAt(Instant.now())
                        .build());

        applyRequest(entity, req);
        entity.setUpdatedAt(Instant.now());
        return toResponse(repository.save(entity));
    }

    public BodyMetricsResponse getBodyMetricsIfExists(UUID memberId) {
        return repository.findByMemberId(memberId).map(this::toResponse).orElse(null);
    }

    private Member loadMember(String coachEmail, UUID memberId) {
        return memberRepository.findById(memberId)
                .filter(mem -> mem.getCoachEmail().equals(coachEmail))
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    private void applyRequest(BodyMetricsEntity entity, BodyMetricsRequest req) {
        double heightCm = req.heightCm();
        double weightKg = req.currentWeightKg();
        String gender = req.gender();
        int age = req.age();
        boolean isLean = Boolean.TRUE.equals(req.isLean());
        double activityFactor = req.activityFactor();
        double proteinRda = req.proteinRda();
        double carbFactor = req.carbFactor() == null ? DEFAULT_CARB_FACTOR : req.carbFactor();
        TargetGoal targetGoal = req.targetGoal();
        double targetCalorieFactor = req.targetCalorieFactor();

        validateTargetCalorieFactor(targetGoal, targetCalorieFactor);

        double ibwKg = calculateIbwKg(heightCm, weightKg, gender, age, isLean);
        if (req.ibwKg() != null) {
            ibwKg = req.ibwKg();
        }

        double bmi = calculateBmi(weightKg, heightCm);
        if (req.bmi() != null) {
            bmi = req.bmi();
        }

        double bmr = calculateBmr(ibwKg, gender, age);
        if (req.bmr() != null) {
            bmr = req.bmr();
        }

        double tdee = bmr * activityFactor;
        if (req.tdee() != null) {
            tdee = req.tdee();
        }

        double targetCalories = (ibwKg * 2.20462) * targetCalorieFactor;
        if (req.targetCalories() != null) {
            targetCalories = req.targetCalories();
        }

        double proteinGrams = ibwKg * proteinRda;
        if (req.proteinGrams() != null) {
            proteinGrams = req.proteinGrams();
        }

        double carbsGrams = ibwKg * carbFactor;
        if (req.carbsGrams() != null) {
            carbsGrams = req.carbsGrams();
        }

        double fatsGrams = calculateFatsGrams(targetCalories, proteinGrams, carbsGrams);
        if (req.fatsGrams() != null) {
            fatsGrams = Math.max(0.0, req.fatsGrams());
        }

        entity.setHeightCm(heightCm);
        entity.setCurrentWeightKg(weightKg);
        entity.setGender(gender);
        entity.setAge(age);
        entity.setIsLean(isLean);
        entity.setActivityFactor(activityFactor);
        entity.setProteinRda(proteinRda);
        entity.setCarbFactor(carbFactor);
        entity.setTargetGoal(targetGoal);
        entity.setTargetCalorieFactor(targetCalorieFactor);

        entity.setIbwKg(ibwKg);
        entity.setBmi(bmi);
        entity.setBmr(bmr);
        entity.setTdee(tdee);
        entity.setTargetCalories(targetCalories);
        entity.setProteinGrams(proteinGrams);
        entity.setCarbsGrams(carbsGrams);
        entity.setFatsGrams(fatsGrams);
    }

    private void validateTargetCalorieFactor(TargetGoal goal, double factor) {
        boolean valid = switch (goal) {
            case FAT_LOSS -> factor >= 10 && factor <= 12;
            case MAINTENANCE -> factor >= 12 && factor <= 14;
            case GAINING -> factor >= 14 && factor <= 16;
        };
        if (!valid) {
            throw new RuntimeException("targetCalorieFactor out of range for goal");
        }
    }

    private double calculateIbwKg(double heightCm, double weightKg, String gender, int age, boolean isLean) {
        if (isLean) {
            return weightKg;
        }
        if (age >= 50) {
            return heightCm - 100;
        }
        if (gender != null && gender.equalsIgnoreCase("female")) {
            return heightCm - 107;
        }
        return heightCm - 105;
    }

    private double calculateBmi(double weightKg, double heightCm) {
        double heightM = heightCm / 100.0;
        return weightKg / (heightM * heightM);
    }

    private double calculateBmr(double ibwKg, String gender, int age) {
        if (age >= 50) {
            return ibwKg * 24;
        }
        if (gender != null && gender.equalsIgnoreCase("female")) {
            return ibwKg * 22.5;
        }
        return ibwKg * 24;
    }

    private double calculateFatsGrams(double targetCalories, double proteinGrams, double carbsGrams) {
        double fats = (targetCalories - (proteinGrams * 4 + carbsGrams * 4)) / 9.0;
        return Math.max(0.0, fats);
    }

    private BodyMetricsResponse toResponse(BodyMetricsEntity entity) {
        return new BodyMetricsResponse(
                entity.getId(),
                entity.getMemberId(),
                entity.getHeightCm(),
                entity.getCurrentWeightKg(),
                entity.getGender(),
                entity.getAge(),
                entity.getIsLean(),
                entity.getActivityFactor(),
                entity.getProteinRda(),
                entity.getCarbFactor(),
                entity.getTargetGoal(),
                entity.getTargetCalorieFactor(),
                entity.getIbwKg(),
                entity.getBmi(),
                entity.getBmr(),
                entity.getTdee(),
                entity.getTargetCalories(),
                entity.getProteinGrams(),
                entity.getCarbsGrams(),
                entity.getFatsGrams(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
