package com.poweroutrage.incident.repository;

import com.poweroutrage.incident.model.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UserDTO, String> {
}
