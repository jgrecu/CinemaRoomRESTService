package cinema;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CinemaRepositoryImpl implements CinemaRepository {

    private List<ReservedSeat> reservedSeats = new ArrayList<>();
    private CinemaRoom cinemaRoom = new CinemaRoom(9, 9);


    @Override
    public List<ReservedSeat> findAllReservedSeats() {
        return new ArrayList<>(reservedSeats);
    }

    @Override
    public CinemaRoom findAllSeats() {
        return cinemaRoom;
    }

    @Override
    public Optional<Seat> findSeatBy(Seat seat) {
        return cinemaRoom.getAvailableSeats().stream().filter(seat::equals).findAny();
    }

    @Override
    public void bookSeat(ReservedSeat reservedSeat) {
        reservedSeats.add(reservedSeat);
    }

    @Override
    public void unBookSeat(ReservedSeat reservedSeat) {
        reservedSeats.removeIf(resSeat -> resSeat.equals(reservedSeat));
    }

    @Override
    public Optional<ReservedSeat> findBookedSeatByToken(String token) {
        return reservedSeats.stream()
                .filter(reservedSeat -> reservedSeat.getToken().equals(token))
                .findFirst();
    }
}
