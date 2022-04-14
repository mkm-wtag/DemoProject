package cinema.entity.ticket;

import cinema.entity.seat.Seat;
import cinema.utility.ResponseObject;

import java.util.List;

public interface TicketDAO {
    List<Ticket> getAllTickets(String userId);

    Ticket getTicket(String userId, String ticketId);

    Ticket createTicket(String userId, Ticket ticket);

    Ticket updateTicket(String userId, Ticket ticket);

    ResponseObject deleteTicket(String userId, String ticketId);

    List<Seat> viewAllSeats();
}
