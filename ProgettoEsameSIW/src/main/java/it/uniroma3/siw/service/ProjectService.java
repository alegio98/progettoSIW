package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Project;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ProjectRepository;



@Service
public class ProjectService {

	@Autowired
	protected ProjectRepository projectRepository;

	@Transactional
	public Project getProject(Long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}


	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
	}

	@Transactional
	public List<Project> getAllProjects(){
		Iterable<Project> iterable = this.projectRepository.findAll();
		List<Project> result = new ArrayList<>();
		for(Project it : iterable) {
			result.add(it);
		}
		return result;
	}


	@Transactional
	public void deleteProject( Project project) {
		this.projectRepository.delete(project);
	}


	@Transactional
	public Project shareProjectWithUser(Project project , User user) {
		project.addMember(user);  //l'associazione tra member e user la mettiamo visibleProject in questo caso
		return this.projectRepository.save(project);
	}




	@Transactional
	public List<Project> getProjectsSharedWithMe(Project project, User me){
		List<Project> result = new ArrayList<>();
		for(Project iterable : this.projectRepository.findByOwner(me))
			result.add(iterable);
		return result;
	}


	public List<Project> retrieveProjectsOwnedBy(User member) {
		List<Project> lista = new ArrayList<>();
		for (Project iterable : this.projectRepository.findByOwner(member))
			lista.add(iterable);
		return lista;
	}

	public List<Project> getVisibleProjects(User user){
		List<Project> result = new ArrayList<>();
		result = this.projectRepository.findByMembers(user);
		return result;
	}
}
