package org.khould.tp.dao;

import java.util.List;

import org.khould.tp.entities.Categorie_services;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends JpaRepository<Service, Long> {
	@Query("select rp from Service rp where rp.titre like :x")
	public List<Service> findByTitre(@Param("x")String mc);
	
	List<Service> findByEntreprises(Entreprise ent);
}
