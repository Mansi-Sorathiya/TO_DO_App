package com.example.to_do_app;

public class Model {
    Integer id;
    String name;
    boolean selected;

    public Model(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    // Setter method for selection state
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
