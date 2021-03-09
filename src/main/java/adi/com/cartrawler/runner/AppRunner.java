package adi.com.cartrawler.runner;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import adi.com.cartrawler.model.CarResult;
import adi.com.cartrawler.service.CarDetailSortingService;
import adi.com.cartrawler.util.CarDetailsLoader;
import adi.com.cartrawler.view.Display;

@Component
public class AppRunner implements CommandLineRunner {

	@Autowired
	CarDetailSortingService service;

	@Override
	public void run(String... args) throws Exception {
		Display display = new Display();

		System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
		System.out.println("Number of unique car entries provided = " + CarDetailsLoader.CARS.size() + "\n");

		// Converting set to list
		List<CarResult> carList = service.getCarsWithoutDuplicate().stream().collect(Collectors.toList());

		// Retrieve list of cars sorted based on CarporateGroup, CarType and Price(Low
		// to high) in each group
		List<CarResult> finalCarList = service.sortByCorporateTypeCarTypePrice(carList);

		System.out.println("******* Listing down cars sorted by CarporateGroup, CarType, Price(low to high *******\n");
		display.render(new LinkedHashSet<CarResult>(finalCarList));

		// Retrieve list of cars sorted based on CarporateGroup, CarType and Price(Low
		// to high) in each group
		// AND not having any car of type FuelType.FULLFULL having rental cost more than
		// median value(129.735)
		List<CarResult> resultCarList = service.getCarListAboveMedian(carList);

		System.out.println("\n******* Listing down cars sorted by CarporateGroup, CarType, Price(low to high *******");
		System.out.println(
				"******* AND not having any car of type FuelType.FULLFULL having rental cost more than median value(129.735) *******\n");
		display.render(new LinkedHashSet<CarResult>(resultCarList));
	}

}
