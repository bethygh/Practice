package bgh.practice.spring.message;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED)
public abstract class BaseMessage implements Serializable {

    @Id
    @GeneratedValue
	private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
