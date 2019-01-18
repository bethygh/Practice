package bgh.practice.spring.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
	private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
