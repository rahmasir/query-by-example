package org.rahmasir.querybyexample.employee;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    // find all employees matching exact criteria
    public List<Employee> findEmployeeByExample(Employee employee) {
        Example<Employee> example = Example.of(employee);
        return employeeRepository.findAll(example);
    }

    // Find employees with custom matching rules
    public List<Employee> findEmployeesWithCustomMatcher(String firstName,
                                                         String department) {
        Employee employee = Employee.builder()
                .firstName(firstName)
                .department(department)
                .build();

        // Create a custom ExampleMatcher
        ExampleMatcher matcher = matching()
                .withIgnoreCase()                          // Ignore case for all string matches
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)// Use LIKE %value% for strings
                .withIgnoreNullValues()                    // Ignore null values
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatcher::exact) // But make firstName exact match
                .withMatcher("department", ExampleMatcher.GenericPropertyMatcher::contains); // Department can be partial

        Example<Employee> example = Example.of(employee, matcher);
        return employeeRepository.findAll(example);
    }

    // Count employees matching example
    public long countEmployeesByExample(Employee employee) {
        Example<Employee> example = Example.of(employee);
        return employeeRepository.count(example);
    }

    // Check if any employees match the example
    public boolean existsByExample(Employee employee) {
        Example<Employee> example = Example.of(employee);
        return employeeRepository.exists(example);
    }
}
