package CarSharing.entities;

import CarSharing.provided.TripStatus;

public class PerMinuteRate extends Rate{

    private int perMinute;


    public PerMinuteRate(int perMinute){
        if (perMinute > 0){
            this.perMinute  = perMinute;
        }else throw new IllegalArgumentException();
    }


    @Override
    public int total(Trip trip) {
        if (trip.getStatus() == TripStatus.COMPLETED){
            return perMinute * trip.duration();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "PerMinuteRate{" +
                "perMinute=" + perMinute +
                '}';
    }
}
