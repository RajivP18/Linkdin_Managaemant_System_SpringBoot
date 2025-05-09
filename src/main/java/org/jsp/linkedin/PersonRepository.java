package org.jsp.linkedin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Query("select p from Person p where p.email=:email and p.password=:password")
	Optional<Person> login(String email, String password);

}
