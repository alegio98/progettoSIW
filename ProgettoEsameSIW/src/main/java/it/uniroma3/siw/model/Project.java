package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String nome;

	private String dataInizio;

	private String descrizione;

	@OneToMany(fetch = FetchType.EAGER , cascade=CascadeType.ALL)
	@JoinColumn(name="project_id")
	private List<Task> tasks;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Tag> tags;

	@ManyToOne (fetch = FetchType.EAGER) // è EAGER di default ma metterlo lo rende più chiaro
	private User owner;    //ogni progetto ha un owner

	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> members;    //ogni progetto puo essere condivisio da piu utenti, lazy di default 


	public void addMember(User user) {
		if (!this.members.contains(user))
			this.members.add(user);
	}

	//aggiungere un Task ad un progetto
	public void addTask(Task task) {
		if(!this.tasks.contains(task))
			this.tasks.add(task);
	}


	public void addTag(Tag tag) {
		if(!this.tags.contains(tag))
			this.tags.add(tag);
	}


	public Project() {
		this.tags = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.members = new ArrayList<>();
	}

	public Project(String nome, String descrizione, String dataInizio) {
		this();
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataInizio=dataInizio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}




}

