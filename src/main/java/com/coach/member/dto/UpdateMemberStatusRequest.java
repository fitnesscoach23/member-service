package com.coach.member.dto;

import com.coach.member.entity.MemberStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberStatusRequest(
        @NotNull MemberStatus status
) {}
