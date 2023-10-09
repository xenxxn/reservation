package com.example.reservation.service;


import com.example.reservation.dto.Auth;
import com.example.reservation.dto.member.MemberDto;
import com.example.reservation.entity.MemberEntity;
import com.example.reservation.exception.impl.AlreadyExistMemberException;
import com.example.reservation.exception.impl.PasswordNotMatchException;
import com.example.reservation.exception.impl.NotExistIdException;
import com.example.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return this.memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + memberId));
    }

    public MemberDto register(Auth.MemberSignUp member) {
        boolean exists = this.memberRepository.existsByMemberId(member.getMemberId());
        if (exists) {
            throw new AlreadyExistMemberException();
        }
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        MemberEntity memberEntity = this.memberRepository.save(member.toEntity());
        return MemberDto.fromEntity(memberEntity);
    }

    public MemberEntity authenticate(Auth.MemberSignIn member) {
        var user =  this.memberRepository.findByMemberId(member.getMemberId())
                .orElseThrow(NotExistIdException::new);
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new PasswordNotMatchException();
        }
        return user;
    }
}
