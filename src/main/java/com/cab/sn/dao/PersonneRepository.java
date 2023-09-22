package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Personne;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long>{

	@Query("select p from Personne p where p.cni like :x")
	public Personne chercherPersonne(@Param("x")Long cniPersonne);
	
	@Query("select p from Personne p where p.idBeneficiaire like :x")
	public Personne chercherPersonneParId(@Param("x")Long id);
}
