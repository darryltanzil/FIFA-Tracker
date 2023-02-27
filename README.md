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
```

```JSON
http://localhost:8080/comparison?player1=1&player2=1&compare=height ->
{"id":3,"winner":"N/A","loser":"N/A","description":"Error: duplicate player"}
```

## Structuring and Intentions

This API was meant to familiarize myself with the Spring Framework, and Maven. I've had previous experience accessing information from RESTful APIs, and posting API endpoints, but was most experience with it via ExpressJS, Node.JS, and React. This is a way for me to familiarize myself with Spring. As well as this, FIFA-TRACKER is an exercise of my knowledge of software engineering principles.

### Single Responsibility Principle
* ComparisonService and ComparisonController are divided into two classes; the ComparisonController focuses purely on handling any GET requests, whereas ComparisonService requests from the FUTDB API, and performs the calculations. This ensures that each class has only one single purpose.

### Singleton Design
* There can only be one "bean" instance of a ComparisonService class. This instance is a dependancy for the ComparisonController class, and is called via the @Autowired annotation. Spring Framework restricts this @Service class by ensuring that it can only be one singular instnace.

### Unit & Integration Testing
From this small project, I learned that Unit testing focuses towards methods, and instances of classes / objects / methods. Integration Testing, on the other hand, focuses towards testings people's requests towards the actual service itself, via mock MVC controllers and requests. I made sure to reflect this on my testing.

### Beans, IoC Containers, and MVC
I learned how Spring handles objects- specifically in the form of beans, which are objects Spring handles with configuration wrapped around them. I learned that Spring's IoC container injects dependencies into an object, makes it available for our use. Spring helped me delve further into MVC (Model-view-controller) architecture, and specifically into the controller aspect. It allowed me to visualize how a user makes a request to the controller, create modelling data from FutDB's API, and send it in the "view" of JSON back to the user.

### Encapsulation
I know that encapsulation is a way of wrapping variables and code acting on the variables as a single unit. I did this in FIFA in a small way, via an AtomicCounter object located in ComparisonService. The AtomicCounter is meant to keep track of ids of different requests the end user makes, and it needs to be accessed by the ServiceController. I created a getter method in the ComparisonService class so I can give access to the object in other classes, without actually making it a public variable.
