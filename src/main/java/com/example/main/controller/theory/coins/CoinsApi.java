package com.example.main.controller.theory.coins;


import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/coins")
@RestController
public class CoinsApi {
    List<Coin> coins = new ArrayList<>();

    //todo Welcome to the theory!
    // To start put these classes into my.project.controller.theory so you can check these using swagger or browser
    // Each team member has to do only 1 assignment and commit/push it to your repository.
    // (So 2 people - 2 assignments, 3 people - 3 assignments, 4 people - 4 assignments).
    // Make sure to commit under your user otherwise the points won't count. Each team member has to score at least 50%.
    // Don't add unnecessary code (no need for services or database).
    // We are doing mock-api design. I am grading urls and structure of the methods.
    // It should still work, i.e I can access this api from swagger or browser.
    // A good source for learning about proper API design is https://docs.microsoft.com/en-us/azure/architecture/best-practices/api-design

    //todo The Story
    // Chris is a coin collector. (Numismatic is the official term)
    // ---
    // I have 1 000 different coins from the Ancient Greek to the Modern Estonian euros.
    // I used to travel a lot with my coin collection. Do you know how many times have I had an exhibition in Telliskivi?
    // I used to travel, not anymore. Have you heard of corona?
    // Anyways I want to develop a web page for my coins so myself and my friends can view my collection wherever they are.
    // I need to have like a list view with many coins. If I click on a single coin, I get to a detail page.
    // I want to add new coins, update existing ones and occasionally delete some.
    // There should be some filtering, by period and region.
    // And sorting, by value and dateAdded. By default it can sort with latest coins first.
    //
    //todo A first things first, please add necessary annotations to this class

    //todo B "I need to have like a list view with many coins"
    // create a method to query coins (plural)
    @GetMapping()
    public List<Coin> getCoins(@RequestParam(required = false) Optional<String> period,
                               @RequestParam(required = false) Optional<String> region,
                               Sort sort) {
        List<Coin> coins = this.coins;

        if (period.isPresent()) {
            coins = coins.stream()
                    .filter(c -> c.getPeriod().equals(period.get()))
                    .collect(Collectors.toList());
        }

        if (region.isPresent()) {
            coins = coins.stream()
                    .filter(c -> c.getRegion().equals(region.get()))
                    .collect(Collectors.toList());
        }

        return coins;
    }


    //todo C "If I click on a single coin, I get to a detail page."
    // create a method to query a single coin
    @GetMapping("{id}")
    public Coin getCoin(@PathVariable int id) {
        return coins.stream()
                .filter(c -> c.getId() == id)
                .findFirst().orElse(null);
    }

    //todo D "I want to add new coins"
    // create a method to save a new coin
    @PostMapping()
    public Coin saveCoin(@RequestBody Coin coin) {
        coins.add(coin);
        return coin;
    }

    //todo E "update existing ones"
    // create a method to update a coin
    @PatchMapping("{id}")
    public Coin updateCoin(@PathVariable int id, @RequestBody Coin updateCoin) {
        Optional<Coin> coin = coins.stream()
                .filter(c -> c.getId() == id)
                .findFirst();

        coin.ifPresent(c -> update(c, updateCoin));

        return coin.orElse(null);
    }

    private void update(Coin c, Coin updateCoin) {
        c.setAge(updateCoin.getAge());
        c.setCondition(updateCoin.getCondition());
        c.setDateAdded(updateCoin.getDateAdded());
        c.setName(updateCoin.getName());
        c.setRegion(updateCoin.getRegion());
        c.setValue(updateCoin.getValue());
        c.setPeriod(updateCoin.getPeriod());
    }

    //todo F "occasionally delete some"
    // create a method to delete a blog
    @DeleteMapping("{id}")
    public Coin deleteCoin(@PathVariable int id) {
        Optional<Coin> coin = coins.stream()
                .filter(c -> c.getId() == id)
                .findFirst();

        coin.ifPresent(c -> coins.remove(c));
        return coin.orElse(null);
    }

    //todo G, H "There should be some filtering, by period and region"
    // G modify correct method to filter by period (ancient times, 18th century, 19th century)
    // H modify correct method to filter by region (americas, europe)
    // make sure existing functionality doesn't break

    //todo I-J "And sorting, by value and date added. By default it can sort with latest coins first."
    // I modify correct method to provide sorting by value and date added
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)



}
