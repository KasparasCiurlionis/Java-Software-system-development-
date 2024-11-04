package lt.vu.rest.contracts;

import lt.vu.entities.Member;
import lt.vu.entities.Team;
import lt.vu.persistence.MembersDAO;
import lt.vu.persistence.TeamsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/teams")
public class TeamController {
    @Inject
    private TeamsDAO teamsDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Team> getAllUsers() {
        List<Team> memberino=teamsDAO.loadAll();
        for(int i=0;i<memberino.size();i++){
            Team tempmember=memberino.get(i);
            List<Member> team=new ArrayList<>();
            tempmember.setMembers(team);
            memberino.set(i, tempmember);
        }
        return teamsDAO.loadAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        Team member = teamsDAO.findOne(id);
        List<Member> team=new ArrayList<>();
        member.setMembers(team);
        if (member == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(member).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(Team member) {
        Team team=new Team();
        team.setName(member.getName());
        teamsDAO.persist(team);
        return Response.status(Response.Status.CREATED).entity(team).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Integer id, Team memberDetails) {
        Team member =  teamsDAO.findOne(id);
        if (member == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        member.setName(memberDetails.getName());
        teamsDAO.update(member);
        return Response.ok(member).build();
    }
}
