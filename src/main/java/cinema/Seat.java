package cinema;

public class Seat {
    private int row;
    private int column;
    private int price;

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
}
