package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.models.Ticket;
import com.flowdesk.aplication.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private SetorRepository setorRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private ComentarioRepository comentarioRepository;
    @Mock
    private HistoricoRepository historicoRepository;
    @Mock
    private AnexoRepository anexoRepository;
    @Mock
    private RelationshipMapper relationshipMapper;
    @InjectMocks
    private TicketService ticketService;

    @Test
    void createRejectsMissingRequiredRelationshipWithBadRequest() {
        Ticket ticket = new Ticket();
        ticket.setSolicitanteID("68e2a123456789abcdef1234");
        when(usuarioRepository.existsById(ticket.getSolicitanteID())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketService.salvaTicket(ticket));

        assertEquals(400, exception.getStatusCode().value());
        verify(ticketRepository, never()).save(ticket);
    }

    @Test
    void deleteRejectsTicketWithDependentCommentsAsConflict() {
        String ticketID = "68e2a123456789abcdef1234";
        when(ticketRepository.existsById(ticketID)).thenReturn(true);
        when(comentarioRepository.existsByTicketID(ticketID)).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketService.deletarTicket(ticketID));

        assertEquals(409, exception.getStatusCode().value());
        verify(ticketRepository, never()).deleteById(ticketID);
    }

    @Test
    void getReturnsNotFoundForUnknownTicket() {
        String ticketID = "68e2a123456789abcdef1234";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketService.buscaTicketID(ticketID));

        assertEquals(404, exception.getStatusCode().value());
    }
}
