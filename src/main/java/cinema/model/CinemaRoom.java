package cinema.model;

import java.util.List;

public class CinemaRoom {

    private int totalRows;
    private int totalColumns;
    private List<Seat> availableSeats;

    public CinemaRoom(int totalRows, int totalColumns, List<Seat> seats) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = seats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }
}
