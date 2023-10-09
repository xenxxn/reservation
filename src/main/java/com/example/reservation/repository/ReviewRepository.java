package com.example.reservation.repository;

import com.example.reservation.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByReservationId(Long reservationId);
    Page<ReviewEntity> findByMemberIdOrderByCreateDateDesc(String userId, Pageable pageable);
    Page<ReviewEntity> findByStoreNameOrderByCreateDateDesc(String storeName, Pageable pageable);
    Page<ReviewEntity> findByStoreNameOrderByRatingDesc(String storeName, Pageable pageable);
    Page<ReviewEntity> findByStoreNameOrderByRatingAsc(String storeName, Pageable pageable);
}
