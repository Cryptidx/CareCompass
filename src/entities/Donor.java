package entities;

import Adapters.Admin;

public class Donor {
    public String name;
    public Integer Points = 0;
    public Integer Donate_score = 0;
    public Admin admin;

    public Donor(String name){
        this.name = name;
    }
    public void donate_item(String item, Integer quantity, Shelter shelter){
        // we will make some assumptions here
        // first we assume that the item exists in their items of need (they are picking from
        // a predetermined list)
        // and the quantity is feasible that we can reduce it)

        // Also the admin updates not the donor.

        admin.update_shelter(item,quantity,shelter);
        update_donate_score();
         // once you donate like 36 times so mod 36, you add 2 points to score

    }
    public void update_donate_score(){
        this.Donate_score += 6;
    }

    // should be under admin
    public void update_points(){
        if (this.Donate_score % 36 == 0){
            this.Points += 2;
        }
    }
}



