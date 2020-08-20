package com.example.multipledatabaseconnect.service.dbi;

import com.example.multipledatabaseconnect.entity.dbi.Employee;
import com.example.multipledatabaseconnect.repository.dbi.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
}
