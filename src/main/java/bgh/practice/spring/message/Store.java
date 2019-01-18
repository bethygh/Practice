package bgh.practice.spring.message;

import bgh.practice.spring.models.Fruit;

public class Store extends BaseMessage {

    private Fruit fruit;
    private static final long serialVersionUID = 1L;

    public Store() {
    }

    public Store(Fruit fruit) {
        this.fruit = fruit;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }
}
