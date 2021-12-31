package com.example.main.controller.theory.skateboards;

import java.util.Objects;

public class Skateboard {

    private Long id;
    private String name;
    private String inStock;
    private String condition;
    private String price;
    private String designer;

    public Skateboard(Long id, String name, String inStock, String condition, String price, String designer) {
        super();
        this.id = id;
        this.name = name;
        this.inStock = inStock;
        this.condition = condition;
        this.price = price;
        this.designer = designer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skateboard that = (Skateboard) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(inStock, that.inStock) && Objects.equals(condition, that.condition) && Objects.equals(price, that.price) && Objects.equals(designer, that.designer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, inStock, condition, price, designer);
    }
}
