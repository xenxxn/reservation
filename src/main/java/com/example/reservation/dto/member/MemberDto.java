package com.example.reservation.dto.member;

import com.example.reservation.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private String memberId;
    private String password;
    private String memberName;
    private String phone;
    private String memberType;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public static MemberDto fromEntity(MemberEntity memberEntity) {
        return MemberDto.builder()
                .memberId(memberEntity.getMemberId())
                .password(memberEntity.getPassword())
                .phone(memberEntity.getPhone())
                .memberType(memberEntity.getMemberType())
                .registerDate(memberEntity.getRegisterDate())
                .updateDate(memberEntity.getUpdateDate())
                .build();
    }
}
