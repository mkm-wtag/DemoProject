package cinema.entity.ticket;

import cinema.entity.seat.Seat;
import cinema.exception.InvalidRequestBodyException;
import cinema.exception.ResourceNotFoundException;
import cinema.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/users/{userId}/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/available-seats")
    public List<Seat> viewAllSeats() {
        return ticketService.viewAllSeats();
    }

    @PostMapping
    public Ticket bookTicket(@PathVariable("userId") String userId, @Valid @RequestBody Ticket ticket, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestBodyException(bindingResult);
        }

        return ticketService.createTicket(userId, ticket, session);
    }


    @GetMapping
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable("userId") String userId) {
        List<Ticket> tickets = ticketService.getAllTickets(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping(path = "{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable("userId") String userId, @PathVariable("ticketId") String ticketId) {
        Ticket ticket = ticketService.getTicket(userId, ticketId);
        if (ticket == null) {
            throw new ResourceNotFoundException("No Ticket is found with ticketId : " + ticketId);
        }
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("{ticketId}")
    public ResponseObject returnTicket(@PathVariable("userId") String userId, @PathVariable("ticketId") String ticketId, HttpSession session) {
        return ticketService.deleteTicket(userId, ticketId, session);
    }


}
