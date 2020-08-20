package com.example.multipledatabaseconnect;

import com.example.multipledatabaseconnect.service.dbi.EmployeeService;
import com.example.multipledatabaseconnect.service.dbo.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultipleDatabaseConnectApplication implements CommandLineRunner {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(MultipleDatabaseConnectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		employeeService.findAll().forEach(System.out::println);
		orderService.findAll().forEach(System.out::println);
	}
}
