## Title: Request Booking for Ride

## Primary Actors: Traveler
## Secondary Actors: Driver

## Preconditions:
- The actor must be registered and logged into the system.
- The driver has created a ride offer available on the system.

## Postconditions:
- The booking request is sent to the selected driver.

## Flow:
1. The actor navigates to the "Search Ride Offer" section.
2. The system loads and shows the form for searching for ride offers.
3. The actor inputs the origin and destination cities, the desired date, and the number of seats needed.
4. The actor confirms the details and submits the form.
5. The system processes the search criteria and displays a list of available rides that match the user's requirements.
6. The actor can view the details of each ride offer and select a specific ride offer from the list.
7. The actor confirms their intent to book the ride.
8. The system sends a booking request to the selected driver via email.

## Alternative Flows:
5a. The inputs are invalid
1.  The system shows an error message indicating the invalid inputs. Go to 3.