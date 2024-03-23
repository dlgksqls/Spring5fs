package spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class MemberRegisterService {
    @Autowired
    private MemberDao memberDao;

    public MemberRegisterService(){
    }

    public MemberRegisterService(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    public Long regist(RegisterRequest req){
        Member member = memberDao.selectByEamil(req.getEmail());
        if(member != null) {
            throw new DuplicateMemberException("dup email" + req.getEmail());
        }
        Member newMember = new Member(
                req.getEmail(), req.getPassword(), req.getName(),
                LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
