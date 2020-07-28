# Ficticius-Clean
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FelipeNathan_Ficticius-Clean&metric=alert_status)](https://sonarcloud.io/dashboard?id=FelipeNathan_Ficticius-Clean)

This API calculate the gasoline consumption and rank all cars registered in database given the price of gasoline, total km to be traveled in the city and total km to be traveled in road.

#### Prerequisites
* Maven
* Java 8+ 
* Lombok

#### Build
* Run `mvn clean package`
    * The packate will be generated at `/target`
    * The name of the package is `ficticiusclean-{version}.jar` where `{version}` is the version tag on pom.xml
    
#### Running
* Run `java -jar ficticiusclean-{version}.jar`
    * The API will run in localhost at port 8080 
    
#### API
For information about API access `http://localhost:8080/swagger-ui.html`
![api doc](/images/api-doc.png?raw=true)
    
#### Usage
First of all, you have to register an vehicle brand
* Using curl on windows: `curl -ContentType "application/json" -Uri "http://localhost:8080/vehicle-brand" -Method Post -Body '{ "name": "HONDA" }'`

After that, you can register a vehicle with this new brand (in this example, we'll register 2 vehicles to check the consumption API)
* Using curl on windows: `curl -ContentType "application/json" -Uri "http://localhost:8080/vehicle" -Method Post -Body '{ "name": "Civic", "brand": "HONDA", "model": "2010", "manufacturingDate": 2009, "gasConsumptionCity": 8, "gasConsumptionRoad": 13 }'`
* Using curl on windows: `curl -ContentType "application/json" -Uri "http://localhost:8080/vehicle" -Method Post -Body '{ "name": "HR-V", "brand": "HONDA", "model": "2010", "manufacturingDate": 2009, "gasConsumptionCity": 7, "gasConsumptionRoad": 15 }'`

And at last, check the rank passing the gasPrice, kmInCity and kmInRoad
* Using curl on windows: `curl -ContentType "application/json" -Uri "http://localhost:8080/vehicle/consumption?gasPrice=4&kmInCity=10&kmInRoad=15"`

### Collaborators
| [@FelipeNathan][felipenathan] |
| :-------------------------------: |
|       ![][p_felipenathan]         |
|         Software Engineer         |

[felipenathan]: http://github.com/FelipeNathan
[p_felipenathan]: https://avatars2.githubusercontent.com/u/16759812?s=100&v=4git
