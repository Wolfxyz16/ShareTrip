## Title: Query Rides

## Primary Actors: NotLogged User
## Secondary Actors: None

## Preconditions:
- The actor has access to the application.

## Postconditions:
- The actor is presented with a list of available rides that match the search criteria.

## Flow:
1. The actor opens the application and navigates to the "Query Rides" section.
2. The system loads and shows the form for searching for ride offers.
3. The actor inputs the origin and destination cities, the desired date, and the number of seats needed.
4. The actor confirms the details and submits the form.
5. The system processes the search criteria and displays a list of available rides that match the user's requirements.
6. The actor can view the details of each ride offer, but can also click a button for creating an alert for new ride offers with the given inputs or save this search as a favorite.

## Alternative Flows:
5a. The inputs are invalid
1.  The system shows an error message indicating the invalid inputs. Go to 3.