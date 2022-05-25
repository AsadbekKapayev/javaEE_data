package com.example.dataproject.model.book;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDAO {
    private static final String GET_BOOKS = "SELECT * FROM book";
    private static final String GET_FINE = "SELECT * FROM fine WHERE fine_id=?";
    private static final String GET_AUTHORS = "SELECT author.author_id, author.name FROM author INNER JOIN author_book ON author.author_id = author_book.author_id INNER JOIN book b on author_book.book_id = b.book_id WHERE b.book_id=?";
    private static final String GET_GENRES = "SELECT genre.genre_id, genre.name FROM genre INNER JOIN genre_book gb on genre.genre_id = gb.genre_id INNER JOIN book b on b.book_id = gb.book_id WHERE b.book_id=?";
    private static final String GET_BOOK = "SELECT * FROM book WHERE book_id=?";
    private static final String GET_ALL_GENRES = "SELECT * FROM genre";
    private static  final String GET_READER_DUBLICATES = "SELECT * FROM dublicate INNER JOIN reader r on r.reader_id = dublicate.reader_id WHERE r.reader_id=?";
    private static  final String GET_BOOK_DUPLICATES = "SELECT * FROM dublicate INNER JOIN book b on dublicate.book_id = b.book_id WHERE b.book_id=?";
    private static final String UPDATE_DUPLICATE = "UPDATE dublicate SET date= current_date, reader_id = ? WHERE dublicate_id=?";
    private static final String GET_FINES = "SELECT * FROM fine";
    private static final String CREATE_BOOK = "INSERT INTO book VALUES (?, ?, ?, ?)";
    private static final String GET_NEXT_ID = "SELECT id FROM ObjectIDs WHERE table_name=?";
    private static final String UPDATE_ID = "UPDATE ObjectIDs SET id=? WHERE table_name=?";
    private static final String CREATE_ID = "INSERT INTO ObjectIDs (table_name, id) VALUES (?, ?)";
    private static final String CREATE_AUTHOR = "INSERT INTO author VALUES (?, ?)";
    private static final String CREATE_AUTHOR_BOOK = "INSERT INTO author_book VALUES (?, ?)";
    private static final String CREATE_GENRE_BOOK = "INSERT INTO genre_book VALUES (?, ?)";
    private static final String CREATE_DUPLICATE = "INSERT INTO dublicate VALUES (?, ?)";
    private static final String RESET_DUPLICATE = "UPDATE dublicate SET reader_id=null WHERE dublicate_id=?";
    private static final String GET_BOOKS_ORDERED_NAME = "SELECT * FROM book ORDER BY name";
    private static final String GET_BOOKS_ORDERED_YEAR = "SELECT * FROM book ORDER BY year";
    private static final String GET_DUPLICATE = "SELECT * FROM dublicate WHERE dublicate_id=?";

    private static final String GET_BOOKS_PROCEDURES = "select * from some_function(%?%)";

    private DataSource dataSource;

    public BookDAO() {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup( "java:/comp/env/jdbc/postgres" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int duplicateId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(RESET_DUPLICATE);
            ps.setInt(1, duplicateId);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public List<Book> getBooks(String order) {
        List<Book> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            if (Objects.equals(order, "name")) {
                ps = con.prepareStatement(GET_BOOKS_ORDERED_NAME);
            } else if (Objects.equals(order, "year")) {
                ps = con.prepareStatement(GET_BOOKS_ORDERED_YEAR);
            } else {
                ps = con.prepareStatement(GET_BOOKS);
            }
            rs = ps.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setName(rs.getString("name"));
                book.setYear(rs.getString("year"));
                book.setFineId(rs.getInt("fine_id"));
                book.setImg(rs.getString("img_url"));
                books.add(book);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return books;
    }

    public List<Book> getBooksProcedure(String search) {
        List<Book> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_BOOKS_PROCEDURES);
            ps.setString(1, search);
            rs = ps.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setName(rs.getString("name"));
                book.setYear(rs.getString("year"));
                book.setFineId(rs.getInt("fine_id"));
                book.setImg(rs.getString("img_url"));
                books.add(book);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return books;
    }

    public void createDuplicate(int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            int id = getNextID("book");
            con = dataSource.getConnection();
            ps = con.prepareStatement(CREATE_DUPLICATE);
            ps.setInt(1, id);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public void updateDuplicate(int duplicateId, int readerId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(UPDATE_DUPLICATE);
            ps.setInt(1, readerId);
            ps.setInt(2, duplicateId);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public Fine getFine(int fineId) {
        Fine fine = new Fine();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_FINE);
            ps.setInt(1, fineId);
            rs = ps.executeQuery();

            rs.next();
            fine.setId(rs.getInt("fine_id"));
            fine.setType(rs.getString("type"));
            fine.setFine(rs.getInt("fine"));
            fine.setFineAdditional(rs.getInt("fine_additional"));
            fine.setIssuedDays(rs.getInt("issueddays"));
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return fine;
    }


    public int createBook(String name, String year, int fineId) {
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
        try {
            id = getNextID("book");
            con = dataSource.getConnection();
            ps = con.prepareStatement(CREATE_BOOK);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDate(3, Date.valueOf(year));
            ps.setInt(4, fineId);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return id;
    }

    public int createAuthor(String name) {
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
        try {
            id = getNextID("author");
            con = dataSource.getConnection();
            ps = con.prepareStatement(CREATE_AUTHOR);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return id;
    }

    public void createAuthorBook(int authorId, int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(CREATE_AUTHOR_BOOK);
            ps.setInt(1, authorId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public void createGenreBook(int genreId, int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(CREATE_GENRE_BOOK);
            ps.setInt(1, genreId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public Book getBook(int bookId) {
        Book book = new Book();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_BOOK);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            rs.next();
            book.setId(rs.getInt("book_id"));
            book.setName(rs.getString("name"));
            book.setYear(rs.getString("year"));
            book.setFineId(rs.getInt("fine_id"));
            book.setImg(rs.getString("img_url"));

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return book;
    }

    public Dublicate getDuplicate(int duplicateId) {
        Dublicate dublicate = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_DUPLICATE);
            ps.setInt(1, duplicateId);
            rs = ps.executeQuery();

            rs.next();
                dublicate = new Dublicate(
                        rs.getInt("dublicate_id"),
                        rs.getInt("book_id"),
                        rs.getInt("reader_id"),
                        rs.getString("date")
                );


        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return dublicate;
    }

    public List<Dublicate> getReaderDublicates(int readerId) {
        List<Dublicate> dublicates = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_READER_DUBLICATES);
            ps.setInt(1, readerId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Dublicate dublicate = new Dublicate(
                        rs.getInt("dublicate_id"),
                        rs.getInt("book_id"),
                        rs.getInt("reader_id"),
                        rs.getString("date")
                );
                dublicates.add(dublicate);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return dublicates;
    }

    public List<Dublicate> getBookDuplicate(int bookId) {
        List<Dublicate> dublicates = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_BOOK_DUPLICATES);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Dublicate dublicate = new Dublicate(
                        rs.getInt("dublicate_id"),
                        rs.getInt("book_id"),
                        rs.getInt("reader_id"),
                        rs.getString("date")
                );
                dublicates.add(dublicate);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return dublicates;
    }

    public List<Author> getAuthors(int bookId) {
        List<Author> authors = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_AUTHORS);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Author author = new Author(
                        rs.getInt("author_id"),
                        rs.getString("name")
                );
                authors.add(author);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return authors;
    }

    public List<Fine> getFines() {
        List<Fine> fines = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_FINES);
            rs = ps.executeQuery();
            while(rs.next()) {
                Fine fine = new Fine(
                    rs.getInt("fine_id"),
                    rs.getString("type"),
                    rs.getInt("fine"),
                    rs.getInt("fine_additional"),
                    rs.getInt("issueddays")
                );
                fines.add(fine);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return fines;
    }

    public List<Genre> getGenres(int bookId) {
        List<Genre> genres = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_GENRES);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Genre genre = new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("name")
                );
                genres.add(genre);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return genres;
    }

    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_ALL_GENRES);
            rs = ps.executeQuery();
            while(rs.next()) {
                Genre genre = new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("name")
                );
                genres.add(genre);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return genres;
    }

    public int getNextID(String tableName) {
        int id = 1;
        Connection con = null;
        PreparedStatement queryStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            queryStmt = con.prepareStatement(GET_NEXT_ID);
            queryStmt.setString(1, tableName);
            rs = queryStmt.executeQuery();
            if(rs.next()) {
                id = rs.getInt("id");
                updateStmt = con.prepareStatement(UPDATE_ID);
                updateStmt.setInt(1, id+1);
                updateStmt.setString(2, tableName);
                updateStmt.executeUpdate();
            } else {
                updateStmt = con.prepareStatement(CREATE_ID);
                updateStmt.setString(1, tableName);
                updateStmt.setInt(2, id+1);
                updateStmt.executeUpdate();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(queryStmt != null) {
                try { queryStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(updateStmt != null) {
                try { updateStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }

        return id;
    }

}
