package cinema.controller;

import cinema.dtos.BookedSeatDTO;
import cinema.dtos.CinemaRoomDTO;
import cinema.dtos.ReturnedTicketDTO;
import cinema.dtos.TockenRequest;
import cinema.model.CinemaStatistics;
import cinema.model.ErrorResponse;
import cinema.model.Seat;
import cinema.service.CinemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CinemaRoomDTO getSeats() {
        return cinemaService.getCinemaRoom();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> bookSeats(@RequestBody @Valid Seat seat) {

        Optional<BookedSeatDTO> optionalReservedSeat = cinemaService.bookSeat(seat);

        if (optionalReservedSeat.isPresent()) {
            return ResponseEntity.ok().body(optionalReservedSeat.get());
        }
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("The ticket has been already purchased!"));
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody TockenRequest tokenBody) {
        String token = tokenBody.getToken().toString();

        Optional<ReturnedTicketDTO> optionalReservedSeat = cinemaService.returnTicket(token);

        if (optionalReservedSeat.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(optionalReservedSeat.get());
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
                    .body(cinemaStatistics);
        }
        return new ResponseEntity<>(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
