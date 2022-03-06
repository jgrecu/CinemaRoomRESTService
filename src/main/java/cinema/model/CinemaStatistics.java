package cinema.model;

public class CinemaStatistics {
    private int numberOfAvailableSeats;
    private int currentIncome;
    private int numberOfPurchasedTickets;

    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    public CinemaStatistics(int numberOfAvailableSeats, int income, int numberOfPurchasedTickets) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.currentIncome = income;
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }
}
