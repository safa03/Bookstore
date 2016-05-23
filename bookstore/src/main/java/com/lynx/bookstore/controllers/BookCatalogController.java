package com.lynx.bookstore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lynx.bookstore.dao.BookDao;
import com.lynx.bookstore.models.Book;

@RestController
public class BookCatalogController {

	@Autowired
	private BookDao bookDao;

	// private final AtomicLong counter = new AtomicLong();
	private List<Book> books = new ArrayList<Book>();

	/**
	 * This method lists all books stored in the DB, in GET mode. It can be
	 * called via : http://localhost:8080/book/
	 */
	@RequestMapping(value = "/book/", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listAllBooks() {
		// If the DB Table is empty, it returns 204 "No Content" Code

		books = (List<Book>) bookDao.findAll();

		if (books.isEmpty()) {
			return new ResponseEntity<List<Book>>(books, HttpStatus.NO_CONTENT);
		}
		// Returns the list of books with 200 "OK" Code
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	/**
	 * This method retrieve a single book with its ID, in GET mode. It can be
	 * called via : http://localhost:8080/book/{id}
	 */
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBook(@PathVariable("id") long id) {

		try {
			Book book = bookDao.findOne(id);
			if (book != null)
				// Returns the book with 200 "OK" Code
				return new ResponseEntity<Book>(book, HttpStatus.OK);
			else
				throw new Exception("Book null");
		} catch (Exception e) {
			// Returns 404 "Not found" Code
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method creates a new book with POST mode.
	 */
	@RequestMapping(value = "/book/", method = RequestMethod.POST)
	public ResponseEntity<Book> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {

		bookDao.save(book);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/book/{id}").buildAndExpand(book.getId()).toUri());
		// Creates the book and returns 201 "OK" Code
		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
	}

	/**
	 * This method update a book with PUT mode.
	 */
	@RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Book> updateUser(@PathVariable("id") long id, @RequestBody Book book) {

		try {
			// Fetch the book
			Book currentBook = bookDao.findOne(id);
			// update its values
			currentBook.setTitle(book.getTitle());
			currentBook.setAuthor(book.getAuthor());
			currentBook.setNumberPages(book.getNumberPages());
			currentBook.setCover(book.getCover());
			// Save it
			bookDao.save(currentBook);
			// Return the updated book with 200 "OK" Code
			return new ResponseEntity<Book>(currentBook, HttpStatus.OK);
		} catch (Exception e) {
			// Return 404 "Not Found" Code
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * This method deletes a book with DELETE mode.
	 */
	@RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Book> deleteUser(@PathVariable("id") long id) {

		try {
			Book book = bookDao.findOne(id);
			bookDao.delete(book);
			// Delete the book and return 204 "No Content" Code
			return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// Return 404 "Not Found" Code
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}

}
