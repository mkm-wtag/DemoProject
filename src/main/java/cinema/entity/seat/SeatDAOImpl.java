package cinema.entity.seat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SeatDAOImpl implements SeatsDAO {
    private final SessionFactory sessionFactory;

    @Autowired

    public SeatDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Seat seat) {
        Session session = sessionFactory.getCurrentSession();
        session.save(seat);
    }

    @Override
    public List<Seat> getAllSeats() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Seat> cq = cb.createQuery(Seat.class);
        Root<Seat> root = cq.from(Seat.class);
        cq.select(root);
        Query<Seat> query = session.createQuery(cq);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    @Override
    public void update(Seat seat) {
        Session session = sessionFactory.getCurrentSession();
        session.update(seat);
    }

    @Override
    public Seat getSeatById(String seatId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Seat.class, seatId);
    }
}
