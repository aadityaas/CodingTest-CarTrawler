package adi.com.cartrawler.util;

import java.util.Comparator;
import java.util.List;

import adi.com.cartrawler.model.CarResult;

public class ChainComparator implements Comparator<CarResult> {

	private List<Comparator<CarResult>> comparatorList;

	public ChainComparator(List<Comparator<CarResult>> comparatorList) {
		this.comparatorList = comparatorList;
	}

	@Override
	public int compare(CarResult car1, CarResult car2) {
		for (Comparator<CarResult> comp : comparatorList) {
			int result = comp.compare(car1, car2);

			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
