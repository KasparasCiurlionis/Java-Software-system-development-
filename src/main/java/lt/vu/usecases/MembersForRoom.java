package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Member;
import lt.vu.entities.Room;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.MembersDAO;
import lt.vu.persistence.RoomsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Model
public class MembersForRoom implements Serializable {

    @Inject
    private RoomsDAO roomsDAO;

    @Inject
    private MembersDAO membersDAO;

    @Getter @Setter
    private Room room;

    @Getter
    private List<Member> allMembers;

    @Getter @Setter
    private Member member;

    @Getter @Setter
    private int selectedID;

    @PostConstruct
    public void init() {
        loadAllMembers();
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer roomId = Integer.parseInt(requestParameters.get("roomId"));
        this.room= roomsDAO.findOne(roomId);
        List<Member> memberList=this.room.getMembers();
        for(int i=0;i<memberList.size();i++){
            this.allMembers.remove(memberList.get(i));
        }
    }

    @Transactional
    @LoggedInvocation
    public void addMember() {
        member =membersDAO.findOne(selectedID);
        member.getRooms().add(this.room);
        this.room.getMembers().add(member);
        membersDAO.merge(member);
        roomsDAO.merge(this.room);
        System.out.println(member.getName()+"\n");
        this.allMembers.remove(member);

    }

    private void loadAllMembers(){
        this.allMembers = membersDAO.loadAll();
    }
}
