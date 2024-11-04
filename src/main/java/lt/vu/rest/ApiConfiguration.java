package lt.vu.rest;

import lt.vu.entities.Member;
import lt.vu.persistence.MembersDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;


@ApplicationPath("/api")
public class ApiConfiguration extends Application {
    @Inject
    private MembersDAO membersDAO;
}