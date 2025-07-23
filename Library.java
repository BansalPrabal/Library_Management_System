import java.util.*;
import java.io.*;

public class Library {
    private List<Book> books;
    private final String FILE_NAME = "books.txt";

    public Library() {
        books = new ArrayList<>();
        loadBooks();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        saveBooks();
        System.out.println("Book added successfully!");
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public void issueBook(int index) {
        if (index < 1 || index > books.size()) {
            System.out.println("Invalid book number.");
            return;
        }
        Book book = books.get(index - 1);
        if (book.isIssued()) {
            System.out.println("Book already issued.");
        } else {
            book.issueBook();
            saveBooks();
            System.out.println("Book issued successfully.");
        }
    }

    public void returnBook(int index) {
        if (index < 1 || index > books.size()) {
            System.out.println("Invalid book number.");
            return;
        }
        Book book = books.get(index - 1);
        if (!book.isIssued()) {
            System.out.println("Book is already available.");
        } else {
            book.returnBook();
            saveBooks();
            System.out.println("Book returned successfully.");
        }
    }

    private void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                writer.println(b.getTitle() + "," + b.getAuthor() + "," + b.isIssued());
            }
        } catch (IOException e) {
            System.out.println("Error saving books.");
        }
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    Book b = new Book(parts[0], parts[1]);
                    if (Boolean.parseBoolean(parts[2])) {
                        b.issueBook();
                    }
                    books.add(b);
                }
            }
        } catch (IOException e) {
            // File might not exist on first run — that's OK
        }
    }
}