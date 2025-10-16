User Registration Application
=============================

In Rest based design, resources are being manipulated using a common set of verbs.

    To Create a resource : HTTP POST should be used
    To Retrieve a resource : HTTP GET should be used
    To Update a resource : HTTP PUT should be used
    To Delete a resource : HTTP DELETE should be used


This is what our REST API does:


user/  - GET, DELETE, POST
/user/<id> - GET, PUT, DELETE


    GET request to /user/ returns a list of users
    GET request to /user/1 returns the user with ID 1
    POST request to /user/ with a user object as JSON creates a new user
    PUT request to /user/3 with a user object as JSON updates the user with ID 3
    DELETE request to /user/4 deletes the user with ID 4
    DELETE request to /user/ deletes all the users


1. Retrieve all users
GET ->  http://localhost:8080/user

Now, let's retry the GET, with an Accept header this time with value "application/xml"

2. Retrieve a single user
GET -> http://localhost:8080/user/1

3. Retrieve an unknown user
GET -> http://localhost:8080/user/9999

4. Create a User (content-type as : application/json)
POST ->  localhost:8080/user/
{
   "name":"ramana",
   "age": 50,
   "salary": 50000
}

New user would be created and will be accessible at the location mentioned in Location header.

5. Update an existing user
Use PUT, specify the content in body and type as "application/json".
PUT-> http://localhost:8080/user/5
{
   "name":"ramana",
   "age":48,
   "salary":70000
}

8. Delete an existing user
Use DELETE, specify the id in url, send. User should be deleted from server.
DELETE > http://localhost:8080/user/2



Check with Pagination and Filtering logic:
-----------------------------------------------

http://localhost:8080/user/page?pageNo=0&pageSize=2&sortBy=name&minSalary=1000


To access swagger-ui
====================

http://localhost:8080/swagger-ui/index.html




