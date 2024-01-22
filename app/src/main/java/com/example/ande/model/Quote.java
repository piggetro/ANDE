package com.example.ande.model;

public class Quote {
    private int quoteId;
    private String quote;
    private String author;
    private String category;

    public Quote() {
    }

    public Quote (String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public Quote(String quote, String author, String category) {
        this.quote = quote;
        this.author = author;
        this.category = category;
    }

    public Quote(int quoteId, String quote, String author, String category) {
        this.quoteId = quoteId;
        this.quote = quote;
        this.author = author;
        this.category = category;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
