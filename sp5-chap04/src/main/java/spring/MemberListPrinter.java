package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collection;

public class MemberListPrinter {
    private MemberDao memberDao;
    private MemberPrinter printer;

    public MemberListPrinter(){
    }
    public MemberListPrinter(MemberDao memberDao, MemberPrinter printer){
        this.memberDao = memberDao;
        this.printer = printer;
    }
    public void printAll(){
        Collection<Member> members = memberDao.selectAll();
        members.forEach(m->printer.print(m));
    }
    @Autowired
    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }
    @Autowired
    @Qualifier("summaryPrinter") // 이렇게 또는
    public void setPrinter(MemberPrinter printer){
        this.printer = printer;
    }

//    @Autowired // 이렇게
//    public void setPrinter(MemberSummaryPrinter printer){
//        this.printer = printer;
//    }
}
