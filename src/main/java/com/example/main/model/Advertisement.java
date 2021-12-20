package com.example.main.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@ToString
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Advertisement that = (Advertisement) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
