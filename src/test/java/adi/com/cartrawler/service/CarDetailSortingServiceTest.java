package adi.com.cartrawler.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import adi.com.cartrawler.model.CarResult;
import adi.com.cartrawler.model.CarResult.FuelPolicy;

@SpringBootTest
class CarDetailSortingServiceTest {

	@Autowired
	private CarDetailSortingService service;

	private static List<CarResult> carList;

	@Test
	public void getCarsWithoutDuplicateTest() {
		assertEquals(239, service.getCarsWithoutDuplicate().size());
	}

	@Test
	public void getCarWithoutDuplicateExceptionTest() {

		assertThrows(IndexOutOfBoundsException.class, () -> {
			service.getCarsWithoutDuplicate().stream().collect(Collectors.toList()).get(239);
		});
	}

	@Test
	public void sortByCorporateTypeTest() {
		List<String> corporateGroup = new ArrayList<>();
		corporateGroup.addAll(Arrays.asList("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY"));

		Set<CarResult> carSet = service.getCarsWithoutDuplicate();
		carList = new ArrayList<>(carSet);

		List<CarResult> carListSorted = service.sortByCorporateType(carList);

		int counter = 0;
		// Assuming at least first 50 items belong to Corporate Group
		for (CarResult car : carListSorted) {
			assertTrue(corporateGroup.contains(car.getSupplierName()));
			counter++;

			if (counter >= 50) {
				break;
			}
		}
	}

	@Test
	public void sortByCarTypeTest() {
		Set<CarResult> carSet = service.getCarsWithoutDuplicate();
		carList = new ArrayList<>(carSet);

		List<CarResult> carListSorted = service.sortByCarType(carList);
		int counter = 0;

		// Assuming at least first 20 items belong to mini, economy, compact car type
		for (CarResult car : carListSorted) {
			assertTrue(car.getSippCode().startsWith("M") || car.getSippCode().startsWith("E")
					|| car.getSippCode().startsWith("C"));
			counter++;

			if (counter >= 20) {
				break;
			}
		}

	}

	@Test
	public void sortByCorporateTypeCarTypePriceTest() {

		List<String> corporateGroup = new ArrayList<>();
		corporateGroup.addAll(Arrays.asList("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY"));

		Set<CarResult> carSet = service.getCarsWithoutDuplicate();
		carList = new ArrayList<>(carSet);

		List<CarResult> carListSorted = service.sortByCorporateTypeCarTypePrice(carList);
		int counter = 0;

		// Assuming at least first 50 items belong to Corporate Group
		for (CarResult car : carListSorted) {
			assertTrue(corporateGroup.contains(car.getSupplierName()));
			counter++;

			if (counter >= 50) {
				break;
			}
		}

		counter = 0;

		// Assuming at least first 20 items belong to mini, economy, compact car type
		for (CarResult car : carListSorted) {
			assertTrue(car.getSippCode().startsWith("M") || car.getSippCode().startsWith("E")
					|| car.getSippCode().startsWith("C"));
			counter++;

			if (counter >= 20) {
				break;
			}
		}

		counter = 0;
		// Check if price at next index is higher than current index
		for (; counter <= 10; counter++) {
			assertTrue(carListSorted.get(counter).getRentalCost() < carListSorted.get(counter + 1).getRentalCost());
			counter++;

			if (counter >= 10) {
				break;
			}
		}

	}

	@Test
	public void getCarListAboveMedianTest() {

		Set<CarResult> carSet = service.getCarsWithoutDuplicate();
		carList = new ArrayList<>(carSet);

		List<CarResult> carListSorted = service.getCarListAboveMedian(carList);

		List<CarResult> carListFuelTypeFULLFULL = carListSorted.stream()
				.filter(car -> FuelPolicy.FULLFULL.equals(car.getFuelPolicy())).collect(Collectors.toList());

		// Rental price for cars having fuel policy FULLFULL should be more than median
		// price
		for (CarResult car : carListFuelTypeFULLFULL) {
			assertTrue(car.getRentalCost() <= 129.735);
		}

	}

}
