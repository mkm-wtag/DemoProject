package cinema.entity.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatsDAO seatsDAO;

    @Autowired
    public SeatServiceImpl(SeatsDAO seatsDAO) {
        this.seatsDAO = seatsDAO;
    }

    @Override
    @Transactional
    public void create(Seat seat) {
        seatsDAO.create(seat);
    }

    @Override
    @Transactional
    public List<Seat> getAllSeats() {
        return seatsDAO.getAllSeats();
    }

    @Override
    @Transactional
    public void update(Seat seat) {
        seatsDAO.update(seat);
    }

    @Override
    @Transactional
    public Seat getSeatById(String seatId) {
        return seatsDAO.getSeatById(seatId);
    }
}
