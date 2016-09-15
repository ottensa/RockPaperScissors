# Rock Paper Scissors
The famous game rock paper scissors as a REST service based on Spring Boot.

This branch covers the basic game with the three moves 'Rock', 'Paper' and 'Scissors'

* Rock beats Scissors
* Scissors beats Paper
* Paper beats Rock

## Usage
After you downloaded the code you can build it with Maven
```
$ mvn package
```
and execute it with
```
$ java -jar target/rockpaperscissors-1.0.jar
```
the application starts a webserver that listens on port 8080

The following shows how to play the game with curl

1) We need to create a game. This happens with a POST request to http://localhost:8080/games which will have no response body, but the headers tell us the location of the created game
```
$ curl -i -X POST http://localhost:8080/games
$ HTTP/1.1 201 Created
$ Server: Apache-Coyote/1.1
$ Location: http://localhost:8080/games/1
$ Content-Length: 0
$ Date: Thu, 15 Sep 2016 15:58:40 GMT
```

2) Now we know that our game is at ```http://localhost:8080/games/1``` and to make our move ("Rock", "Paper", "Scissors") we need to make a PUT request with our move in JSON format as request body to that resource
```
$ curl -i -H "Content-Type: application/json" -X PUT -d '"Rock"' http://localhost:8080/games/1
$ HTTP/1.1 200 OK
$ Server: Apache-Coyote/1.1
$ Content-Length: 0
$ Date: Thu, 15 Sep 2016 16:07:58 GMT
````

3) Now we made our move and can check how the game ended with a GET request to our resource
```
$ curl -i -X GET http://localhost:8080/games/1
$ HTTP/1.1 200 OK
$ Server: Apache-Coyote/1.1
$ Content-Type: application/json;charset=UTF-8
$ Transfer-Encoding: chunked
$ Date: Thu, 15 Sep 2016 16:09:22 GMT
$
$ {"id":1,"status":"finished","playerOne":"Rock","playerTwo":"Rock","result":"Draw"}
```

It is also possible to abort a game if it is not finished yet with a DELETE request to the resource
First we create the game and then we abort it
```
$ curl -i -X POST http://localhost:8080/games
$ HTTP/1.1 201 Created
$ Server: Apache-Coyote/1.1
$ Location: http://localhost:8080/games/2
$ Content-Length: 0
$ Date: Thu, 15 Sep 2016 16:11:02 GMT
$
$ curl -i -X DELETE http://localhost:8080/games/2
$ HTTP/1.1 200 OK
$ Server: Apache-Coyote/1.1
$ Content-Length: 0
$ Date: Thu, 15 Sep 2016 16:11:12 GMT
```
