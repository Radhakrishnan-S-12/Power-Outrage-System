package com.poweroutrage.incident.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.poweroutrage.incident.client.UsersFeignClient;
import com.poweroutrage.incident.configuration.BaseConfiguration;
import com.poweroutrage.incident.constants.TicketConstants;
import com.poweroutrage.incident.model.IncidentEntity;
import com.poweroutrage.incident.model.UserDTO;
import com.poweroutrage.incident.repository.IncidentRepository;
import com.poweroutrage.incident.repository.UsersRepository;
//import com.poweroutrage.users.register.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest extends BaseConfiguration {

    @Autowired
    private IncidentService incidentService;

    @Mock
    private IncidentEntity incidentEntity;

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsersFeignClient userFeignClient;

    @BeforeEach
    public void init() {
        super.setUp();
//        this.incidentService = new IncidentService(incidentEntity,
//                incidentRepository, usersRepository, userFeignClient);
    }

    @Test
    public void getIncidentTest() throws IOException {
        when(incidentRepository.findById("1")).thenReturn(Optional.of(getIncidentEntity()));
        IncidentEntity incidentEntity = incidentService.getIncident(1);
        assertEquals("1", incidentEntity.getTicketId());
        verify(incidentRepository).findById("1");
    }

    @Test
    public void addIncidentTest() throws IOException {
        when(userFeignClient.getUserEntity("Adm_20")).thenReturn(getUserEntity());
        IncidentEntity incidentEntity = getIncident();
        incidentEntity.setTicketActive(true);
        incidentEntity.setTicketStatus(TicketConstants.TICKET_STATUS_NEW);
        incidentEntity.setTicketCreationTime(new Date(System.currentTimeMillis()));
        when(usersRepository.save(getUserEntity())).thenReturn(getUserEntity());
        when(incidentRepository.save(incidentEntity)).thenReturn(getIncidentEntity());
        assertTrue(
                incidentService.addIncident(incidentEntity));
        verify(incidentRepository).save(incidentEntity);
        verify(usersRepository).save(getUserEntity());
    }

    @Test
    public void getAllTicketsTest(){

    }

    private IncidentEntity getIncidentEntity() throws IOException {
        return objectMapper.readValue(readFromFile("src/test/resources/json" +
                "/IncidentEntity.json"), IncidentEntity.class);
    }

    private IncidentEntity getIncident() throws IOException {
        return objectMapper.readValue(readFromFile("src/test/resources/json" +
                "/Incident.json"), IncidentEntity.class);
    }

    private UserDTO getUserEntity() throws IOException {
        return objectMapper.readValue(readFromFile("src/test/resources/json" +
                "/Users.json"), UserDTO.class);
    }

}
