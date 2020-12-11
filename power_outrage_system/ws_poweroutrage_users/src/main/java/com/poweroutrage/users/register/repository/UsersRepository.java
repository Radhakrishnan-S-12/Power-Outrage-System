package com.poweroutrage.users.register.repository;

import com.poweroutrage.users.register.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, String>{

}