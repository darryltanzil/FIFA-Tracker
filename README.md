# FIFA-COMPARE

FIFA-COMPARE is a small comparison API developed via Java and the Spring Framework that grabs player information from EA's Ultimate Football 23 Game, and pits them against each other to determine a winner and loser. The original player information was obtained from https://futdb.app/

## Installation

Obtain an API key from https://futdb.app/, make sure Maven is installed on your computer, and run the following command in the project directory. Since the program is compiled using Maven, ensure that it is installed.

```bash
./mvnw spring-boot:run
```

## API endpoints

### Comparison GET endpoint

#### Parameters :

`player1`: id of player1,

`player2`: id of player2,

`compare`: what to compare by. Usage: `height`, `weight`

#### Usage

```JSON
http://localhost:8080/comparison -> 

{"id":4,"winner":"Neither","loser":"Neither","description":"Error: Invalid comparison operator"}
```

```JSON
http://localhost:8080/comparison?player1=1&player2=2&compare=height ->

{"id":5,"winner":"Thomas Partey","loser":"Martin Ødegaard","description":"Thomas Partey is 7 cm taller then Martin Ødegaard."}
```JSON

```JSON
http://localhost:8080/comparison?player1=1&player2=1&compare=height ->
{"id":3,"winner":"N/A","loser":"N/A","description":"Error: duplicate player"}
```

## Intentions

This API was meant to familiarize myself with the Spring Framework, Maven, accessing information from RESTful APIs, and posting my own individual API endpoint.
