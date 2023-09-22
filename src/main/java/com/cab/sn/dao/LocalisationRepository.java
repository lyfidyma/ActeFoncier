package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Localisation;


public interface LocalisationRepository extends JpaRepository<Localisation, Long>{
	

	@Modifying(clearAutomatically = true)
	@Query("update Localisation d set d.commune= :a, d.communeArrond= :b, d.departement= :c, d.region= :d where d.idLocalisation= :x")
	public void updateLocalisation(@Param("x")Long id, @Param("a")String commune, @Param("b")String communeArrond, 
			@Param("c")String departement, @Param("d")String region);
}
