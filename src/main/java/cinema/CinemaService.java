package cinema;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
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
