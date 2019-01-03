package org.khould.tp.dao;


import java.util.List;

import org.khould.tp.entities.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReparationRepository extends JpaRepository<Reparation, Long> {
	@Query("select rp from Reparation rp where rp.titre like :x")
	public List<Reparation> findByTitre(@Param("x")String mc);
}
