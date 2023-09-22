package com.cab.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.PiecesJointes;

@Repository
public interface PiecesJointesRepository extends JpaRepository<PiecesJointes, Long>{
	@Query("select p from PiecesJointes p where p.documents.idDocument like :x")
	public List<PiecesJointes> chercherPiecesJointes(@Param("x")Long idDoc);
	
}
