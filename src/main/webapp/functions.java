import java.util.Collections;

public class Fare implements Comparable <Fare> {
	
	public String transit_line_name ,  departure_time, arrival_time time , travel_time,   origin  , destination, train_number   ;
	float price  ;
	int num_stops  ;
	
	public int compareTo(Fare other)
	{
		return this.departure_time.compareTo(other.departure_time);
	}
}

public class Reservation
{
   
   double total_fare;
   String origin, destination,number,passenger,date;
   public Reservation (ArrayList<Fare> fares)
   {
	   Collections.sort(fares);
	   double total = 0;
	   for (Fare fare: fares ) total+= fare.price;
	   origin = fares[0].origin;
	   destination = fares[fares.length-1].destination;
   }
}