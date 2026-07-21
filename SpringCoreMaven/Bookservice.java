package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BookService – business-logic layer for the Library Management system.
 *
 * Exercises covered by this class:
 *
 *  Exercise 1  – Basic bean: Spring instantiates this class as a bean
 *                defined in applicationContext.xml.
 *
 *  Exercise 2  – Setter Injection: Spring calls setBookRepository()
 *                to wire the BookRepository dependency (XML: <property>).
 *
 *  Exercise 5  – IoC Container: Spring's IoC container manages the
 *                lifecycle and dependency graph of this bean.
 *
 *  Exercise 7  – Constructor Injection: a second bean definition in
 *                applicationContext.xml uses <constructor-arg> to
 *                inject BookRepository via the constructor below.
 *
 *  Exercise 6  – @Service annotation enables component-scanning to
 *                auto-detect this class without XML bean declaration.
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    // ── Exercise 7: Constructor Injection ───────────────────────────────────
    /**
     * Constructor injection – Spring uses this when the XML bean definition
     * has a {@code <constructor-arg ref="bookRepository"/>} element.
     *
     * Advantage: dependency is mandatory and the bean is immutable after
     * construction (preferred for required dependencies).
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] Instantiated via constructor injection.");
    }

    /**
     * No-arg constructor – required for setter injection and
     * for Spring to create a proxy (e.g., AOP).
     */
    public BookService() {
        System.out.println("[BookService] Instantiated via no-arg constructor.");
    }

    // ── Exercise 2 & 7: Setter Injection ────────────────────────────────────
    /**
     * Setter injection – Spring calls this when the XML bean definition
     * has a {@code <property name="bookRepository" ref="bookRepository"/>} element.
     *
     * Advantage: dependency is optional and can be changed after construction
     * (useful for optional or reconfigurable dependencies).
     *
     * @Autowired also lets Spring inject automatically in annotation-driven config.
     */
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] BookRepository injected via setter.");
    }

    // ── Service Methods ──────────────────────────────────────────────────────

    public String getBookDetails(String title) {
        System.out.println("[BookService] getBookDetails called for: " + title);
        return bookRepository.findBookByTitle(title);
    }

    public void addBook(String title, String author) {
        System.out.println("[BookService] addBook called – " + title + " by " + author);
        bookRepository.addBook(title, author);
    }

    public void listAllBooks() {
        System.out.println("[BookService] listAllBooks called");
        bookRepository.listAllBooks();
    }
}
