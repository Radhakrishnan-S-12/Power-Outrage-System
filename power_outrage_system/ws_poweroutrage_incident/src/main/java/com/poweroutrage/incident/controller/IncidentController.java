package com.poweroutrage.incident.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.poweroutrage.incident.model.IncidentEntity;
import com.poweroutrage.incident.service.IncidentService;

@RestController
@RequestMapping("/incident-api")
@AllArgsConstructor
@Slf4j
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private IncidentEntity incidentEntity;

   // @Secured({"Admin"})
    @GetMapping("/admin/incident/{incidentId}")
    public ResponseEntity<IncidentEntity> getIncident(@PathVariable Integer incidentId) {
        log.info("Inside IncidentController::getIncident with incidentId: " + incidentId);
        incidentEntity = incidentService.getIncident(incidentId);
        if (!incidentEntity.getTicketId().isEmpty()) {
            log.info("Retrieved IncidentEntity " + incidentEntity);
            return ResponseEntity.ok(incidentEntity);
        } else {
            log.error("No data found");
            return ResponseEntity.notFound().build();
        }
    }

    //@Secured({"User"})
    @PostMapping("/users/addincident")
    public boolean addIncident(@RequestBody IncidentEntity incidentEntity) {
        log.info("Inside IncidentController::addIncident with IncidentEntity: " +
                incidentEntity);
        return incidentService.addIncident(incidentEntity);
    }

   // @Secured({"Admin"})
    @PutMapping("/admin/updateincident/{ticketId}/{status}")
    public ResponseEntity<IncidentEntity> updateIncident(@PathVariable String ticketId, @PathVariable String status) {
        log.info("Inside IncidentController::updateIncident, tickerId " + ticketId + ", status: " + status);
        incidentEntity = incidentService.updateIncident(ticketId, status);
        if (!incidentEntity.toString().isEmpty()) {
            log.info("Updated IncidentEntity " + incidentEntity + " with status " + status);
            return ResponseEntity.ok(incidentEntity);
        } else {
            log.error("No data found");
            return ResponseEntity.noContent().build();
        }
    }

    //@Secured({"Admin"})
    @GetMapping("/admin/alltickets")
    public ResponseEntity<List<IncidentEntity>> getAllTickets() {
        log.info("Inside IncidentController::getAllTickets");
        List<IncidentEntity> incidentList = incidentService.getAllTickets();
        if (!incidentList.toString().isEmpty()) {
            log.info("Retrieved the IncidentEntity List " + incidentList);
            return ResponseEntity.ok(incidentList);
        } else {
            log.error("No data found");
            return ResponseEntity.noContent().build();
        }
    }

   // @Secured({"Admin"})
    @GetMapping("/admin/search/{ticketStatus}")
    public ResponseEntity<List<IncidentEntity>> searchTicketsWithStatus(@PathVariable String ticketStatus) {
        log.info("Inside IncidentController::getTicketsWithNewStatus");
        List<IncidentEntity> incidentList = incidentService.searchTicketsWithStatus(ticketStatus);
        Integer size = incidentList.size();
        if (!"0".equalsIgnoreCase(size.toString())) {
            log.info("Retrieved all New Tickets " + incidentList);
            return ResponseEntity.ok(incidentList);
        } else {
            log.error("No data found");
            return ResponseEntity.noContent().build();
        }
    }

    //@Secured({"User"})
    @GetMapping("/users/gettickets/{userId}")
    public ResponseEntity<List<IncidentEntity>> getUsersTickets(@PathVariable String userId) {
        log.info("Inside IncidentController::getUsersTickets with UserId: " + userId);
        List<IncidentEntity> incidentList = incidentService.getUsersTickets(userId);
        Integer size = incidentList.size();
        if (!"0".equalsIgnoreCase(size.toString())) {
            log.info("Retrieved the ticket " + incidentList + " created by User " + userId);
            return ResponseEntity.ok(incidentList);
        } else {
            log.error("No data found");
            return ResponseEntity.noContent().build();
        }
    }

}
