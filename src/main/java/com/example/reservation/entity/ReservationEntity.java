package com.example.reservation.entity;

import com.example.reservation.constants.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    private String phone;

    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "head_count")
    private Integer headCount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;
}
