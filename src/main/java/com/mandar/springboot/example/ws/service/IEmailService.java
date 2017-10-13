/**
 * 
 */
package com.mandar.springboot.example.ws.service;

import java.util.concurrent.Future;

import com.mandar.springboot.example.ws.model.Greeting;

/**
 * @author Mandar
 *
 */
public interface IEmailService {
	
	Boolean send(Greeting greeting);
	
	void sendAsync(Greeting greeting);
	
	Future<Boolean> sendAsyncWithResult(Greeting greeting);
	

}
