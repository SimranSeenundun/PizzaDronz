package uk.ac.ed.inf;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Iterator;

public class Menu {
    private String name;
    private int price;

    public Menu(String name, int price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
