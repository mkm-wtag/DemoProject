package cinema.entity.ticket;

import cinema.entity.seat.Seat;
import cinema.entity.seat.SeatsDAO;
import cinema.entity.user.User;
import cinema.entity.user.UserDAO;
import cinema.exception.AccessDeniedException;
import cinema.exception.ResourceNotFoundException;
import cinema.utility.ResponseObject;
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
public class TicketDAOImpl implements TicketDAO {
    private final SessionFactory sessionFactory;
    private final UserDAO userDAO;
    private final SeatsDAO seatsDAO;

    @Autowired
    public TicketDAOImpl(SessionFactory sessionFactory, UserDAO userDAO, SeatsDAO seatsDAO) {
        this.sessionFactory = sessionFactory;
        this.userDAO = userDAO;
        this.seatsDAO = seatsDAO;
    }


    @Override
    public List<Ticket> getAllTickets(String userId) {
        User user = userDAO.getByUserId(userId);
        if (user == null) {
            throw new ResourceNotFoundException("No User found with userId : " + userId);
        }
        return user.getTickets();
    }

    @Override
    public Ticket getTicket(String userId, String ticketId) {
        User user = userDAO.getByUserId(userId);
        if (user == null) {
            return null;
        }
        List<Ticket> ticketsList = user.getTickets();
        Ticket ticket = null;
        for (Ticket tickets : ticketsList) {
            if (tickets.getTicketId().equals(ticketId)) {
                ticket = tickets;
                break;
            }
        }
        return ticket;
    }

    @Override
    public Ticket createTicket(String userId, Ticket ticket) {
        User user = userDAO.getByUserId(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User Not found with userId : " + userId);
        }
        String seatFromTicket = ticket.getSeatId();

        Seat seat = seatsDAO.getSeatById(seatFromTicket);
        if (seat != null && !seat.isBooked()) {
            seat.setBooked(true);
        }
        else if (seat==null){
            throw new ResourceNotFoundException("No seat found with seatId : " + seatFromTicket);
        }
        else{
            throw new AccessDeniedException("The ticket is already booked by someone else.");
        }
        user.getTickets().add(ticket);
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        return ticket;
    }

    @Override
    public Ticket updateTicket(String userId, Ticket updateTicket) {
        User user = userDAO.getByUserId(userId);
        if (user == null) {
            return null;
        }
        List<Ticket> ticketsList = user.getTickets();
        Ticket oldTicket = null;
        for (Ticket ticket : ticketsList) {
            if (ticket.getTicketId().equals(updateTicket.getTicketId())) {
                oldTicket = ticket;
                break;
            }
        }
        if (oldTicket == null) {
            return null;
        }
        oldTicket.setSeatId(updateTicket.getSeatId());
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        return oldTicket;
    }

    @Override
    public ResponseObject deleteTicket(String userId, String ticketId) {
        User user = userDAO.getByUserId(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User Not found with userId : " + userId);
        }
        List<Ticket> ticketsList = user.getTickets();
        Ticket ticketToBeDeleted = null;
        for (Ticket ticket : ticketsList) {
            if (ticket.getTicketId().equals(ticketId)) {
                ticketToBeDeleted = ticket;
                break;
            }
        }
        if (ticketToBeDeleted == null) {
            throw new ResourceNotFoundException("Ticket Not found with ticketId : " + ticketId);
        }
        String seatFromTicket = ticketToBeDeleted.getSeatId();
        Seat seat = seatsDAO.getSeatById(seatFromTicket);
        seat.setBooked(false);
        ticketsList.remove(ticketToBeDeleted);
        user.setTickets(ticketsList);

        Session session = sessionFactory.getCurrentSession();
        session.update(ticketToBeDeleted);

        return new ResponseObject("The ticket with ticketId " + ticketId + " has been returned.");
    }

    @Override
    public List<Seat> viewAllSeats() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Seat> cq = cb.createQuery(Seat.class);
        Root<Seat> root = cq.from(Seat.class);
        cq.select(root);
        Query<Seat> query = session.createQuery(cq);
        return query.getResultList();
    }
}
