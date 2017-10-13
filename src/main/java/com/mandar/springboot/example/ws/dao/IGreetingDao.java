package com.mandar.springboot.example.ws.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mandar.springboot.example.ws.model.Greeting;

@Repository
public interface IGreetingDao extends JpaRepository<Greeting, Long>{

}
