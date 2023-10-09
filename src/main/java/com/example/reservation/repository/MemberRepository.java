package com.example.reservation.repository;

import com.example.reservation.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    boolean existsByMemberId(String memberId);
    Optional<MemberEntity> findByMemberId(String memberId);
}
