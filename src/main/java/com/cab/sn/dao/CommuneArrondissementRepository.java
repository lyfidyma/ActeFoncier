package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.CommuneArrondissement;

@Repository
public interface CommuneArrondissementRepository extends JpaRepository<CommuneArrondissement, Long>{
	
	@Query("select c from CommuneArrondissement c where c.libelleCommuneArrond like :x")
	public CommuneArrondissement findByCommuneArrondissement(@Param("x")String communeArrond);

}
