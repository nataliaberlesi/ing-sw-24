# IS24-LB07

## Implemented Features

| Functionality  | Status |
| :------------- | :-------------: |
| MODEL  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/tree/master/src/main/java/it/polimi/ingsw/Server/Model)  |
| SERVER CONTROLLER  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/tree/master/src/main/java/it/polimi/ingsw/Server/Controller)  |
| SERVER NETWORK  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/tree/master/src/main/java/it/polimi/ingsw/Server/Network)  |
| CLIENT NETWORK  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/tree/master/src/main/java/it/polimi/ingsw/Client/Network)  |
| CLIENT CLI  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/tree/master/src/main/java/it/polimi/ingsw/Client/View/CLI)  |
| CLIENT GUI  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/tree/master/src/main/java/it/polimi/ingsw/Client/View/GUI)  |
| CHAT | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/blob/master/src/main/java/it/polimi/ingsw/Server/Controller/GameController.java)  |
| PERSISTENCE  | [:white_check_mark:](https://github.com/nataliaberlesi/ing-sw-24/blob/master/src/main/java/it/polimi/ingsw/Server/Controller/PersistenceHandler.java)  |
| MULTIPLAYER  | :no_entry: |
| RESILIENCE  | :no_entry: |

### Legend

:white_check_mark: Implemented   :stopwatch: Work in Progress   :no_entry: Not implemented

## Tools

| Tool  | Description |
| :------------- | :------------- |
| IntelliJ IDEA Ultimate  | IDE  |
| Visio | UML  |
| Maven | Dependency Management  |
| JUnit  | Unit Testing  |
| JavaFX  | Graphical Library  |

## How to start the game
To start the game from your terminal, you need to launch the LB07.jar you can find in the shade directory and specify the following arguments:

### To start the server:
Depending on your operating system, go to the MacOS or Windows directory from your commmand line, then type: java -jar LB07.jar --server

### To start the client:
* To play in GUI:
When you find yourself in the MacOS or Windows directory, type: java -jar LB07.jar --gui ServerIPAddress
* To play in CLI: 
When you find yourself in the MacOS or Windows directory, type: java -jar LB07.jar --gui ServerIPAddress

Note: If you want to start both server and clients using the same network, use ServerIPAddress = localhost.

## Team

* Giuliano Croppi - giuliano.croppi@mail.polimi.it
* Kevin Vallerin - kevin.vallerin@mail.polimi.it
* Natalia Berlesi de Vasconcellos - natalia.berlesidevasconcellos@mail.polimi.it

## License

[Codex Naturalis](https://www.craniocreations.it/prodotto/codex-naturalis) is property of [Cranio Creations](https://www.craniocreations.it) and all of the copyrighted graphical assets used in this project were supplied by [Politecnico di Milano](https://www.polimi.it) in collaboration with their rights' holders.
