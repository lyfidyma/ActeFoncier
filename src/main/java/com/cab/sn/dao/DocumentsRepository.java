package com.cab.sn.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cab.sn.entities.Documents;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long>{
	@Query("select d from Documents d where d.numDocument like :x")
	public Page<Documents> chercher(@Param("x")String motCle, Pageable pageable);
	
	
	
	

}
