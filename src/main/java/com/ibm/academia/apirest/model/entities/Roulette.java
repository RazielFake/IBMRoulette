package com.ibm.academia.apirest.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibm.academia.apirest.enums.Color;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "roulettes", schema = "casino")
public class Roulette implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "is_open")
	private boolean isOpen;
	
	@Column(name = "number_winer")
	private Integer numberWiner;
	
	@Column(name = "color_winer")
	private Color colorWiner;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "roulette", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "roulette"})
	@Column(name = "bets")
	private Set<Bet> bets;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "modification_date")
	private Date modificationDate;
	

	public Roulette(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(colorWiner, id, isOpen, numberWiner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roulette other = (Roulette) obj;
		return colorWiner == other.colorWiner && Objects.equals(id, other.id) && isOpen == other.isOpen
				&& Objects.equals(numberWiner, other.numberWiner);
	}
	
	@PrePersist
	private void antesPersisitir() {
		this.creationDate = new Date();
	}
	
	@PreUpdate
	private void antesActualizar() {
		this.modificationDate = new Date();
	}

	private static final long serialVersionUID = 3483679098825696127L;


}
