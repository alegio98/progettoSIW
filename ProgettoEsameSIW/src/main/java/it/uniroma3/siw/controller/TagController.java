package it.uniroma3.siw.controller;

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
import it.uniroma3.siw.model.Tag;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ProjectService;

@Controller
public class TagController {

	@Autowired
	SessionData sessionData;

	@Autowired
	ProjectService projectService;


	//GET PER AGGIUNTA TAG AL PROGETTO
	@RequestMapping(value={"/add/tag/{projectid}"}, method=RequestMethod.GET)
	public String insertTask(@PathVariable Long projectid, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectid);
		model.addAttribute("loggedUser",  loggedUser);
		model.addAttribute("projectForm", project);
		model.addAttribute("tagForm", new Tag());
		return "insertTag";
	}

	//POST PER AGGIUNTA TAG AL PROGETTO
	@RequestMapping(value={"/add/tag/{projectid}"}, method=RequestMethod.POST)
	public String validTask(@Valid @ModelAttribute("tagForm") Tag tag , BindingResult tagBindingResult, Model model,
			@PathVariable Long projectid) {

		User loggedUser = sessionData.getLoggedUser();


		Project project = this.projectService.getProject(projectid);
		project.addTag(tag);
		this.projectService.saveProject(project);

		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", project);
		model.addAttribute("tagForm", tag);
		return "insertionTagSuccessful";

	}




}
