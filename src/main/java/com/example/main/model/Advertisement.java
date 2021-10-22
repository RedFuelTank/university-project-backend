package com.example.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Advertisement {
  private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private int authorId;
  private double lat;
  private double lng;
  private String address;
  private String startDate;
  private String expirationDate;
  private Type type;

  public Advertisement(String title, String description, int authorId, double lat, double lng, String address, String expirationDate, Type type) {
    this.title = title;
    this.description = description;
    this.authorId = authorId;
    this.lat = lat;
    this.lng = lng;
    this.address = address;
    this.startDate = DTF.format(LocalDateTime.now());
    this.expirationDate = expirationDate;
    this.type = type;
  }

  public Advertisement() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public enum Type {
    REQUEST, OFFER
  }
}
