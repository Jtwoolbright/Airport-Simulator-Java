/*        Name: Josh Woolbright
 * Description: This code simulates the activity on a runway for 
 *              1000 units of time and prints some interesting statistics.
 */
public class Main 
{
	public static void main(String[] args) 
	{
		 Airport airport = new Airport();
		 for (int i = 0; i < 1000; i++)
		 {
			 airport.update(i);
		 }
		 
		 airport.printStatistics();

	}
}
