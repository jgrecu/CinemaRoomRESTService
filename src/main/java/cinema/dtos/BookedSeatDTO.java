package cinema.dtos;

public class BookedSeatDTO {
    private final String token;
    private final SeatDTO ticket;

    public BookedSeatDTO(String token, SeatDTO ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public SeatDTO getTicket() {
        return ticket;
    }
}
