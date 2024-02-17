package entities;


public class Volunteer {
    public String name;
    public float Hours = (float) 0.0;

    public float base_hours;
    public boolean Certified = false;
    public Volunteer_Community volunteerCommunity;

    public Shelter shelter;

    public Volunteer(String name, Volunteer_Community volunteerCommunity,Shelter shelter, float base_hours){
        this.name = name;
        this.volunteerCommunity = volunteerCommunity;
        this.base_hours = base_hours;
        this.shelter = shelter;
    }

    public void clock_out(){
        // once clock out is clicked, the volunteer community updates the hours to
        // assume they did their base_hours
        volunteerCommunity.update_hours(this);
    }

    // this is not clean architectue.
    public void set_certified(boolean cert){
        this.Certified = cert;
    }


}
