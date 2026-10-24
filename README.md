# Book Shop Backend

Spring Boot 3.5.0, Java 17+ (works with a compatible newer JDK), Maven, H2, Spring Data JPA, Validation, Spring Security, JWT, Swagger.

## Run
1. Open this folder in IntelliJ as a Maven project.
2. Use JDK 17 or newer and let Maven download dependencies.
3. Run `BookApplication.java` (or `mvn spring-boot:run`).
4. Swagger: http://localhost:8080/swagger-ui/index.html
5. H2 console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:file:./data/bookshop`, username `sa`, blank password).

## Demo librarian
- Username: `librarian`
- Password: `ChangeMe123!`
- Change these in `application.properties` / environment variables before using this outside a local demo.
- Set `app.seed-librarian=false` to disable creation of the demo librarian.
- Public registration always creates a `STUDENT`; clients cannot self-assign `LIBRARIAN`.

## Main endpoints
- `POST /users/register` (student registration)
- `POST /users/login` (returns accessToken, refreshToken, permission)
- `POST /users/refresh` body `{ "refreshToken": "..." }`
- `GET /books?page=0&size=10&sort=name,asc`
- `GET /books/filter?name=...&author=...&page=0&size=10`
- `GET /books/{id}`, `POST /books`, `PUT /books/{id}`, `DELETE /books/{id}`
- `GET /cart`, `POST /cart/items`, `PUT /cart/items/{itemId}`, `DELETE /cart/items/{itemId}`
- `POST /orders` body `{ "address": "..." }`; `GET /orders/my`; `GET /orders/{id}`

After login, use Swagger's Authorize button and enter `Bearer <accessToken>` if the UI prompts for the full value; otherwise paste the token according to the displayed scheme.

Book request example:
```json
{
  "name": "Clean Code",
  "author": "Robert C. Martin",
  "quantity": 5,
  "pageCount": 464,
  "publishDate": "2008-08-01",
  "price": 25.5,
  "barcode": "BK-0001",
  "imageUrl": "https://example.com/book.jpg"
}
```

Date validation currently rejects future dates. If the course assignment specifies a fixed minimum/maximum publish date, update the DTO validation to that exact range.

## Notes
- H2 uses a local file so data survives restarts. Do not expose the H2 console publicly.
- JWT secret and demo credentials are for local development only. Use strong environment-provided secrets for deployment.
- Stock is validated and reduced inside the order transaction; for high-concurrency production systems, add database row locking and use `BigDecimal` for money.
