package entities;

import java.util.ArrayList;
import java.util.Map;

public class Centre_log {
    public Map<String, Map<String, Centre> > registered_centres;

    // this is for shelters in need actually. but could be extended to volunteer communities
    public Map<String,Integer> centres_in_need; // it can map to anything tbh,
    // however, we want to use map so removing it is constant time
}
