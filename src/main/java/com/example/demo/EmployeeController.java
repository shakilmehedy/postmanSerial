package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final File employeeFile = new File("employee.json");
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee) {
        try {
            List<Employee> employees = new ArrayList<>();
            if (employeeFile.exists()) {
                employees = mapper.readValue(employeeFile, new
                        TypeReference<List<Employee>>() {
                        });
            }
            for (Employee existingEmployee : employees) {
                if
                (existingEmployee.getPassportNumber().equals(employee.getPassportNumber())) {
                    return "Error: Duplicate passport number!";
                }
            }
            employees.add(employee);
            mapper.writeValue(employeeFile, employees);
            return "Employee added successfully!";
        } catch (IOException e) {
            return "Error saving employee data: " + e.getMessage();
        }
    }
}
