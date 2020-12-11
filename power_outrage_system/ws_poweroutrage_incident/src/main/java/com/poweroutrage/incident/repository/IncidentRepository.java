package com.poweroutrage.incident.repository;

import com.poweroutrage.incident.model.IncidentEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface IncidentRepository extends CrudRepository<IncidentEntity, String> {
	
	@Query(value="Select * from incident.ticket_details where ticket_details.user_id=?", nativeQuery = true)
	List<IncidentEntity> getAllUserTickets(String userId);

}
