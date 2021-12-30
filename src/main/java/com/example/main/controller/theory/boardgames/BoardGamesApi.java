package ee.taltech.heroes.controller.theory.boardgames;

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
    //todo A first things first, please add necessary annotations to this class

    //todo B "We have a large catalog of games from which our members can select games they want to play."
    // create a method to query BoardGames (plural)

    //todo C "Each game has a detailed info on a separate page."
    // create a method to query a single BoardGame

    //todo D "Each month we buy new game and add it to our system"
    // create a method to save a new BoardGame

    //todo E "We are missing functionality to update a game, but would like to have it"
    // create a method to update a BoardGame

    //todo F "Currently we have to delete a game and add a new one." We can assume they need delete
    // create a method to delete a BoardGame

    //todo G, H "For a catalog of games we can filter by genre and number of players."
    // G modify correct method to filter by genre (strategy, cards, etc)
    // H modify correct method to filter by number of players (2, 4, 6 etc)
    // make sure existing functionality doesn't break

    //todo I-J "Sort by gameplay time and year released."
    // I modify correct method to provide sorting by gameplay time and year released
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

}
