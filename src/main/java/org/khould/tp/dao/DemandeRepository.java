package org.khould.tp.dao;


import org.khould.tp.entities.demandes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  DemandeRepository   extends JpaRepository<demandes, Long> {

}
