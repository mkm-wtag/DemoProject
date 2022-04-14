package cinema.entity.seat;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seat {

    @Id
    @GenericGenerator(name = "sequence_id", strategy = "cinema.utility.MyGenerator")
    @GeneratedValue(generator = "sequence_id")
    private String seatId;

    private boolean booked;

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean status) {
        this.booked = status;
    }
}
