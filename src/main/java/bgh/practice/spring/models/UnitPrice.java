package bgh.practice.spring.models;

import bgh.practice.spring.constants.Constants.FruitUnit;
import javax.persistence.*;

@Entity
public class UnitPrice extends BaseEntity {


    private FruitUnit unit;
    private long price;
    private static final long serialVersionUID = 1L;

    public UnitPrice() {
    }

    public UnitPrice(FruitUnit unit, long price) {
        this.unit = unit;
        this.price = price;
    }

    public FruitUnit getUnit() {
        return unit;
    }

    public void setUnit(FruitUnit unit) {
        this.unit = unit;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
