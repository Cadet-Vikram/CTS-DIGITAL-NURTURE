package com.cognizant.ormlearn.model;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Skill – JPA entity for Many-to-Many mapping exercise.
 *
 * Hands-on 6 (doc 2): @ManyToMany relationship between Employee and Skill.
 *
 * An Employee can have MANY Skills, and a Skill can belong to MANY Employees.
 * The join table is 'employee_skill' with columns es_em_id and es_sk_id.
 *
 * 'mappedBy = "skillList"' means Employee owns the relationship
 * (Employee defines the @JoinTable).
 */
@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sk_id")
    private int id;

    @Column(name = "sk_name")
    private String name;

    /**
     * Inverse side of the ManyToMany.
     * mappedBy = "skillList" refers to the 'skillList' field in Employee.
     */
    @ManyToMany(mappedBy = "skillList", fetch = FetchType.EAGER)
    private Set<Employee> employeeList;

    // ── Constructors ─────────────────────────────────────────────────
    public Skill() {}

    public Skill(String name) {
        this.name = name;
    }

    // ── Getters and Setters ──────────────────────────────────────────
    public int getId()   { return id; }
    public void setId(int id) { this.id = id; }

    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Employee> getEmployeeList()               { return employeeList; }
    public void setEmployeeList(Set<Employee> empList)   { this.employeeList = empList; }

    @Override
    public String toString() {
        return "Skill{id=" + id + ", name='" + name + "'}";
    }
}
