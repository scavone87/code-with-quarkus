package it.scavone87.service;

import java.util.List;

import io.smallrye.mutiny.Uni;
import it.scavone87.entity.Fruit;
import it.scavone87.model.FruitRequest;

public interface FruitService {

    /**
     * Finds all fruits.
     *
     * @return a Uni containing a list of all fruits
     */
    Uni<List<Fruit>> findAll();
    /**
     * Finds a fruit by its name.
     *
     * @param name the name of the fruit
     * @return a Uni containing the found fruit or null if not found
     */
    Uni<Fruit> findByName(String name);

    /**
     * Finds a fruit by its color.
     *
     * @param color the color of the fruit
     * @return a Uni containing the found fruit or null if not found
     */
    Uni<Fruit> findByColor(String color);

    /**
     * Finds a fruit by its ID.
     *
     * @param id the ID of the fruit
     * @return a Uni containing the found fruit or null if not found
     */
    Uni<Fruit> findById(Long id);

    /**
     * Saves a new fruit.
     *
     * @param fruit the fruit to save
     * @return a Uni containing the saved fruit
     */
    Uni<Fruit> save(FruitRequest fruit);

    /**
     * Deletes a fruit by its ID.
     *
     * @param id the ID of the fruit to delete
     * @return a Uni indicating whether the deletion was successful
     */
    Uni<Boolean> deleteById(Long id);

    Uni<Fruit> changeColor(Long id, String color);
}
