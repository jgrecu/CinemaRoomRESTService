package cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SeatOutOfBoundsException extends IllegalStateException {
    public SeatOutOfBoundsException() {
        super("The number of a row or a column is out of bounds!");
    }
}
