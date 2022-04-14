package cinema.entity.ticket;

import cinema.entity.seat.Seat;
import cinema.utility.ResponseObject;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets(String userId);

    Ticket getTicket(String userId, String ticketId);

    Ticket createTicket(String userId, Ticket ticket, HttpSession session);

    Ticket updateTicket(String userId, Ticket ticket);

    ResponseObject deleteTicket(String userId, String ticketId, HttpSession session);

    List<Seat> viewAllSeats();
}
