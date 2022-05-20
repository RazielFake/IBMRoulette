package com.ibm.academia.apirest.model.services;

import java.util.List;
import java.util.Optional;



public interface IGenericService<E> {

	public Optional<E> findById(Integer id);
	public E save(E entity);
	public List<E> findAll();
	public void deleteById(Integer id);
}
