/**
 * 
 */
package com.mandar.springboot.example.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mandar.springboot.example.ws.model.Greeting;
import com.mandar.springboot.example.ws.service.IGreetingService;

/**
 * @author Mandar
 * 
 */
@Component
public class GreetingBatchBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IGreetingService greetingService;

	// @Scheduled(cron = "0,30 * * * * *")//0 11 47 * * ?
	public void cronJob() {
		logger.info("> cronJob");
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("There are {} greetings in the data store: ",greetings.size());
		logger.info("< cronJob");
	}

	//@Scheduled(initialDelay = 5000, fixedRate = 15000)
	public void fixedRateJobWithInitialDelay(){
		logger.info("> fixedRateJobWithInitialDelay");
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if(start + pause < System.currentTimeMillis()){
				break;
			}			
		}while(true);
		logger.info("Processing time was {} seconds: ", pause/1000);
		logger.info("< fixedRateJobWithInitialDelay");
	}
	
	//@Scheduled(initialDelay = 5000, fixedDelay = 15000)
	public void fixedDelayJobWithInitialDelay(){
		logger.info("> fixedDelayJobWithInitialDelay");
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if(start + pause < System.currentTimeMillis()){
				break;
			}			
		}while(true);
		logger.info("Processing time was {} seconds: ", pause/1000);
		logger.info("< fixedDelayJobWithInitialDelay");
	}
}
