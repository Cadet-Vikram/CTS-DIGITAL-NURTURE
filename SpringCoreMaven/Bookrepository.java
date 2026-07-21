package com.library.repository;

import org.springframework.stereotype.Repository;

/**
 * BookRepository – data-access layer for the Library Management system.
 *
 * @Repository (Exercise 6 – annotation-based config) marks this class
 * as a Spring-managed bean.  In Exercises 1–5 the bean is wired via
 * applicationContext.xml; in Exercise 6 component-scanning picks it up.
 */
@Repository
public class BookRepository {

    /**
     * Simulates fetching a book by title from a data store.
     * In a real application this would query a database.
     */
    public String findBookByTitle(String title) {
        // Simulated data store
        System.out.println("[BookRepository] findBookByTitle called with: " + title);
        return "\"" + title + "\" by J.K. Rowling – Available";
    }

    /**
     * Simulates saving a new book to the data store.
     */
    public void addBook(String title, String author) {
        System.out.println("[BookRepository] addBook called – Title: "
                + title + ", Author: " + author);
    }

    /**
     * Simulates retrieving all books from the data store.
     */
    public void listAllBooks() {
        System.out.println("[BookRepository] listAllBooks called");
        System.out.println("  1. Clean Code – Robert C. Martin");
        System.out.println("  2. Effective Java – Joshua Bloch");
        System.out.println("  3. Spring in Action – Craig Walls");
    }
}
