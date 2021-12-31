package com.example.main.controller.theory.skateboards;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/skateboards")
@RestController
public class SkateboardsApi {

    private final List<Skateboard> skateboards = new ArrayList<>();

    //todo The Story
    // Fred has a Skateboard shop in Telliskivi.
    // ---
    // Hi. I'm Fred the hipster. I studied law and music, but now I'm selling and making skateboards. Wild life!
    // Our business has grown and I need some help automating it.
    // Currently our inventory is managed by pen and paper. You need to make it better.
    // This is wSkateboard I need:
    // - an overview of the skateboards we sell
    // - I want to know which ones are in stock and which ones are new (vs used)
    // - I want to order by the price or by the name alphabetically
    // and a
    // - page for each skateboard where I can see it's info
    // - button to add a new skateboard
    // - button to update existing skateboard
    // - button to delete skateboard

    //todo A first things first, please add necessary annotations to this class

    //todo B "an overview of the skateboards we sell"
    // create a method to query skateboards (plural)
    @GetMapping
    public List<Skateboard> getAllSkateboards(@RequestParam Optional<String> sortType, @RequestParam Optional<String> condition,
                                @RequestParam Optional<String> inStock, @RequestParam Optional<Integer> reverse) {
        List<Skateboard> copy = skateboards;
        if (inStock.isPresent())
            copy = copy.stream().filter(w -> w.getInStock().equals(inStock.get())).collect(Collectors.toList());
        if (condition.isPresent())
            copy = copy.stream().filter(w -> w.getCondition().equals(condition.get())).collect(Collectors.toList());
        if (sortType.isPresent()) {
            if (sortType.get().equals("name")) copy.sort(Comparator.comparing(Skateboard::getName));
            if (sortType.get().equals("price")) copy.sort(Comparator.comparing(Skateboard::getPrice));
            if (reverse.isPresent() && reverse.get() == 1) Collections.reverse(copy);
        }
        return copy;
    }

    //todo C "page for each skateboard where I can see it's info"
    // create a method to query a single skateboard
    @GetMapping("{id}")
    public Skateboard getSkateboardById(@PathVariable int id) {
        Optional<Skateboard> hatOptional = skateboards.stream().filter(w -> w.getId() == id).findFirst();
        if (hatOptional.isPresent()) return hatOptional.get();
        throw new SkateNotFoundBoardException();
    }

    //todo D "button to add a new skateboard"
    // create a method to save a new skateboard
    @PostMapping
    public void saveSkateboard(@RequestBody Skateboard hat) {
        skateboards.add(hat);
    }


    //todo E "button to update existing skateboard"
    // create a method to update a skateboard
    @PatchMapping("{id}")
    public Skateboard updateSkateboard(@RequestBody Skateboard updatedSkateboard, @PathVariable int id) {
        Skateboard skateboard = getSkateboardById(id);
        updateHatField(skateboard, updatedSkateboard);
        return skateboard;
    }

    private void updateHatField(Skateboard skateboard, Skateboard newSkateboard) {
        skateboard.setCondition(newSkateboard.getCondition());
        skateboard.setDesigner(newSkateboard.getDesigner());
        skateboard.setPrice(newSkateboard.getPrice());
        skateboard.setName(newSkateboard.getName());
        skateboard.setInStock(newSkateboard.getInStock());
    }

    //todo F "button to delete skateboard"
    // create a method to delete a skateboard
    @DeleteMapping("{id}")
    public void deleteSkateboard(@PathVariable int id) {
        Skateboard skateboard = getSkateboardById(id);
        skateboards.remove(skateboard);
    }

    //todo G, H "I want to know which ones are in stock and which ones are new (vs used)"
    // G modify correct method to filter whether the skateboard is in stock or out of stock
    // H modify correct method to filter by condition (new, used, broken)
    // make sure existing functionality doesn't break

    //todo I-J "I want to order by the price or by the name alphabetically"
    // I modify correct method to provide sorting by price and name
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

}
