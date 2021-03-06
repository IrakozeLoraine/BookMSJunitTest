package com.example.BookMSJunitTest.model;

import javax.persistence.*;

@Entity
@Table(name="books")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String title;

    private String author;

    private int year;

    private String publisher;

    private double cost;


    public Book() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Book(int id, String title, String author, int year, String publisher, double cost) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.cost = cost;
    }

    public Book(String title, String author, int year, String publisher, double cost) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
