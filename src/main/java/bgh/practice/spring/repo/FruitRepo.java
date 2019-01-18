package bgh.practice.spring.repo;

import bgh.practice.spring.models.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepo extends JpaRepository<Fruit,Integer> {
}