package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cab.sn.entities.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long>{

	@Query("select p.idBeneficiaire from Personne p where p.cni like :x")
	public Long chercherIdPersonne(@Param("x")Long cniPersonne);
}
