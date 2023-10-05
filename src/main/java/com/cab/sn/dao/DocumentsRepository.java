package com.cab.sn.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Documents;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long>{
	@Query("select d from Documents d where d.numDocument like :x or d.typeDocument.typeDoc like :x")
	public Page<Documents> chercher(@Param("x")String motCle, Pageable pageable);
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Bail%' and d.statutDocument = 'transmis'")
	public List<Documents> findByTypeDocBailTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Bail%' and d.statutDocument = 'non transmis'")
	public List<Documents> findByTypeDocBailNonTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Arrêté%' and d.statutDocument = 'transmis'")
	public List<Documents> findByTypeDocArreteTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Arrêté%' and d.statutDocument = 'non transmis'")
	public List<Documents> findByTypeDocArreteNonTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Décision%' and d.statutDocument = 'transmis'")
	public List<Documents> findByTypeDocDécisionTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Décision%' and d.statutDocument = 'non transmis'")
	public List<Documents> findByTypeDocDécisionNonTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Décret%' and d.statutDocument = 'transmis'")
	public List<Documents> findByTypeDocDecretTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Décret%' and d.statutDocument = 'non transmis'")
	public List<Documents> findByTypeDocDecretNonTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Autres%' and d.statutDocument = 'transmis'")
	public List<Documents> findByTypeDocAutresTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like '%Autres%' and d.statutDocument = 'non transmis'")
	public List<Documents> findByTypeDocAutresNonTransmis();
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like :x")
	public List<Documents> findByTypeDoc(@Param("x")String typeDoc);
	
	@Query("select d from Documents d where d.codeUniqueDocument like :x and d.statutDocument = 'transmis'")
	public Documents findByCodeUniqueDocument(@Param("x")String CodeUniqueDocument);
	
	@Query("select d from Documents d where d.beneficiaire.cni like :x")
	public List <Documents> findByCni(@Param("x")Long cni);
}
