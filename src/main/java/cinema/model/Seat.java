package cinema.model;

import java.util.Objects;

public class Seat {
    private int row;
    private int column;
    private int price;
    private String token;

    public Seat() {
    }

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Seat)) {
            return false;
        }

        Seat otherSeat = (Seat) obj;
        if (this.row == otherSeat.row && this.column == otherSeat.column) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", price=" + price +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this) + 42;
    }
}
