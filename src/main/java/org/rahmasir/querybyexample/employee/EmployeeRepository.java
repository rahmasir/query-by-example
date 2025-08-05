package org.rahmasir.querybyexample.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QueryByExampleExecutor<Employee> {

    List<Employee> findAllByFirstName(String firstName);
    List<Employee> findAllByFirstNameAndLastName(String firstName, String lastName);
}
