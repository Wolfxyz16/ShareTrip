# Send a Message / Flow of events

## Title: Send a Message 

## Primary Actors: Messenger
## Secondary Actors: None

## Preconditions:
- The actor must be registered and logged into the system.

## Postconditions:  
- The actor´s message is sent.
- The message exchange is recorded in the system.

## Flow:
1. The actor selects the "View Messages" option in the system.
2. The system shows the actors chats in a overview.
3. The actor selects a chat he wants to send a message in from the overview and clicks on it.
4. The system opens the chat history.
5. The actor writes a message in the given text field and clicks on "Send".
6. The system sends the message.
7. The system opens the "View Messages" overview again with the new message.

## Alternative Flows:
2a. If the actor has no messages yet.
    1. The system informs the user that there no messages for him and breaks.
5a. If the actor wants to cancel the reply after typing a message:
    1. The actor selects the "Cancel" option.
    2. The system discards the written message and does not send it.