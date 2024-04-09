## Title: Create Favorite

## Primary Actors: Traveler
## Secondary Actors: None

## Preconditions:
- The actor must be registered and logged into the system.

## Postconditions:
- The favorite gets stored.

## Flow:
1. The actor opens the application and navigates to the "Search Ride Offer" section.
2. The system loads and shows the form for searching for ride offers.
3. The actor inputs the origin and destination cities, the desired date, and the number of seats needed.
4. The actor confirms the details and submits the form.
5. The system processes the search criteria and displays a list of available rides that match the user's requirements.
6. The actor can view the details of each ride offer, but can also click a button for creating a favorite for ride offers with the given inputs.
7. The actor clicks on the favorite button.
8. The system processes the inputs, creates and stores the new favorite.

## Alternative Flows:
5a. The inputs are invalid
1.  The system shows an error message indicating the invalid inputs. Go to 3.