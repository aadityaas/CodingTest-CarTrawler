package adi.com.cartrawler.util;

import java.util.Comparator;

import adi.com.cartrawler.model.CarResult;

public class CarTypeGroupComparator implements Comparator<CarResult> {

	// Define CarType enum to group them
	public enum CarType {

		MINI('M'), ECONOMY('E'), COMPACT('C'), OTHER('O');

		private Character initialChar;

		private CarType(Character initialChar) {
			this.initialChar = initialChar;
		}

		public Character getInitialChar() {
			return initialChar;
		}

		public static CarType getCarType(Character value) {
			for (CarType carType : CarType.values()) {
				if (carType.initialChar.equals(value)) {
					return carType;
				}
			}

			return OTHER;
		}
	}

	public int compare(CarResult car1, CarResult car2) {

		// First car type belongs to group Mini or Economy or Compact but second car
		// doesn't
		if (!CarType.OTHER.equals(car1.getCarTypeGroup()) && CarType.OTHER.equals(car2.getCarTypeGroup())) {
			return -1;
		}

		// First car type does not belong to group Mini or Economy or Compact but second
		// car does
		if (CarType.OTHER.equals(car1.getCarTypeGroup()) && !CarType.OTHER.equals(car2.getCarTypeGroup())) {
			return 1;
		}

		// Both car type belong to group Mini or Economy or Compact
		if (!CarType.OTHER.equals(car1.getCarTypeGroup()) && !CarType.OTHER.equals(car2.getCarTypeGroup())) {
			return 0;
		}

		return 0;
	}
}
