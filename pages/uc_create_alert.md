## Title: Create Alert

## Primary Actors: Traveler
## Secondary Actors: None

## Preconditions:
- The actor must be registered and logged into the system.

## Postconditions:
- The alert gets stored.

## Flow:
1. The actor opens the application and navigates to the "View Alerts" section.
2. The system loads and shows the overview of all alerts.
3. The actor clicks on "Create new Alert".
4. The system loads and shows the form for creating a new alert.
5. The actor inputs the origin and destination cities, the desired date, and the number of seats needed.
6. The actor confirms the details and submits the form.
7. The system processes the inputs, creates and stores the new alert.
8. The system opens the "View Alerts" overview again with the added alert.

## Alternative Flows:
7a. The inputs are invalid
1.  The system shows an error message indicating the invalid inputs. Go to 3.