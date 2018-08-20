# services.getting.started 

Learn how to create Services the Synoa Style.

## Prerequisites

You need to install the following Software:

* Java >= 1.8
* Maven >= 3.x
* Docker >= 1.13
* git
* a Terminal you are familiar with
* Studio3T or Robo3T (mongoDB Client)
* IntelliJ or Eclipse

## Step 1 - Check your local environment

* Open a Terminal and change the Directory to the root folder of this project.
* Execute the following commands
```
docker-compose up -d
cd helloworld/
mvn spring-boot:run
```
* You should see the Log Message `Hello Microservice World` in your Terminal printed every 5 Seconds.
* Stop it with `CTRL-C`

## Step 2 - Make yourself familiar with the project

* Look through all the files of the project
* Don't forget to look into the `pom.xml`
* Have a look at the Documentation of the [Camel Components](https://github.com/apache/camel/blob/camel-2.20.4/components/readme.adoc).
* Change the `greeting.name` to something else.

## Step 3 - Read in CSV files

* Create a new RouteBuilder called `ReadPersonCSVRouteBuilder`
* In this RouteBuilder read in the file `example-data/persons.csv` from the directory `helloworld/data/in`
* Unmarshall the CSV
* Convert the birthday to a date
* Save every line as single document into the mongoDB
* Add useful Log entries
* Delete the GettingStartedRouteBuilder

## Step 4 - Error handling

* Try to import `example-data/persons-error.csv`
* Fail hard
* Try to import everything and skip the faulty line
* Use a log message to show which person failed

## Step 5 - Send Person Id's to ActiveMQ

* Add an unique Index for `personalNumber` with mongobee (you can assume that the collection is empty)
* Drop the `genisys` Database in mongoDB
* Upsert the persons in the mongoDB instead of inserting them
* Send the `personalNumber` of a successful upserted person to the Queue `persons`
* Test your Service with and without transactional ActiveMQ Component. Figure out what is the difference and think about what could be the better choice

## Step 6 - Service to write JSON's

* Add a new Service with the name `person.out.file`
* Use the Genisys [Archetype](https://github.com/synoa/genisys.archetype) to create it.
* This Service should read the imported person from the mongoDB and write it in the following format as file:
```json
{
    "birthday" : "23.10.1994",
    "name" : "Goff, Emery",
    "phone" : "(038905) 439257",
    "company" : "Vivamus Euismod Foundation",
    "pNumber" : "16661223-0604"
}
```
* Use a Model and Jackson to create the JSON
* Write the files into the directory `person.out.file/data/out`

## Step 7 - Aggregate 100 persons in one JSON

* Change the Route, so that always 100 persons are aggregated together in a single valid JSON file