package com.ibm.academia.apirest.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;

public class GenericServiceImpl<E, R extends CrudRepository<E, Integer>> implements IGenericService<E>{

	protected final R repository;
	
	public GenericServiceImpl(R repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Integer id) {
		if(!this.repository.findById(id).isPresent())
			throw new NotFoundException("This element doesn't exist.");
		else 
			return this.repository.findById(id);
	}

	@Override
	@Transactional
	public E save(E entity) {
		return this.repository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> findAll() {
		List<E> listAll = (List<E>)this.repository.findAll();
		if(listAll.isEmpty())
			throw new BadRequestException("There are no elements created previously.");
		return listAll;
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		this.repository.deleteById(id);
	}

}
