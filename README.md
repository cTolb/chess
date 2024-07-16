# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

## My Server Design 
https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5kvDrco7jK2XwfksIGLpg-6-kUaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatLlqOJpEuGFocjA3K8gagrCjAr5iK60pJpe8HlAaNG+JwjrOgS+EsuUnrekGAZBiGrHmpG1HRjAsbBtooYUW6nY4aU06zug-GJsm5zAuUaE8tmubQQZHbsXBJRXAMpGjAuk59FpjbNuOoHfleUBWdkPYwP2g69HZI4Oe51ZTkG2nzmF+wwGYfGeN4fiBF4KDoHuB6+Mwx7pJkmC+Re1nXtIACiu4lfUJXNC0D6qE+3QuXO7awTBtmNW5E59ACFmwRpMBIfYWWoZlvoYRi2FyrhgmqcJMDkmAikSXG2jkUyamyVyPK2vR2hCmE7VoCpa1sbKnHwD4UCQqQ3GcrxHDdCRIUoBN8EakJpJGCg3CZIt05Se9EaFJaMhfRShiKQ60kRqdTryjAirKrpuH6UCJZoVlpkIHmPXqV5pQ9J51CWacBVgH2A5DkuCVrslASQrau7QjAADio6sjlp75eezAw9ezMVdV9ijg1kWuYdP5sq1pbOaLc6OV15mo71k2IdCrOjKoqHQmNWFI29M0ffNv2SStR1moD7LlLR232rtjEHWblFWX1cAXVdzA3XdD1GqbOH68dH3ILE6tqLCq3m1R5SRMAHAtIzsQs2zjvrTD4Ih6y8dYH7VmtZn6eY9jSu40TaY9FMQsayBFT9BXKAAJLSCBACMvYAMwACxPCemQGhWExfDoCCgA2vfAf3Ty1wAcqO-d7DAjSE8cPnc+TgVl30teqFXNejg3zdt53Uzd-q9n3H0A9DyAI+nysXxTzP59zwv8Urol66BNgF3YNw8C6pkiejBSLlM8OQeYcRspUWoDRBbC2CLLdAQ576jEXpZP8PV8Yyz9NAJAAAveIiRIJOSQfcDyitUxsj6qJTIIclozjFrMYhL1YZTRkADQiC1xJ-VNlDSONEtp2gFHbfa8DDo8Odirc6l1zACNgLdHwnBva23kEwgSrCDbuhgFQlANDa7-XUbwuuaAOjMBDsnE64C06jnjJNTsUstEh3TDmLGZCCziJLlccuu9G7lBbh3GAKCQQkxXv5CmvRPGjD3j4g+-iX6riShuSwX0kLJBgAAKQgDyABhgAiD2HlzUBFC8aQMpHeFotcRZ1jFkOb+wBElQDgBAJCUAGFeICZLdB0tpzYLwQQAhnUng1LqQ0pphC75eJAt1Iuqc4YACsMloBoQdd8Q8hmNOgC0iJ0gVGJmmgHDRRtOEm2UWYmSQNqLWxkQxYRlSmpiOmQhSR7sZE8XkfdNAj0oAhmzrs827CdFePDk7M55RmYUiySc6GFi4a6OUt8tRezyh+C0NQ0csJBmUGGdAQF61gV8mwMiwwVQVkdkxVACFxdmFcXxXqW0ML5B6xzuggmLjibgIAm05eoDV6U1iW-WmXhaldi9LAYA2Bv6EHwUeY++TpG8zTBUUq5VKrVWMM1dpSsriTPIfckS3A8BhwZT88M5QOCgx+uJbFbFcXSDNeDZUCkgzdMlTAKKGg7lQoeQjFUcKoYmttcRS1pzLYg2+nahAMi3UAwpeCeGSpvU2Lwuov1ob04GrEda21Cl7Xp3JW4ylsbEbZxRqmTVLLAlstLhyoJXKAo8uXJgIAA

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
