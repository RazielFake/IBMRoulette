package com.ibm.academia.apirest.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table(name = "persons", schema = "casino")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "tipo"
		)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Player.class, name = "player")
})
public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8327217992837684363L;

	/*
	
	
	
	
	
	

	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	

	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	private static final long serialVersionUID = 6483155343563193792L;
	*/
}
