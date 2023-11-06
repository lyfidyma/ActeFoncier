package com.cab.sn.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cab.sn.entities.Responsable;

public interface ResponsableRepository extends JpaRepository<Responsable, Long>{
	
	Optional <Responsable> findByNomResponsable(String nom);
	Optional <Responsable> findByPrenomResponsable(String prenom);
	Optional <Responsable> findByFonction(String fonction);

}
