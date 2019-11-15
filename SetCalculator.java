
public class SetCalculator {
	
	public int greyScale(int limit, int counter)
	{
		
		if(counter == limit)
		{
			return counter;
		}
		return greyScale(limit, counter++);
	}

}
