## Title: Offer a Ride

## Primary Actors: Driver
## Secondary Actors: None

## Preconditions:
- The actor must be registered and logged into the system.

## Postconditions:
- The ride is registered in the system and is visible to other users.
- If a new city was created, it is now available in the system.

## Flow:
1. The driver selects the "Offer a Ride" option in the system.
2. The system loads and shows the form for offering a ride.
3. The driver fills out the form with the origin and destination cities, date, number of seats, and price per passenger.
4. The driver confirms the details and submits the form.
5. The system processes the inputs, creates the new ride offers and stores it.

## Alternative Flows:
3a.  If the origin or destination city does not exist in the system, the driver is offered the option to create a new city.
1. The driver enters the "Create a City" flow of events
2. After that flow is finished, go to 2.