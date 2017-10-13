package com.mandar.springboot.example.ws.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mandar.springboot.example.ws.model.Greeting;
import com.mandar.springboot.example.ws.service.IEmailService;
import com.mandar.springboot.example.ws.service.IGreetingService;

@RestController
public class GreetingController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IGreetingService greetingService;
	
	@Autowired
	private IEmailService emailService;
	
	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		Collection<Greeting> greetings = greetingService.findAll();
		return new ResponseEntity<Collection<Greeting>>(greetings,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id) {
		Greeting greeting = greetingService.findById(id);
		if(greeting == null){
			return new ResponseEntity<Greeting>(greeting,HttpStatus.NOT_FOUND);
		}else{
			return new ResponseEntity<Greeting>(greeting,HttpStatus.OK);
		}		
	}
	
	@RequestMapping(value = "/api/greetings", method = RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting){
		Greeting saveGreeting = greetingService.createGreeting(greeting);
		return new ResponseEntity<Greeting>(saveGreeting,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){
		Greeting updateGreeting = greetingService.updateGreeting(greeting);
		return new ResponseEntity<Greeting>(updateGreeting, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id){
		greetingService.deleteGreeting(id);
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);		
	}
	
	@RequestMapping(value = "/api/greetings/{id}/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") BigInteger id,
			@RequestParam(value = "wait",defaultValue = "false") boolean waitForAsyncResult){
		logger.info("> sendGreeting");
		Greeting greeting = null;
		try{
			greeting = greetingService.findById(id);
			if(greeting == null){
				logger.info("< sendGreeting");
				return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
			}
			if(waitForAsyncResult){
				Future<Boolean> asyncResponse  = emailService.sendAsyncWithResult(greeting);
				boolean emailSent = asyncResponse.get();
				logger.info("greeting email sent? {} ",emailSent);				
			}else{
				emailService.sendAsync(greeting);
			}
		}catch (Exception e){
			logger.error("Error occured sending the Greeting ", e);
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		logger.info("< sendGreeting");
		return new ResponseEntity<Greeting>(HttpStatus.OK);
	}
}
