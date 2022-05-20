package com.ibm.academia.apirest.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players", schema = "casino")
public class Player implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "This field can not be null")
	@NotEmpty(message = "This field can not be empty")
	@Size(min = 5, max = 80)
	@Column(name = "name", nullable = false, length = 80)
	private String name;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "modification_date")
	private Date modificationDate;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "player"})
	private Set<Bet> bets;

	public Player(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@PrePersist
	private void antesPersisitir() {
		this.creationDate = new Date();
	}
	
	@PreUpdate
	private void antesActualizar() {
		this.modificationDate = new Date();
	}

	private static final long serialVersionUID = -6066809075830761125L;
}
