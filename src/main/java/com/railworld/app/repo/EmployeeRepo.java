package com.railworld.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.railworld.app.entity.Employee;


@Repository
public interface EmployeeRepo  extends JpaRepository<Employee, Integer>{
         
	
	Optional<Employee> findByName(String name);
	       
}
