package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
		CI CONSENTE DI ACCEDE ALLA PAGINA DI BENVENUTO
 */
@Controller
public class MainController {

	public MainController() {
	}

	/**
       Questo metodo viene chiamato quando una richiesta GET viene inviata dall'utente all'URL "/" o "/ index".
	 * Questo metodo prepara e invia la vista indice.
	 */

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}
}