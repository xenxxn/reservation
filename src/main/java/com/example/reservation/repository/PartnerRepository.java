package com.example.reservation.repository;

import com.example.reservation.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, String> {
    boolean existsByPartnerId(String partnerId);
    Optional<PartnerEntity> findByPartnerId(String partnerId);
}
