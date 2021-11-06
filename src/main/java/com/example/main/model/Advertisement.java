package com.example.main.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement {
  private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NonNull private String title;
  @NonNull private String description;
  @NonNull private int authorId;
  @NonNull private double lat;
  @NonNull private double lng;
  @NonNull private String address;
  private String startDate;
  @NonNull private String expirationDate;
  @NonNull private Type type;

  public enum Type {
    REQUEST, OFFER
  }
}
