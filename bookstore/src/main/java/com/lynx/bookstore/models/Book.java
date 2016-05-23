package com.lynx.bookstore.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    private String title;
    private String author;
    private int numberPages;
    private String cover;
    
    public Book() {
    	super();
        this.title = "";
        this.author = "";
        this.numberPages = 0;
        this.cover = "";
    }
    
    public Book(long id) {
		super();
		this.id = id;
	}

	public Book(String title, String author, int numberPages, String cover) {
		super();
		this.title = title;
		this.author = author;
		this.numberPages = numberPages;
		this.cover = cover;
	}
	
	public Book(long id, String title, String author, int numberPages, String cover) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.numberPages = numberPages;
		this.cover = cover;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getNumberPages() {
		return numberPages;
	}

	public String getCover() {
		return cover;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setNumberPages(int numberPages) {
		this.numberPages = numberPages;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
	}
	
}
