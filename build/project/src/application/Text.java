package application;

import java.time.Duration;
import java.time.LocalDateTime;

public class Text {

	public static void main(String... arg)
	{
		LocalDateTime tempConnectionTime = LocalDateTime.now(); 
		try
		{
			Thread.sleep(50000);
		}
		catch(Exception e)
		{
			
		}
		
		LocalDateTime tempConnectionTime2 = LocalDateTime.now(); 
		Duration d = Duration.between( tempConnectionTime , tempConnectionTime2 );
		System.out.println(d.getSeconds());
	}
	
}
