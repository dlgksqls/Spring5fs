package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.*;

@Configuration // 스프링 설정 클래스
public class AppCtx {
    @Bean // 스프링 빈 클래스 memberDao로 생성된 객체는 memberDao라는 이름으로 스프링에 등록
    public MemberDao memberDao(){
        return new MemberDao();
    }

    @Bean // memberDao가 생성한 객체를 MemberRegiserService 생성자를 통해 주입
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService();
    }

    @Bean // 세터를 통해 의존 객체 주입
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc = new ChangePasswordService();
        //pwdSvc.setMemberDao(memberDao()); 자동 주입으로 인해 필요 x
        return pwdSvc;
    }

//    @Bean
//    public MemberPrinter memberPrinter(){
//        return new MemberPrinter();
//    }
    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
}

    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2(){
        return new MemberSummaryPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter();
    }

    @Bean
    public MemberinfoPrinter infoPrinter(){
        MemberinfoPrinter infoPrinter = new MemberinfoPrinter();
        //infoPrinter.setMemberDao(memberDao()); 의존 자동 주입
        infoPrinter.setMemberPrinter(memberPrinter2());
        return new MemberinfoPrinter();
    }

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }

}
