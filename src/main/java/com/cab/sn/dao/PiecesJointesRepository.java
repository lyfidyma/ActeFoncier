package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cab.sn.entities.PiecesJointes;

public interface PiecesJointesRepository extends JpaRepository<PiecesJointes, Long>{
	@Query("select code_document from PiecesJointes pj where pj.codeDocument like :x")
	public Long chercherIdPiecesJointes(@Param("x")Long idDoc);
	
}
