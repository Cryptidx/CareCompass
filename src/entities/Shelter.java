package entities;

import java.util.HashMap;
import java.util.Map;

public class Shelter extends Centre {
    public boolean in_need = true;
    public Map<String, Integer> items_needed;

    public Integer total_items; // as i add the items that are needed, i increase the total item
    public Map<String, Integer> surplus;

    public Shelter(String name, String centre_type, Map<String, Integer> items_needed, Integer total_items,
                   String location){

        super(name, centre_type,location);
        this.items_needed = items_needed;
        this.total_items = total_items;     // we are assuming that the database gives us this total
        this.surplus = new HashMap<>();
    }

}
