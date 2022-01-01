package com.example.main.controller.theory.boardgames;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("/boardgames")
@RestController
public class BoardGamesApi {

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
    // Brandon has been elected head of the board game club.
    // ---
    // Nice to meet you, I'm Brandon. I'm the head of the board game club.
    // It's an active club with thousands of players and thousands of games. (We meet once a week in Telliskivi).
    // To manage this system we have some student php application which is very bad.
    // I need a better and more modern application. Written with some cool Java and Spring Boot.
    // This could also function as api for other applications that could connect to our api.
    // I am looking to replace games part of the application and if this goes well then also users part.
    // We have a large catalog of games from which our members can select games they want to play.
    // Each game has a detailed info on a separate page.
    // Each month we buy new game and add it to our system. We are missing functionality to update a game, but would like to have it.
    // Currently we have to delete a game and add a new one.
    // For a catalog of games we can filter by genre and number of players.
    // Sort by gameplay time and year released.
    //

    private final List<BoardGame> boardGames = new ArrayList<>();

    //todo A first things first, please add necessary annotations to this class

    //todo B "We have a large catalog of games from which our members can select games they want to play."
    // create a method to query BoardGames (plural)

    @GetMapping
    public List<BoardGame> getBoardGames(@RequestParam Optional<String> genre,
                                            @RequestParam Optional<String> playersNumber,
                                            @RequestParam Optional<String> sortBy,
                                            @RequestParam Optional<Boolean> descendingOrder) {
        Stream<BoardGame> games = boardGames.stream();
        if (genre.isPresent()) {
            games = games.filter(game -> game.getGenre().equals(genre.get()));
        }
        if (playersNumber.isPresent()) {
            games = games.filter(game -> game.getNumberOfPlayers().equals(playersNumber.get()));
        }
        if (sortBy.isPresent()) {
            if (sortBy.get().equals("gameplay_time")) {
                games = games.sorted(Comparator.comparingInt(g -> Integer.parseInt(g.getGameplayTime())));
            }
            if (sortBy.get().equals("year_released")) {
                games = games.sorted(Comparator.comparingInt(g -> Integer.parseInt(g.getYearReleased())));
            }
            if (descendingOrder.isPresent() && descendingOrder.get()) {
                List<BoardGame> list = games.collect(Collectors.toList());
                Collections.reverse(list);
                games = list.stream();
            }
        }
        return games.collect(Collectors.toList());
    }

    //todo C "Each game has a detailed info on a separate page."
    // create a method to query a single BoardGame

    @GetMapping("{id}")
    public BoardGame getSingleBoardGame(@PathVariable long id) {
        Optional<BoardGame> requiredBoardGame = boardGames.stream().filter(game -> game.getId() == id).findFirst();
        if (requiredBoardGame.isPresent()) {
            return requiredBoardGame.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board game not found");
        }
    }

    //todo D "Each month we buy new game and add it to our system"
    // create a method to save a new BoardGame

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardGame postBoardGame(@RequestBody BoardGame boardGame) {
        boardGames.add(boardGame);
        return boardGame;
    }

    //todo E "We are missing functionality to update a game, but would like to have it"
    // create a method to update a BoardGame

    @PutMapping("{id}")
    public BoardGame updateBoardGame(@PathVariable long id, @RequestBody BoardGame updatedBoardGame) {
        BoardGame boardGame = getSingleBoardGame(id);
        boardGame.update(updatedBoardGame);
        return boardGame;
    }

    //todo F "Currently we have to delete a game and add a new one." We can assume they need delete
    // create a method to delete a BoardGame

    @DeleteMapping("{id}")
    public void deleteBoardGame(@PathVariable long id) {
        BoardGame boardGame = getSingleBoardGame(id);
        boardGames.remove(boardGame);
    }

    //todo G, H "For a catalog of games we can filter by genre and number of players."
    // G modify correct method to filter by genre (strategy, cards, etc)
    // H modify correct method to filter by number of players (2, 4, 6 etc)
    // make sure existing functionality doesn't break

    //todo I-J "Sort by gameplay time and year released."
    // I modify correct method to provide sorting by gameplay time and year released
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

    // Examples
    // The API provides a way to sort by either gameplay time or year released, not both at the same time
    // To specify which way to sort an optional String sortBy parameter is present
    //
    // If the parameter is set to "gameplay_time", the games will be sorted by gameplay time
    // as example http://localhost:8080/api/boardgames?sortBy=gameplay_time
    //
    // If the parameter is set to "year_released", the games will be sorted by year released
    // as example http://localhost:8080/api/boardgames?sortBy=year_released

    // By default, both sorts will be sorting in ascending order.
    // Optional Boolean descendingOrder parameter allows to change the order accordingly if set to true
    // If you wish to leave ascending order, you can either set it as false or don't use this parameter at all
    //
    // as example http://localhost:8080/api/boardgames?sortBy=gameplay_time&descendingOrder=true
    // this url will sort by gameplay time in descending order
    //
    // as example http://localhost:8080/api/boardgames?sortBy=year_released&descendingOrder=true
    // this url will sort by game released year in descending order
    //
    // as example http://localhost:8080/api/boardgames?sortBy=gameplay_time&descendingOrder=false
    // this url will sort by gameplay time in ascending order, just as the next one
    // http://localhost:8080/api/boardgames?sortBy=gameplay_time
    //
    // Can work for both types of sorts, but will not change anything unless one of two proper sort types is used.
}
