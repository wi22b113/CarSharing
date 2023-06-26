package CarSharing.util;

import CarSharing.entities.Trip;
import CarSharing.provided.Matcher;
import CarSharing.provided.TripStatus;

public class StatusMatcher extends Matcher<Trip> {
    private TripStatus status;

    public StatusMatcher(TripStatus status){
        this.status = status;
    }

    @Override
    public boolean matches(Trip trip) {
        if (status == trip.getStatus()){
            return true;
        }
        return false;
    }
}
