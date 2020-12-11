package com.poweroutrage.incident.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.stereotype.Component;

import java.sql.Date;

@ApiModel(description = "Model which holds the basic details to create a " +
        "Ticket")
@Component
@Entity
@Table(name = "ticket_details")
@Data
@NoArgsConstructor
public class IncidentEntity {

    @Id
    @Column(name = "ticket_id", length = 20)
    @ApiModelProperty(name = "TicketID", notes = "This is a Unique key for " +
            "each Tickets.")
    private String ticketId;
    @ApiModelProperty(name = "Incident", notes = "A short notes of what " +
            "actually happened.")
    @Column(name = "incident")
    private String incident;
    @ApiModelProperty(name = "Ticket Status", notes = "Progress of the ticket " +
            "whether its open or closed or progress")
    @Column(name = "ticket_status")
    private String ticketStatus;
    @ApiModelProperty(name = "Active Status", notes = "Whether it is Open or " +
            "Closed")
    @Column(name = "active_status")
    private boolean isTicketActive = true;
    @ApiModelProperty(name = "UserID", notes = "This is a Unique key for each " +
            "user whoever wants to register themselves with our service")
    @Column(name = "ticket_creation_time")
    private Date ticketCreationTime;
    @ApiModelProperty(name = "UserID", notes = "UserID of the User who created" +
            " the ticket")
    @Column(name = "user_id")
    private String userId;
}
