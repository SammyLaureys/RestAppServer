package com.example.demo.controllers;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;

@RestController
public class BookController {

    @Autowired
    private BookRepository BookRepository;

    @GetMapping("/")
    public String home(){
        return "Spring server, go to /books";
    }

    @CrossOrigin
    @ApiOperation(value="find all the books that are stored in the database")
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        BookRepository.findAll().forEach(books::add);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return books;
    }

    @CrossOrigin
    @PostMapping("/books")
    public Book create(@RequestBody Book book){
        if(BookRepository.findByTitle(book.getTitle()).isPresent())
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Book with title %s already exists.", book.getTitle()));
        return BookRepository.save(book);
    }

    @CrossOrigin
    @PutMapping("/books/{id}")
    public Book edit(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> bookFromDb = BookRepository.findById(id);
        if (bookFromDb.isPresent()) {
            return BookRepository.save(book);
        }
        if (book.getId()!=id){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No book with id %s", book.getId()));
        }
        return null;
    }

    @CrossOrigin
    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> bookFromDb = BookRepository.findById(id);
        if(bookFromDb.isPresent()){
            BookRepository.deleteById(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No book with id %s", book.getId()));
        }
    }


}
