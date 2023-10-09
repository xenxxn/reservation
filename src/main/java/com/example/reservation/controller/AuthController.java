package com.example.reservation.controller;
import com.example.reservation.dto.Auth;
import com.example.reservation.filter.TokenProvider;
import com.example.reservation.service.MemberService;
import com.example.reservation.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final PartnerService partnerService;

    @PostMapping("/member-register")
    public ResponseEntity<?> register(@RequestBody Auth.MemberSignUp request) {
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/member-signin")
    public ResponseEntity<?> signin(@RequestBody Auth.MemberSignIn request) {
        var member = this.memberService.authenticate(request);
        var token = this.tokenProvider.generateMemberToken(member.getMemberId());
        return ResponseEntity.ok(token);
    }



    @PostMapping("/partner-register")
    public ResponseEntity<?> register(@RequestBody Auth.PartnerSignUp request) {
        var result = this.partnerService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/partner-signin")
    public ResponseEntity<?> signin(@RequestBody Auth.PartnerSignIn request) {
        var partner = this.partnerService.authenticate(request);
        var token = this.tokenProvider.generatePartnerToken(partner.getPartnerId());
        return ResponseEntity.ok(token);
    }
}
