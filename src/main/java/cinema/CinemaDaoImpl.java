package cinema;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CinemaDaoImpl implements CinemaDao {

    private List<ReservedSeat> reservedSeats = new ArrayList<>();
    private CinemaRoom cinemaRoom = new CinemaRoom(9, 9);

    @Override
    public List<ReservedSeat> getAllReservedSeats() {
        List<ReservedSeat> tempList = new ArrayList<>(reservedSeats);
        return tempList;
    }

    @Override
    public CinemaRoom getAllSeats() {
        return cinemaRoom;
    }

    @Override
    public Seat getSeat() {
        return null;
    }

    @Override
    public void bookSeat(Seat seat) {

    }

    @Override
    public void returnTicket(Map<String, String> tokenBody) {

    }
}
