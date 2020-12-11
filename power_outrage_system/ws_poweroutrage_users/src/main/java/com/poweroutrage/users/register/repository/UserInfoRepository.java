package com.poweroutrage.users.register.repository;

import com.poweroutrage.users.register.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {
}
