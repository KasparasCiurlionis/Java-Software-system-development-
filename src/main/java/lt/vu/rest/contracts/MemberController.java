package lt.vu.rest.contracts;

import lt.vu.entities.Member;
import lt.vu.entities.Team;
import lt.vu.persistence.MembersDAO;
import lt.vu.persistence.TeamsDAO;
import org.apache.ibatis.jdbc.Null;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/members")
public class MemberController {
    @Inject
    private MembersDAO membersDAO;

    @Inject
    private TeamsDAO teamsDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Member> getAllUsers() {
        List<Member> memberino=membersDAO.loadAll();
        for(int i=0;i<memberino.size();i++){
            Member tempmember=memberino.get(i);
            Team team=new Team();
            tempmember.setTeam(team);
            memberino.set(i, tempmember);
        }
        return membersDAO.loadAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        Member member = membersDAO.findOne(id);
        Team team=new Team();
        member.setTeam(team);
        if (member == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(member).build();
    }

    @POST
    public Response createUser(Member member, Integer id) {
        member.setTeam(teamsDAO.loadAll().get(1));
        membersDAO.persist(member);
        return Response.status(Response.Status.CREATED).entity(member).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Integer id, Member memberDetails) {
        Member member =  membersDAO.findOne(id);
        if (member == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        member.setName(memberDetails.getName());
        member.setRank(memberDetails.getRank());
        membersDAO.update(member);
        return Response.ok(member).build();
    }
}
