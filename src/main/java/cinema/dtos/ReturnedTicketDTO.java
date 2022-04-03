package cinema.dtos;

public class ReturnedTicketDTO {
    private final SeatDTO returnedTicket;

    public ReturnedTicketDTO(SeatDTO returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    public SeatDTO getReturnedTicket() {
        return returnedTicket;
    }
}
