package com.cab.sn.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Documents;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long>{
	@Query("select d from Documents d where CONCAT (d.numDocument, ' ', d.typeDocument.typeDoc, ' ', d.codeUniqueDocument, ' ', d.statutDocument, ' ', d.responsableDocument, ' ', d.commune.libelleCommune, ' ', d.titreGlobal, ' ', d.personne.cni, ' ', d.personne.nomPersonne, ' ', d.personne.prenom) like %?1%")
	public Page<Documents> chercher(String motCle, Pageable pageable);
	
	@Query("select d from Documents d where d.typeDocument.typeDoc = :x")
	public Page<Documents> filtreTypeDocument(@Param("x")String keySearch, Pageable pageable);
	
	@Query("select d from Documents d where d.typeDocument.typeDoc like :x")
	public List<Documents> findByTypeDoc(@Param("x")String typeDoc);
	
	@Query("select d from Documents d where d.typeDocument.typeDoc = ?1 and d.statutDocument = ?2 ")
	public List <Documents> findByTypeDocumentAndStatutDocument(String typeDoc, String statut);
	
	@Query("select d from Documents d where d.codeUniqueDocument like :x and d.statutDocument = 'transmis'")
	public Documents findByCodeUniqueDocument(@Param("x")String CodeUniqueDocument);
	
	@Query("select d from Documents d where d.personne.cni like :x")
	public List <Documents> findByCni(@Param("x")String cni);
	
	@Query("select d from Documents d where d.commune.libelleCommune like :x")
	public List <Documents> findDocumentsByCommune(@Param("x")String libelleCommune);
	
	@Query("select d from Documents d where d.entreprise.ninea like :x")
	public List <Documents> findByNinea(@Param("x")String ninea);
	
	@Query("select d from Documents d where d.statutDocument = 'transmis'")
	public Page<Documents> findByStatutTransmis(Pageable pageable);
	
	@Query("select d from Documents d where (d.numDocument like :x or d.typeDocument.typeDoc like :x or d.codeUniqueDocument like :x) and d.statutDocument = 'transmis'")
	public Page<Documents> chercherDocumentAValider(@Param("x")String motCle, Pageable pageable);
	
	@Query("select d from Documents d where  (:a is null or d.typeDocument.typeDoc like %:a%) and (:b is null or d.numDocument like %:b%) and (:c is null or d.codeUniqueDocument like %:c%) and (:d is null or d.dateDocument = %:d%) and (:e is null or d.responsableDocument like %:e%)")
	public Page<Documents> chercherDocumentParCriteres(@Param("a")String typeDocument, @Param("b")String numDocument, @Param("c")String codeDocument, @Param("d")LocalDate dateDocument, @Param("e")String responsableDocument, Pageable pageable);
	
	@Query("select d from Documents d where (:a is null or d.personne.nin like %:a%) and (:b is null or d.personne.cni like %:b%) and (:c is null or d.entreprise.ninea like %:c%)")
	public Page<Documents> chercherDocumentParBeneficiaire(@Param("a") String numCEDEAO, @Param("b") String cni, @Param("c")String ninea, Pageable pageable);
	
	@Query("select d from Documents d where d.commune.libelleCommune like %:a%")
	public Page<Documents> chercherDocumentParCommune(@Param("a")String commune, Pageable pageable);
	
	@Query("select d from Documents d where (?1 is null or d.titreGlobal like %?1%) and (?2 is null or d.nicad like %?2%)")
	public Page<Documents> chercherDocumentParTitreDePropriete(String titreGlobal, String nicad, Pageable pageable);
	
	@Query("select d from Documents d where  (:a is null or d.typeDocument.typeDoc like %:a%) and (:b is null or d.numDocument like %:b%) and (:c is null or d.codeUniqueDocument like %:c%) and (:d is null or d.dateDocument = %:d%) and (:e is null or d.responsableDocument like %:e%)")
	public List<Documents> chercherDocumentParCriteresPourExport(@Param("a")String typeDocument, @Param("b")String numDocument, @Param("c")String codeDocument, @Param("d")LocalDate dateDocument, @Param("e")String responsableDocument);
	
	@Query("select d from Documents d where (:a is null or d.personne.nin like %:a%) and (:b is null or d.personne.cni like %:b%) and (:c is null or d.entreprise.ninea like %:c%)")
	public List<Documents> chercherDocumentParBeneficiairePourExport(@Param("a")String numCEDEAO, @Param("b")String cni, @Param("c")String ninea);
	
	@Query("select d from Documents d where d.commune.libelleCommune like %:a%")
	public List<Documents> chercherDocumentParCommunePourExport(@Param("a")String commune);
	
	@Query("select d from Documents d where (?1 is null or d.titreGlobal like %?1%) and (?2 is null or d.nicad like %?2%)")
	public List<Documents> chercherDocumentParTitreDeProprietePourExport(String titreGlobal, String nicad);
	
	@Query("select d from Documents d where (d.personne.cni=?1 or d.entreprise.ninea=?1) and d.typeDocument.typeDoc = ?2 ")
	public List <Documents> findByBeneficiaireAndTypeDocument(String cniOuNinea, String typeDoc);
	
	@Query("select d from Documents d where (d.commune.libelleCommune=?1 or d.commune.communeArrondissement.libelleCommuneArrond=?1 or d.commune.communeArrondissement.departement.libelleDepartement = ?1 or d.commune.communeArrondissement.departement.region.libelleRegion = ?1) and d.typeDocument.typeDoc = ?2 ")
	public List <Documents> findByLocalisationAndTypeDocument(String site, String typeDoc);
	
	@Query("select d from Documents d where d.commune.libelleCommune=?1 and d.typeDocument.typeDoc = ?2 ")
	public List <Documents> findByCommuneAndTypeDocument(String site, String typeDoc);
	
	@Query("select d from Documents d where d.commune.communeArrondissement.libelleCommuneArrond=?1 and d.typeDocument.typeDoc = ?2 ")
	public List <Documents> findByCommuneArrondAndTypeDocument(String site, String typeDoc);
	
	@Query("select d from Documents d where d.commune.communeArrondissement.departement.libelleDepartement = ?1 and d.typeDocument.typeDoc = ?2 ")
	public List <Documents> findByDepartementAndTypeDocument(String site, String typeDoc);
	
	@Query("select d from Documents d where d.commune.communeArrondissement.departement.region.libelleRegion = ?1 and d.typeDocument.typeDoc = ?2 ")
	public List <Documents> findByRegionAndTypeDocument(String site, String typeDoc);
}
