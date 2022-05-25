package com.example.dataproject.model.book;

import java.util.List;

public class BookLibrary {
    private BookDAO bookDAO;
    private List<Book> books;

    public BookLibrary() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getBooks(String order) {
        books = bookDAO.getBooks(order);
        for (Book book: books) {
            book.setAuthors(bookDAO.getAuthors(book.getId()));
            book.setGenres(bookDAO.getGenres(book.getId()));
        }
        return books;
    }

    public Dublicate getDuplicateById(int id) {
        return bookDAO.getDuplicate(id);
    }

    public List<Author> getAuthors(int id) {
        return bookDAO.getAuthors(id);
    }

    public List<Genre> getGenres(int id) {
        return bookDAO.getGenres(id);
    }

    public List<Dublicate> getDublicate(int id) {
        return bookDAO.getReaderDublicates(id);
    }

    public Book getBook(int id) {
        return bookDAO.getBook(id);
    }

    public Fine getFine(int id) {
        return bookDAO.getFine(id);
    }

    public List<Dublicate> getBookDuplicate(int id) {
        return bookDAO.getBookDuplicate(id);
    }

    public List<Fine> getFines() {
        return bookDAO.getFines();
    }

    public List<Genre> getAllGenres() {
        return bookDAO.getAllGenres();
    }

    public void updateDuplicate(int readerId, int duplicateId) {
        bookDAO.updateDuplicate(duplicateId, readerId);
    }

    public void createDuplicate(int bookId) {
        bookDAO.createDuplicate(bookId);
    }

    public int createBook(String name, String year, int fineId) {
        return bookDAO.createBook(name, year, fineId);
    }

    public int createAuthor(String authorName) {
        return bookDAO.createAuthor(authorName);
    }

    public void setAuthorToBook(int bookId, String authorName) {
        int authorId = createAuthor(authorName);
        bookDAO.createAuthorBook(authorId, bookId);
    }

    public void setGenreToBook(int genreId, int bookId) {
        bookDAO.createGenreBook(genreId, bookId);
    }

    public void returnBook(int duplicateId) {
        bookDAO.returnBook(duplicateId);
    }

    public List<Book> getBookProcedure(String search) {
        return bookDAO.getBooksProcedure(search);
    }
}
