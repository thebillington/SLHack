package trading_game;

import game.DailyInput;
import game.DailyOutput;
import game.TradingManager;
import game.BaseTradingStrategy;
import exceptions.InsufficientFundsException;
import exceptions.InsufficientSharesException;

import java.util.ArrayList;
import java.util.List;

public class TradingStrategy extends BaseTradingStrategy {

	List<List> companies = new ArrayList<List>();
	private double lastPurchasePrice;
	private double lastPurchaseDay;
	private boolean stockOwned = false;
	List<Boolean> volatileCompanies = new ArrayList<Boolean>();

	public TradingStrategy() {
		// Initialise any variables needed.

	}

	@Override
	public DailyOutput makeDailyTrade(DailyInput input) throws InsufficientFundsException, InsufficientSharesException {

		// Use the trading manager to make trades based on input.
		if (input.getDay() == 1) {
			companies.add(new ArrayList<DayInfo>());
			volatileCompanies.add(new Boolean(false));
		}

		List<DayInfo> companyInfo = companies.get(companies.size() - 1);
		companyInfo.add(new DayInfo(input.getHigh(), input.getLow(), input.getOpen(), input.getClose()));

		DailyOutput output;
		
		if(input.getDay() > 8) {
			
			List<DayInfo> lastWeek = companyInfo.subList(input.getDay() - 8, input.getDay() - 1);
			
			double vol = weekVolatility(lastWeek);
			
			if(vol > 0.05) {
				volatileCompanies.set(volatileCompanies.size()-1, new Boolean(true));
				if(weekIncrease(lastWeek) > 0.01) {
					output = tradingManager.buySharesOfValue(input, (int) (tradingManager.getAvailableFunds()*0.5));
					lastPurchasePrice = input.getClose();
					lastPurchaseDay = input.getDay();
					stockOwned = true;
				}
				else {
					if(input.getDay() - lastPurchaseDay > 7 && input.getClose() > lastPurchasePrice) {
						output = tradingManager.sellAllShares(input);
						stockOwned = false;
					}
					else {
						output = tradingManager.doNothing(input);
					}
				}
			}
			else if(volatileCompanies.get(volatileCompanies.size()-1).equals(new Boolean(false))) {
				if(weekIncrease(lastWeek) > 0.02) {
					output = tradingManager.buySharesOfValue(input, (int) (tradingManager.getAvailableFunds()*0.5));
					lastPurchasePrice = input.getClose();
					lastPurchaseDay = input.getDay();
					stockOwned = true;
				}
				else {
					if(input.getDay() - lastPurchaseDay > 7 && input.getClose() > lastPurchasePrice) {
						output = tradingManager.sellAllShares(input);
						stockOwned = false;
					}
					else {
						output = tradingManager.doNothing(input);
					}
				}
			}
			else {
				output = tradingManager.doNothing(input);
			}
			
		}
		else {
			output = tradingManager.doNothing(input);
		}
		if(input.getDay() - lastPurchaseDay > 8 && input.getClose() > lastPurchasePrice) {
			output = tradingManager.sellAllShares(input);
			stockOwned = false;
		}
		
		System.out.println(tradingManager.getAvailableFunds());
		
		return output;
	}
	
	private double weekVolatility(List<DayInfo> lastWeek) {
		double volatility = 0;
		for(DayInfo d : lastWeek) {
			volatility += d.getVolatitility();
		}
		return volatility / lastWeek.size();
	}
	
	private double weekIncrease(List<DayInfo> lastWeek) {
		double weekOpen = lastWeek.get(0).getOpen();
		double weekClose = lastWeek.get(lastWeek.size()-1).getClose();
		return weekClose - weekOpen;
	}

}
