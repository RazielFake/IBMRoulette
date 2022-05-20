package com.ibm.academia.apirest.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibm.academia.apirest.enums.Color;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "bets", schema = "casino")
public class Bet implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "This field can not be null")
	@Min(value = 100, message = "The minimum bet is $100")
	@Max(value = 10000, message = "The maximum bet is $10000")
	@Positive(message = "This field must be above 0.")
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	
	@Min(value = 0, message = "Roulette range is between 0 and 36")
	@Max(value = 36, message = "Roulette range is between 0 and 36")
	@Column(name = "roulette_number")
	private Integer rouletteNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "roulette_color")
	private Color rouletteColor;
	
	
	@Min(value = 0, message = "This field must be 0 or above")
	//@NotNull(message = "This field can not be null")
	@Transient
	private Integer playerId;
	
	//@NotNull(message = "This field can not be null")
	@Min(value = 0, message = "This field must be 0 or above")
	@Transient
	private Integer rouletteId;
	
	@ToString.Exclude
	@ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "roulette_id", foreignKey = @ForeignKey(name = "FK_ROULETTE_ID"))
	@JsonIgnoreProperties({"hibernateLazyInitializer", "bets"})
	private Roulette roulette;
	
	@ToString.Exclude
	@ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "FK_PLAYER_ID"))
	@JsonIgnoreProperties({"hibernateLazyInitializer", "bets"})
	private Player player;
	
	@Column(name = "is_winner")
	private Boolean isWinner;
	
	@Column(name = "reward")
	private Double reward;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "modification_date")
	private Date modificationDate;
	
	public Bet(Integer id,  Double amount, Integer rouletteNumber, Integer playerId, Integer rouletteId) {
		this.id = id;
		this.amount = amount;
		this.rouletteNumber = rouletteNumber;
		this.playerId = playerId;
		this.rouletteId = rouletteId;
	}
	
	public Bet(Integer id,  Double amount, Color rouletteColor, Integer playerId, Integer rouletteId) {
		this.id = id;
		this.amount = amount;
		this.rouletteColor = rouletteColor;
		this.playerId = playerId;
		this.rouletteId = rouletteId;
	}
	
	public Bet(Integer id,  Double amount, Integer rouletteNumber, Color rouletteColor, Integer playerId, Integer rouletteId) {
		this.id = id;
		this.amount = amount;
		this.rouletteNumber = rouletteNumber;
		this.rouletteColor = rouletteColor;
		this.playerId = playerId;
		this.rouletteId = rouletteId;
	}
	
	
	
	@PrePersist
	private void prePersist() {
		this.creationDate = new Date();
	}
	
	@PreUpdate
	private void preUpdate() {
		this.modificationDate = new Date();
	}
	
	private static final long serialVersionUID = 615973380368763257L;

	
}
