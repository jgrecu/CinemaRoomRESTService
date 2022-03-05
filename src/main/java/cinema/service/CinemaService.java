package cinema.service;

import cinema.repository.CinemaRepositoryImpl;
import cinema.model.CinemaRoom;
import cinema.model.CinemaStatistics;
import cinema.exception.SeatOutOfBoundsException;
import cinema.model.ReservedSeat;
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

    public Optional<ReservedSeat> bookSeat(Seat seat) {
        if (seat.getRow() < 1 || seat.getColumn() < 1 || seat.getColumn() > 9 || seat.getRow() > 9) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of(ERROR, "The number of a row or a column is out of bounds!"));
            throw new SeatOutOfBoundsException();
        }
        Optional<Seat> optionalSeat = cinemaRepository.findSeatBy(seat);
        if (optionalSeat.isPresent()) {
            Optional<ReservedSeat> reservedSeatOptional = cinemaRepository.findAllReservedSeats().stream()
                    .filter(reservedSeat -> reservedSeat.getTicket().equals(seat))
                    .findAny();
            if (reservedSeatOptional.isPresent()) {
                return Optional.empty();
            } else {
                ReservedSeat reservedSeat = new ReservedSeat(UUID.randomUUID().toString(), optionalSeat.get());
                cinemaRepository.bookSeat(reservedSeat);
                return Optional.of(reservedSeat);
            }
        }
        return Optional.empty();
    }

    public Optional<ReservedSeat> returnTicket(String token) {
        Optional<ReservedSeat> bookedSeatByToken = cinemaRepository.findBookedSeatByToken(token);
        bookedSeatByToken.ifPresent(cinemaRepository::unBookSeat);
        return bookedSeatByToken;
    }

    public CinemaStatistics getStats() {
        int availableSeats = 81 - cinemaRepository.findAllReservedSeats().size();
        int income = cinemaRepository.findAllReservedSeats().stream()
                .mapToInt(resSeat -> resSeat.getTicket().getPrice())
                .sum();
        int numReservedSeats = cinemaRepository.findAllReservedSeats().size();
        return new CinemaStatistics(availableSeats,income, numReservedSeats);
    }
}
