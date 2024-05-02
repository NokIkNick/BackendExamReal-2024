#### Task 2 REST Error Handling:

#### Exceptionlog can be found at the root of the project "exceptionLog.txt".


<table>
<tr>
<th>HTTP METHOD</th>
<th>REST Resource</th>
<th>Exceptions and Status code</th>
</tr>
<tr>
<td>GET</td>
<td>/api/cars</td>
<td>APIException, NOT_FOUND 404</td>
</tr>
<tr>
<td>GET</td>
<td>/api/cars/{id}</td>
<td>APIException, NOT_FOUND 404. NumberFormatException</td>
</tr>
<tr>
<td>POST</td>
<td>/api/cars</td>
<td>APIException, 400</td>
</tr>
<tr>
<td>PUT</td>
<td>/api/cars/{id}</td>
<td>Exception, NOT_FOUND 404. NumberFormatException, UNPROCESSABLE_CONTENT 422, UnprocessableContentResponse</td>
</tr>
<tr>
<td>DELETE</td>
<td>/api/cars/{id}</td>
<td>Exception, NOT_FOUND 404. NumberFormatException</td>
</tr>
</table>

#### Task 4 & 5: JPA endpoint testing:

### Difference between RestAssured testing and JUnit testing:
- JUnit testing is testing a specific method or component. while RestAssured/endpoint testing is testing the whole flow of the endpoint, from controller to dao and back. JUnit usually don't require network requests / https requests. RestAssured is usually used for verifying the behaviour of a Restful API.

<table>
<tr>
<th>HTTP METHOD:</th>
<th>REST RESOURCE:</th>
<th>JSON:</th>
<th>DESCRIPTION:</th>
</tr>
<td>
GET
</td>
<td>
/api/cars
</td>
<td>
response: [
  {
    "id": 1,
    "brand": "Volkswagen",
    "model": "Polo",
    "make": "Volkswagen",
    "year": 2007,
    "firstRegistrationDate": [
      2010,
      9,
      12
    ],
    "price": 125000.0
  },
...
]
</td>
<td>
Retrieve all cars.
</td>


<tr>
<td>
GET
</td>
<td>
/api/cars/{id}
</td>
<td>
response: {
    "id": 1,
    "brand": "Volkswagen",
    "model": "Polo",
    "make": "Volkswagen",
    "year": 2007,
    "firstRegistrationDate": [
      2010,
      9,
      12
    ],
    "price": 125000.0
  }
</td>
<td>
Retrieve specific car by id.
</td>
<tr>


<tr>
<td>
POST
</td>
<td>
/api/cars
</td>
<td>
payload: {
  "brand":"Citroen",
  "model":"Cactus 5",
  "make":"Citroen",
  "year":2010,
  "firstRegistrationDate":"2012-08-07",
  "price":175000
}

response: {
"id": 5,
"brand": "Citroen",
"model": "Cactus 5",
"make": "Citroen",
"year": 2010,
"firstRegistrationDate": [
2012,
8,
7
],
"price": 175000
}
</td>
<td>
Add a new car. The created product object should be returned with the assigned id.
</td>
</tr>

<tr>
<td>
PUT
</td>
<td>
/api/cars/{id}
</td>
<td>
payload: {
    "brand":"Volkswagen",
    "model":"Tiguan",
    "make":"Volkswagen",
    "year":2004,
    "firstRegistrationDate":"2009-09-11",
    "price":125000
}

response: {
"id": 1,
"brand": "Volkswagen",
"model": "Tiguan",
"make": "Volkswagen",
"year": 2004,
"firstRegistrationDate": [
2009,
9,
11
],
"price": 125000
}
</td>
<td>
Updates an existing car.
</td>
</tr>

<tr>
<td>
DELETE
</td>
<td>
/api/cars/{id}
</td>
<td>
response: {
"id": 1,
"brand": "Volkswagen",
"model": "Tiguan",
"make": "Volkswagen",
"year": 2004,
"firstRegistrationDate": [
2009,
9,
11
],
"price": 125000
}
</td>
<td>
Deletes an existing car.
</td>
</tr>





</table>
