package cinema.model;

public class CinemaStatistics {
    private int availableSeats;
    private int income;
    private int reservedSeats;

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public CinemaStatistics(int availableSeats, int income, int reservedSeats) {
        this.availableSeats = availableSeats;
        this.income = income;
        this.reservedSeats = reservedSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
