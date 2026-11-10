package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {

}
