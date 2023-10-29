package com.cab.sn.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Profil;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long>{
	@Query("select p from Profil p where p.nomProfil = ?1")
	Profil findByNomProfil(String nomProfil);

}
