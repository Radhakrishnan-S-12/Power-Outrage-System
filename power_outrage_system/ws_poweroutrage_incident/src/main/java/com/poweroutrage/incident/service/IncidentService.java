package com.poweroutrage.incident.service;

import static com.poweroutrage.incident.constants.TicketConstants.TICKET_STATUS_CLOSED;
import static com.poweroutrage.incident.constants.TicketConstants.TICKET_STATUS_NEW;
import static com.poweroutrage.incident.constants.TicketConstants.TICKET_STATUS_PROGRESS;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.poweroutrage.incident.model.UserDTO;
import com.poweroutrage.incident.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poweroutrage.incident.client.UsersFeignClient;
import com.poweroutrage.incident.model.IncidentEntity;
import com.poweroutrage.incident.repository.IncidentRepository;
//import com.poweroutrage.users.register.model.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidentService {

    @Autowired
    private IncidentEntity incidentEntity;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersFeignClient userFeignClient;

    public IncidentEntity getIncident(Integer id) {
        try {
            log.info("Inside IncidentService");
            incidentEntity = incidentRepository.findById(id.toString()).get();
            log.info("Retrieved the Incident " + incidentEntity + " with " + "IncidentId " + id);
        } catch (Exception e) {
            log.error("No such Ticket exists with this IncidentID " + id);
        }
        return incidentEntity;
    }

    public boolean addIncident(IncidentEntity incidentEntity) {
        log.info("Inside Incident Service::addIncident");
        Date date = new Date(System.currentTimeMillis());
        incidentEntity.setTicketCreationTime(date);
        incidentEntity.setTicketStatus(TICKET_STATUS_NEW);
        UserDTO userEntity =
                userFeignClient.getUserEntity(incidentEntity.getUserId());
        log.info("Retrieved UserEntity via client call "+userEntity);
        usersRepository.save(userEntity);
        incidentRepository.save(incidentEntity);
        log.info("Incident Added successfully " + incidentEntity);
        return true;
    }

    public IncidentEntity updateIncident(String incidentId, String status) {
        try {
            log.info("Inside IncidentService::updateIncident");
            incidentEntity = incidentRepository.findById(incidentId).get();
            if (status.equalsIgnoreCase(TICKET_STATUS_CLOSED)) {
                incidentEntity.setTicketActive(false);
                log.info("Ticket Closed. TicketId: " + incidentId);
                incidentEntity.setTicketStatus(status);
            } else {
                log.info("Ticket Status has been changed to " + status);
                incidentEntity.setTicketStatus(status);
            }
        } catch (Exception e) {
            log.error("No such ticket found " + e.getMessage());
        }
        return incidentRepository.save(incidentEntity);
    }

    public List<IncidentEntity> searchTicketsWithStatus(String ticketStatus) {
        log.info("Inside IncidentService::getTicketsWithNewStatus");
        List<IncidentEntity> incidentEntities = new ArrayList<>();
        List<IncidentEntity> newTicketList = new ArrayList<>();
        if (ticketStatus.equalsIgnoreCase(TICKET_STATUS_NEW)) {
            incidentRepository.findAll().forEach(incidentEntities::add);
            incidentEntities.stream()
                    .filter(incidentEntity -> incidentEntity.getTicketStatus().equalsIgnoreCase(TICKET_STATUS_NEW))
                    .forEach(newTicketList::add);
            log.info("New tickets are retrieved");
            return newTicketList;
        } else if (ticketStatus.equalsIgnoreCase(TICKET_STATUS_PROGRESS)) {
            incidentEntities.stream()
                    .filter(incidentEntity -> incidentEntity.getTicketStatus().equalsIgnoreCase(TICKET_STATUS_PROGRESS))
                    .forEach(newTicketList::add);
            log.info("In-Progress tickets are retrieved");
            return newTicketList;
        } else {
            incidentEntities.stream()
                    .filter(incidentEntity -> incidentEntity.getTicketStatus().equalsIgnoreCase(TICKET_STATUS_CLOSED))
                    .forEach(newTicketList::add);
            log.info("Closed tickets are retrieved");
            return newTicketList;
        }
    }

//    public void deleteIncident(String id) {
//        log.info("Successfully deleted the following ticket " + id);
//        if (incidentRepository.findById(id).get().getTicketStatus().equalsIgnoreCase(TICKET_STATUS_CLOSED))
//            incidentRepository.deleteById(id);
//        else
//            log.error("The ticket is not yet closed");
//    }

    public List<IncidentEntity> getAllTickets() {
        List<IncidentEntity> ticketList = new ArrayList<>();
        log.info("Retrieved all the tickets");
        incidentRepository.findAll().forEach(ticketList::add);
        return ticketList;
    }

    public List<IncidentEntity> getUsersTickets(String userId) {
        log.info("Inside IncidentService::getUsersTickets");
        log.info("Retrieved the ticket for the user with " + userId);
        return incidentRepository.getAllUserTickets(userId);
    }
}
