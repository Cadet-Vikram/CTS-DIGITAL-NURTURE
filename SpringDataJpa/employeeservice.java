package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EmployeeService – business logic for Employee operations.
 *
 * Hands-on 4 (doc 2): get() and save() for ManyToOne exercise.
 * Hands-on 2 (doc 3): getAllPermanentEmployees() using HQL.
 * Hands-on 4 (doc 3): getAverageSalary() using HQL AVG().
 * Hands-on 5 (doc 3): getAllEmployeesNative() using native SQL.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee get(int id) {
        LOGGER.info("Start get employee: id={}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
    }

    @Transactional
    public void save(Employee employee) {
        LOGGER.info("Start save employee: {}", employee);
        employeeRepository.save(employee);
        LOGGER.info("End save employee");
    }

    @Transactional
    public List<Employee> getAllPermanentEmployees() {
        LOGGER.info("Start getAllPermanentEmployees");
        return employeeRepository.getAllPermanentEmployees();
    }

    @Transactional
    public double getAverageSalary() {
        LOGGER.info("Start getAverageSalary");
        return employeeRepository.getAverageSalary();
    }

    @Transactional
    public double getAverageSalaryByDepartment(int departmentId) {
        LOGGER.info("Start getAverageSalaryByDepartment: deptId={}", departmentId);
        return employeeRepository.getAverageSalaryByDepartment(departmentId);
    }

    @Transactional
    public List<Employee> getAllEmployeesNative() {
        LOGGER.info("Start getAllEmployeesNative");
        return employeeRepository.getAllEmployeesNative();
    }
}
