package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class CinemaController {

    private final String ERROR = "error";
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public CinemaRoom getSeats() {
        return cinemaService.getCinemaRoom();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> bookSeats(@RequestBody Seat seat) {
        if (seat.getRow() < 1 || seat.getColumn() < 1 || seat.getColumn() > 9 || seat.getRow() > 9) {
            return ResponseEntity.badRequest()
                    .body(Map.of(ERROR, "The number of a row or a column is out of bounds!"));
        }

        Optional<ReservedSeat> optionalReservedSeat = cinemaService.bookSeat(seat);

        if (optionalReservedSeat.isPresent()) {
            return ResponseEntity.ok().body(optionalReservedSeat.get());
        }
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(ERROR, "The ticket has been already purchased!"));
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> tokenBody) {
        String token = tokenBody.get("token");

        Optional<ReservedSeat> optionalReservedSeat = cinemaService.returnTicket(token);

        if (optionalReservedSeat.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("returned_ticket", optionalReservedSeat.get().getTicket()));
        } else {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(ERROR, "Wrong token!"));
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {

        if ("super_secret".equals(password)) {
            CinemaStatistics cinemaStatistics = cinemaService.getStats();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("current_income", cinemaStatistics.getIncome(),
                    "number_of_available_seats", cinemaStatistics.getAvailableSeats(),
                    "number_of_purchased_tickets", cinemaStatistics.getReservedSeats()));
        }
        return new ResponseEntity<>(Map.of(ERROR, "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
