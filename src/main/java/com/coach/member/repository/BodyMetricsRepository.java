package com.coach.member.repository;

import com.coach.member.entity.BodyMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BodyMetricsRepository extends JpaRepository<BodyMetricsEntity, UUID> {
    Optional<BodyMetricsEntity> findByMemberId(UUID memberId);
}
