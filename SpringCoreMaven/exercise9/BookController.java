package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController – REST controller that exposes CRUD endpoints for Book.
 *
 * Exercise 9: Create a REST Controller to handle CRUD operations.
 *
 * Base URL: /api/books
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │  Method  │  Endpoint              │  Description               │
 * ├──────────┼────────────────────────┼────────────────────────────┤
 * │  GET     │  /api/books            │  Get all books             │
 * │  GET     │  /api/books/{id}       │  Get book by ID            │
 * │  GET     │  /api/books/search     │  Search books by title     │
 * │  GET     │  /api/books/author     │  Get books by author       │
 * │  POST    │  /api/books            │  Add a new book            │
 * │  PUT     │  /api/books/{id}       │  Update an existing book   │
 * │  DELETE  │  /api/books/{id}       │  Delete a book             │
 * └─────────────────────────────────────────────────────────────────┘
 *
 * Test with curl or Postman:
 *   POST   localhost:8080/api/books
 *          Body: {"title":"Clean Code","author":"Robert C. Martin","isbn":"978-0132350884","price":39.99}
 *   GET    localhost:8080/api/books
 *   GET    localhost:8080/api/books/1
 *   PUT    localhost:8080/api/books/1
 *          Body: {"title":"Clean Code (2nd Ed)","author":"Robert C. Martin","isbn":"978-0132350884","price":44.99}
 *   DELETE localhost:8080/api/books/1
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ── GET all books ─────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // ── GET book by ID ────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)                          // 200 OK with body
                .orElse(ResponseEntity.notFound().build());       // 404 Not Found
    }

    // ── GET books by author ───────────────────────────────────────────────────

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @RequestParam String name) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(name));
    }

    // ── GET books by title keyword search ────────────────────────────────────

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchByTitle(keyword));
    }

    // ── POST – create a new book ──────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book saved = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);  // 201 Created
    }

    // ── PUT – update an existing book ─────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody Book bookDetails) {
        try {
            Book updated = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();   // 404 if ID not found
        }
    }

    // ── DELETE – remove a book ────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();      // 204 No Content
    }
}
