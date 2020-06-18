package it.uniroma3.siw.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String nome;


	private String descrizione;

	@Column
	private String dataCreazione;

	/**
	 * Timestamp for the instant this Task was created/loaded into the DB
	 */
	@Column(updatable = false, nullable = false)
	private LocalDateTime creationTimestamp;

	/**
	 * Timestamp for the last update of this Task into the DB
	 */
	@Column(nullable = false)
	private LocalDateTime lastUpdateTimestamp;

	@Column(nullable = false)
	private boolean completed;





	public Task() {}


	public Task(String nome , String descrizione , boolean completed) {
		this();
		this.nome=nome;
		this.descrizione=descrizione;
		this.completed=completed;
	}

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



	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
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
		result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
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
		Task other = (Task) obj;
		if (creationTimestamp == null) {
			if (other.creationTimestamp != null)
				return false;
		} else if (!creationTimestamp.equals(other.creationTimestamp))
			return false;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
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






}
