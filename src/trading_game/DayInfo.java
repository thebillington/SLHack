package trading_game;

import java.util.List;

public class DayInfo {
	
	//Variables to store information for any given day, for any given company
	private double high;
	private double low;
	private double open;
	private double close;
	private double difference;
	
	private int increase;
	private int volatitility;
	
	public DayInfo(double high, double low, double open, double close) {
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.difference = close - open;
		
		if(open > close) {
			increase = -1;
		}
		else if(open == close) {
			increase = 0;
		}
		else {
			increase = 1;
		}
		
		
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getOpen() {
		return open;
	}

	public double getClose() {
		return close;
	}

	public int getIncrease() {
		return increase;
	}

	public int getVolatitility() {
		return volatitility;
	}
	
	public double getDifference() {
		return difference;
	}
	
	static int overallVolatility(List<DayInfo> days) {
		int total = 0;
		for(DayInfo d : days){
			total += d.volatitility;
		}
		return total/days.size();
	}
	
}
