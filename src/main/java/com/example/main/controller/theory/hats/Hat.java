package com.example.main.controller.theory.hats;

import java.util.Objects;

public class Hat {

    private Long id;
    private String colour;
    private String brand;
    private String price;
    private String style;
    private String size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hat hat = (Hat) o;
        return Objects.equals(id, hat.id) && Objects.equals(colour, hat.colour) && Objects.equals(brand, hat.brand) && Objects.equals(price, hat.price) && Objects.equals(style, hat.style) && Objects.equals(size, hat.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, colour, brand, price, style, size);
    }
}
