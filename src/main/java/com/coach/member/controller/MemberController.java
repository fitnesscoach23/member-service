package com.coach.member.controller;

import com.coach.member.dto.*;
import com.coach.member.service.MemberService;
import com.coach.member.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;
    private final CurrentUserUtil current;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateMemberRequest req) {
        UUID id = service.createMember(current.getCoachEmail(), req);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getMember(current.getCoachEmail(), id));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> list() {
        return ResponseEntity.ok(service.listMembers(current.getCoachEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateMemberRequest req) {
        service.updateMember(current.getCoachEmail(), id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.deleteMember(current.getCoachEmail(), id);
        return ResponseEntity.noContent().build();
    }
}
