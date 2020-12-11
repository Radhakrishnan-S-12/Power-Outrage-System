package com.poweroutrage.users.register.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description = "DTO class for client call")
@Data
public class UserDTO {

    @ApiModelProperty(name = "User ID", notes = "Unique ID of the User")
    private String userId;
    @ApiModelProperty(name = "Name", notes = "Name of the User")
    private String name;
    @ApiModelProperty(name = "E-mail", notes = "Mail ID of that user")
    private String email;
    @ApiModelProperty(name = "Contact Number", notes = "User's Contact Number")
    private Long contactNumber;
    @ApiModelProperty(name = "Zip-Code", notes = "User's Locality ID")
    private String zipCode;
    @ApiModelProperty(name = "Address", notes = "User's Address")
    private String address;
    @ApiModelProperty(name = "Country", notes = "User's Country")
    private String country;
}
