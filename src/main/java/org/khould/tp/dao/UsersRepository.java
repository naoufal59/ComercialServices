package org.khould.tp.dao;


import org.khould.tp.entities.Users;
import org.khould.tp.entities.employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long>{
	@Query("select u FROM Users u where u.username = :username and u.password = :password") 
	Users findUserByEmailAndPassword(@Param("username") String username , @Param("password") String password);
	
	Users findByUsername(String username);
}
