package cinema.repository;

import cinema.model.CinemaRoom;
import cinema.model.Seat;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository {
    List<Seat> findAllReservedSeats();

    CinemaRoom findAllSeats();

    Optional<Seat> findSeat(Seat seat);

    Optional<Seat> findSeatByToken(String token);
}
