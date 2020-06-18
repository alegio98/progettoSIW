package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.model.Project;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ProjectService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.ProjectValidator;

@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectValidator projectValidator; 

	@Autowired
	UserService userService;

	@Autowired
	SessionData sessionData;

	//GET PER LA CREAZIONE DEL PROGETTO
	@RequestMapping(value= {"/projects/add"}, method=RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm" , new Project());
		return "addProject";
	}

	//POST PER LA CREAZIONE DEL PROGETTO
	@RequestMapping(value= {"/projects/add"}, method=RequestMethod.POST)
	public String validProject(@Valid @ModelAttribute("projectForm") Project project, BindingResult projectBindingResult,
			Model model) {

		User loggedUser = sessionData.getLoggedUser();

		projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {	
			project.setOwner(loggedUser); //si aggiunge l'utente autenticato alla lista
			project.addMember(loggedUser);
			this.projectService.saveProject(project);//salvo il progetto nel DB dopo aver indicato che l'utente loggato è effettivamente il proprietario (setOwner)
			model.addAttribute("projectForm", project);
			return "insertionSuccessful";
		}
		model.addAttribute("loggedUser", loggedUser);
		return "addProject";
	}





	//GET ALLA LISTA DI TUTTI I MIEI PROGETTI
	@RequestMapping(value = { "/listaProgetti" }, method = RequestMethod.GET)
	public String userProject(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = this.projectService.retrieveProjectsOwnedBy(loggedUser);//ottengo la lista dei progetti
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);//la passo come attributo nel modello
		return "listaProgetti";
	}		



	//GET PER VISUALIZZARE DATI DEL SINGOLO PROGETTO
	@RequestMapping(value = "/projects/{projectid}", method = RequestMethod.GET)
	public String project(@PathVariable Long projectid, Model model){
		//vedo se l'utente loggato ha diritto di vedere il project
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectid); //ottengo l'oggetto tramite l'id
		if(project == null) //se non esiste
			return "redirect:/listaProgetti";

		//se ho ottenuto un progetto ne prendo tutti i members e vedo se l'owner non è l'utente loggato e se l'utente loggato non è tra i membri di quel progetto ritorno projects
		List<User> members = userService.getMembers(project);
		if(!project.getOwner().equals(loggedUser) && !members.contains(loggedUser))
			return "redirect:/listaProgetti";

		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);

		return "singleProject";

	}

	//GET PER VEDERE TUTTI I PROGETTI DI TUTTI
	@RequestMapping(value = {"/user/share/projects"}, method = RequestMethod.GET)
	public String visibleProjectsUser(Model model) {

		User loggedUser = sessionData.getLoggedUser();

		List<Project> projects = this.projectService.getAllProjects();

		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsForm", projects);

		return "visibleProjectsShared";
	}


	//CANCELLAZIONE PROGETTO
	@RequestMapping(value= {"/projects/{projectid}/delete"}, method=RequestMethod.POST)
	public String removeProject(Model model, @PathVariable Long projectid) {
		Project project = this.projectService.getProject(projectid);	
		this.projectService.deleteProject(project);
		return "redirect:/listaProgetti";
	}





}
