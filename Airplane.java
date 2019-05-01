/*        Name: Josh Woolbright
 * Description: This code creates an Airplane object that is used in the Airport
 *              class. It allows you to differentiate between an airplane that is
 *              taking off and an airplane that is landing. It also allows you to compare
 *              planes based on fuel amounts.
 */           
import java.util.Random;

public class Airplane implements Comparable<Airplane>, Updatable<Airplane>
{
	private int fuelAmount;
	private int timeArrived;
	
	public Airplane(int landing, int timeArrived)
	{
		//The landing parameter distinguishes between a plane that is landing
		//and a plane taking off
		if (landing == 1)
		{
			Random random = new Random();
			fuelAmount = random.nextInt(11) + 6;
			this.timeArrived = timeArrived;
		}
		else
		{
			this.timeArrived = timeArrived;
		}
	}
	
	public int getFuelAmount()
	{
		return fuelAmount;
	}
	
	public int getTimeArrived()
	{
		return timeArrived;
	}
	
	@Override
	public void update()
	{
		fuelAmount--;
	}
	
	@Override
	public int compareTo(Airplane airplane) 
	{
		if (fuelAmount < airplane.getFuelAmount())
		{
			return -1;
		}
		else if (fuelAmount > airplane.getFuelAmount())
		{
			return 1;
		}
		else if (fuelAmount == airplane.getFuelAmount())
		{
			return 0;
		}
		else
		{
			throw new NullPointerException();
		}
	}
	
}
