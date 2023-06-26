package CarSharing.entities;

import CarSharing.provided.TripStatus;


public class LongTermRate extends Rate{
    private int	baseAmount;
    private int	baseDuration = 1;
    private int	perDay;


    public LongTermRate(int baseAmount, int perDay){
        if (baseAmount > 0 && perDay > 1){
            this.baseAmount = baseAmount;
            this.perDay = perDay;
        }
    }

    public int total(Trip t){
        if (t.getStatus() == TripStatus.COMPLETED) {
            return baseAmount + ((t.duration() - baseDuration) * perDay);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "LongTermRate{" +
                "baseAmount=" + baseAmount +
                ", baseDuration=" + baseDuration +
                ", perDay=" + perDay +
                '}';
    }
}
