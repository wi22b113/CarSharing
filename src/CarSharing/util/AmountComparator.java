package CarSharing.util;

import CarSharing.entities.Trip;

import java.util.Comparator;

public class AmountComparator implements Comparator<Trip> {


    @Override
    public int compare(Trip o1, Trip o2) {
        return Integer.compare(o1.total(),o2.total());
    }
}
