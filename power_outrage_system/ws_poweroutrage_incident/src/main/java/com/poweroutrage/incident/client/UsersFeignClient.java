package com.poweroutrage.incident.client;

import static com.poweroutrage.incident.constants.TicketConstants.CLIENT_NAME;

import com.poweroutrage.incident.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = CLIENT_NAME)
public interface UsersFeignClient {

	@GetMapping("/users-api/users/{userId}")
	UserDTO getUserEntity(@PathVariable String userId);
}
