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

        Optional<ReservedSeat> optionalReservedSeat = cinemaService.bookSeat(seat);

        if (optionalReservedSeat.isPresent()) {
            return ResponseEntity.ok().body(optionalReservedSeat.get());
        }
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("The ticket has been already purchased!"));
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
                    .body(new ErrorResponse("Wrong token!"));
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
        return new ResponseEntity<>(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
