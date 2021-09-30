package cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CinemaRoom {

    private int totalRows;
    private int totalColumns;
    private List<Map<String, Integer>> availableSeats;

    public CinemaRoom(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        availableSeats = new ArrayList<>();

        // populate the list of seats
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                availableSeats.add(Map.of("row", i, "column", j));
            }
        }
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

    public List<Map<String, Integer>> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Map<String, Integer>> availableSeats) {
        this.availableSeats = availableSeats;
    }
}
