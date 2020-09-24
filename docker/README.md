## TODO: instruction for docker containers

This container is built with the image "clue/json-server" which basically creates a RESTful-API service able to provide
a fixed JSON for different entrypoints.

The registered entrypoints are:
    (1)  "http://localhost:4444/pokemon/{pokemonId}":
         it simulates a GET Request to " https://pokeapi.co/api/v2/pokemon-species/{pokemonName or pokemonId}"
    (2) "http://localhost:4444/shakespeare/{pokemonId}":
         it simulates a POST Request to "https://api.funtranslations.com/translate/shakespeare"

The only Pokemon managed by this docker container are:
- Charizard (id = 6)
- Pikachu (id = 25)
- Ditto (id = 132)


### EXAMPLE ###

To get pokemon-species description for:
- Charizard ---> "http://localhost:4444/pokemon/6"
- Pikachu ---> "http://localhost:4444/pokemon/25"
- Ditto---> "http://localhost:4444/pokemon/132"

To get shakespeare description for:
- Charizard --->
