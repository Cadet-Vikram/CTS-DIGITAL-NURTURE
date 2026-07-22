package com.cognizant.ormlearn.model;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Department – JPA entity for O/R mapping exercises.
 *
 * Hands-on 3 (doc 2): Create payroll tables and bean mapping.
 * Hands-on 5 (doc 2): @OneToMany relationship between Department and Employee.
 *
 * A Department can have MANY Employees → @OneToMany.
 * 'mappedBy = "department"' means Employee owns the relationship
 * (Employee has the @ManyToOne + @JoinColumn pointing to Department).
 */
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dp_id")
    private int id;

    @Column(name = "dp_name")
    private String name;

    /**
     * Hands-on 5 (doc 2): One-to-Many relationship.
     *
     * FetchType.LAZY  (default) – employee list is NOT loaded with department.
     *   → LazyInitializationException if accessed outside a @Transactional method.
     * FetchType.EAGER – employee list IS loaded with department (used in HOL 5).
     *
     * Currently set to EAGER to demonstrate the OneToMany mapping working.
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employeeList;

    // ── Constructors ─────────────────────────────────────────────────
    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    // ── Getters and Setters ──────────────────────────────────────────
    public int getId()   { return id; }
    public void setId(int id) { this.id = id; }

    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Employee> getEmployeeList()                       { return employeeList; }
    public void setEmployeeList(Set<Employee> employeeList)      { this.employeeList = employeeList; }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }
}
