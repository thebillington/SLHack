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
	
	public TradingStrategy (){
		// Initialise any variables needed.
		
		
	}
	
	@Override
	public DailyOutput makeDailyTrade(DailyInput input) throws InsufficientFundsException, InsufficientSharesException {
		
		// Use the trading manager to make trades based on input.	
		if(input.getDay() == 1){
			companies.add(new ArrayList<DayInfo>());
		}
		
		List<DayInfo> companyInfo = companies.get(companies.size()-1);
		companyInfo.add(new DayInfo(input.getHigh(), input.getLow(), input.getOpen(), input.getClose()));
		
		DailyOutput output;
		if (input.getDay() % 2 == 0) {
			output = tradingManager.buyMaxNumberOfShares(input);
		} else {
			output = tradingManager.sellAllShares(input);
		}
		
		return output;
	}
	
	
}
