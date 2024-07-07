### Endpoints

Firstly you must register if you haven't done so before and log in.

- `POST /login`
  `` json for log in:``
  {
  "email":"default@email.com",
  "password":"password"
  }

and then you are allowed to interact.
- `GET /space-all` - obtain all space entries
- `GET, DELETE /space` - deletion, and getting by id spaces
  {
  "id":100002
  }
- `POST /space` - add new entry to the space table
  {
  "location":"some",
  "spaceType":"WORKING_SPACE"
  }
- `GET, DELETE /booking` - deletion, and getting by id bookings
- `POST /booking` - add new entry to the booking table
- `GET /booking-free` - 


