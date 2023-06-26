package CarSharing.util;

import CarSharing.entities.Trip;
import java.util.Comparator;

public class StatusComparator implements Comparator<Trip> {


    @Override
    public int compare(Trip o1, Trip o2) {
        return o1.getStatus().compareTo(o2.getStatus());
    }
}
