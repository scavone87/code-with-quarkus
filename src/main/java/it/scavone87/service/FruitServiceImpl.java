package it.scavone87.service;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.scavone87.entity.Fruit;
import it.scavone87.model.FruitRequest;
import it.scavone87.repository.FruitRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FruitServiceImpl implements FruitService {

    @Inject
    FruitRepository fruitRepository;

    @Override
    @WithSession
    public Uni<List<Fruit>> findAll() {
        return fruitRepository.listAll();
    }

    @Override
    public Uni<Fruit> findByName(String name) {
        return fruitRepository.findByName(name);
    }

    @Override
    public Uni<Fruit> findByColor(String color) {
        return fruitRepository.findByColor(color);
    }

    @Override
    @WithSession
    public Uni<Fruit> findById(Long id) {
        return fruitRepository.findById(id);
    }

    @Override
    @WithTransaction
    public Uni<Fruit> save(FruitRequest fruit) {
        Fruit newFruit = new Fruit();
        newFruit.setName(fruit.name());
        newFruit.setColor(fruit.color());
        return fruitRepository.save(newFruit);
    }

    @Override
    public Uni<Boolean> deleteById(Long id) {
        return fruitRepository.deleteById(id);
    }

    @Override
    @WithTransaction
    public Uni<Fruit> changeColor(Long id, String color) {
        return fruitRepository.findById(id)
                .onItem()
                .ifNull().failWith(() -> new IllegalArgumentException("Fruit not found with id: " + id))
                .onItem().ifNotNull().transform(fruit -> {
                    fruit.setColor(color);
                    return fruit;
                })
                .call(fruitRepository::persist);
    }

}
