package lt.vu.usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Member;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.MembersDAO;
import lt.vu.persistence.TeamsDAO;
import lt.vu.entities.Team;

@Model
public class MembersForTeam implements Serializable {

    @Inject
    private TeamsDAO teamsDAO;

    @Inject
    private MembersDAO membersDAO;

    @Getter @Setter
    private Team team;

    @Getter @Setter
    private Member memberToCreate = new Member();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer teamId = Integer.parseInt(requestParameters.get("teamId"));
        this.team = teamsDAO.findOne(teamId);
    }

    @Transactional
    public void createMember() {
        memberToCreate.setTeam(this.team);
        membersDAO.persist(memberToCreate);
    }



}
