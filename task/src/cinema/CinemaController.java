package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {

    private CinemaRoom cinemaRoom = new CinemaRoom(9, 9);

    @GetMapping("/seats")
    public CinemaRoom getSeats() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<?> bookSeats(@RequestBody Seat seat) {

        if (seat.getRow() > 9 || seat.getColumn() > 9) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } else {
            for (Seat seats : cinemaRoom.getAvailableSeats()) {
                if (seat.getRow() == seats.getRow() && seat.getColumn() == seats.getColumn()) {
                    cinemaRoom.getAvailableSeats().remove(seats);
                    return new ResponseEntity<>(seats, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(Map.of("error","The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }
    }
}
