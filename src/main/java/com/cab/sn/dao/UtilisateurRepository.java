package com.cab.sn.dao;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.sn.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	@Query("select u from Utilisateur u where u.email = ?1")
	Optional<Utilisateur> findByEmail(String email);
}
