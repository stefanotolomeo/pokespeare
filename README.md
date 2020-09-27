# Pokespeare

What does it happen if an API merges Pokemon and Shakespeare?
Just try it!

## Goal
Provide a RESTful API able to translate pokemon description into "shakespearean" text.

## API
# Request
GET /pokemon/{string:pokemon_name}

# Request Example
GET http://localhost:5000/pokemon/pikachu

# Response
{
    name: string,
    description: string
}

#Response Example
{
    "name": "pikachu",
    "description": "At which hour several of these pokémon gather,  their electricity couldst buildeth and cause lightning storms."
}

## Project Management and tools
This API is developed in JAVA 8 and Spring Framework (in particular, Spring Boot).

Project and dependencies are managed by MAVEN (https://maven.apache.org/install.html).

GIT is the version-control system used during all the project.
Branch for tagging is "master" and development are carried out in "develop".
In this case, development has been carried out in a feature-branch created from master.
The best solution is having both a development branch ("develop") and a tag branch ("master").

Relevant used tools are:
- Log4J: for logging
- Apache-HttpComponents: for an http-client
- Google Guava: for preconditions
- Jackson: for marshall and unmarshall JSON

## Testing
### Unit test
Running all unit-tests with "mvn test"

## Integration Test
Running integration-test requires both Docker and Docker-Compose properly installed.
Follow the instructions to install these tools in the official documentation:
    - Docker: https://docs.docker.com/get-docker/
    - Docker-Compose: https://docs.docker.com/compose/install/

Steps to run integration-test:
(1) turn on docker containers for test:
    (1.a) move to folder ./docker: "cd docker/"
    (1.b) build containers with docker-compose: "docker-compose -f docker-compose-wiremock.yml build"
    (1.c) run containers with docker-compose: "docker-compose -f docker-compose-wiremock.yml up"

(2) Choose the test and run it
    (2.a) changing properties into application-it.yml, it is possible to simulate call to both:
        - mock API (into docker container)
        - remote API (actual API)

## Run app within Docker container
(1) Install docker and docker-compose:
    - docker: https://docs.docker.com/get-docker/
    - docker-compose: https://docs.docker.com/compose/install/
(1) Build the container:
    - ddocker-compose -f docker-compose-pokespeare.yml build
(2) Run the container:
    - docker-compose -f docker-compose-pokespeare.yml.yml up
(3) Access the application via browser at localhost:5000/
(4) Turn off the container:
    - docker-compose -f docker-compose-pokespeare.yml down

## Limitation and Possible Optimization
(1) Introduce API versioning: http://{hostname}:{port}/v1/pokemon/{name}
(2) Shakespeare-API allows at maximum 5-calls per hour. Currently, the application try to overcome this limit using an internal cache
where previous requests are stored and returned without any external call. The best solution is to buy a licence for Shakespeare-API:
in this scenario, it is likely that a token (or other security mechanism) will be provided and it should be set in the request header
to Shakespeare-API.
(3) Currently, the API always gets the first pokemon english description and translate it. Anyway, multiple descriptions are
available for different game (green, yellow, blu, red, etc.) and it would be better to let the user enter this parameter as request
parameter.
For instance, two possible solutions are:
    - http://{hostname}:{port}/pokemon/{game_version}/{pokemon_name}
    - http://{hostname}:{port}/game/{game_version}/pokemon/{pokemon_name}

