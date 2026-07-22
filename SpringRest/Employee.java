package com.cognizant.springlearn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * Employee – plain POJO used for Spring XML config-based REST service.
 *
 * doc3: Loaded from employee.xml by EmployeeDao.
 * doc4: Bean validation annotations for PUT /employees request body.
 *
 * @JsonFormat: tells Jackson how to parse/format the dateOfBirth field.
 *   Input JSON:  {"dateOfBirth": "15/03/1990"}
 *   Java field:  java.util.Date
 */
public class Employee {

    @NotNull(message = "Employee id must not be null")
    private Integer id;

    @NotNull @NotBlank
    @Size(min = 1, max = 30, message = "Name must be 1-30 characters")
    private String name;

    @NotNull
    @Min(value = 0, message = "Salary must be zero or above")
    private Double salary;

    @NotNull(message = "Permanent flag must not be null")
    private Boolean permanent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Valid
    private Department department;

    @Valid
    private List<Skill> skills;

    // ── Constructors ─────────────────────────────────────────────
    public Employee() {}

    // ── Getters and Setters ──────────────────────────────────────
    public Integer    getId()               { return id; }
    public void       setId(Integer id)     { this.id = id; }

    public String     getName()             { return name; }
    public void       setName(String n)     { this.name = n; }

    public Double     getSalary()           { return salary; }
    public void       setSalary(Double s)   { this.salary = s; }

    public Boolean    isPermanent()         { return permanent; }
    public void       setPermanent(Boolean p){ this.permanent = p; }

    public Date       getDateOfBirth()      { return dateOfBirth; }
    public void       setDateOfBirth(Date d){ this.dateOfBirth = d; }

    public Department getDepartment()                 { return department; }
    public void       setDepartment(Department dept)  { this.department = dept; }

    public List<Skill> getSkills()                  { return skills; }
    public void        setSkills(List<Skill> skills) { this.skills = skills; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name
                + "', salary=" + salary + ", permanent=" + permanent + "}";
    }
}
