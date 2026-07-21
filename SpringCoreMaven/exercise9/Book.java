package com.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Book – JPA entity mapped to the "books" table.
 *
 * Exercise 9: Define Entities and Repositories.
 * Spring Data JPA + H2 will auto-create this table at startup
 * because spring.jpa.hibernate.ddl-auto=update in application.properties.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private double price;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Book() {}

    public Book(String title, String author, String isbn, double price) {
        this.title  = title;
        this.author = author;
        this.isbn   = isbn;
        this.price  = price;
    }

    // ── Getters and Setters ───────────────────────────────────────────────────

    public Long   getId()     { return id; }
    public void   setId(Long id) { this.id = id; }

    public String getTitle()  { return title; }
    public void   setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void   setAuthor(String author) { this.author = author; }

    public String getIsbn()   { return isbn; }
    public void   setIsbn(String isbn) { this.isbn = isbn; }

    public double getPrice()  { return price; }
    public void   setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author
                + "', isbn='" + isbn + "', price=" + price + "}";
    }
}
