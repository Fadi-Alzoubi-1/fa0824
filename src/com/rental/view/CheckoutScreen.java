package com.rental.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.rental.model.Tool;
import com.rental.repo.ToolRepo;
import com.rental.service.Checkout;
import com.rental.util.MyException;

public class CheckoutScreen {
	public static void main(String[] args) {

		Checkout checkout = new Checkout();

		try (Scanner sc = new Scanner(System.in)) {
			//Tool code
			String toolCode = null;
			boolean validToolCode = true;
			Tool tool = new Tool();
			ToolRepo toolRepo = new ToolRepo();
			do {
				validToolCode = true;
				try {
					System.out.println("Enter tool code: ");
					toolCode = sc.nextLine();
					tool = toolRepo.getTool(toolCode);
					if (tool == null) {
						throw new MyException(toolCode
								+ " is not valid tool code must be one of the following: LADW, CHNS, JAKD, JAKR");
					}

				} catch (MyException ex) {
					validToolCode = false;
					tool = new Tool();
					System.out.println(ex.getMessage());
				}
			} while (!validToolCode);
			//Checkout date
			boolean validCheckoutDate = true;
			String checkoutDate = "";

			do {
				validCheckoutDate = true;
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
				dateFormat.setLenient(false);
				try {
					System.out.println("Enter checkoutDate: ");
					checkoutDate = sc.next();
					Date parsedDate = dateFormat.parse(checkoutDate);
					if (parsedDate == null) {
						throw new MyException(
								checkoutDate + " is not valid; Checkout date must be in this format MM/dd/yy");
					}

				} catch (MyException | ParseException ex) {
					validCheckoutDate = false;
					System.out.println(ex.getMessage());
				}

			} while (!validCheckoutDate);
			//Rental days
			int rentalDays = 0;
			boolean validRentalDays = true;

			do {
				validRentalDays = true;

				try {

					System.out.println("Enter rental days: ");
					rentalDays = sc.nextInt();
					if (rentalDays < 1) {
						throw new MyException(rentalDays + " is not valid number; Rental days must be greater than 1.");
					}

				} catch (MyException ex) {
					validRentalDays = false;
					System.out.println(ex.getMessage());
				}

			} while (!validRentalDays);
			//Discount percent
			boolean validDiscountPercent = true;
			int discountPrecent = 0;
			do {
				validDiscountPercent = true;
				try {
					System.out.println("Enter discount perecent: ");
					discountPrecent = sc.nextInt();
					if (discountPrecent < 0 || discountPrecent > 100) {
						throw new MyException(discountPrecent
								+ " is not valid number; Discount perecent must be number between 0-100.");
					}

				} catch (MyException ex) {
					validDiscountPercent = false;
					System.out.println(ex.getMessage());
				}

			} while (!validDiscountPercent);


			try {
				checkout.applayCharges(tool, rentalDays, discountPrecent, checkoutDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}
