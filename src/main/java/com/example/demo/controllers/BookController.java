package com.example.demo.controllers;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
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

        return BookRepository.save(book);
    }

    @CrossOrigin
    @DeleteMapping("/books")
    public void delete(@RequestBody Book book) {
        BookRepository.delete(book);
    }


}
