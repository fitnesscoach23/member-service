package com.coach.member.service;

import com.coach.member.dto.*;
import com.coach.member.entity.Member;
import com.coach.member.entity.MemberStatus;
import com.coach.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final BodyMetricsService bodyMetricsService;

    public UUID createMember(String coachEmail, CreateMemberRequest req) {
        String normalizedEmail = normalize(req.email());
        Member member = findExistingMember(coachEmail, normalizedEmail)
                .orElseGet(() -> Member.builder()
                        .coachEmail(coachEmail)
                        .createdAt(Instant.now())
                        .build());

        applyCreateRequest(member, req);
        member.setUpdatedAt(Instant.now());
        repository.save(member);
        return member.getId();
    }

    public MemberResponse getMember(String coachEmail, UUID memberId) {
        Member m = repository.findById(memberId)
                .filter(mem -> mem.getCoachEmail().equals(coachEmail))
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return toResponse(m);
    }

    public List<MemberResponse> listMembers(String coachEmail) {
        return repository.findByCoachEmail(coachEmail)
                .stream().map(this::toResponse).toList();
    }

    public void updateMember(String coachEmail, UUID id, UpdateMemberRequest req) {
        Member m = repository.findById(id)
                .filter(mem -> mem.getCoachEmail().equals(coachEmail))
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (req.fullName() != null) m.setFullName(req.fullName());
        if (req.email() != null) m.setEmail(normalize(req.email()));
        if (req.phone() != null) m.setPhone(req.phone());
        if (req.gender() != null) m.setGender(req.gender());
        if (req.dateOfBirth() != null) m.setDateOfBirth(req.dateOfBirth());
        if (req.age() != null) m.setAge(req.age());
        if (req.country() != null) m.setCountry(req.country());
        if (req.stateCityProvince() != null) m.setStateCityProvince(req.stateCityProvince());
        if (req.heightCm() != null) m.setHeightCm(req.heightCm());
        if (req.currentWeightKg() != null) m.setCurrentWeightKg(req.currentWeightKg());
        if (req.mainTrainingGoal() != null) m.setMainTrainingGoal(req.mainTrainingGoal());
        if (req.goal() != null) m.setGoal(req.goal());
        if (req.previousWeightLoss() != null) m.setPreviousWeightLoss(req.previousWeightLoss());
        if (req.weightRegain() != null) m.setWeightRegain(req.weightRegain());
        if (req.priorTrainingExperience() != null) m.setPriorTrainingExperience(req.priorTrainingExperience());
        if (req.dailyTrainingCommitmentHours() != null) m.setDailyTrainingCommitmentHours(req.dailyTrainingCommitmentHours());
        if (req.preferredWorkoutTiming() != null) m.setPreferredWorkoutTiming(req.preferredWorkoutTiming());
        if (req.daysPerWeekTrain() != null) m.setDaysPerWeekTrain(req.daysPerWeekTrain());
        if (req.personalTrainingBefore() != null) m.setPersonalTrainingBefore(req.personalTrainingBefore());
        if (req.goalReward() != null) m.setGoalReward(req.goalReward());
        if (req.additionalInfo() != null) m.setAdditionalInfo(req.additionalInfo());
        if (req.alcoholConsumption() != null) m.setAlcoholConsumption(req.alcoholConsumption());
        if (req.smokingHabits() != null) m.setSmokingHabits(req.smokingHabits());
        if (req.supplementsPast() != null) m.setSupplementsPast(req.supplementsPast());
        if (req.steroidUsage() != null) m.setSteroidUsage(req.steroidUsage());
        if (req.stressLevel() != null) m.setStressLevel(req.stressLevel());
        if (req.sleepHours() != null) m.setSleepHours(req.sleepHours());
        if (req.pastSportsActivity() != null) m.setPastSportsActivity(req.pastSportsActivity());
        if (req.foodPreference() != null) m.setFoodPreference(req.foodPreference());
        if (req.activityLevel() != null) m.setActivityLevel(req.activityLevel());
        if (req.typicalDay() != null) m.setTypicalDay(req.typicalDay());
        if (req.currentDietPlan() != null) m.setCurrentDietPlan(req.currentDietPlan());
        if (req.favoriteFoods() != null) m.setFavoriteFoods(req.favoriteFoods());
        if (req.foodAllergies() != null) m.setFoodAllergies(req.foodAllergies());
        if (req.medicalCondition() != null) m.setMedicalCondition(req.medicalCondition());
        if (req.injuries() != null) m.setInjuries(req.injuries());
        if (req.medicalConditionsDetailed() != null) m.setMedicalConditionsDetailed(req.medicalConditionsDetailed());
        if (req.pushUp() != null) m.setPushUp(req.pushUp());
        if (req.squat() != null) m.setSquat(req.squat());
        if (req.rowBandDumbbell() != null) m.setRowBandDumbbell(req.rowBandDumbbell());
        if (req.overheadPressDumbbell() != null) m.setOverheadPressDumbbell(req.overheadPressDumbbell());
        if (req.hipHingeRdl() != null) m.setHipHingeRdl(req.hipHingeRdl());
        if (req.frontView() != null) m.setFrontView(req.frontView());
        if (req.sideView() != null) m.setSideView(req.sideView());
        if (req.backView() != null) m.setBackView(req.backView());
        if (req.status() != null) m.setStatus(req.status());
        if (req.notes() != null) m.setNotes(req.notes());
        m.setUpdatedAt(Instant.now());

        repository.save(m);
    }

    public void updateMemberStatus(String coachEmail, UUID id, MemberStatus status) {
        Member m = repository.findById(id)
                .filter(mem -> mem.getCoachEmail().equals(coachEmail))
                .orElseThrow(() -> new RuntimeException("Member not found"));
        m.setStatus(status);
        m.setUpdatedAt(Instant.now());
        repository.save(m);
    }

    public void deleteMember(String coachEmail, UUID id) {
        Member m = repository.findById(id)
                .filter(mem -> mem.getCoachEmail().equals(coachEmail))
                .orElseThrow(() -> new RuntimeException("Member not found"));
        repository.delete(m);
    }

    private MemberResponse toResponse(Member m) {
        BodyMetricsResponse bodyMetrics = bodyMetricsService.getBodyMetricsIfExists(m.getId());
        return new MemberResponse(
                m.getId(),
                m.getCoachEmail(),
                m.getFullName(),
                m.getEmail(),
                m.getPhone(),
                m.getGender(),
                m.getDateOfBirth(),
                m.getAge(),
                m.getCountry(),
                m.getStateCityProvince(),
                m.getHeightCm(),
                m.getCurrentWeightKg(),
                m.getMainTrainingGoal(),
                m.getGoal(),
                m.getPreviousWeightLoss(),
                m.getWeightRegain(),
                m.getPriorTrainingExperience(),
                m.getDailyTrainingCommitmentHours(),
                m.getPreferredWorkoutTiming(),
                m.getDaysPerWeekTrain(),
                m.getPersonalTrainingBefore(),
                m.getGoalReward(),
                m.getAdditionalInfo(),
                m.getAlcoholConsumption(),
                m.getSmokingHabits(),
                m.getSupplementsPast(),
                m.getSteroidUsage(),
                m.getStressLevel(),
                m.getSleepHours(),
                m.getPastSportsActivity(),
                m.getFoodPreference(),
                m.getActivityLevel(),
                m.getTypicalDay(),
                m.getCurrentDietPlan(),
                m.getFavoriteFoods(),
                m.getFoodAllergies(),
                m.getMedicalCondition(),
                m.getInjuries(),
                m.getMedicalConditionsDetailed(),
                m.getPushUp(),
                m.getSquat(),
                m.getRowBandDumbbell(),
                m.getOverheadPressDumbbell(),
                m.getHipHingeRdl(),
                m.getFrontView(),
                m.getSideView(),
                m.getBackView(),
                m.getStatus(),
                m.getNotes(),
                bodyMetrics,
                m.getCreatedAt(),
                m.getUpdatedAt()
        );
    }

    private Optional<Member> findExistingMember(String coachEmail, String normalizedEmail) {
        if (normalizedEmail == null) {
            return Optional.empty();
        }
        return repository.findByCoachEmailAndEmailIgnoreCase(coachEmail, normalizedEmail);
    }

    private String normalize(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }

    private void applyCreateRequest(Member member, CreateMemberRequest req) {
        member.setFullName(req.fullName() == null ? null : req.fullName().trim());
        member.setEmail(normalize(req.email()));
        member.setPhone(req.phone());
        member.setGender(req.gender());
        member.setDateOfBirth(req.dateOfBirth());
        member.setAge(req.age());
        member.setCountry(req.country());
        member.setStateCityProvince(req.stateCityProvince());
        member.setHeightCm(req.heightCm());
        member.setCurrentWeightKg(req.currentWeightKg());
        member.setMainTrainingGoal(req.mainTrainingGoal());
        member.setGoal(req.goal());
        member.setPreviousWeightLoss(req.previousWeightLoss());
        member.setWeightRegain(req.weightRegain());
        member.setPriorTrainingExperience(req.priorTrainingExperience());
        member.setDailyTrainingCommitmentHours(req.dailyTrainingCommitmentHours());
        member.setPreferredWorkoutTiming(req.preferredWorkoutTiming());
        member.setDaysPerWeekTrain(req.daysPerWeekTrain());
        member.setPersonalTrainingBefore(req.personalTrainingBefore());
        member.setGoalReward(req.goalReward());
        member.setAdditionalInfo(req.additionalInfo());
        member.setAlcoholConsumption(req.alcoholConsumption());
        member.setSmokingHabits(req.smokingHabits());
        member.setSupplementsPast(req.supplementsPast());
        member.setSteroidUsage(req.steroidUsage());
        member.setStressLevel(req.stressLevel());
        member.setSleepHours(req.sleepHours());
        member.setPastSportsActivity(req.pastSportsActivity());
        member.setFoodPreference(req.foodPreference());
        member.setActivityLevel(req.activityLevel());
        member.setTypicalDay(req.typicalDay());
        member.setCurrentDietPlan(req.currentDietPlan());
        member.setFavoriteFoods(req.favoriteFoods());
        member.setFoodAllergies(req.foodAllergies());
        member.setMedicalCondition(req.medicalCondition());
        member.setInjuries(req.injuries());
        member.setMedicalConditionsDetailed(req.medicalConditionsDetailed());
        member.setPushUp(req.pushUp());
        member.setSquat(req.squat());
        member.setRowBandDumbbell(req.rowBandDumbbell());
        member.setOverheadPressDumbbell(req.overheadPressDumbbell());
        member.setHipHingeRdl(req.hipHingeRdl());
        member.setFrontView(req.frontView());
        member.setSideView(req.sideView());
        member.setBackView(req.backView());
        if (req.status() != null) {
            member.setStatus(req.status());
        } else if (member.getStatus() == null) {
            member.setStatus(MemberStatus.ACTIVE);
        }
        member.setNotes(req.notes());
    }
}
