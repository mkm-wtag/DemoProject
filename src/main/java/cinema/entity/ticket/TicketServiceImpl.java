package cinema.entity.ticket;

import cinema.entity.seat.Seat;
import cinema.entity.seat.SeatService;
import cinema.entity.user.User;
import cinema.entity.user.UserDAO;
import cinema.exception.AccessDeniedException;
import cinema.exception.CustomerLoginException;
import cinema.exception.ResourceNotFoundException;
import cinema.utility.AuthorizationUtility;
import cinema.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    private final TicketDAO ticketDAO;
    private final UserDAO userDAO;
    private final SeatService seatService;

    @Autowired
    public TicketServiceImpl(TicketDAO ticketDAO, UserDAO userDAO, SeatService seatService) {
        this.ticketDAO = ticketDAO;
        this.userDAO = userDAO;
        this.seatService = seatService;
    }

    @Override
    @Transactional
    public List<Ticket> getAllTickets(String userId) {
        return ticketDAO.getAllTickets(userId);
    }

    @Override
    @Transactional
    public Ticket getTicket(String userId, String ticketId) {
        return ticketDAO.getTicket(userId,ticketId);
    }

    @Override
    @Transactional
    public Ticket createTicket(String userId, Ticket ticket, HttpSession session) {
        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            throw new CustomerLoginException("You are not logged in");
        }
        User oldUser = userDAO.getByUserId(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("No User Found with userId : " + userId);
        }
        if (!AuthorizationUtility.isCustomerAuthorized(oldUser.getUserEmail(), session)) {
            throw new AccessDeniedException("You are not supposed to do this.");
        }
        Ticket bookedTicket=ticketDAO.createTicket(userId, ticket);

        return bookedTicket;
    }

    @Override
    @Transactional
    public Ticket updateTicket(String userId, Ticket ticket) {
        return ticketDAO.updateTicket(userId, ticket);
    }

    @Override
    @Transactional
    public ResponseObject deleteTicket(String userId, String ticketId, HttpSession session) {
        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            throw new CustomerLoginException("You are not logged in");
        }
        User oldUser = userDAO.getByUserId(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("No User Found with userId : " + userId);
        }
        if (!AuthorizationUtility.isCustomerAuthorized(oldUser.getUserEmail(), session)) {
            throw new AccessDeniedException("You are not supposed to do this.");
        }

        return ticketDAO.deleteTicket(userId, ticketId);
    }

    @Override
    @Transactional
    public List<Seat> viewAllSeats() {
        List<Seat> seatList=ticketDAO.viewAllSeats();
        if(seatList.size()==0){
            for (int i = 0; i < 50; i++) {
                Seat seat = new Seat();
                seat.setBooked(false);
                seatService.create(seat);
            }
            seatList=ticketDAO.viewAllSeats();
        }
        return seatList;
    }
}
