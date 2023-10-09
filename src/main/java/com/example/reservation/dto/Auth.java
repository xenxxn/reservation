package com.example.reservation.dto;

import com.example.reservation.constants.Authority;
import com.example.reservation.entity.MemberEntity;
import com.example.reservation.entity.PartnerEntity;
import lombok.Data;

import java.time.LocalDateTime;

public class Auth {
    @Data
    public static class MemberSignIn {
        private String memberId;
        private String password;
    }

    @Data
    public static class MemberSignUp {
        private String memberId;
        private String password;
        private String memberName;
        private String phone;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .memberId(this.memberId)
                    .password(this.password)
                    .memberName(this.memberName)
                    .phone(this.phone)
                    .memberType(Authority.MEMBER.name())
                    .registerDate(LocalDateTime.now())
                    .build();
        }
    }

    @Data
    public static class PartnerSignIn {
        private String partnerId;
        private String password;
    }

    @Data
    public static class PartnerSignUp {
        private String partnerId;
        private String password;
        private String partnerName;
        private String phone;

        public PartnerEntity toEntity() {
            return PartnerEntity.builder()
                    .partnerId(this.partnerId)
                    .password(this.password)
                    .partnerName(this.partnerName)
                    .phone(this.phone)
                    .memberType(Authority.PARTNER.name())
                    .registerDate(LocalDateTime.now())
                    .build();
        }
    }
}
