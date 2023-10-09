package com.example.reservation.controller;

import com.example.reservation.constants.ReservationStatus;
import com.example.reservation.dto.reservation.*;
import com.example.reservation.entity.MemberEntity;
import com.example.reservation.entity.PartnerEntity;
import com.example.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/detail/{reservationId}")
    public ResponseEntity<?> reservationDetail(@PathVariable Long reservationId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        ReservationDto reservationDto =  reservationService.reservationDetail(reservationId, userDetails.getUsername());
        return ResponseEntity.ok(reservationDto);
    }

    @PreAuthorize("hasRole('PARTNER')")
    @GetMapping("/partner/reservation/list")
    public ResponseEntity<?> reservationListForPartner(@RequestParam(required = false) String status,
                                                       @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                       @RequestParam(value = "p", defaultValue = "1") Integer page,
                                                       @AuthenticationPrincipal PartnerEntity partnerEntity) {
        Page<ReservationDto> reservationList;

        if(Objects.isNull(status) && Objects.isNull(date)){
            reservationList = reservationService.listForPartner(partnerEntity.getPartnerId(), page - 1);
        }else if(Objects.nonNull(status) && Objects.isNull(date)){
            reservationList = reservationService.listForPartnerByStatus(
                    partnerEntity.getPartnerId(), ReservationStatus.valueOf(status), page - 1);
        }else if(Objects.nonNull(date) && Objects.isNull(status)){
            reservationList = reservationService.listForPartnerByDate(
                    partnerEntity.getPartnerId(), date, page - 1);
        }else{
            reservationList = reservationService.listForPartnerByStatusAndDate(
                    partnerEntity.getPartnerId(), ReservationStatus.valueOf(status), date, page - 1);
        }

        return ResponseEntity.ok(reservationList);
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/partner/reservation/{reservationId}")

    public ResponseEntity<?> chaneReservationStatus(@PathVariable("reservationId") Long reservationId,
                                                    @RequestBody ReservationStatusChangeDto changeDto,
                                                    @AuthenticationPrincipal PartnerEntity partner) {
        reservationService.changeReservationStatus(
                partner.getPartnerId(), reservationId,
                ReservationStatus.valueOf(changeDto.getStatus())
        );
        return ResponseEntity.ok(reservationService.reservationDetail(reservationId, partner.getPartnerId()));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/reservation/request")
    public ResponseEntity<?> reservation(@RequestBody MakeReservationDto.Request request,
                                         @AuthenticationPrincipal MemberEntity memberEntity){
        request.setMemberId(memberEntity.getMemberId());
        ReservationDto reservationDto = reservationService.makeReservation(request);

        return ResponseEntity.ok(MakeReservationDto.Response.fromDto(reservationDto));
    }


    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/reservation/list")
    public ResponseEntity<?> reservationListForUser(@RequestParam(value = "p", defaultValue = "1") Integer page,
                                                    @AuthenticationPrincipal MemberEntity memberEntity){
        Page<ReservationDto> reservationList =
                reservationService.listForMember(memberEntity.getMemberId(), page - 1);

        return ResponseEntity.ok(reservationList);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/reservation/list/{status}")
    public ResponseEntity<?> reservationListForUserByStatus(@PathVariable ReservationStatus status,
                                                            @RequestParam(value = "p", defaultValue = "1") Integer page,
                                                            @AuthenticationPrincipal MemberEntity memberEntity){
        Page<ReservationDto> reservationList =
                reservationService.listForMemberByStatus(memberEntity.getMemberId(), page - 1, status);

        return ResponseEntity.ok(reservationList);
    }


    @PostMapping("/reservation/arrived")
    public ResponseEntity<?> arrivedHandling(@RequestBody MemberArrivedCheckDto memberArrivedCheckDto){
        ReservationDto reservationDto =
                reservationService.arrivedCheck(
                        memberArrivedCheckDto.getReservationId(),
                        memberArrivedCheckDto.getLastPhoneNumber()
                );

        return ResponseEntity.ok(new MemberArrivedCompletedDto(reservationDto));
    }
}
