## Title: Create a City

## Primary Actors: Driver
## Secondary Actors: None

## Preconditions:
- The actor must be registered and logged into the system.

## Postconditions:
- The new city is now available in the system.

## Flow:
1. The system opens a form where the actor can add a name for the new city.
2. The actor inputs the name of the new city.
3. The system processes the input, creates and stores the new city.

## Alternative Flows: None
3a. Ride already exists
    1. The system shows an error message
       Go to 1.