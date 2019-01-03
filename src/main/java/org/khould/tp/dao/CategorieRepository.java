package org.khould.tp.dao;

import java.util.List;

import org.khould.tp.entities.Categorie_services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategorieRepository extends JpaRepository<Categorie_services, Long>{
	@Query("select rp from Categorie_services rp where rp.name like :x")
	public List<Categorie_services> findByName(@Param("x")String mc);
}
