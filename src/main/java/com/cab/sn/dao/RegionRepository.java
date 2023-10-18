package com.cab.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cab.sn.entities.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
	
	@Query("select r from Region r where r.libelleRegion like :x")
	public Region findByRegion(@Param("x")String region);

}
