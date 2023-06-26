package CarSharing.app;

import CarSharing.entities.*;
import CarSharing.provided.*;
import CarSharing.util.AmountComparator;
import CarSharing.util.QuickFormat;
import CarSharing.util.StatusComparator;
import CarSharing.util.StatusMatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A demo app.
 */
public class Main {

	private static List<Car> cars;
	private static List<Customer> customers;
	private static List<Rate> rates;
	private static List<Trip> trips;

	/**
	 * Create some test data.
	 */
	static {
		cars = Arrays.asList(new Car("Mercedes", "450 SEL 6.9", "W-I8NY"), //
				new Car("Volvo", "960", "W-I8LA"), //
				new Car("Volvo", "850", "W-ACAB"), //
				new Car("Volvo", "760", "W-LOL"), //
				new Car("Chevrolet", "G30", "W-JAVA RULEZ") //
		);

		customers = Arrays.asList(new Customer("John Doe"), //
				new Customer("Jane Doe")//
		);

		rates = Arrays.asList(new LongTermRate(12000, 3000), //
				new LongTermRate(15000, 3000), //
				new LongTermRate(25000, 5000), //
				new LongTermRate(9000, 100), //
				new PerMinuteRate(40), //
				new PerMinuteRate(27), //
				new PerMinuteRate(75), //
				new PerMinuteRate(23) //
		);

		trips = Arrays.asList(

				new Trip(cars.get(2), customers.get(0), rates.get(0)) //
				, //

				new Trip(cars.get(3), customers.get(0), rates.get(1)). //
						start(new Location(48.238938, 16.378749), new DateTime(2021, 6, 23, 15, 17)) //
				, //

				new Trip(cars.get(0), customers.get(0), rates.get(2), //
						new Location(48.238938, 16.378749), new DateTime(2021, 1, 1, 0, 0))//
				, //

				new Trip(cars.get(4), customers.get(0), rates.get(3)). //
						start(new Location(48.238938, 16.378749), new DateTime(2021, 1, 1, 0, 0)). //
						end(new Location(48.20613882633994, 16.422068310841116), new DateTime(2021, 1, 1, 0, 23), 10.2)//
				, //

				new Trip(cars.get(1), customers.get(0), rates.get(4), //
						new Location(48.20613882633994, 16.422068310841116), new DateTime(2021, 1, 1, 15, 23), //
						new Location(48.238938, 16.378749), new DateTime(2021, 1, 3, 15, 17), 5.2) //
				, //

				new Trip(cars.get(0), customers.get(1), rates.get(5), //
						new Location(48.238938, 16.378749), new DateTime(2021, 05, 23, 11, 15)) //
				, //

				new Trip(cars.get(3), customers.get(0), rates.get(6)), //

				new Trip(cars.get(4), customers.get(0), rates.get(3)). //
						start(new Location(48.238938, 16.378749), new DateTime(2021, 1, 2, 3, 12)). //
						end(new Location(48.20613882637994, 16.422068310841416), new DateTime(2021, 1, 2, 3, 23), 10.2)//

		);

	}

	private static int export(List<Trip> trips, String fn){
		int cnt =0;
		try(BufferedWriter wr = new BufferedWriter(new FileWriter(fn))){
			for (Trip t : trips){
				wr.write(t.toString());
				cnt++;
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return cnt;
	}

	private static void tabularPrint(List<Trip> trips, Formatter<Trip> fmt){
		for(Trip t : trips) {
			System.out.println(fmt.format(t));
		}
	}

	private static List<Trip> filter(List<Trip> trips, Matcher<Trip> matcher){
		return trips.stream().filter(t -> matcher.matches(t)).collect(Collectors.toList());
	}

	/**
	 * A simple demo app which
	 *
	 * <ul>
	 * <li>prints the trips test data in tabular long format</li>
	 * <li>sorts the trips</li>
	 * <li>prints the trips in tabular long format again</li>
	 * <li>sorts the trips by amount charged</li>
	 * <li>prints the trips in tabular quick format again</li>
	 * <li>filters the trips by status, keeping only completed trips</li>
	 * <li>prints the completed trips in tabular long format again</li>
	 * <li>sorts the filtered trips by duration</li>
	 * <li>exports the filtered and sorted trips to file "completed_trips.txt"</li>
	 * <li>prints the number of trips exported</li>
	 * <li>provoke an exception in one of the trip methods using an existing trip
	 * object and <strong>handle</strong> that exception in main</li>
	 *
	 * </ul>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		tabularPrint(trips);
		Collections.sort(trips);
		tabularPrint(trips);
		Collections.sort(trips, new AmountComparator());
		tabularPrint(trips,new QuickFormat());
		List<Trip> filteredList = filter(trips, new StatusMatcher(TripStatus.COMPLETED));
		tabularPrint(filteredList);
		Collections.sort(filteredList,new DurationComparator());
		int exported = export(filteredList, "completed_trips.txt");
		System.out.printf("%d trips exported\n", exported);

		try{
			filteredList.get(2).start(new Location(48.238938, 16.378749), new DateTime(2021, 1, 2, 3, 12));
		}catch (IllegalStateException e){
			System.out.println("Das war wohl ein Fehler");
		}
	}


	private static void tabularPrint(List<Trip> trips) {
		System.out.printf("---\n");
		for (Trip t : trips) {
			System.out.printf("%s\n", t);
		}
		System.out.printf("---\n");
	}

}
