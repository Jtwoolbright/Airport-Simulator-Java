/*        Name: Josh Woolbright
 * Description: This code allows the user to create an Airport object that keeps track of planes that
 *              are taking off and landing from a runway. It includes a method which allows the user
 *              to print a report of some interesting statistics.
 */
import java.util.Random;

public class Airport 
{
	private Random random;
	private int planesWaitedTakeOff;
	private int planesWaitedLanding;
	private int totalTimeWaitedTakeOff;
	private int totalTimeWaitedLanding;          
	private int longestWaitTimeTakeOff;
	private int longestWaitTimeLanding;
	private int crashedPlanes; 
	private int totalPlanes;
	
	private int runWayCounter;  //Keeps track of the time each plane spends on the run way
	private int timeBetweenArrivals;  //Keeps track of when the next plane will arrive
	private boolean takeOff;  //Keeps track of whether the plane on the runway is landing or taking off
	
	private QueueInterface<Airplane> takeOffQueue;
	private PriorityQueue<Airplane> landingQueue;
	
	public Airport()
	{
		takeOffQueue = new LinkedQueue<>();
		landingQueue = new PriorityQueue<>();
		random = new Random();
		planesWaitedTakeOff = 0;
		planesWaitedLanding = 0;
		totalTimeWaitedTakeOff = 0;
		totalTimeWaitedLanding = 0;
		longestWaitTimeTakeOff = 0;
		longestWaitTimeLanding = 0;
		crashedPlanes = 0;
		totalPlanes = 0;
		runWayCounter = 0;
		timeBetweenArrivals = 0;
		takeOff = false;
	}
	
	public void update(int currentTime)
	{	
		runWayCounter--;
		timeBetweenArrivals--;

		if (!landingQueue.isEmpty())
		{
			landingQueue.updateValues();
			if (landingQueue.getFront().getFuelAmount() < 0)
			{
				landingQueue.dequeue();
				crashedPlanes++;
				runWayCounter = -1;
			}
		}
		
		if (runWayCounter <= 0) 
		{
			if (!landingQueue.isEmpty() && takeOff == false)
			{
				//Check if a plane needs to be dequeued from the landing queue
				if (runWayCounter == 0)
				{
					landingQueue.dequeue();
					totalPlanes++;
				}
				//Adds a plane to the runway from the landing queue 
				else
				{
					setWaitStats(1, landingQueue.getFront(), currentTime);
					runWayCounter = 2;
				}
			}
			else if (!takeOffQueue.isEmpty())
			{
				//Check if a plane needs to be dequeued from the takeOff queue
				if (takeOff == true && runWayCounter == 0)
				{
					takeOffQueue.dequeue();
					totalPlanes++;
					takeOff = false;
				}
				//Adds a plane to the runway from the takeOff queue
				else
				{
					setWaitStats(0, takeOffQueue.getFront(), currentTime);
					takeOff = true;
					runWayCounter = 3;
				}
			}
		}
		
		if (timeBetweenArrivals <= 0)
		{
			addPlane(currentTime);
			//Randomly decides when the next plane will arrive
			timeBetweenArrivals = random.nextInt(4) + 3;		
		}
		
	}
	
	private void setWaitStats(int landing, Airplane airplane, int currentTime)
	{
		int waitTime = 0;
		waitTime = currentTime - airplane.getTimeArrived();
		
		if (landing == 1)
		{
			if (waitTime > longestWaitTimeLanding)
			{
				longestWaitTimeLanding = waitTime;
			}
			totalTimeWaitedLanding += waitTime;
			planesWaitedLanding++;
		}
		else
		{
			if (waitTime > longestWaitTimeTakeOff)
			{
				longestWaitTimeTakeOff = waitTime;
			}
			totalTimeWaitedTakeOff += waitTime;
			planesWaitedTakeOff++;
		}
	}
	
	private void addPlane(int timeArrived)
	{
		//Randomly decides if the new plane is landing or taking off
		int landing = random.nextInt(2);
		Airplane airplane = new Airplane(landing, timeArrived);
		if (landing == 1)
		{
			//This prevents the new plane from taking priority
			//over a plane that is currently on the runway
			if (runWayCounter > 0 && takeOff == false)
			{
				landingQueue.enqueueAfterFront(airplane);
			}
			else
			{
				landingQueue.enqueue(airplane);
			}
		}
		else if (landing == 0)
		{
			takeOffQueue.enqueue(airplane);
		}
	}
	
	//The average wait time getters calculate wait time based off the amount of time
	//waited before accessing the runway and will include planes still on the runway
	//as well as crashed planes
	public double getAvgWaitTimeTakeOff()
	{
		return (double)totalTimeWaitedTakeOff / planesWaitedTakeOff;
	}
	
	public double getAvgWaitTimeLanding()
	{
		return (double)totalTimeWaitedLanding / planesWaitedLanding;
	}
	
	public int getPlanesOnRunWay()
	{
		return planesWaitedLanding + planesWaitedTakeOff - crashedPlanes - totalPlanes;
	}
	
	public void printStatistics()
	{
		System.out.println("Average time spent waiting for take off: " + getAvgWaitTimeTakeOff());
		System.out.println("Longest time spent waiting for take off: " + longestWaitTimeTakeOff);
		System.out.println("Average time spent waiting to land: " + getAvgWaitTimeLanding());
		System.out.println("Longest time spent waiting to land: " + longestWaitTimeLanding);
		System.out.println("Plane crashes: " + crashedPlanes);
		System.out.println("Total successful landings and takeoffs: " + totalPlanes);
		System.out.println("Planes currently on runway: " + getPlanesOnRunWay());
	}
}
