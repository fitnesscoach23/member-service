package com.coach.member.repository;

import com.coach.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    List<Member> findByCoachEmail(String coachEmail);
    Optional<Member> findByCoachEmailAndEmailIgnoreCase(String coachEmail, String email);
}
