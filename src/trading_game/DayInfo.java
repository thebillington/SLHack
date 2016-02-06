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
	private double volatility;
	
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
		
		volatility = ((high - low) / open);
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

	public double getVolatitility() {
		return volatility;
	}
	
	public double getDifference() {
		return difference;
	}
	
}
