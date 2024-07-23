### Endpoints
`run application`

-`1. docker-compose up -d`

-`2.run tomcat 9 `

Firstly you must register if you haven't done so before and log in.

- `POST /auth/login`
- `POST auth/register`
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
- `GET /booking-free` - shows all free booking slots
- `POST /booking-by-date` - shows all booking by date
  {
  "date":"2024 02 06"
  }
- `PUT /booking/reserve` reserves a slot with given id if it's free
- `PUT /booking/release` releases a slot with given id
  {
  "id":100005
  }
