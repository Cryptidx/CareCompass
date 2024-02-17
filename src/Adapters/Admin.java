package Adapters;

import entities.Centre;
import entities.Centre_log;
import entities.Shelter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin {
    // takes a type of centre and adds it to a centre log.

    Centre_log centreLog;
    public Admin(){
        this.centreLog = new Centre_log(new HashMap<>(), new HashMap<>());
    }
    public void register(Centre_log centrelog, Centre centre){
        // registers a centre. as we register, we add them to
        // places in need because they start off in need

        Map<String, Centre> new_centre = new HashMap<>();
        new_centre.put(centre.Centre_name, centre);

        centrelog.registered_centres.put(centre.centre_type, new_centre);

        if (centre.centre_type.equals("Shelter")){
            add_to_in_need(centre); // we are using shelter as place in need
        }
    }

    public void register_all(Centre_log centreLog, ArrayList<Centre> centres){
        for (Centre centre : centres) {
            register(centreLog, centre);
        }

    }
    public void add_to_in_need(Centre shelter){
        centreLog.centres_in_need.put(shelter.Centre_name, 1);
    }

    // aside from registering, they also update shelters items needed when someone donates

    public void update_shelter_donations(String item, Integer quantity, Shelter shelter){
        Integer old_value = shelter.items_needed.get(item);

        if (old_value == null){
            shelter.surplus.put(item, quantity);
            System.out.println("Thank you for your donation!");
        }

        else{
            if (quantity > old_value){
                shelter.items_needed.put(item, 0);  // greater than what they need. update to 0
                shelter.surplus.put(item,quantity - old_value);
            }
            else {
                int updated = old_value - quantity;
                shelter.items_needed.put(item, updated);
            }
        }

        // update the log
        Map<String, Centre> update_shelter = new HashMap<>();
        update_shelter.put(shelter.Centre_name, shelter);

        centreLog.registered_centres.put(shelter.centre_type, update_shelter);

        // update entre umber
        if (quantity > shelter.total_items){
            shelter.total_items = 0;
        }
        else{
            shelter.total_items -= quantity;
        }

        System.out.println("Thank you for your donation!");
    }

    public void update_shelter_volunteer(Shelter shelter, String event){
        int old = shelter.volunteer_numbers.get(event);

        if (old > 0){
            shelter.volunteer_numbers.put(event,old-1);
        }
    }

    public void update_need_status(Shelter shelter){
        if (shelter.total_items == 0){
            shelter.in_need = false;
        }
    }

    public void remove_from_in_need(Shelter shelter){
        if (!shelter.in_need){
            centreLog.centres_in_need.remove(shelter.Centre_name);
        }
    }
}
