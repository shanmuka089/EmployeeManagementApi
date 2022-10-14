package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Employee;


public interface EmployeeService {
	
	public Employee save(Employee e);
	public List<Employee> getEmployees();
	public Employee updateEmployee(Employee emp);
	public void deleteEmployee(int id);
	public Employee getEmployee(int id);

}
