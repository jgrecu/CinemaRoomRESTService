package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {

    private CinemaRoom cinemaRoom = new CinemaRoom(9, 9);
    private List<ReservedSeat> reservedSeats = new ArrayList<>();

    @GetMapping("/seats")
    public CinemaRoom getSeats() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> bookSeats(@RequestBody Seat seat) {

        if (seat.getRow() > 9 || seat.getColumn() > 9 || seat.getRow() < 1 || seat.getColumn() < 1) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            for (Seat seats : cinemaRoom.getAvailableSeats()) {
                if (seat.equals(seats) && reservedSeats.stream().noneMatch(o -> o.getTicket().equals(seats))) {
                    ReservedSeat reservedSeat = new ReservedSeat(UUID.randomUUID().toString(), seats);
                    reservedSeats.add(reservedSeat);
                    return new ResponseEntity<>(reservedSeat, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> tokenBody) {
        String token = tokenBody.get("token");
        Seat ticket = reservedSeats.stream().filter(seat -> token.equals(seat.getToken())).map(ReservedSeat::getTicket)
                .findAny().orElse(null);

        if (ticket != null) {
            reservedSeats.removeIf(reservedSeat -> reservedSeat.getToken().equals(token));
            return new ResponseEntity<>(Map.of("returned_ticket", ticket), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {

        if ("super_secret".equals(password)) {
            int availableSeats = 81 - reservedSeats.size();
            int income = 0;
            for (ReservedSeat reservedSeat : reservedSeats) {
                income += reservedSeat.getTicket().getPrice();
            }

            return new ResponseEntity<>(Map.of("current_income", income, "number_of_available_seats", availableSeats,
                    "number_of_purchased_tickets", reservedSeats.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
