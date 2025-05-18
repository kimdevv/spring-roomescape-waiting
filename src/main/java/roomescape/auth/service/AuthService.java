package roomescape.auth.service;

import org.springframework.stereotype.Service;
import roomescape.member.model.Member;
import roomescape.auth.dto.request.LoginRequest;
import roomescape.auth.infrastructure.JwtTokenProvider;
import roomescape.member.service.MemberService;

@Service
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginRequest loginRequest) {
        Member member = memberService.findByEmailAndPassword(loginRequest.email(), loginRequest.password());
        return jwtTokenProvider.createToken(member);
    }
}
