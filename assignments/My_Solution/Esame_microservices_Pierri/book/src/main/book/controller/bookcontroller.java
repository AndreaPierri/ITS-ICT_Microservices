package org.pierri.book.controller;

import lombok.extern.slf4j.Slf4j;
import org.pierri.book.model.Book;
import org.pierri.book.repo.BookRepository;
import org.pierri.book.services.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/v2/book")
public class BookController {

    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    TraceService traceService;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Book> getAllBooks() {
        List<Book> result = new ArrayList<Book>();
        Iterable<Book> iterable = bookRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void addBook(@RequestBody Book book) {
        bookRepository.save(book);
        System.out.println("BOOK ADDED SUCCESSFULLY: " + book.toString());
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public Book getBook(@PathVariable String bookId) {
        Optional bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            return (Book) bookOpt.get();
        } else {
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.POST)
    public Book editBook(@RequestBody Book book, @RequestBody String bookId) {
        return bookRepository.save(book);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable String bookId) {
        bookRepository.deleteById(bookId);
    }

   
}