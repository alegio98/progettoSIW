package it.uniroma3.siw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Project;

@Component
public class ProjectValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIPTION_LENGTH = 1000;



	@Override
	public void validate(Object o, Errors errors) {
		Project project = (Project)o;
		String nome = project.getNome().trim();
		String descrizione = project.getDescrizione().trim();

		if(nome.isBlank())
			errors.rejectValue("nome", "required");
		else if(nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");


		if(descrizione.length() > MAX_DESCRIPTION_LENGTH) 
			errors.rejectValue("descrizione", "size");
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}

}
