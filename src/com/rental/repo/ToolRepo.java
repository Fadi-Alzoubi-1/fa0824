package com.rental.repo;

import com.rental.model.Tool;
import com.rental.util.Constatnts;

public class ToolRepo {
	
	public Tool getTool(String toolCode) {
		
		if(toolCode.equalsIgnoreCase("LADW")) {
			return new Tool("LADW", "Ladder", "Werner");	
		} else if(toolCode.equalsIgnoreCase("CHNS")) {
			return new Tool("CHNS", "Chainsaw", "Stihl");	
		} else if(toolCode.equalsIgnoreCase("JAKD")) {
			return new Tool("JAKD", "Jackhammer", "DeWalt");	
		} else if(toolCode.equalsIgnoreCase("JAKR")) {
			return  new Tool("JAKR", "Jackhammer", "Ridgid");
		}
		return null;	
	}
	
	public float getToolPrice(String toolType) {	 
		if(toolType.equalsIgnoreCase("ladder"))
			return Constatnts.lADDER_PRICE;
		else if(toolType.equalsIgnoreCase("chainsaw"))
			return Constatnts.CHAINSAW_PRICE;
		else if(toolType.equalsIgnoreCase("jackhammer"))
			return Constatnts.JACKHAMMER_PRICE;
		
		return 0;
	}
	

}
