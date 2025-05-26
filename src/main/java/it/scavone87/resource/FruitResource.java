package it.scavone87.resource;

import java.util.List;
import java.util.Map;

import org.jboss.resteasy.reactive.RestResponse;

import io.smallrye.mutiny.Uni;
import it.scavone87.entity.Fruit;
import it.scavone87.model.FruitRequest;
import it.scavone87.service.FruitService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    private final FruitService fruitService;

    public FruitResource(FruitService fruitService) {
        this.fruitService = fruitService;
    }
    

    @GET
    @Path("/fruits")
    public Uni<List<Fruit>> getAllFruits() {
        return fruitService.findAll();
    }

    @POST
    @Path("/fruit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<Fruit>> createFruit(FruitRequest fruit) {
        // Validazione input
        if (fruit == null || fruit.name() == null || fruit.color() == null) {
            return Uni.createFrom().item(RestResponse.status(400, "Invalid fruit data"));
        }
        
        // Salvataggio del frutto e trasformazione della risposta
        return fruitService.save(fruit)
                .map(savedFruit -> RestResponse.ok(savedFruit));
    }

    @PUT
    @Path("/fruit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<Fruit>> updateFruit(@PathParam("id") Long id, @QueryParam("color") String color) {
        // Validazione input
        if (color == null) {
            return Uni.createFrom().item(RestResponse.status(400, "Invalid fruit data"));
        }

        // Aggiornamento del frutto e trasformazione della risposta
        return fruitService.changeColor(id, color)
                .map(updatedFruit -> RestResponse.ok(updatedFruit));
    }

    @GET
    @Path("/stats")
    public Uni<RestResponse<Map<String, Long>>> getFruitStats() {
       return fruitService.findAll()
                .map(fruits -> {
                    // For each color, count the number of fruits
                    Map<String, Long> stats = fruits.stream()
                            .collect(java.util.stream.Collectors.groupingBy(Fruit::getColor, java.util.stream.Collectors.counting()));
                    return RestResponse.ok(stats);
                });

    }
}
