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
import it.uniroma3.siw.model.Task;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ProjectService;
import it.uniroma3.siw.service.TaskService;

@Controller
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	ProjectService projectService;

	@Autowired
	SessionData sessionData;



	//GET PER AGGIUNTA TASK AL PROGETTO
	@RequestMapping(value={"/add/task/{projectid}"}, method=RequestMethod.GET)
	public String insertTask(@PathVariable Long projectid, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectid);
		model.addAttribute("loggedUser",  loggedUser);
		model.addAttribute("projectForm", project);
		model.addAttribute("taskForm", new Task());
		return "insertTask";
	}


	//POST PER AGGIUNTA TASK AL PROGETTO
	@RequestMapping(value={"/add/task/{projectid}"}, method=RequestMethod.POST)
	public String validTask(@Valid @ModelAttribute("taskForm") Task task , BindingResult taskBindingResult, Model model,
			@PathVariable Long projectid) {

		User loggedUser = sessionData.getLoggedUser();

		Project project = this.projectService.getProject(projectid);
		project.addTask(task);
		this.projectService.saveProject(project);

		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", project);
		model.addAttribute("taskForm", task);
		return "insertionTaskSuccessful";

	}


	//CANCELLAZIONE TASK
	@RequestMapping(value= {"/project/task/{taskid}/delete"}, method=RequestMethod.POST)
	public String removeTask(Model model, @PathVariable Long taskid) {
		Task task = this.taskService.getTask(taskid);
		this.taskService.deleteTask(task);
		return "redirect:/listaProgetti";
	}


}
