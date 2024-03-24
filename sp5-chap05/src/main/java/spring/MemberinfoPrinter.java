package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("infoPrinter")
public class MemberinfoPrinter {
    //@Autowired
    private MemberDao memberDao;
    //@Autowired
    private MemberPrinter printer;

    public void printMemberInfo(String email){
        Member member = memberDao.selectByEamil(email);
        if(member == null){
            System.out.println("데이터 없음\n");
            return;
        }
        printer.print(member);
        System.out.println();
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }
    @Autowired
    @Qualifier("printer")
    public void setMemberPrinter(MemberPrinter memberPrinter){ this.printer = memberPrinter;}
}
