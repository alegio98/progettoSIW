package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "users") 
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable =false)
	private String nome;

	@Column(nullable=false)
	private String cognome;

	@ManyToMany(mappedBy = "members")    
	private List<Project> visibleProjects;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="owner") //lista dei progetti di cui è proprietario l'utente
	private List<Project> ownedProjects;

	/**
	 * The date that this User was created/loaded into the DB
	 */
	@Column(updatable = false, nullable = false)
	private LocalDateTime creationTimestamp;

	/**
	 * The date of the last update for this User in the DB
	 */
	@Column(nullable = false)
	private LocalDateTime lastUpdateTimestamp;


	public User() {
		this.visibleProjects= new ArrayList<>();
		this.ownedProjects= new ArrayList<>();
	}

	public User(String userName , String password , String nome , String cognome) {
		this();
		this.nome=nome;
		this.cognome=cognome;
	}

	/**
	 * This method initializes the creationTimestamp and lastUpdateTimestamp of this User to the current instant.
	 * This method is called automatically just before the User is persisted thanks to the @PrePersist annotation.
	 */
	@PrePersist
	protected void onPersist() {
		this.creationTimestamp = LocalDateTime.now();
		this.lastUpdateTimestamp = LocalDateTime.now();
	}

	/**
	 * This method updates the lastUpdateTimestamp of this User to the current instant.
	 * This method is called automatically just before the User is updated thanks to the @PreUpdate annotation.
	 */
	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateTimestamp = LocalDateTime.now();
	}

	//METODI GET E SET 

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Project> getProjects() {
		return visibleProjects;
	}

	public void setProjects(List<Project> projects) {
		this.visibleProjects = projects;
	}

	public List<Project> getProjectsProprietario() {
		return ownedProjects;
	}

	public void setProjectsProprietario(List<Project> projectsProprietario) {
		this.ownedProjects = projectsProprietario;
	}

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public LocalDateTime getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
		result = prime * result + ((lastUpdateTimestamp == null) ? 0 : lastUpdateTimestamp.hashCode());
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
		User other = (User) obj;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (creationTimestamp == null) {
			if (other.creationTimestamp != null)
				return false;
		} else if (!creationTimestamp.equals(other.creationTimestamp))
			return false;
		if (lastUpdateTimestamp == null) {
			if (other.lastUpdateTimestamp != null)
				return false;
		} else if (!lastUpdateTimestamp.equals(other.lastUpdateTimestamp))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [nome=" + nome + ", cognome=" + cognome + ", creationTimestamp=" + creationTimestamp
				+ ", lastUpdateTimestamp=" + lastUpdateTimestamp + "]";
	}






}
