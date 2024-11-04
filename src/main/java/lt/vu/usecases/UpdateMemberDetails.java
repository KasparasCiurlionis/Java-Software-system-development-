package lt.vu.usecases;


import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Member;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.MembersDAO;
import lt.vu.services.AsyncCalculation;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ViewScoped
@Named
@Getter @Setter
public class UpdateMemberDetails implements Serializable {

    private Member member;

    @Inject
    private MembersDAO membersDAO;





    @PostConstruct
    private void init() {
        System.out.println("UpdateMemberDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer memberId = Integer.parseInt(requestParameters.get("memberId"));
        this.member = membersDAO.findOne(memberId);
    }

    @Transactional
    @LoggedInvocation
    public String updatePlayerRank() {
        try{
            membersDAO.update(this.member);
        } catch (OptimisticLockException e) {
            return "/memberDetails.xhtml?faces-redirect=true&memberId=" + this.member.getId() + "&error=optimistic-lock-exception";
        }
        return "members.xhtml?teamId=" + this.member.getTeam().getId() + "&faces-redirect=true";
    }


}
