/**
 * 
 */
package com.mandar.springboot.example.ws.service;

import java.math.BigInteger;
import java.util.Collection;

import com.mandar.springboot.example.ws.model.Greeting;

/**
 * @author Mandar
 *
 */
public interface IGreetingService {
	
	public Collection<Greeting> findAll();
	
	public Greeting findById(BigInteger id);
	
	public Greeting createGreeting(Greeting greeting);
	
	public Greeting updateGreeting(Greeting greeting);
	
	public void deleteGreeting(BigInteger id);
	
	public void evictCache();
}
