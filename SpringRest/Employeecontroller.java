package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.EmployeeService;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EmployeeController – doc3/doc4: REST endpoints for Employee resource.
 *
 *   GET    /employees       → getAllEmployees()
 *   PUT    /employees       → updateEmployee()    (doc4: @Valid + @RequestBody)
 *   DELETE /employees/{id}  → deleteEmployee()    (doc4: EmployeeNotFoundException → 404)
 *
 * Test with curl:
 *   curl -s -u user:pwd http://localhost:8083/employees
 *   curl -s -u user:pwd -X PUT -H 'Content-Type: application/json' \
 *        -d '{"id":1,"name":"Alice Updated","salary":80000,"permanent":true,...}' \
 *        http://localhost:8083/employees
 *   curl -s -u user:pwd -X DELETE http://localhost:8083/employees/2
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start getAllEmployees");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.debug("employees={}", employees);
        LOGGER.info("End getAllEmployees");
        return employees;
    }

    @PutMapping
    public void updateEmployee(@RequestBody @Valid Employee employee)
            throws EmployeeNotFoundException {
        LOGGER.info("Start updateEmployee");
        LOGGER.debug("employee={}", employee);
        employeeService.updateEmployee(employee);
        LOGGER.info("End updateEmployee");
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) throws EmployeeNotFoundException {
        LOGGER.info("Start deleteEmployee: id={}", id);
        employeeService.deleteEmployee(id);
        LOGGER.info("End deleteEmployee");
    }
}
