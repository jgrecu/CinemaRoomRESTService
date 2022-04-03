package cinema.dtos;

import java.util.List;

public class CinemaRoomDTO {
    private final int totalRows;
    private final int totalColumns;
    private final List<SeatDTO> availableSeats;

    public CinemaRoomDTO(int totalRows, int totalColumns, List<SeatDTO> availableSeats) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = availableSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<SeatDTO> getAvailableSeats() {
        return availableSeats;
    }
}
