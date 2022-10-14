package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Employee;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.repositories.Emprepository;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private Emprepository repo;

	@Override
	public Employee save(Employee e) {
		return repo.save(e);

	}

	@Override
	public List<Employee> getEmployees() {
		return repo.findAll();

	}

	@Override
	public Employee updateEmployee(Employee emp) {
		if (repo.existsById(emp.getEno())) {
			return repo.save(emp);
		} else {
			return null;
		}
	}

	@Override
	public void deleteEmployee(int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
		}else {
			throw new EmployeeNotFoundException("employee details not found");
		}
	}

	@Override
	public Employee getEmployee(int id) {
		
//		Optional<Employee> optionalEmployee = repo.findById(id);
//		Employee e=null;
//		if(optionalEmployee.isPresent()) {
//			e=optionalEmployee.get();
//		}else {
//			throw new EmployeeNotFoundException("employee deatils not found");
//		}
//		return e;
		
		Employee e=repo.findById(id).orElseThrow(()->new EmployeeNotFoundException("employee details not found"));
		return e;
	}

}
