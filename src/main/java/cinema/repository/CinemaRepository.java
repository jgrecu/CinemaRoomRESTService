package cinema.repository;

import cinema.model.CinemaRoom;
import cinema.model.ReservedSeat;
import cinema.model.Seat;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository {
    public List<ReservedSeat> findAllReservedSeats();
    public CinemaRoom findAllSeats();
    public Optional<Seat> findSeatBy(Seat seat);
    public void bookSeat(ReservedSeat reservedSeat);
    public void unBookSeat(ReservedSeat reservedSeat);
    public Optional<ReservedSeat> findBookedSeatByToken(String token);
}
