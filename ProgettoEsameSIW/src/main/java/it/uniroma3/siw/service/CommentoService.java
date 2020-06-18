package it.uniroma3.siw.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.repository.CommentoRepository;

@Service
public class CommentoService {

	@Autowired
	protected CommentoRepository commentoRepository;

	@Transactional
	public Commento getCommento(Long id) {
		Optional<Commento> result = this.commentoRepository.findById(id);
		return result.orElse(null);
	}

}
