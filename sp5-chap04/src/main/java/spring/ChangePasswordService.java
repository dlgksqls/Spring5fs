package spring;

import org.springframework.beans.factory.annotation.Autowired;

public class ChangePasswordService {
    @Autowired // 의존 자동 주입
    private MemberDao memberDao;

    public void changePassword(String email, String oldPwd, String newPwd){
        Member member = memberDao.selectByEamil(email);
        if(member == null)
            throw new MemberNotFoundException();
        member.changePassword(oldPwd, newPwd);
        memberDao.update(member);
    }
    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }
}
