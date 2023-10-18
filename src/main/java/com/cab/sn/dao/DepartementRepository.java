package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.cab.sn.entities.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long>{
	
	@Query("select d from Departement d where d.libelleDepartement like :x")
	public Departement findByDepartement(@Param("x")String departement);

}
