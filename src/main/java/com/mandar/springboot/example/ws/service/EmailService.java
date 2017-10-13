/**
 * 
 */
package com.mandar.springboot.example.ws.service;

import java.util.concurrent.Future;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mandar.springboot.example.util.AsyncResponse;
import com.mandar.springboot.example.ws.model.Greeting;

/**
 * @author Mandar
 *
 */
@Service
public class EmailService implements IEmailService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Boolean send(Greeting greeting) {
		logger.info("> send");
		
		Boolean success = Boolean.FALSE;
		long pause = 5000;
		try{
			Thread.sleep(pause);
		}catch (Exception e){
			
		}
		logger.info("Processing time was {} seconds: ", pause/1000);
		
		success = Boolean.TRUE;
		logger.info("< send");
		return null;
	}

	@Async
	@Override
	public void sendAsync(Greeting greeting) {
		logger.info("> sendAsync");
		
		try{
			send(greeting);
		}catch (Exception e){
			logger.warn("Exception Caught sending asynchronous mail ", e);
		}
		logger.info("< sendAsync");
	}

	@Async
	@Override
	public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
		logger.info("> sendAsyncWithResult");
		AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();
		try{
			Boolean success = send(greeting);
			response.complete(success);			
		}catch (Exception e){
			logger.warn("Exception Caught sending asynchronous mail ", e);
			response.completeWithException(e);
		}
		logger.info("< sendAsyncWithResult");
		return response;
	}

}
