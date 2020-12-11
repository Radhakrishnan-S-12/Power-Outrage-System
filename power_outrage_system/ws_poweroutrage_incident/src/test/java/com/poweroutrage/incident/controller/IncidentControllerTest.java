package com.poweroutrage.incident.controller;

import com.poweroutrage.incident.configuration.BaseConfiguration;

import static com.poweroutrage.incident.constants.TicketConstants.TICKET_STATUS_CLOSED;
import static com.poweroutrage.incident.constants.TicketConstants.TICKET_STATUS_NEW;

import com.poweroutrage.incident.model.IncidentEntity;
import com.poweroutrage.incident.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class IncidentControllerTest extends BaseConfiguration {

    @Autowired
    private IncidentController incidentController;

    @Mock
    private IncidentService incidentService;
    private IncidentEntity incidentEntity;

    @BeforeEach
    public void init() {
        super.setUp();
        this.incidentController = new IncidentController(incidentService, incidentEntity);
    }

    @Test
    public void getCallTests() throws IOException {
        //get-only-one-ticket
        ResponseEntity<IncidentEntity> expectedResponse =
                ResponseEntity.ok(getIncidentEntity());
        when(incidentService.getIncident(1)).thenReturn(getIncidentEntity());
        ResponseEntity<IncidentEntity> actualResponse =
                incidentController.getIncident(1);
        assertEquals(actualResponse, expectedResponse);
        verify(incidentService, times(1)).getIncident(1);

        //getAllTickets
        List<IncidentEntity> incidentEntities = new ArrayList<>();
        incidentEntities.add(getIncidentEntity());
        ResponseEntity<List<IncidentEntity>> expected =
                ResponseEntity.ok(incidentEntities);
        when(incidentService.getAllTickets()).thenReturn(incidentEntities);
        ResponseEntity<List<IncidentEntity>> actual =
                incidentController.getAllTickets();
        assertEquals(actual, expected);
        assertEquals(1, actual.getBody().size());
        assertTrue(
                actual.getBody().stream().anyMatch(incidentEntity1 ->
                        incidentEntity1.getTicketId().equalsIgnoreCase("1")));
        verify(incidentService, times(1)).getAllTickets();

        //Specified User's Ticket
        when(incidentService.getUsersTickets("Adm_20")).thenReturn(incidentEntities);
        actual = incidentController.getUsersTickets("Adm_20");
        assertEquals(actual, expected);
        assertEquals(1, actual.getBody().size());
        assertTrue(actual.getBody().stream().anyMatch(incidentEntity1 ->
                incidentEntity1.getUserId().equalsIgnoreCase("Adm_20")));
        ResponseEntity<List<IncidentEntity>> expectedBadResponse =
                ResponseEntity.noContent().build();
        when(incidentService.getUsersTickets("Adm_20")).thenReturn(new ArrayList<>());
        ResponseEntity<List<IncidentEntity>> actualBadResponse =
                incidentController.getUsersTickets("Adm_20");
        assertEquals(expectedBadResponse, actualBadResponse);
        verify(incidentService, times(2)).getUsersTickets("Adm_20");

        //Search
        expected = ResponseEntity.ok(incidentEntities);
        when(incidentService.searchTicketsWithStatus(TICKET_STATUS_NEW)).thenReturn(incidentEntities);
        actual = incidentController.searchTicketsWithStatus(TICKET_STATUS_NEW);
        assertEquals(actual, expected);
        expectedBadResponse =
                ResponseEntity.noContent().build();
        when(incidentService.searchTicketsWithStatus(TICKET_STATUS_NEW)).thenReturn(new ArrayList<>());
        actualBadResponse =
                incidentController.searchTicketsWithStatus(TICKET_STATUS_NEW);
        assertEquals(expectedBadResponse, actualBadResponse);
        verify(incidentService, times(2)).searchTicketsWithStatus(TICKET_STATUS_NEW);
    }

    @Test
    public void postCallTest() throws IOException {
        //Add incident
        when(incidentService.addIncident(getIncidentEntity())).thenReturn(true);
        assertTrue(incidentController.addIncident(getIncidentEntity()));
        when(incidentService.addIncident(getIncidentEntity())).thenReturn(false);
        assertFalse(incidentController.addIncident(getIncidentEntity()));
    }

    @Test
    public void putCallTest() throws IOException {
        ResponseEntity<IncidentEntity> expectedResponse =
                ResponseEntity.ok(getIncidentEntity());
        when(incidentService.updateIncident("1", TICKET_STATUS_CLOSED)).thenReturn(getIncidentEntity());
        ResponseEntity<IncidentEntity> actualResponse =
                incidentController.updateIncident("1", TICKET_STATUS_CLOSED);
        assertEquals(actualResponse, expectedResponse);
        verify(incidentService).updateIncident("1", TICKET_STATUS_CLOSED);
    }

    private IncidentEntity getIncidentEntity() throws IOException {
        return objectMapper.readValue(readFromFile("src/test/resources/json" +
                "/IncidentEntity.json"), IncidentEntity.class);
    }
}
