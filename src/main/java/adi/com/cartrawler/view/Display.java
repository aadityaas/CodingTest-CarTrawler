package adi.com.cartrawler.view;
import java.util.Set;

import adi.com.cartrawler.model.CarResult;

public class Display {
    public void render(Set<CarResult> cars) {
        for (CarResult car : cars) {
            System.out.println (car);
        }
    }
}
