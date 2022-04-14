package cinema.entity.ticket;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ticket {

    @Id
    @GenericGenerator(name = "sequence_id", strategy = "cinema.utility.MyGenerator")
    @GeneratedValue(generator = "sequence_id")
    private String ticketId;

    @NotEmpty
    private String seatId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatDescription) {
        this.seatId = seatDescription;
    }
}
