package com.cognizant.ormlearn.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Employee – JPA entity demonstrating multiple O/R mapping types.
 *
 * Hands-on 3 (doc 2): Basic entity setup with fields and annotations.
 * Hands-on 4 (doc 2): @ManyToOne relationship with Department.
 * Hands-on 6 (doc 2): @ManyToMany relationship with Skill.
 * Hands-on 2 (doc 3): HQL query with 'permanent' field filter.
 *
 * Table: employee
 *   em_id            INT  PK  AUTO_INCREMENT
 *   em_name          VARCHAR(100)
 *   em_salary        DOUBLE
 *   em_permanent     BOOLEAN
 *   em_date_of_birth DATE
 *   em_dp_id         INT  FK → department(dp_id)
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "em_id")
    private int id;

    @Column(name = "em_name")
    private String name;

    @Column(name = "em_salary")
    private double salary;

    @Column(name = "em_permanent")
    private boolean permanent;

    @Column(name = "em_date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    // ── Hands-on 4 (doc 2): @ManyToOne – Employee belongs to one Department ──
    /**
     * ManyToOne: many employees can belong to one department.
     * @JoinColumn specifies the FK column in the employee table.
     *
     * Default FetchType for @ManyToOne is EAGER (JPA spec).
     * Hibernate will JOIN department when loading employee.
     */
    @ManyToOne
    @JoinColumn(name = "em_dp_id")
    private Department department;

    // ── Hands-on 6 (doc 2): @ManyToMany – Employee has many Skills ───────────
    /**
     * ManyToMany: an employee can have many skills; a skill can belong to many employees.
     * @JoinTable defines the join/bridge table.
     *   joinColumns        = FK from employee (this entity) → es_em_id
     *   inverseJoinColumns = FK from skill (other entity) → es_sk_id
     *
     * FetchType.EAGER ensures skills load with the employee (HOL 6).
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_skill",
        joinColumns        = @JoinColumn(name = "es_em_id"),
        inverseJoinColumns = @JoinColumn(name = "es_sk_id")
    )
    private Set<Skill> skillList;

    // ── Constructors ─────────────────────────────────────────────────
    public Employee() {}

    // ── Getters and Setters ──────────────────────────────────────────
    public int     getId()           { return id; }
    public void    setId(int id)     { this.id = id; }

    public String  getName()         { return name; }
    public void    setName(String n) { this.name = n; }

    public double  getSalary()             { return salary; }
    public void    setSalary(double s)     { this.salary = s; }

    public boolean isPermanent()           { return permanent; }
    public void    setPermanent(boolean p) { this.permanent = p; }

    public Date    getDateOfBirth()        { return dateOfBirth; }
    public void    setDateOfBirth(Date d)  { this.dateOfBirth = d; }

    public Department getDepartment()                 { return department; }
    public void       setDepartment(Department dept)  { this.department = dept; }

    public Set<Skill> getSkillList()                  { return skillList; }
    public void       setSkillList(Set<Skill> skills) { this.skillList = skills; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name
                + "', salary=" + salary
                + ", permanent=" + permanent + "}";
    }
}
