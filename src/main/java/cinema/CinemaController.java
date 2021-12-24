package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {

    private static final String ERROR = "error";
    private CinemaRoomService cinemaRoomService;

    public CinemaController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @GetMapping("/seats")
    public CinemaRoom getSeats() {
        return cinemaRoomService.getCinemaRoom();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> bookSeats(@RequestBody Seat seat) {

        try {
            ReservedSeat reservedSeat = cinemaRoomService.bookSeat(seat);
            if (reservedSeat != null) {
                return new ResponseEntity<>(reservedSeat, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of(ERROR, "The ticket has been already purchased!"),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of(ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> tokenBody) {
        String token = tokenBody.get("token");

        ReservedSeat ticket = cinemaRoomService.returnTicket(token);

        if (ticket != null) {
            return new ResponseEntity<>(Map.of("returned_ticket", ticket), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of(ERROR, "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {

        if ("super_secret".equals(password)) {
            Map<String, Integer> stats = cinemaRoomService.getStats();
            return new ResponseEntity<>(stats, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of(ERROR, "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
