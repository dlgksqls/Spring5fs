package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ChangePasswordService;
import spring.Member;
import spring.MemberDao;
import spring.MemberRegisterService;

@Configuration // 스프링 설정 클래스
public class AppCtx {
    @Bean // 스프링 빈 클래스 memberDao로 생성된 객체는 memberDao라는 이름으로 스프링에 등록
    public MemberDao memberDao(){
        return new MemberDao();
    }

    @Bean // memberDao가 생성한 객체를 MemberRegiserService 생성자를 통해 주입
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService(memberDao());
    }

    @Bean // 세터를 통해 의존 객체 주입
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao());
        return pwdSvc;
    }
}
