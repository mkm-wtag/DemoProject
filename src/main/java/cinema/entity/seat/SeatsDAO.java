package cinema.entity.seat;

import java.util.List;

public interface SeatsDAO {
    void create(Seat seat);

    List<Seat> getAllSeats();

    void update(Seat seat);

    Seat getSeatById(String seatId);
}
