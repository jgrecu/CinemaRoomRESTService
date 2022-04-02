package cinema.service;

import cinema.repository.CinemaRepositoryImpl;
import cinema.model.CinemaRoom;
import cinema.model.CinemaStatistics;
import cinema.exception.SeatOutOfBoundsException;
import cinema.model.Seat;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaService {

    private final CinemaRepositoryImpl cinemaRepository;

    public CinemaService(CinemaRepositoryImpl cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }


    public CinemaRoom getCinemaRoom() {
        return cinemaRepository.findAllSeats();
    }

    public Optional<Seat> bookSeat(Seat seat) {
//        if (seat.getRow() < 1 || seat.getColumn() < 1 || seat.getColumn() > 9 || seat.getRow() > 9) {
//            throw new SeatOutOfBoundsException();
//        }
        Optional<Seat> optionalSeat = cinemaRepository.findSeat(seat);
        if (optionalSeat.isPresent()) {
            Seat tempSeat = optionalSeat.get();
            if (tempSeat.getToken() == null) {
                tempSeat.setToken(UUID.randomUUID().toString());
                return Optional.of(tempSeat);
            } else {
                return Optional.empty();
            }
        } else {
            throw new SeatOutOfBoundsException();
        }
    }

    public Optional<Seat> returnTicket(String token) {
        Optional<Seat> bookedSeatByToken = cinemaRepository.findSeatByToken(token);
        if (bookedSeatByToken.isPresent()) {
            bookedSeatByToken.get().setToken(null);
            return bookedSeatByToken;
        }
        return Optional.empty();
    }

    public CinemaStatistics getStats() {
        int availableSeats = 81 - cinemaRepository.findAllReservedSeats().size();
        int income = cinemaRepository.findAllReservedSeats().stream()
                .mapToInt(Seat::getPrice)
                .sum();
        int numReservedSeats = cinemaRepository.findAllReservedSeats().size();
        return new CinemaStatistics(availableSeats, income, numReservedSeats);
    }
}
