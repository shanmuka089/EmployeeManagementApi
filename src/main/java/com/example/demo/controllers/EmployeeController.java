package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	
	@PostMapping("/employee")
	public ResponseEntity saveEmployee(@RequestBody Employee e) {
		System.out.println(e);
		empService.save(e);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmploye(@PathVariable("id")int id,Authentication authentication){
		User user=((User)authentication.getPrincipal());
		System.out.println(user.getUsername());
		Employee emp=empService.getEmployee(id);
		return new ResponseEntity<Employee>(emp,HttpStatus.OK);
	}
	
	
	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> getEmployee(){
		List<Employee> empList=empService.getEmployees();
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}
	
	@DeleteMapping("/employee/{emp}")
	public ResponseEntity deleteEmployee(@PathVariable("emp") int id) {
		empService.deleteEmployee(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PutMapping("/employee")
	public ResponseEntity updateEmployee(@RequestBody Employee e) {
		empService.updateEmployee(e);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
}
