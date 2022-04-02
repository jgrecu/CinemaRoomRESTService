package cinema.repository;

import cinema.model.CinemaRoom;
import cinema.model.Seat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CinemaRepositoryImpl implements CinemaRepository {

    private final int totalRows = 9;
    private final int totalColumns = 9;
    private final List<Seat> cinemaRoom = createSeatsList(totalRows, totalColumns);


    @Override
    public List<Seat> findAllReservedSeats() {
        return cinemaRoom.stream()
                .filter(seat -> seat.getToken() != null)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaRoom findAllSeats() {
        return new CinemaRoom(totalRows, totalColumns, cinemaRoom);
    }

    @Override
    public Optional<Seat> findSeat(Seat seat) {
        return cinemaRoom.stream()
                .filter(seat::equals)
                .findAny();
    }

    @Override
    public Optional<Seat> findSeatByToken(String token) {
        return cinemaRoom.stream()
                .filter(seat -> seat.getToken().equals(token))
                .findAny();
    }

    private List<Seat> createSeatsList(int totalRows, int totalColumns) {
        final List<Seat> tempList = new ArrayList<>();
        // populate the list of seats
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                tempList.add(new Seat(i, j, i <= 4 ? 10 : 8));
            }
        }

        return tempList;
    }
}
