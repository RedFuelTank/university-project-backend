package com.example.main.controller.theory.skateboards;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/skateboards")
@RestController
public class SkateboardsApi {

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

    private final List<Skateboard> skateboards = new ArrayList<>();

    //todo A first things first, please add necessary annotations to this class

    //todo B "an overview of the skateboards we sell"
    // create a method to query skateboards (plural)
    @GetMapping()
    public List<Skateboard> getSkateboards(@RequestParam(required = false) Optional<String> inStock,
                               @RequestParam(required = false) Optional<String> condition,
                               @RequestParam(required = false) Optional<String> sort,
                               @RequestParam(required = false) Optional<String> reverse) {

        List<Skateboard> skateboardsCopy = skateboards;

        if (inStock.isPresent()) {
            skateboardsCopy = skateboardsCopy.stream().filter(s -> s.getInStock().equals(inStock))
                    .collect(Collectors.toList());
        }
        if (condition.isPresent()) {
            skateboardsCopy = skateboardsCopy.stream().filter(s -> s.getCondition().equals(condition))
                    .collect(Collectors.toList());
        }
        if (sort.isPresent()) {
            if (sort.equals("name"))
                skateboardsCopy.sort(Comparator.comparing(skateboard -> skateboard.getName()));
            else if (sort.equals("price")) {
                skateboardsCopy.sort(Comparator.comparing(skateboard -> skateboard.getPrice()));
            }
        }
        if (reverse.isPresent()) Collections.reverse(skateboardsCopy);

        return skateboardsCopy;
    }


    //todo C "page for each skateboard where I can see it's info"
    // create a method to query a single skateboard

    @GetMapping("{id}")
    public Skateboard getSkateboard(@PathVariable int id) {
        return skateboards.stream().filter(c -> c.getId() == id).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //todo D "button to add a new skateboard"
    // create a method to save a new skateboard

    @PostMapping()
    public void saveSkateboard(@RequestBody Skateboard skateboard) {
        skateboards.add(skateboard);
    }

    //todo E "button to update existing skateboard"
    // create a method to update a skateboard

    @PatchMapping("{id}")
    public Skateboard updateSkateboard(@PathVariable int id, @RequestBody Skateboard updateSkateboard) {
        Skateboard skateboard = getSkateboard(id);

        return update(skateboard, updateSkateboard);
    }

    public Skateboard update(@PathVariable Skateboard skateboard, @RequestBody Skateboard updateSkateboard) {
        skateboard.setName(updateSkateboard.getName());
        skateboard.setCondition(updateSkateboard.getCondition());
        skateboard.setDesigner(updateSkateboard.getDesigner());
        skateboard.setPrice(updateSkateboard.getPrice());
        skateboard.setInStock(updateSkateboard.getInStock());
        return skateboard;
    }

    //todo F "button to delete skateboard"
    // create a method to delete a skateboard

    @DeleteMapping("{id}")
    public void deleteSkateboard(@PathVariable int id) {
        Skateboard skateboard = getSkateboard(id);
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
