package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Tag;
import it.uniroma3.siw.repository.TagRepository;

@Service
public class TagService {


	@Autowired
	protected TagRepository tagRepository;

	@Transactional
	public Tag getTag(Long id) {
		Optional<Tag> result = this.tagRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Tag saveTag(Tag tag) {
		return this.tagRepository.save(tag);
	}


	@Transactional
	public void deleteTag(Tag tag) {
		this.tagRepository.delete(tag);
	}





}
