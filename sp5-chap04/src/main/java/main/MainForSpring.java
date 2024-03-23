package main;

import assembler.Assembler;
import config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForSpring {
    private static ApplicationContext ctx = null;
    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class); // 스프링 컨테이너 생성 이후 의존 객체를 주입
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("명령어를 입력하세요.");
            String command = reader.readLine();
            if(command.equalsIgnoreCase("exit")){
                System.out.println("종료합니다.");
                break;
            }
            if(command.startsWith("new ")){
                processNewCommand(command.split(" "));
                continue;
            }
            else if(command.startsWith("change ")){
                processChangeCommand(command.split(" "));
                continue;
            }
            else if(command.equals("list")){
                processListCommand();
                continue;
            }
            else if(command.startsWith("info ")){
                proceinfoCommand(command.split(" "));
                continue;
            }
            else if(command.equals("version")){
                processVersionCommand();
                continue;
            }
            printHelp();
        }
    }
    private static void printHelp(){
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법:");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
    private static Assembler assembler = new Assembler();

    private static void processNewCommand(String[] arg){
        if (arg.length != 5){
            printHelp();
            return;
        }
        //MemberRegisterService regSvc = assembler.getMemberRegisterService();
        MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        if (!req.isPasswordEqualToConfirmPaaword()){
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try{
            regSvc.regist(req);
            System.out.println("등록했습니다.\n");
        }catch (DuplicateMemberException e){
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }
    private static void processChangeCommand(String[] arg){
        if (arg.length != 4){
            printHelp();
            return;
        }
        //ChangePasswordService changePwdSvc = assembler.getChangePasswordService();
        ChangePasswordService changePwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);

        try{
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("암호를 변경했습니다.\n");
        }catch (MemberNotFoundException e){
            System.out.println("존재하지 않는 이메일 입니다.\n");
        }catch (WrongIdPaawordException e){
            System.out.println("이메일과 암호가 일치하지 않습니다.\n");
        }
    }
    private static void processListCommand(){
        MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }
    private static void proceinfoCommand(String[] arg){
        MemberinfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberinfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }
    private static void processVersionCommand(){
        VersionPrinter versionPrinter = ctx.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.print();
    }
}
