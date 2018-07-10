package org.iwish.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class BookReadingList {

    @Id
    @GeneratedValue
    private int id;

    private String bookTitle;
    private String isbn;
    private String bookThumbNailImage;

    private String readingStatus;

    @Basic
    private Date targetDateToComplete;

    @Basic
    private Date startedOn;

    @Basic
    private Date completedOn;

    @ManyToOne
    private User user;

    public BookReadingList() {

    }

    public int getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookThumbNailImage() {
        return bookThumbNailImage;
    }

    public void setBookThumbNailImage(String bookThumbNailImage) {
        this.bookThumbNailImage = bookThumbNailImage;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public Date getTargetDateToComplete() {
        return targetDateToComplete;
    }

    public void setTargetDateToComplete(Date targetDateToComplete) {
        this.targetDateToComplete = targetDateToComplete;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BookReadingList{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", isbn='" + isbn + '\'' +
                ", bookThumbNailImage='" + bookThumbNailImage + '\'' +
                ", readingStatus='" + readingStatus + '\'' +
                ", targetDateToComplete=" + targetDateToComplete +
                ", startedOn=" + startedOn +
                ", completedOn=" + completedOn +
                ", user=" + user +
                '}';
    }
}
