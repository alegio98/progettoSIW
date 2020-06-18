package it.uniroma3.siw.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import it.uniroma3.siw.model.Project;
import it.uniroma3.siw.model.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

	public List<Project> findByOwner(User owner);
	
	public List<Project> findByMembers(User member);
	
	
}	
