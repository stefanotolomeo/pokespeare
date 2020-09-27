## Instruction for Integration Test with Docker Container

This container is built with the image "rodolpheche/wiremock" which basically creates a RESTful-API service able to provide a fixed JSON for different request.

The aim is simply to locally simulate real Pokemon and Shakespeare API calls to remote systems and provide actual answer to the caller.

### How to run
Look at the main READ_ME into the section "Integration Test"


### Mocked Entrypoints
#### Mock for Pokemon-API
A GET request to simulate the call to Pokemon-Species API for three specified pokemon: Charizard, Pikachu and Ditto.

`GET /pokemon/{string:pokemon_name}`

E.g.: [http://localhost:4444/pokemon/charizard]

#### Mock for Shakespeare-API
A GET request to simulate the call to Shakespeare-Translation API for three description of the aforementioned pokemon.

`GET /shakespeare?text={string:pokemon_description}`

E.g.: for Charizard, the request is: 
[http://localhost:4444/shakespeare?text=Spits+fire+that+is+hot+enough+to+melt+boulders.+Known+to+cause+forest+fires+unintentionally]

E.g.: for Pikachu, the request is: 
[http://localhost:4444/shakespeare?text=It+keeps+its+tail+raised+to+monitor+its+surroundings.+If+you+yank+its+tail,+it+will+try+to+bite+you.]

E.g.: for Ditto, the request is: 
[http://localhost:4444/shakespeare?text=It+can+freely+recombine+its+own+cellular+structure+to+transform+into+other+life-forms.]

Note: the mock is available only for these three descriptions