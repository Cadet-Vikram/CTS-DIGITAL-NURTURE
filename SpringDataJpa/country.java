package com.cognizant.ormlearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Country – JPA entity mapped to the "country" table.
 *
 * Hands-on 1 (doc 1): Quick Example – demonstrates @Entity, @Table,
 * @Id, and @Column annotations to map a Java class to a DB table.
 *
 * Key annotations:
 *   @Entity  – marks this as a JPA-managed persistence class
 *   @Table   – specifies the database table name
 *   @Id      – marks the primary key field
 *   @Column  – maps the field to a specific column name
 *
 * Table structure (created by Hibernate from these annotations):
 *   co_code  VARCHAR(2)  PRIMARY KEY
 *   co_name  VARCHAR(50)
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "co_code")
    private String code;

    @Column(name = "co_name")
    private String name;

    // ── Constructors ────────────────────────────────────────────────
    public Country() {}

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // ── Getters and Setters ─────────────────────────────────────────
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name + "'}";
    }
}
