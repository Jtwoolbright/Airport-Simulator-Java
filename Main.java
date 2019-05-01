
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
