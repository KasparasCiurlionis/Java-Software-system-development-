package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.RoomMapper;
import lt.vu.mybatis.model.Room;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class RoomsMyBatis {
    @Inject
    private RoomMapper roomMapper;

    @Getter
    private List<Room> allRooms;

    @Getter @Setter
    private Room roomToCreate = new Room();

    @PostConstruct
    public void init() {
        this.loadAllRooms();
    }

    private void loadAllRooms() {
        this.allRooms = roomMapper.selectAll();
    }

    @Transactional
    public String createRoom() {
        roomMapper.insert(roomToCreate);
        return "/myBatis/rooms?faces-redirect=true";
    }
}
