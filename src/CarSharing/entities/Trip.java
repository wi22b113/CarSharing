package CarSharing.entities;

import CarSharing.provided.Car;
import CarSharing.provided.Customer;
import CarSharing.provided.DateTime;
import CarSharing.provided.Location;
import CarSharing.provided.TripStatus;




/**
 * A trip in the car sharing system.<br>
 * 
 * A trip collects information about time, location, customer and car involved.
 * It also contains a rate responsible for calculation of the total amount charged for
 * a trip.
 * 
 * Some operations' behavior depends on this trip's
 * {@link CarSharing.provided.TripStatus}.
 * 
 */
public class Trip implements Comparable<Trip> {

	private Car	car;
	private double distance;
	private Location endLocation;
	private DateTime endTime;
	private Rate rate;
	private Customer renter;
	private Location startLocation;
	private DateTime startTime;
	private TripStatus	status = TripStatus.CREATED;

	public Trip(Car car, Customer customer, Rate rate){
		if (car == null || customer == null || rate == null){
			throw new IllegalArgumentException("input invalid");
		}else {
			this.car = car;
			this.renter = customer;
			this.rate = rate;
		}

	}

	public Trip(Car car, Customer renter, Rate rate, Location startLocation, DateTime startTime){
		this(car,renter,rate);
		this.status = TripStatus.STARTED;
		this.startLocation = startLocation;
		this.startTime = startTime;
	}

	public Trip(Car car, Customer renter, Rate rate, Location startLocation, DateTime startTime, Location endLocation, DateTime endTime, double distance){
		this(car,renter,rate,startLocation,startTime);
		this.endLocation = endLocation;
		this.endTime = endTime;
		this.distance = distance;
		this.status = TripStatus.COMPLETED;
	}

	public Trip(Trip tr) {
		this.car = new Car(tr.car);
		this.distance = distance;
		this.endLocation = new Location(tr.endLocation);
		this.endTime = new DateTime(tr.endTime);
		this.rate = rate;
		this.renter = new Customer(tr.renter);
		this.startLocation = new Location(tr.startLocation);
		this.startTime = new DateTime(tr.startTime);
		this.status = status;
	}

	public Trip start(Location startLocation, DateTime startTime){
		if (startLocation == null || startTime == null) throw new IllegalArgumentException();
		if (status == TripStatus.COMPLETED || status == TripStatus.STARTED) throw new IllegalStateException();

		this.startLocation = startLocation;
		this.startTime = startTime;
		status = TripStatus.STARTED;
		return this;
	}

	public Trip end(Location endLocation, DateTime endTime, double distance){
		if (endLocation == null || endTime == null) throw new IllegalArgumentException();
		if (status != TripStatus.STARTED) throw new IllegalStateException();

		this.endTime = endTime;
		this.endLocation = endLocation;
		this.distance = distance;
		status = TripStatus.COMPLETED;

		return this;
	}

	public final int total(){
		return rate.total(this);
	}


	@Override
	public String toString() {
		return "Trip{" +
				"car=" + car +
				", distance=" + distance +
				", endLocation=" + endLocation +
				", endTime=" + endTime +
				", rate=" + rate +
				", renter=" + renter +
				", startLocation=" + startLocation +
				", startTime=" + startTime +
				", status=" + status +
				'}';
	}


	public int compareTo(Trip o) {
		if(o.getStatus()==TripStatus.CREATED){
			return -1;
		}
		if(startTime == null && o.startTime == null ){
			return 0;
		}
		if(startTime == null && o.startTime != null){
			return 1;
		}
		if(startTime != null && o.startTime == null) {
			return -1;
		}
		return startTime.compareTo(o.startTime);
	}

	public double getDistance() {
		return distance;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public Rate getRate() {
		return rate;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public TripStatus getStatus() {
		return status;
	}

	/**
	 * The duration of this trip in seconds.<br>
	 * 
	 * 
	 * 
	 * @ProgrammingProblem.Hint use {@link DateTime#diff(DateTime)}
	 * @return the difference in seconds if this trip is completed, zero otherwise
	 */
	public int duration() {
		if (status == TripStatus.COMPLETED)
			return (int) (startTime.diff(endTime));

		return 0;
	}
}
