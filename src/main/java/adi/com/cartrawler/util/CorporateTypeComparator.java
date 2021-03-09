package adi.com.cartrawler.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import adi.com.cartrawler.model.CarResult;

public class CorporateTypeComparator implements Comparator<CarResult> {

	List<String> corporateCarType = new ArrayList<String>();

//	public enum SuppliersEnum {
//
//	    AVIS, BUDGET, CENTAURO, DELPASO, ENTERPRISE, FIREFLY, FLIZZR, GOLDCAR, HERTZ, MARBESOL, NIZA, RECORD, RHODIUM, SIXT, THRIFTY;
//
//	}

	public List<String> getCorporateType() {

		corporateCarType.addAll(Arrays.asList("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY"));
		return corporateCarType;

	}

	public int compare(CarResult car1, CarResult car2) {

		// Both are cars belong to corporate type, apply natural order
		if (getCorporateType().contains(car1.getSupplierName())
				&& getCorporateType().contains(car2.getSupplierName())) {
			return 0;
		}

		// First car only belong to corporate type
		if (getCorporateType().contains(car1.getSupplierName())
				&& !getCorporateType().contains(car2.getSupplierName())) {
			return -1;
		}

		// Second car only belong to corporate type
		if (!getCorporateType().contains(car1.getSupplierName())
				&& getCorporateType().contains(car2.getSupplierName())) {
			return 1;
		}

		// None of the card belong to corporate type cars, apply natural order
		return 0;
	}

}
