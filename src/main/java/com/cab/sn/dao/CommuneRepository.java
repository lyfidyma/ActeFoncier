package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Commune;

@Repository

public interface CommuneRepository extends JpaRepository<Commune, Long>{
	@Query("select c from Commune c where c.libelleCommune like :x")
	public Commune findByCommune(@Param("x")String commune);

}
