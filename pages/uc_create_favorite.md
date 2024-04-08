## Title: Create Favorite

## Primary Actors: Traveler
## Secondary Actors: None

## Preconditions:
- The actor must be registered and logged into the system.

## Postconditions:
- The favorite gets stored.

## Flow:
1. The actor opens the application and navigates to the "View Favorites" section.
2. The system loads and shows the overview of all favorites.
3. The actor clicks on "Create new Favorite".
4. The system loads and shows the form for creating a new favorite.
5. The actor inputs the origin and destination cities, the desired date, and the number of seats needed.
6. The actor confirms the details and submits the form.
7. The system processes the inputs, creates and stores the new favorite.
8. The system opens the "View Favorites" overview again with the added favorite.

## Alternative Flows:
7a. The inputs are invalid
1.  The system shows an error message indicating the invalid inputs. Go to 3.