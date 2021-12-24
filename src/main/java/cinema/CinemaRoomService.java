package cinema;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaRoomService {
    private final CinemaRoom cinemaRoom = new CinemaRoom(9, 9);
    private final List<ReservedSeat> reservedSeats = new ArrayList<>();

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public ReservedSeat bookSeat(Seat seat) {
        if (isSeatValid(seat)) {
            Optional<Seat> optionalSeat = cinemaRoom.getAvailableSeats()
                    .stream()
                    .filter(s -> s.equals(seat))
                    .findFirst();
            Seat wantedSeat = optionalSeat.orElseGet(null);
            if (optionalSeat.isPresent() && reservedSeats.stream().noneMatch(o -> o.getTicket().equals(wantedSeat))) {
                ReservedSeat reservedSeat = new ReservedSeat(UUID.randomUUID().toString(), wantedSeat);
                reservedSeats.add(reservedSeat);
                return reservedSeat;
            }
            return null;
        }
        throw new IllegalArgumentException("The number of a row or a column is out of bounds!");
    }

    public ReservedSeat returnTicket(String token) {
        ReservedSeat ticket = reservedSeats.stream().filter(seat -> token.equals(seat.getToken()))
                .findAny().orElse(null);
        if (ticket != null) {
            reservedSeats.remove(ticket);
            return ticket;
        }
        return ticket;
    }

    public Map<String, Integer> getStats() {
        int availableSeats = 81 - reservedSeats.size();
        int income = 0;
//        for (ReservedSeat reservedSeat : reservedSeats) {
//            income += reservedSeat.getTicket().getPrice();
//        }
        income = reservedSeats.stream().map(ReservedSeat::getTicket).mapToInt(Seat::getPrice).sum();
        return Map.of("current_income", income, "number_of_available_seats", availableSeats,
                "number_of_purchased_tickets", reservedSeats.size());
    }
    private boolean isSeatValid(Seat seat) {
        return seat.getRow() <= 9 && seat.getColumn() <= 9 && seat.getRow() >= 1 && seat.getColumn() >= 1;
    }
}
