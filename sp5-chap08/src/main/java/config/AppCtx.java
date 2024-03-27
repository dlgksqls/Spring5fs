package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.tomcat.jdbc.pool.DataSource;
import spring.ChangePasswordService;
import spring.Member;
import spring.MemberDao;
import spring.MemberRegisterService;
import spring.*;

import javax.print.attribute.standard.PrinterInfo;
//import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration // 스프링 설정 클래스
public class AppCtx {
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/spring5fs?characterEncoding=utf8&useSSL=false");
        ds.setUsername("spring5");
        ds.setPassword("spring5");
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        return ds;
    }
    @Bean // 스프링 빈 클래스 memberDao로 생성된 객체는 memberDao라는 이름으로 스프링에 등록
    public MemberDao memberDao() {
        return new MemberDao(dataSource());
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

    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao(), memberPrinter());
    }

    @Bean
    public MemberinfoPrinter infoPrinter(){
        MemberinfoPrinter infoPrinter = new MemberinfoPrinter();
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
