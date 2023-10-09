package com.example.reservation.service;
import com.example.reservation.constants.ReservationStatus;
import com.example.reservation.dto.reservation.MakeReservationDto;
import com.example.reservation.dto.reservation.ReservationDto;
import com.example.reservation.entity.MemberEntity;
import com.example.reservation.entity.ReservationEntity;
import com.example.reservation.entity.StoreEntity;
import com.example.reservation.exception.impl.*;
import com.example.reservation.repository.MemberRepository;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReservationDto makeReservation(MakeReservationDto.Request request) {
        ReservationEntity reservationEntity = makeReservationEntity(request);
        reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    private ReservationEntity makeReservationEntity(MakeReservationDto.Request request) {
        MemberEntity memberEntity = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(NotExistIdException::new);
        StoreEntity storeEntity = storeRepository.findByStoreName(request.getStoreName())
                .orElseThrow(NoStoreException::new);
        LocalDateTime reserveTime = LocalDateTime.of(request.getDate(), request.getTime());

        return ReservationEntity.builder()
                .memberId(memberEntity.getMemberId())
                .phone(memberEntity.getPhone())
                .partnerId(storeEntity.getPartnerId())
                .storeName(storeEntity.getStoreName())
                .headCount(request.getHeadCount())
                .status(ReservationStatus.REQUEST)
                .statusUpdatedAt(LocalDateTime.now())
                .time(reserveTime)
                .build();
    }

    public ReservationDto reservationDetail(Long reservationId, String memberName) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(NoReservationException::new);
        if (!this.validateReservationAccessAuthority(memberName, reservationEntity)) {
            throw new FailAccessAuthorityReservationException();

        }
        return ReservationDto.fromEntity(reservationEntity);
    }

    private boolean validateReservationAccessAuthority(String memberName,
                                                       ReservationEntity reservationEntity) {
        if (reservationEntity.getMemberId().equals(memberName)) {
            return true;
        } else if (reservationEntity.getPartnerId().equals(memberName)) {
            return true;
        } return false;
    }

    public void changeReservationStatus(String partnerId, Long reservationId,
                                        ReservationStatus status) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(NoReservationException::new);

        if (!reservationEntity.getPartnerId().equals(partnerId)) {
            throw new NotExistIdException();

        }
        reservationEntity.setStatus(status);
        reservationEntity.setStatusUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservationEntity);
    }

    public ReservationDto arrivedCheck(Long reservationId, String lastPhoneNumber) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(NoReservationException::new);

        validate(reservationEntity, lastPhoneNumber);
        reservationEntity.setStatus(ReservationStatus.ARRIVE);
        reservationEntity.setStatusUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    public void validate(ReservationEntity reservationEntity, String lastPhoneNumber) {
        String phone = reservationEntity.getPhone().substring(7);
        if (!phone.equals(lastPhoneNumber)) {
            throw new PhoneNumberNotMatchException();
        } else if (!reservationEntity.getStatus().equals(ReservationStatus.CONFIRM)) {
            throw new NotApprovedReservationException();
        } else if (LocalDateTime.now().isAfter(reservationEntity.getTime().minusMinutes(10L))) {
            throw new NotArrivedReservationException();
        }
    }

    public Page<ReservationDto> listForPartner(String partnerId, int page) {
        Page<ReservationEntity> reservationEntityPage =
                reservationRepository.findByPartnerIdOrderByTimeDesc(partnerId, PageRequest.of(page, 5));
        if (reservationEntityPage.getSize() == 0) {
            throw new NoReservationException();
        }
        return reservationEntityPage.map(ReservationDto::fromEntity);
    }

    public Page<ReservationDto> listForPartnerByStatus (String partnerId, ReservationStatus status, int page) {
        Page<ReservationEntity> reservationEntityPage =
                reservationRepository.findByPartnerIdAndStatusOrderByTime(
                        partnerId, status, PageRequest.of(page, 3)
                );
        if (reservationEntityPage.getSize() == 0) {
            throw new NoReservationException();
        }
        return reservationEntityPage.map(ReservationDto::fromEntity);
    }

    public Page<ReservationDto> listForPartnerByDate(String partnerId, LocalDate date, int page) {
        Page<ReservationEntity> reservationEntityPage =
                reservationRepository.findByPartnerIdAndTimeBetweenOrderByTime(
                        partnerId,
                        LocalDateTime.of(date, LocalTime.MIN),
                        LocalDateTime.of(date, LocalTime.MAX),
                        PageRequest.of(page, 3)
                );

        if (reservationEntityPage.getSize() == 0) {
            throw new NoReservationException();
        }
        return reservationEntityPage.map(ReservationDto::fromEntity);
    }

    public Page<ReservationDto> listForPartnerByStatusAndDate(String partnerId, ReservationStatus status, LocalDate date, int page) {
        Page<ReservationEntity> reservationEntityPage =
                reservationRepository.findByPartnerIdAndStatusAndTimeBetweenOrderByTime(
                        partnerId,
                        status,
                        LocalDateTime.of(date, LocalTime.MIN),
                        LocalDateTime.of(date, LocalTime.MAX),
                        PageRequest.of(page, 3)
                );
        if (reservationEntityPage.getSize() == 0) {
            throw new NoReservationException();

        }
        return reservationEntityPage.map(ReservationDto::fromEntity);
    }

    public Page<ReservationDto> listForMember(String memberId, int page) {
        Page<ReservationEntity> reservationEntityPage =
                reservationRepository.findByMemberIdOrderByTimeDesc(
                        memberId, PageRequest.of(page, 3)
                );
        if (reservationEntityPage.getSize() == 0) {
            throw new NoReservationException();
        }
        return reservationEntityPage.map(ReservationDto::fromEntity);
    }

    public Page<ReservationDto> listForMemberByStatus(String memberId, int page, ReservationStatus status) {
        Page<ReservationEntity> reservationEntityPage =
                reservationRepository.findByMemberIdAndStatusOrderByTime(
                        memberId, status, PageRequest.of(page, 3)
                );
        if (reservationEntityPage.getSize() == 0) {
            throw new NoReservationException();
        }
        return reservationEntityPage.map(ReservationDto::fromEntity);
    }

}
