package com.rental.service;

import java.util.Date;
import java.util.Locale;
import com.rental.model.Tool;
import com.rental.repo.ToolRepo;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;

public class Checkout {

	SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
	String dueDate;

	public float applayCharges(Tool tool, int rentalDays, int discountPercent, String checkoutDate) throws ParseException {


		ToolRepo toolRepo = new ToolRepo();
		
		float dailyRentalCharge = toolRepo.getToolPrice(tool.getToolType());
		int chargeDays = countChargeDays(tool, rentalDays, checkoutDate);
		float preDiscountCharge = chargeDays * dailyRentalCharge;
		float discountAmount = (float) (preDiscountCharge * discountPercent * 0.01);
		float finalCharge = preDiscountCharge - discountAmount;
		NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		Currency usd = Currency.getInstance("USD");
		usdFormatter.setCurrency(usd);
	
		System.out.println("--------------------Rental Agreement------------------");
		System.out.println("Tool code: " + tool.getToolCode());
		System.out.println("Tool type: " + tool.getToolType());
		System.out.println("Tool brand: " + tool.getToolBrand());
		System.out.println("Rental days: " + rentalDays);
		System.out.println("Checkout date: " + checkoutDate);
		System.out.println("Due date: " + this.dueDate);
		System.out.println("Daily rental charge: " + usdFormatter.format(dailyRentalCharge));
		System.out.println("Charge days: " + chargeDays);
		System.out.println("Pre-discount charge: " + usdFormatter.format(preDiscountCharge));
		System.out.println("Discount percent: " + discountPercent + "%");
		System.out.println("Discount amount: " + usdFormatter.format(discountAmount));
		System.out.println("Final charge: " + usdFormatter.format(finalCharge));
		System.out.println("|----------------------   End   ----------------------|");
		System.out.println("");
		return finalCharge;
	}

	private int countChargeDays(Tool tool, int rentalDays, String date) throws ParseException {
		int chargeDays = 0;
		Calendar calender = Calendar.getInstance();
		Date dteDate = new SimpleDateFormat("MM/dd/yy").parse(date);
		calender.setTime(dteDate);

		for (int i = 1; i <= rentalDays; i++) {
			calender.add(Calendar.DAY_OF_MONTH, 1);
			date = dateFormatter.format(calender.getTime());
			
			if (tool.getToolType().equalsIgnoreCase("ladder") && !isHoliday(date)) {
				chargeDays += 1;
			} else if (tool.getToolType().equalsIgnoreCase("chainsaw") && !isWeekEnd(date)) {
				chargeDays += 1;
			} else if (tool.getToolType().equalsIgnoreCase("jackhammer") && !isWeekEnd(date) && !isHoliday(date)) {
				chargeDays += 1;
			}

		}
		dueDate = date;
		return chargeDays;
	}

	private boolean isWeekEnd(String strRentalDate) throws ParseException {
		Calendar calender = Calendar.getInstance();
		Date dteDate = new SimpleDateFormat("MM/dd/yy").parse(strRentalDate);
		calender.setTime(dteDate);	
		if (calender.get(Calendar.DAY_OF_WEEK) == 7 || calender.get(Calendar.DAY_OF_WEEK) == 1) {
			return true;
		}
		return false;
	}

	private boolean isHoliday(String strRentalDate) throws ParseException {
		Calendar calender = Calendar.getInstance();
		Date dteDate = new SimpleDateFormat("MM/dd/yy").parse(strRentalDate);
		calender.setTime(dteDate);
		if (calender.get(Calendar.MONTH) == 6 && calender.get(Calendar.DAY_OF_MONTH) == 3
				&& (calender.get(Calendar.DAY_OF_WEEK) == 6)) {
			return true;
		} else if (calender.get(Calendar.MONTH) == 6 && calender.get(Calendar.DAY_OF_MONTH) == 5
				&& (calender.get(Calendar.DAY_OF_WEEK) == 2)) {
			return true;
		} else if (calender.get(Calendar.MONTH) == 6 && calender.get(Calendar.DAY_OF_MONTH) == 4
				&& !(calender.get(Calendar.DAY_OF_WEEK) == 7) && !(calender.get(Calendar.DAY_OF_WEEK) == 1)) {
			return true;
		} else if (calender.get(Calendar.MONTH) == 8 && calender.get(Calendar.DAY_OF_MONTH) <= 7
				&& calender.get(Calendar.DAY_OF_WEEK) == 2) {
			return true;
		}
		return false;
	}
}
