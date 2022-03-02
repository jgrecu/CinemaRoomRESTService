package cinema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CinemaRepository {
    public List<ReservedSeat> findAllReservedSeats();
    public CinemaRoom findAllSeats();
    public Optional<Seat> findSeatBy(Seat seat);
    public void bookSeat(ReservedSeat reservedSeat);
    public void unBookSeat(ReservedSeat reservedSeat);
    public Optional<ReservedSeat> findBookedSeatByToken(String token);
}
