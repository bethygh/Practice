package bgh.practice.spring.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fruit extends BaseEntity {

	private String type;
	@OneToMany(fetch = FetchType.LAZY ,
			cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	private List<UnitPrice> priceList;
	private static final long serialVersionUID = 1L;

	public Fruit() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<UnitPrice> getPrice() {
		return priceList;
	}

	public void setPrice(List<UnitPrice> priceList) {
		this.priceList = priceList;
	}

}