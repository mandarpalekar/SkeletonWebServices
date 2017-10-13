/**
 * 
 */
package com.mandar.springboot.example.ws.service;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mandar.springboot.example.ws.dao.IGreetingDao;
import com.mandar.springboot.example.ws.model.Greeting;

/**
 * @author Mandar
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingService implements IGreetingService {

	@Autowired
	private IGreetingDao greetingDao;
	
	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingDao.findAll();
		return greetings;
	}

	
	@Override
	@Cacheable(value = "greetings", key = "#id")
	public Greeting findById(BigInteger id) {
		Greeting greeting = greetingDao.findOne(id.longValue());
		return greeting;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "#greeting.id")
	public Greeting createGreeting(Greeting greeting) {
		if(greeting.getId() != null){
			return null;
		}
		Greeting saveGreeting = greetingDao.save(greeting);
		if(saveGreeting.getId() == 5L){
			throw new RuntimeException("Rollback Transaction");
		}
		return saveGreeting;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "#greeting.id")
	public Greeting updateGreeting(Greeting greeting) {
		Greeting persistedGreeting = greetingDao.findOne(greeting.getId().longValue());
		if(persistedGreeting == null){
			return null;
		}
		Greeting updateGreeting = greetingDao.save(greeting);
		return updateGreeting;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "greetings", key = "#id")
	public void deleteGreeting(BigInteger id) {
		greetingDao.delete(id.longValue());

	}


	@Override
	@CacheEvict(value = "greetings", allEntries = true)
	public void evictCache() {
		
	}

}
