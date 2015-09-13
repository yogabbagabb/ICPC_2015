package setOne;
import java.util.Scanner;
import java.math.MathContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RideToSchool {
	
	public static double charleySpeed, charleyTime, charleyDistance;
	public static final double MAX_DISTANCE = 4500;
	public static final double KPH_TO_MPS = 3.6;
	public static void main (String[]args)
	{
		int cases;
		Scanner stdin = new Scanner (System.in);
		java.util.List <Rider> riders = new java.util.ArrayList <Rider> ();
		
		while ((cases = stdin.nextInt()) != 0)
		{
			
			for (int i = 0; i < cases; i++)
			{
				double speed = KPH_TO_MPS * stdin.nextDouble();
				double timeStart = stdin.nextDouble();
				riders.add(new Rider(speed, timeStart));
			}
		
			RideToSchool.initializeCharley(riders);
			RideToSchool.calculatePositioning(riders);
		}
	}
	
	public static void initializeCharley(java.util.List<Rider> riders)
	{
		int index = -1;
		for (int i = 0; i < riders.size(); i++)
		{
			double riderTimeStart = riders.get(i).timeStart;
			double riderSpeed = riders.get(i).speed;
			
			if (riderTimeStart == 0 && riderSpeed > charleySpeed)
			{
				charleySpeed = riderSpeed;
				riders.remove(i);
			}
		}
		
		
	}
	public static void calculatePositioning(java.util.List <Rider> riders)
	{
		
		int quickestPasserbyIndex = -1;
		double minTime = 0;
		
		
		for (int i = 0; i < riders.size(); i++)
		{
				Rider selectedRider = riders.get(i);
				double deltaTime = selectedRider.timeStart - charleyTime;
				double deltaSpeed = selectedRider.speed - charleySpeed;
				
				if (deltaTime < 0 || deltaSpeed < 0)
					continue;
				
				double timeReach = (charleyDistance + deltaTime * charleySpeed)/(deltaSpeed);
				
				if (timeReach <= minTime && timeReach*charleySpeed + charleyDistance <= MAX_DISTANCE)
				{
					minTime = timeReach;
					quickestPasserbyIndex = i;
				}
		}
		
			
		if (quickestPasserbyIndex != -1)
		{
			Rider catchUpRider = riders.get(quickestPasserbyIndex);
			
			double speed = catchUpRider.speed;
			double timeStart = catchUpRider.timeStart;
			double deltaTime = timeStart - charleyTime;
			double deltaSpeed = speed - charleySpeed;
			
			double timeReach = (charleyDistance + deltaTime * charleySpeed)/(deltaSpeed);
			
			charleyTime = timeReach;
			charleyDistance = timeReach*charleySpeed + charleyDistance;
			charleySpeed = speed;
			
			riders.remove(quickestPasserbyIndex);
			RideToSchool.calculatePositioning(riders);
		}
			
		else
		{
			charleyTime += (MAX_DISTANCE - charleyDistance)/charleySpeed;
			MathContext context = new MathContext (RideToSchool.getOrder(charleyTime), RoundingMode.HALF_UP);
			BigDecimal time = new BigDecimal (charleyTime);
			time = time.round(context);
			System.out.println(time);
		}
		
	
	}
	
	public static int getOrder(double num)
	{
		int count = 0;
		int order = 1; 
		
		while ((int) num/order !=0)
		{
			order *= 10;
			count++;
		}
		
		return count;
	}
	
	
//	public static int getOrder(double value)
//	{
//		int count = 0;
//		int powerOfTen = 1;
//		while ((int)value/powerOfTen != 0)
//		{
//			count++;
//			powerOfTen *= 10;
//		}
//		
//		return count;
//	}
}

	

	class Rider 
	{
		public double speed;
		public double timeStart;
		
		public Rider (double a, double b)
		{
			speed = a;
			timeStart = b;
		}
		
	}
