package com.netcracker.server;

import com.netcracker.shared.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BookService {
    private List<Book> books = new ArrayList<>();
    private File source;
    private final int TAB_COUNT = 2;

    public BookService(String path) throws IOException {
        source = new File(path+"Books.xml");
        if(!source.exists()){
            source.createNewFile();
            addBook(new Book(0,"My First Book","Me",348,1996,System.currentTimeMillis()));
            addBook(new Book(1,"Second Book","Not me",215,1994,System.currentTimeMillis()));
            addBook(new Book(2,"1984","George Orwell",432,1953,System.currentTimeMillis()));
        }
        parseSource();
    }

    public void saveChanges() {
        try (PrintWriter printWriter = new PrintWriter(source)) {
            if (source.exists()) {
                source.delete();
            }
            source.createNewFile();

            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            printWriter.println("<books>");
            int[] ids=new int[1];
            ids[0]=0;
            books.forEach((book) -> {
                int tabs = 0;
                tabs++;
                printWriter.println(tab(tabs) + "<book>");
                tabs++;
                printWriter.print(tab(tabs) + "<id>");
                printWriter.print(" " + ids[0]);
                printWriter.println(" " + "</id>");

                ids[0]++;

                printWriter.print(tab(tabs) + "<name>");
                printWriter.print(" " + book.getName());
                printWriter.println(" " + "</name>");

                printWriter.print(tab(tabs) + "<author>");
                printWriter.print(" " + book.getAuthor());
                printWriter.println(" " + "</author>");

                printWriter.print(tab(tabs) + "<AmountList>");
                printWriter.print(" " + book.getKolStr());
                printWriter.println(" " + "</AmountList>");

                printWriter.print(tab(tabs) + "<year>");
                printWriter.print(" " + book.getYear());
                printWriter.println(" " + "</year>");

                printWriter.print(tab(tabs) + "<date>");
                printWriter.print(" " + book.getDate());
                printWriter.println(" " + "</date>");
                tabs--;
                printWriter.println(tab(tabs) + "</book>");
            });
            printWriter.print("</books>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book b) {
        books.add(b);
        saveChanges();
    }

    public List<Book> getList(){
        books.clear();
        parseSource();
        return books;
    }

    private void parseSource() {
        books.clear();
        try (Scanner scanner = new Scanner(source)) {
            while (scanner.hasNextLine()) {
                int id;
                String name;
                int quantity;
                double price;
                String authorName;
                int amountList;
                int year;
                long dateLong;
                Date date=new Date();
                if (scanner.nextLine().equals("  <book>")) {
                    id = Integer.parseInt(scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1]);
                    name = getNames(scanner.nextLine(), TAB_COUNT * 2 + 1,"</name>");
                    authorName = getNames(scanner.nextLine(), TAB_COUNT * 2 + 1,"</author>");
                    amountList = Integer.parseInt(scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1]);
                    year = Integer.parseInt(scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1]);
                    dateLong = Long.parseLong(scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1]);
                    books.add(new Book(id,name,authorName,amountList,year,dateLong));

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeBooks(Book book) {
        ArrayList<Book> selectedBooks = new ArrayList<>();
        books.remove(book);
        saveChanges();
        parseSource();
    }

    private String getNames(String line, int startIndex,String end) {
        StringBuilder result = new StringBuilder();
        String[] substrings = line.split(" ");
        for (int i = startIndex; i < substrings.length; i++) {
            if (end.equals(substrings[i])) break;
            result.append(substrings[i]);
            result.append(" ");
        }
        return result.toString().trim();
    }

    private String tab(int countTab) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < countTab; i++) {
            result.append("  ");
        }
        return result.toString();
    }


    public List<Book> getSortedList(Integer numSort){
        books.clear();
        parseSource();
        ArrayList<Book> sortedList=new ArrayList<>();
        sortedList.addAll(books);
        switch(numSort){
            case 1: sortedList.sort(Comparator.comparing(Book::getId)); break;
            case 2: sortedList.sort(Comparator.comparing(Book::getName)); break;
            case 3: sortedList.sort(Comparator.comparing(Book::getAuthor)); break;
            case 4: sortedList.sort(Comparator.comparing(Book::getYear)); break;
            case 5: sortedList.sort(Comparator.comparing(Book::getKolStr)); break;
            case 6: sortedList.sort(Comparator.comparing(Book::getDate)); break;
            case 10: sortedList.sort(Comparator.comparing(Book::getId).reversed()); break;
            case 20: sortedList.sort(Comparator.comparing(Book::getName).reversed()); break;
            case 30: sortedList.sort(Comparator.comparing(Book::getAuthor).reversed()); break;
            case 40: sortedList.sort(Comparator.comparing(Book::getYear).reversed()); break;
            case 50: sortedList.sort(Comparator.comparing(Book::getKolStr).reversed()); break;
            case 60: sortedList.sort(Comparator.comparing(Book::getDate).reversed()); break;
        }
        return sortedList;
    }

}
