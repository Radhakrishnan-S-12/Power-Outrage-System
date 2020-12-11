package com.poweroutrage.incident.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EnableSwagger2
@Entity
@Table(name = "user_details")
@Component
@Data
@NoArgsConstructor
public class UserDTO {

    @ApiModelProperty(name = "UserID", notes = "UserID of the User who created" +
            " the ticket")
    @Id
    @Column(name = "user_id")
    private String userId;
    @ApiModelProperty(name = "Name", notes = "Name of the User")
    @Column(name = "name")
    private String name;
    @ApiModelProperty(name = "E-mail", notes = "Mail ID of that user")
    @Column(name = "email")
    private String email;
    @ApiModelProperty(name = "Contact Number", notes = "User's Contact Number")
    @Column(name = "contact_number")
    private String contactNumber;
    @ApiModelProperty(name = "Zip-Code", notes = "User's Locality ID")
    @Column(name = "zip_code")
    private String zipCode;
}
