package com.rental.service.test;

import static org.junit.Assert.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import com.rental.model.Tool;
import com.rental.service.Checkout;

public class CheckoutTest {

	private Checkout checkout;
	private NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	private Currency usd = Currency.getInstance("USD");
	private Tool JAKR = new Tool("JAKR", "Jackhammer", "Ridgid");
	private Tool JAKD = new Tool("JAKD", "Jackhammer", "DeWalt");
	private Tool LADW = new Tool("LADW", "Ladder", "Werner");
	private Tool CHNS = new Tool("CHNS", "Chainsaw", "Stihl");

	@Before
	public void setUp() throws Exception {
		checkout = new Checkout();
		usdFormatter.setCurrency(usd);
	}

	@Test
	public void test1() throws ParseException {
		float finalCharge = checkout.applayCharges(JAKR, 5, 101, "9/3/15");
		assertTrue(usdFormatter.format(finalCharge).equals("-$0.06"));
	}

	@Test
	public void test2() throws ParseException {
		float finalCharge = checkout.applayCharges(LADW, 3, 10, "7/2/20");
		assertTrue(usdFormatter.format(finalCharge).equals("$3.58"));
	}

	@Test
	public void test3() throws ParseException {
		float finalCharge= checkout.applayCharges(CHNS, 5, 25, "7/2/15");	
		assertTrue(usdFormatter.format(finalCharge).equals("$3.35"));	
	}

	@Test
	public void test4() throws ParseException {
		float finalCharge= checkout.applayCharges(JAKD, 6, 0, "9/3/15");	
		assertTrue(usdFormatter.format(finalCharge).equals("$8.97"));
	}

	@Test
	public void test5() throws ParseException {	
		float finalCharge=  checkout.applayCharges(JAKR, 9, 0, "7/2/15");	
		assertTrue(usdFormatter.format(finalCharge).equals("$14.95"));
	}

	@Test
	public void test6() throws ParseException {
		float finalCharge= checkout.applayCharges(JAKR, 4, 50, "7/2/20");
		assertTrue(usdFormatter.format(finalCharge).equals("$1.50"));
	
	}

}
