# Country Reactive Service

## Development Environment
### Prerequisites
- Java 17
- Spring Boot 3.3.0

## Application Properties
Default application properties:
```
#source 
api.country.source=RESTFUL_COUNTRIES
api.country.timeout.seconds=10

#restcountries
restcountries.api.base.url=https://restcountries.com/v3.1

#restfulcountries
restfulcountries.api.base.url=https://restfulcountries.com/api/v1
restfulcountries.api.auth.token=<TO_SUPPLY>
```

### Note:
- source - general settings for connecting to 3rd-party source of country information
- restcountries - Source #1
  * To use, set ```api.country.source=REST_COUNTRIES```
  * This source is very slow at times. 
- restfulcountries - Source #2
  * To use, set ```api.country.source=RESTFUL_COUNTRIES```
  * This source is faser. It needs <i>authorization token</i>. It can be created here : https://restfulcountries.com/request-access-token
  * Email address is enough to request a token.
- default service port : 8080


## Running the app
1. Run backend:
- Via IDE : Run CountryApplication
- Via terminal : Navigate to the root directory of the project, and use:
```bash
 mvn spring-boot:run
```

2. Access the country API
- all countries: ```/api/example/countries``` via a ```GET``` request
  * Example: http://localhost:8080/api/example/countries
- country by name: ```/api/example/countries/{name}``` via a ```GET``` request
  * Example: http://localhost:8080/api/example/countries/finland

     
