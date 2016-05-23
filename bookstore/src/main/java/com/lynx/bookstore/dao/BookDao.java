package com.lynx.bookstore.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.lynx.bookstore.models.Book;

/**
 * Note that all included methods are not implemented and its working code will
 * be automatically generated from its signature by Spring Data JPA.
 */
@Transactional
public interface BookDao extends CrudRepository<Book, Long> {

	public Book save(Book book);

	public Book findOne(Long id);

	public Iterable<Book> findAll();

	public long count();

	public void delete(Book book);

	public boolean exists(Long id);

}