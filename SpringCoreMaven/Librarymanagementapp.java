package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * LibraryManagementApp – entry point for the classic Spring application.
 *
 * Loads the Spring ApplicationContext from applicationContext.xml and
 * exercises every configuration described in Exercises 1–8.
 *
 * Running this class verifies:
 *   ✔ Exercise 1  – Spring context loads; beans are created
 *   ✔ Exercise 2  – Setter injection: BookRepository wired into BookService
 *   ✔ Exercise 3  – @Around AOP advice prints execution times
 *   ✔ Exercise 5  – IoC container manages the bean lifecycle
 *   ✔ Exercise 7  – Both injection styles produce a working BookService
 *   ✔ Exercise 8  – @Before and @After AOP advice prints log messages
 */
public class LibraryManagementApp {

    public static void main(String[] args) {

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  Library Management System – Spring Core Demo");
        System.out.println("═══════════════════════════════════════════════════\n");

        // ── Load the Spring ApplicationContext (IoC container) ───────────────
        // ClassPathXmlApplicationContext reads applicationContext.xml from
        // src/main/resources and instantiates + wires all defined beans.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n───────────────────────────────────────────────────");
        System.out.println("  Exercise 2 & 5: Setter-Injected BookService");
        System.out.println("───────────────────────────────────────────────────");

        // Retrieve the setter-injected bean (id = "bookService")
        BookService bookService = (BookService) context.getBean("bookService");

        // Each call goes through the LoggingAspect AOP proxy:
        //   @Before logs entry, @Around measures time, @After logs exit
        bookService.listAllBooks();
        System.out.println();
        System.out.println("Book Details: " + bookService.getBookDetails("Clean Code"));
        System.out.println();
        bookService.addBook("Refactoring", "Martin Fowler");

        System.out.println("\n───────────────────────────────────────────────────");
        System.out.println("  Exercise 7: Constructor-Injected BookService");
        System.out.println("───────────────────────────────────────────────────");

        // Retrieve the constructor-injected bean (id = "bookServiceConstructorInjected")
        BookService bookServiceCI =
                (BookService) context.getBean("bookServiceConstructorInjected");

        bookServiceCI.listAllBooks();
        System.out.println();
        System.out.println("Book Details: "
                + bookServiceCI.getBookDetails("Effective Java"));

        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  Application completed successfully.");
        System.out.println("  Check the console output above to verify:");
        System.out.println("  [BookService] lines  → beans created and wired");
        System.out.println("  [AOP - Before] lines → Exercise 8 advice fired");
        System.out.println("  [AOP - Around] lines → Exercise 3 timing logged");
        System.out.println("  [AOP - After]  lines → Exercise 8 advice fired");
        System.out.println("═══════════════════════════════════════════════════");

        // Close the context to trigger bean lifecycle destroy callbacks
        ((ClassPathXmlApplicationContext) context).close();
    }
}
