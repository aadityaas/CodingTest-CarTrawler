package adi.com.cartrawler.model;

import org.apache.commons.lang3.StringUtils;

import adi.com.cartrawler.util.CarTypeGroupComparator.CarType;

public class CarResult {
	private final String description;
	private final String supplierName;
	private final String sippCode;
	private final Double rentalCost;
	private final FuelPolicy fuelPolicy;

	public enum FuelPolicy {
		FULLFULL, FULLEMPTY
	};

	public CarResult(String description, String supplierName, String sipp, double cost, FuelPolicy fuelPolicy) {
		this.description = description;
		this.supplierName = supplierName;
		this.sippCode = sipp;
		this.rentalCost = cost;
		this.fuelPolicy = fuelPolicy;
	}

	public String getDescription() {
		return this.description;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public String getSippCode() {
		return this.sippCode;
	}

	public Double getRentalCost() {
		return this.rentalCost;
	}

	public FuelPolicy getFuelPolicy() {
		return this.fuelPolicy;
	}

	// Not including rental cost as that's not in the requirement
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((fuelPolicy == null) ? 0 : fuelPolicy.hashCode());
		result = prime * result + ((sippCode == null) ? 0 : sippCode.hashCode());
		result = prime * result + ((supplierName == null) ? 0 : supplierName.hashCode());
		return result;
	}

	// Not including rental cost as that's not in the requirement
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarResult other = (CarResult) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fuelPolicy != other.fuelPolicy)
			return false;
		if (sippCode == null) {
			if (other.sippCode != null)
				return false;
		} else if (!sippCode.equals(other.sippCode))
			return false;
		if (supplierName == null) {
			if (other.supplierName != null)
				return false;
		} else if (!supplierName.equals(other.supplierName))
			return false;
		return true;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.rightPad(this.supplierName, 15, " "));
		sb.append(StringUtils.rightPad(this.description, 40, " "));
		sb.append(StringUtils.rightPad(this.sippCode, 10, " "));
		sb.append(StringUtils.rightPad(String.valueOf(this.rentalCost), 10, " "));
		sb.append(this.fuelPolicy);

		return sb.toString();
	}

	// It will return either of the car type defined in CarType enum
	// MINI('M'), ECONOMY('E'), COMPACT('C'), OTHER('O');
	public CarType getCarTypeGroup() {
		return CarType.getCarType(this.sippCode.charAt(0));
	}

}
