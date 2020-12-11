package com.poweroutrage.users.register.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@ApiModel(description = "This model holds all the required details to " +
        "create a user")
@Table(name = "user_table")
@Entity
@Data
@NoArgsConstructor
public class UserEntity {
  @Id
  @Column(name = "user_id", length=30)
  @ApiModelProperty(name = "UserID",notes = "This is a Unique key for each " +
          "user whoever wants to register themselves with our service")
  private String userId;
  @ApiModelProperty(name = "Name",notes = "Respective User's Name")
  private String name;
  @ApiModelProperty(name = "User_Name",notes = "Customized Username for each" +
          " Users")
  @Column(name = "user_name")
  private String userName;
  @ApiModelProperty(name = "Password",notes = "Passowrd which is given by the" +
          " Users which can be futher used to login")
  private String password;
  @ApiModelProperty(name = "Address",notes = "User's Address")
  private String address;
  @ApiModelProperty(name = "State",notes = "The State where the user belong")
  private String state;
  @ApiModelProperty(name = "Country",notes = "User's Nationality")
  private String country;
  @ApiModelProperty(name = "Zip code",notes = "Their Locality Id numer")
  @Column(name = "zip_code")
  private String zipCode;
  @ApiModelProperty(name = "E-mail",notes = "User's Mail Id to contact them " +
          "in future")
  private String email;
  @ApiModelProperty(name = "PAN",notes = "Permanent Account Number of User to" +
          " validate their originality")
  private String pan;
  @ApiModelProperty(name = "Contact Number",notes = "Another mode of contact " +
          "in addition to Email ID")
  @Column(name = "contact_number")
  private Long contactNumber;
  @ApiModelProperty(name = "Account Type",notes = "Respective User's Role: " +
          "Admin/User")
  @Column(name = "account_type")
  private String accountType;
}
