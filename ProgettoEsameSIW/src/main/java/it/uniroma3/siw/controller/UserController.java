package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserValidator userValidator;

	@Autowired
	CredentialsService credentialsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	SessionData sessionData;


	//GET PER VEDERE LA HOME
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser(); //prendo l'utente autenticato!
		model.addAttribute("user", loggedUser);
		return "home";
	}

	//GET PER PROFILO UTENTE
	@RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
	public String me(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("user", loggedUser);
		model.addAttribute("credentials", credentials);

		return "userProfile";
	}


	//GET PER ACCEDERE ALLA PAGINA ADMIN
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String admin(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		return "admin";
	}

	//GET PER ACCEDERE ALLA PAGINA ADMIN DI TUTTI GLI UTENTI
	@RequestMapping(value = { "/admin/users" }, method = RequestMethod.GET)
	public String usersList(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials= this.credentialsService.getAllCredentials();
		model.addAttribute("loggedUser", loggedUser);  //dati sull'admin
		model.addAttribute("credentialsList", allCredentials); //lista delle credenziali
		return "allUsers";
	}


	//CANCELLAZIONE UTENTE
	@RequestMapping(value= {"/admin/users/{username}/delete"}, method=RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		return "redirect:/admin/users";
	}


}
