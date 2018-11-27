# cooking-recipes
This project has been implemented using [Spring Boot 2.1](https://spring.io/projects/spring-boot)

## Build requirements
- JDK 8 installed
- Maven 3 installed

## Build artifact
In a terminal run the next command:

`mvn clean package`

The artifact can be found in the next path:

<project-path>/target/recipes-0.0.1-SNAPSHOT.jar

## Run the project
Execute the next command:

`java -jar recipes-0.0.1-SNAPSHOT.jar`

# Implementation details

## Security
As part of the exercise we avoid the use spring-security nor any common web security.
In order to simply this the project need to add a http header (email-pwd) containing the credential in this format *userEmail:Password* as the next example:

`email-pwd: perez@gmail.com:myPwd`

## User manual

The application is a web service, and define an API to interact with it, no UI implemented.

### Register a new User

Example request:

```
POST http://localhost:8080/users
Accept: */*
Cache-Control: no-cache
Content-Type: application/json

{ "fullName": "Luis Roberto", "password": "myPassword", "email": "perez@gmail.com", "birthInDdMmYy": "15-09-2018" }
```

Expected reponse:
```
HTTP/1.1 201 
Location: http://localhost:8080/users/1
Content-Length: 0
Date: Tue, 27 Nov 2018 11:45:26 GMT

<Response body is empty>
```

This user will have the next credential to add in the http headers *perez@gmail.com:MyPassword*

### View all users public information (Full Name & email)

This resource is Paginable, then you can pass Query Params as page and limit to get bunch of results

Example Request for default Pagination:

```
GET http://localhost:8080/users
Accept: */*
Cache-Control: no-cache
```

Example Request for default Pagination Page 0, Limit 2:

```
GET http://localhost:8080/recipes?page=0&size=2
Accept: */*
Cache-Control: no-cache
```

Expected Response:
```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 27 Nov 2018 14:15:35 GMT

{
  "content": [
    {
      "fullName": "Luis Roberto",
      "email": "perez@gmail.com"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "pageSize": 20,
    "pageNumber": 0,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 1,
  "size": 20,
  "number": 0,
  "empty": false
}
```

### Get personal information, using credentials

This is only accessible using credentials (email-pwd header)

Example request:
```
GET http://localhost:8080/users/personal
Accept: */*
Cache-Control: no-cache
email-pwd: perez@gmail.com:myPassword   <-- Note this, a custom header as credential
```
Expected Response:
```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 27 Nov 2018 12:42:01 GMT

{
  "id": 1,
  "email": "perez@gmail.com",
  "password": "myPassword",
  "fullName": "Luis Roberto",
  "birthDate": "2018-09-15T04:00:00.000+0000",
  "registeredAt": "2018-11-27T12:41:51.953+0000"
}
```

### Update personal information

- This is only accessible using credentials (email-pwd header)
- You have to add full fields to update the User Data 

Example Request
```
PUT http://localhost:8080/users/personal
Accept: */*
Cache-Control: no-cache
email-pwd: perez@gmail.com:myPassword
Content-Type: application/json

{ "fullName": "Luis Roberto Perez", "password": "myPassword", "email": "perez@gmail.com", "birthInDdMmYy": "15-09-1987" }
```

Expected Response:
```
HTTP/1.1 200 
Content-Length: 0
Date: Tue, 27 Nov 2018 13:05:07 GMT

<Response body is empty>
```

Note that if you change your email or password, the credentials will be updated as well.

### Create recipes

The recipe credential is only possible using User credentials (email-pwd header)

Example Request:
```
POST http://localhost:8080/recipes
Accept: */*
Cache-Control: no-cache
email-pwd: perez@gmail.com:myPassword
Content-Type: application/json

{"name":"Ice Cream","content":"mix milk with favorite flavors and put it into the freezer"}
```

Expected Response:
```
HTTP/1.1 201 
Location: http://localhost:8080/recipes/2  <-- This link is not functional yet, I did no see the value to get recipe by ID
Content-Length: 0
Date: Tue, 27 Nov 2018 13:09:10 GMT

<Response body is empty>
```

### View all users' recipes

This resource is Paginable, then you can pass Query Params as page and limit to get bunch of results

No credentials needed

Example Request for default Pagination:
```
GET http://localhost:8080/recipes
Accept: */*
Cache-Control: no-cache
```

Example Request for default Pagination Page 0, Size 2:
```
GET http://localhost:8080/recipes?page=0&size=2
Accept: */*
Cache-Control: no-cache
```

Expected Response:

```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 27 Nov 2018 14:33:16 GMT

{
  "content": [
    {
      "name": "Ice Cream",
      "content": "mix milk with favorite flavors and put it into the freezer"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "pageSize": 2,
    "pageNumber": 0,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 1,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 1,
  "size": 2,
  "number": 0,
  "empty": false
}
```

### View my user recipes

Based on user credentials.

Example Request:
```
GET http://localhost:8080/recipes/personal
Accept: */*
Cache-Control: no-cache
email-pwd: perez@gmail.com:myPassword
```

Expected Response:
```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 27 Nov 2018 13:13:56 GMT

[
  {
    "name":"Ice Cream",
    "content":"mix milk with favorite flavors and put it into the freezer"
  }
]
```

### Find recipes

The first version is to search a string (one or more words) in the both name and content Recipe fields.

The key to search is keyWord passed as Query parameter

Example Request:

```
GET http://localhost:8080/recipes/search?keyWord=ice cream
Accept: */*
Cache-Control: no-cache
```

Expected Response, found in **name**
```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 27 Nov 2018 13:19:21 GMT

[
  {
    "name": "Ice Cream",
    "content": "mix milk with favorite flavors and put it into the freezer"
  }
]
```

Additional Request Example:
```
GET http://localhost:8080/recipes/search?keyWord=flavors
Accept: */*
Cache-Control: no-cache
```

Expected Response, found keyWord in **content**
```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 27 Nov 2018 13:19:21 GMT

[
  {
    "name": "Ice Cream",
    "content": "mix milk with favorite flavors and put it into the freezer"
  }
]
```

### Modify Recipes

Only the User is allowed to edit its recipes, then User Credentials are needed.

Additionally to know what recipes is the goal to be edited, the system ask for the current Recipe Name.

Then you have to provide the Original Name and the full new Data to update in the Recipe.

Example Request:
```
PUT http://localhost:8080/recipes/personal
Accept: */*
Cache-Control: no-cache
email-pwd: perez@gmail.com:myPassword
Content-Type: application/json

{"originalName": "Ice Cream", "recipeEntry": {"name":"agua ardiente","content":"dont mix"}}
```

Expected Response:
```
HTTP/1.1 200 
Content-Length: 0
Date: Tue, 27 Nov 2018 13:35:44 GMT

<Response body is empty>
```

### Delete Recipes

Only the User is allowed to delite its recipes, then User Credentials are needed.

You have to provide the Recipe Name to be deleted.

Example Request:
```
DELETE http://localhost:8080/recipes/personal?recipeNameToDelete=Ice Cream
Accept: */*
Cache-Control: no-cache
email-pwd: perez@gmail.com:myPassword
```

Expected Response:
```
HTTP/1.1 200 
Content-Length: 0
Date: Tue, 27 Nov 2018 13:38:33 GMT

<Response body is empty>
```

## Known missing implementation

- Find Recipes is not serving paginated results. This should be fixed as next step.
- In this first version, for edit/update user or recipe data is mandatory to send all the fields, this could be improved updating only the new data, instead to force users to send the whole data.