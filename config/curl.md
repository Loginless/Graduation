### curl samples (application deployed in application context `graduation`).
> For windows use `Git Bash`

#### Get list of restaurants with available menu by menu date. Need to paste menu date.
curl -X GET \
  'http://localhost:8080/rest/restaurants/byMenuDate?date=2019-01-08' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c120c6b5-416f-4043-a977-615ed6ead4e8' \
  -H 'cache-control: no-cache'

#### Vote for restaurant with Id.
curl -X POST \
  'http://localhost:8080/rest/votes/for?restaurantId=100005' \
  -H 'Authorization: Basic dXNlcjJAeWFuZGV4LnJ1OnBhc3N3b3JkMg==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 4a7e07a1-bc6e-4d68-9001-d6a13ff0df14' \
  -H 'cache-control: no-cache'

#### Get list of restaurants with counted votes for today.
curl -X GET \
  http://localhost:8080/rest/restaurants/restaurantVotes \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Postman-Token: 14dbdeba-fc42-492a-b5f2-9965aa5b609b' \
  -H 'cache-control: no-cache'

#### get All Votes
curl -X GET \
  http://localhost:8080/rest/votes \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Postman-Token: 8eb17a89-a0f9-4176-ade6-aa99ccefe938' \
  -H 'cache-control: no-cache'
  