package it.scavone87.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import it.scavone87.entity.Fruit;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FruitRepository implements PanacheRepository<Fruit> {

    public Uni<Fruit> findByName(String name) {
        return find("name", name).firstResult();
    }
    public Uni<Fruit> findByColor(String color) {
        return find("color", color).firstResult();
    }
    public Uni<Fruit> save(Fruit fruit) {
        return persist(fruit);
    }

    public Uni<Boolean> deleteById(Long id) {
        return deleteById(id);
    }
}
