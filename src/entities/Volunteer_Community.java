package entities;

import java.util.ArrayList;
import java.util.Map;

public class Volunteer_Community extends Centre{
    public Map<String, Volunteer> Volunteers;    // volunteer to hrs worked
    public int spots_left;

    public Map<String, Shelter> shelters_worked_with;

    public String event;    // cleaning and soup kitchen. This centre does cleaning!

    // we should also put some shelters that they work with initially for the
    // event they provide services for
    // and as we load shelters, we are updating the spots left to increase
    public Volunteer_Community(String name, String centre_type, String event, Map<String, Volunteer> volunteers,
                               Integer spots_left, Map<String, Shelter> shelters_worked_with,
    String location){

        super(name,centre_type,location);
        this.Volunteers = volunteers;
        this.spots_left = spots_left;   // will be the number of people in db that is needed for volunteering
        this.shelters_worked_with = shelters_worked_with;
        this.event = event;
    }

    // this is also not clean architecture.
    public void update_hours(Volunteer volunteer){
        //this.Volunteers.put(volunteer.name, volunteer.base_hours);
        volunteer.Hours += volunteer.base_hours;
    }

    public void update_cert_status(Volunteer volunteer){
        if (volunteer.Hours % 15 == 0){
            volunteer.set_certified(true);
        }
    }
    public void add_volunteer(Volunteer volunteer){
        Volunteers.put(volunteer.name,volunteer);
        this.spots_left -= 1;
    }
}
