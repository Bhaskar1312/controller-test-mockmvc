
mvn spring-boot:run

curl http://localhost:8080/books

curl http://localhost:8080/books/1

curl --location -X POST 'http://localhost:8080/books' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 4,
    "title": "Fundamentals of Software Architecture: An Engineering Approach",
    "author": "Mark Richards, Neal Ford",
    "publisher": "Upfront Books",
    "releaseDate": "Feb 2021",
    "isbn": "B08X8H15BW",
    "topic": "Architecture"
}'

https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html

cd DIRECTORY; mvn test

