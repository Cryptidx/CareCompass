package entities;

import java.util.ArrayList;
import java.util.Map;

public class Volunteer_Community extends Centre{
    public Map<String, Float> Volunteers;    // volunteer to hrs worked
    public int spots_left;

    public ArrayList<Shelter> shelters_worked_with;

    public Volunteer_Community(String name, String centre_type, Map<String, Float> volunteers,
                               Integer spots_left, ArrayList<Shelter> shelters_worked_with,
    String location){

        super(name, centre_type,location);
        this.Volunteers = volunteers;
        this.spots_left = spots_left;
        this.shelters_worked_with = shelters_worked_with;
    }

    // this is also not clean architecture.
    public void update_hours(Volunteer volunteer){
        this.Volunteers.put(volunteer.name, volunteer.base_hours);
        volunteer.Hours += volunteer.base_hours;
    }

    public void update_cert_status(Volunteer volunteer){
        if (volunteer.Hours % 15 == 0){
            volunteer.set_certified(true);
        }
    }
}
