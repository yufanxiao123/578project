# CSCI578 Team 6 project project

Guideline on launching the app

You need to open ./patrol directory in Android Studio and run MainActivity. We recommend
Pixel 8 as the virtual device and VanillaiceCream(Android 15) as the Android Operating System.

To run backend Do following:
cd ./backend
pip install -r requirement.txt
flask run

Project Description

Back End:
For the backend, we provided 3 APIs for the mobile end to use.

To provide these functions we chose to use Flask as our backend structure in combination with
blueprint and sqlalchemy to provide a MVC structure. For the database, we used SqLite to
provide persistent storage of our location, and news data.

To simulate the process of gathering data we created a COR task using APScheduler, and with
the Flask app, we will start a task that will update the location data every 10 mins.
The Flask app serves as a controller that will query the database for all the people that are sick in
the range of 0.1 degrees, provide news related to COVID-19, and give history data in the past 5
months


Mobile end:

We have two packages. One is the view package (com.example.patrol.view). This package is to
display the user interface of our app, coordinates with XML files in the res/layout directory. For
each functionality, we build an activity for users to see the result they request. For each activity,
we also created a ViewModel file to dynamically show variables on the view.

The other package is the logic package (com.example.patrol.logic). This package is to receive
user input from the user interface, process it, and send requests to the backend. After receiving a
response from the backend, it will update the user interface with ViewModel files.
