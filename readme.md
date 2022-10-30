# User-service

## Roles:

### ROLE_USER : Normal User, who signed up for the app.
> * Has the ability to view their profile, edit.
>> * View trash coins earned.
>> * View leader board.
>> * *and much more*.



### ROLE_ADMIN : User who are allowed to access specific route by the moderator. Normally people from the urzad.
> * has the ability to view their profile, user profile, edit or deactivate certain user.
>> * Admin dashboard from the front end. !!**Todo**🤔


### ROLE_MODERATOR : People who work on the app (creator of the app).
> * has the ability to view their profile, admin profile and userProfile.
> * ***super-user*** Capability.

## Test the service locally 📍
Clone the Repo
> 1. use `git clone ` command to clone the repo. <br>
> 2. After cloning install all maven dependency. <br>
> 3. setup database of your choice and add respective dependency for maven. configure `main/java/resources/application.properties`.
> 4. Run the service using the ^R (mac).
> 5. Application starts without any errors.

Test API endpoints using POSTMAN📮 <br>
**create user** <br>
`POST` `http://localhost:8080/user/auth/register` <br>
~~~
{
    "username":"Marcin",
    "email":"marcin@gmail.com",
    "role":[
        "user","admin","mod"
    ],
    "password":"marcinkaminski"
}
~~~
#### here we take basic inputs from the frontend along with roles. and store this data in backend. <br>
### todo : response status for now is just a string we will implement email verification to activate account later on.

**login user** <br>
`POST` `http://localhost:8080/user/auth/signin` <br>
~~~
{
    "username":"Marcin",
    "password":"marcinkaminski"
}
~~~

### we login user with username and password. <br>
### we will return a JWT token like this one: <br> 
`eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXJjaW4iLCJpYXQiOjE2NjcxMzQ4MjksImV4cCI6MTY2NzIyMTIyOX0.DE2x_r4Ddx19JURDK1QGvUAi3150TSewZCWzsJP07d2eTHoBeHp6ggKkV0a1Ra-ovLO5Ndy8t4_IfmIYizlTXg` <br>

#### what values does this token contains?

![JWT image](https://user-images.githubusercontent.com/52125327/198880600-5cb41922-a0b5-40b8-b446-e7eaff78f9ca.png)