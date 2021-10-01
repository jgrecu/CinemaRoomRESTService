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
    @ResponseBody
    public ResponseEntity<?> bookSeats(@RequestBody Seat seat) {

        if (seat.getRow() > 9 || seat.getColumn() > 9 || seat.getRow() < 1 || seat.getColumn() < 1) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            for (Seat seats : cinemaRoom.getAvailableSeats()) {
                if (seat.getRow() == seats.getRow() && seat.getColumn() == seats.getColumn()) {
                    if (!reservedSeats.stream().anyMatch(o -> o.getTicket().equals(seats))) {
                        ReservedSeat reservedSeat = new ReservedSeat(UUID.randomUUID().toString(), seats);
                        reservedSeats.add(reservedSeat);
                        return new ResponseEntity<>(reservedSeat, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(Map.of("error","The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    @ResponseBody
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> tokenBody) {
        String token = tokenBody.get("token");
        Seat ticket = new Seat(1,1,1);
        if (reservedSeats.stream().anyMatch(o -> o.getToken().equals(token))) {
            for (ReservedSeat reservedSeat : reservedSeats) {
                if (reservedSeat.getToken().equals(token)) {
                    ticket = reservedSeat.getTicket();
                }
            }
            reservedSeats.removeIf(reservedSeat -> reservedSeat.getToken().equals(token));
            return new ResponseEntity<>(Map.of("returned_ticket", ticket), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    @ResponseBody
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
