package it.uniroma3.siw.validator;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;


@Component
public class UserValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Autowired
	UserService userService;

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		String nome = user.getNome().trim();
		String cognome = user.getCognome().trim();

		if (nome.isBlank())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");

		if (cognome.isBlank())
			errors.rejectValue("cognome", "required");
		else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("cognome", "size");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

}
