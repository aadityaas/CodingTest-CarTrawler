package adi.com.cartrawler.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.springframework.stereotype.Service;

import adi.com.cartrawler.model.CarResult;
import adi.com.cartrawler.model.CarResult.FuelPolicy;
import adi.com.cartrawler.util.CarDetailsLoader;
import adi.com.cartrawler.util.CarTypeGroupComparator;
import adi.com.cartrawler.util.ChainComparator;
import adi.com.cartrawler.util.CorporateTypeComparator;

@Service
public class CarDetailSortingService {


	/**
	 * Removed duplicate entries in set by implementing equals method in CarResult
	 * Set does not allow any duplicate entry 
	 * This method is not doing anything, just returning the set of cars from Loader
	 * 
	 * @return
	 */
	public Set<CarResult> getCarsWithoutDuplicate() {
		return CarDetailsLoader.CARS;
	}
	
	
	/**
	 * Sort the list so that all corporate cars appear at the top. Note corporate cars are those supplied
	 * by AVIS, BUDGET, ENTERPRISE, FIREFLY, HERTZ, SIXT, THRIFTY.
	 *  
	 * @param cars
	 * @return
	 */
	public List<CarResult> sortByCorporateType(List<CarResult> cars){
		return cars.stream().sorted(new CorporateTypeComparator()).collect(Collectors.toList());
	}
	
	
	/**
	 * Within both the corporate and non-corporate groups, sort the cars into “mini”, “economy”,
	 * “compact” and “other” based on SIPP beginning with M, E, C respectively.
	 * 
	 * @param cars
	 * @return
	 */
	public List<CarResult> sortByCarType(List<CarResult> cars){

		List<Comparator<CarResult>> comparatorList = new ArrayList<>();
		comparatorList.add(new CorporateTypeComparator());
		comparatorList.add(new CarTypeGroupComparator());
		
		Collections.sort(cars, new ChainComparator(comparatorList));
		
		return cars;
	}
	
	/**
	 * Final method - Just calling this method will give us the result
	 * 
	 * Within each group sort low-to-high on price
	 * We will have to apply sorting on these 4 group
	 * 1- Corporate group with Mini, Economy, Compact cars
	 * 2- Corporate group without Mini, Economy, Compact cars
	 * 3- Non-Corporate group with Mini, Economy, Compact cars
	 * 4- Non-Corporate group without Mini, Economy, Compact cars
	 * 
	 * @param cars
	 * @return
	 */
	public List<CarResult> sortByCorporateTypeCarTypePrice(List<CarResult> cars){

		List<Comparator<CarResult>> comparatorList = new ArrayList<>();
		comparatorList.add(new CorporateTypeComparator());
		comparatorList.add(new CarTypeGroupComparator());
		comparatorList.add((car1, car2) -> car1.getRentalCost().compareTo(car2.getRentalCost()));
		
		Collections.sort(cars, new ChainComparator(comparatorList));
		
		return cars;
	}
	

	/**
	 * Functionality to remove all FuelType.FULLFULL cars that are priced above the median price within their groups
	 * @param cars
	 * @return
	 */
	public List<CarResult> getCarListAboveMedian(List<CarResult> cars){
		
		List<CarResult> carList = cars;
		
		//Separate whole list in two parts
		//1 - having fuleType FULLFULL
		List<CarResult> fullFuelTypeList = carList
				.stream()
				.filter(car -> FuelPolicy.FULLFULL.equals(car.getFuelPolicy()))
				.collect(Collectors.toList());
		
		//2 - having fuleType FULLEMPTY
		List<CarResult> emptyFuelTypeList = carList
				.stream()
				.filter(car -> FuelPolicy.FULLEMPTY.equals(car.getFuelPolicy()))
				.collect(Collectors.toList());
		
		//Get fuelPrice stream for fuelType FULLFULL
		DoubleStream fuelPrice = fullFuelTypeList
				.stream()
				.mapToDouble(CarResult::getRentalCost)
				.sorted();
		
		//Get the median value for fuelType FULLFULL
		Double median = fullFuelTypeList.size()%2 == 0?
				fuelPrice.skip(fullFuelTypeList.size()/2-1).limit(2).average().getAsDouble():        
					fuelPrice.skip(fullFuelTypeList.size()/2).findFirst().getAsDouble();
				
		System.out.println("Median value for Fuel Type FULLFULL is = "+median);
		
		//Join both lists
		//Filter first list so that it does not include rental cost above median value
		//Second list of Fuel Type FULLEMPTY as is
		List<CarResult> result = java.util.stream.Stream.concat(fullFuelTypeList.stream().filter(car -> car.getRentalCost() < median), 
				emptyFuelTypeList.stream()).collect(Collectors.toList());
				
		//Now apply sorting for CorporateType, CarType and Price
		return sortByCorporateTypeCarTypePrice(result);
		
	}
}
