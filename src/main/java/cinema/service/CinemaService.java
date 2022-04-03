package cinema.service;

import cinema.dtos.BookedSeatDTO;
import cinema.dtos.CinemaRoomDTO;
import cinema.dtos.ReturnedTicketDTO;
import cinema.dtos.SeatDTO;
import cinema.repository.CinemaRepositoryImpl;
import cinema.model.CinemaRoom;
import cinema.model.CinemaStatistics;
import cinema.exception.SeatOutOfBoundsException;
import cinema.model.Seat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private final CinemaRepositoryImpl cinemaRepository;

    public CinemaService(CinemaRepositoryImpl cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }


    public CinemaRoomDTO getCinemaRoom() {
        CinemaRoom cinemaRoom = cinemaRepository.findAllSeats();
        int totalColumns = cinemaRoom.getTotalColumns();
        int totalRows = cinemaRoom.getTotalRows();
        List<SeatDTO> seatDTOS = cinemaRoom.getAvailableSeats().stream().map(
                        seat -> new SeatDTO(
                                seat.getRow(),
                                seat.getColumn(),
                                seat.getPrice()))
                .collect(Collectors.toList());
        return new CinemaRoomDTO(totalRows, totalColumns, seatDTOS);
    }

    public Optional<BookedSeatDTO> bookSeat(Seat seat) {
//        if (seat.getRow() < 1 || seat.getColumn() < 1 || seat.getColumn() > 9 || seat.getRow() > 9) {
//            throw new SeatOutOfBoundsException();
//        }
        Optional<Seat> optionalSeat = cinemaRepository.findSeat(seat);
        if (optionalSeat.isPresent()) {
            Seat tempSeat = optionalSeat.get();
            if (tempSeat.getToken() == null) {
                tempSeat.setToken(UUID.randomUUID().toString());
                SeatDTO seatDTO = new SeatDTO(tempSeat.getRow(), tempSeat.getColumn(), tempSeat.getPrice());
                BookedSeatDTO bookedSeatDTO = new BookedSeatDTO(tempSeat.getToken(), seatDTO);
                return Optional.of(bookedSeatDTO);
            } else {
                return Optional.empty();
            }
        }

        throw new SeatOutOfBoundsException();
    }

    public Optional<ReturnedTicketDTO> returnTicket(String token) {
        Optional<Seat> bookedSeatByToken = cinemaRepository.findSeatByToken(token);
        if (bookedSeatByToken.isPresent()) {
            bookedSeatByToken.get().setToken(null);
            Seat seat = bookedSeatByToken.get();
            SeatDTO seatDTO = new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice());
            return Optional.of(new ReturnedTicketDTO(seatDTO));
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
