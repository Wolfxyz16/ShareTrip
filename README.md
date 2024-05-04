# Share Trip

> #### **Warning!**
> This is not a real application. It is a kick-off project made by EHU students for Software Engineering class.

## About

ShareTrip is an open source carpooling application between users that aims to be simple and effective. It also focuses on users privacy not sharing the users data with 3rd party companies.

## Features

* Consult rides available in your zone
* Offer a new ride
* Book the ride which you are interested
* Check your ride status (date, seats, status...)
* Create an alert 
* Send messages between users so you can organize the trip

## Extra features ⭐⭐

* You can add your favourite ride for easy searching
* More to come...

## Last sprint work

* Favorite ride logic
* Password hashing
* Event flows updated
* Builders on domain classes
* Double check when logout
* Language change logic
* JUNIT test
* Long messages preview (when the user hovers over the message)
* Notification (message) when a ride that matches any of your alerts is created
* Remote DB with Azure
* Autocompletation when creating a new city
* UI tweaks
* Labels disappear after some time (threads)
* Book ride use case implementation


## How can (*must*) I contribute

First thing to be done is to clone the repository. Go to the directory where you want to clone this repository. IE ``~/IdeaProjects``. Then copy the ssh URL and paste the next command on your terminal

``git clone git@github.com:Wolfxyz16/ShareTrip.git``

In order to have a clean and organized working experience each member of Orquesta Guayacan would need to create a local branch in the repository. This can be done with the next command:

``git branch YOUR_NAME``

Remember to change your current branch with the next command:

``git checkout YOUR_NAME``

When the task is implemented just push it upstream to the repository. This should create a remote branch in the GitHub repository. Then a pull request should be requested.

With this method we will guarantee that the `main` branch will be always working and with the full features implemented.

## What about the sprints?
1. feb 28 - mar 19
2. mar 20 - apr 12
3. apr 12 - may 4

## How can I pull a single git branch?
1. `git fetch <remote_name> <branch_name>`
2. `git branch <branch_name> FETCH_HEAD`
3. `git checkout <branch_name>`

## Requirements Analysis

[Use Case Model](pages/use_case_model.md)\
[Domain Model](pages/domain_model.md)

[Event Flow for Search Ride Offer](pages/uc_search_ride_offer.md)

[Event Flow for Request Booking for Ride](pages/uc_request_booking_for_ride.md)\
[Event Flow for View Ride Bookings](pages/uc_view_ride_bookings.md)\
[Event Flow for Create Alert](pages/uc_create_alert.md)\
[Event Flow for View Alerts](pages/uc_view_alerts.md)\
[Event Flow for Delete Alert](pages/uc_delete_alert.md)\
[Event Flow for Create Favorite](pages/uc_create_favorite.md)\
[Event Flow for View Favorites](pages/uc_view_favorites.md)\
[Event Flow for Delete Favorite](pages/uc_delete_favorite.md)

[Event Flow for Send a Message](pages/uc_send_a_message.md)\
[Event Flow for View Messages](pages/uc_view_messages.md)

[Event Flow for Create a City](pages/uc_create_a_city.md)\
[Event Flow for Offer a Ride](pages/uc_offer_a_ride.md)\
[Event Flow for View Booking Requests](pages/uc_view_booking_requests.md)\
[Event Flow for Decide Booking Requests](pages/uc_decide_booking_requests.md)

## Design

[Sequence Diagram "Query Rides"](pages/sequence_diagramm_query_rides.md)
