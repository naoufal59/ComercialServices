package org.khould.tp.dao;

import java.util.List;

import org.khould.tp.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository  extends JpaRepository<Entreprise, Long> {

	List<Entreprise> findByNomSociate(String motCle);
}
