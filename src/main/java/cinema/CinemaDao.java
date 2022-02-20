package cinema;

import java.util.List;
import java.util.Map;

public interface CinemaDao {
    public List<ReservedSeat> getAllReservedSeats();
    public CinemaRoom getAllSeats();
    public Seat getSeat();
    public void bookSeat(Seat seat);
    public void returnTicket(Map<String, String> tokenBody);
}
