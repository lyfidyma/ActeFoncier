package com.cab.sn.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.cab.sn.entities.TypeDocument;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long>{
	@Query("select d from TypeDocument d where d.typeDoc like :x")
	public TypeDocument chercherTypeDocumentParTypeDoc(@Param("x")String typeDoc);

}
