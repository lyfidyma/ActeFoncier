package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Entreprise;

@Repository

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>{
	
	@Query("select e from Entreprise e where e.idBeneficiaire like :x")
	public Entreprise chercherEntreprise(@Param("x")Long id);

}
