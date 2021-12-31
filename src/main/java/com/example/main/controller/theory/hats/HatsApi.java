package com.example.main.controller.theory.hats;

import com.example.main.controller.theory.hats.exceptions.HatNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/hats")
@RestController
public class HatsApi {
	private final List<Hat> hats = new ArrayList<>();

	/**
	 * Examples:
	 * 		 /hats?style=50cent&colour=blaaack&sort=size&reverse=1
	 * 		 /hats?style=50cent&colour=blaaack&sort=size
	 * 		 /hats?style=eminem&colour=white&sort=price&reverse=0
	 * @param style param to filter by style
	 * @param colour param to filter by colour
	 * @param sort param to choose how to sort list
	 * @param reverse param to choose if list should be reversed
	 * @return list of hats
	 */

	@GetMapping
	public List<Hat> getAllHats(@RequestParam Optional<String> style, @RequestParam Optional<String> colour,
								@RequestParam Optional<String> sort, @RequestParam Optional<Integer> reverse) {
		List<Hat> copy = hats;
		if (style.isPresent())
			copy = hats.stream().filter(w -> w.getStyle().equals(style.get())).collect(Collectors.toList());
		if (colour.isPresent())
			copy = copy.stream().filter(w -> w.getColour().equals(colour.get())).collect(Collectors.toList());
		if (sort.isPresent()) {
			if (sort.get().equals("size")) copy.sort(Comparator.comparing(Hat::getSize));
			else if (sort.get().equals("price")) copy.sort(Comparator.comparing(Hat::getPrice));
			if (reverse.isPresent() && reverse.get() == 1) Collections.reverse(copy);
		}
		return copy;
	}

	@GetMapping("{id}")
	public Hat getHatById(@PathVariable int id) {
		Optional<Hat> hatOptional = hats.stream().filter(w -> w.getId() == id).findFirst();
		if (hatOptional.isPresent()) return hatOptional.get();
		throw new HatNotFoundException();
	}

	@PostMapping
	public void saveHat(@RequestBody Hat hat) {
		hats.add(hat);
	}

	@PatchMapping("{id}")
	public Hat updateHat(@RequestBody Hat updatedHat, @PathVariable int id) {
		Hat hat = getHatById(id);
		updateHatField(hat, updatedHat);
		return hat;
	}

	@DeleteMapping("{id}")
	public void deleteHat(@PathVariable int id) {
		Hat hat = getHatById(id);
		hats.remove(hat);
	}

	private void updateHatField(Hat hatToUpdate, Hat newHat) {
		hatToUpdate.setBrand(newHat.getBrand());
		hatToUpdate.setColour(newHat.getColour());
		hatToUpdate.setPrice(newHat.getPrice());
		hatToUpdate.setSize(newHat.getSize());
		hatToUpdate.setStyle(newHat.getStyle());
	}

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
	// Mad Hatter. Another Telliskivi butique. Hat know-how link (:  https://www.youtube.com/watch?v=6lYuL_kz9Ak
	// ---
	// Hey, I am Max Hatter. I'm from the states (US). I played basketball in my youth and I fell in love with hats.
	// I came to Estonia few years ago and started a business selling hats. I have a busy shop in Telliskivi region.
	// However during winter-time our sales are slow, so I am thinking of expanding our online presence.
	// I think we need to do something on the web. Like a shop or gallery or both. Connect it to tik-tok, instagram, facebook.
	// Do the online thing. Can you help?
	// I guess I need like a landing page where you can see many hats.
	// And each hat has some info, so once you click on it, it displays it.
	// And then there are buttons for saving and updating when I have new hats or some info was wrong.
	// Oh, and some way to remove hats.
	// For landing page it is important that the hats can be filtered by style and colour.
	// Also I'd like to order them by size and price.

	//todo A first things first, please add necessary annotations to this class

	//todo B "I guess I need like a landing page where you can see many hats"
	// create a method to query hats (plural)

	//todo C "And each hat has some info, so once you click on it, it displays it"
	// create a method to query a single hat

	//todo D "And then there are buttons for saving [..] when I have new hats [..]"
	// create a method to save a new hat

	//todo E "And then there are buttons for [..] updating when [..] some info was wrong"
	// create a method to update a hat

	//todo F "Oh, and some way to remove hats."
	// create a method to delete a hat

	//todo G, H "For landing page it is important that the hats can be filtered by style and colour."
	// G modify correct method to filter by hat style (59fifty, 9twenty, cap, etc)
	// G modify correct method to filter by hat colour (red, blue, etc)
	// make sure existing functionality doesn't break

	//todo I-J "Also I'd like to order them [the hats] by size and price."
	// I modify correct method to provide sorting by size and price
	// J modify correct method to support sorting in ascending and descending order
	// in addition write some examples for how you will sort using your api (provide urls)
}
