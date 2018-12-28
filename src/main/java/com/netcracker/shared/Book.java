package com.netcracker.shared;

import java.util.Objects;

public class Book {
    private int id;
    private String name;
    private String author;
    private int year;
    private int kolStr;
    private long date;

    public Book() {
    }


    public Book(String name, int kolStr) {
        this.name = name;
        this.kolStr = kolStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKolStr() {
        return kolStr;
    }

    public void setKolStr(int kolStr) {
        this.kolStr = kolStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public Book(int id, String name, String author, int kolStr, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.kolStr = kolStr;
    }

    public Book(int id, String name, String author, int kolStr, int year,long date) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.kolStr = kolStr;
        this.date = date;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", kolStr=" + kolStr +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                year == book.year &&
                kolStr == book.kolStr &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, author, year, kolStr);
    }
}
