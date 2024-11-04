package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Room;
import lt.vu.entities.Team;
import lt.vu.persistence.RoomsDAO;
import lt.vu.persistence.TeamsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Rooms {

    @Inject
    private RoomsDAO roomsDAO;

    @Getter @Setter
    private Room roomToCreate = new Room();

    @Getter
    private List<Room> allRooms;

    @PostConstruct
    public void init(){
        loadAllRooms();
    }

    @Transactional
    public void createRoom(){
        this.roomsDAO.persist(roomToCreate);
    }

    private void loadAllRooms(){
        this.allRooms = roomsDAO.loadAll();
    }
}
