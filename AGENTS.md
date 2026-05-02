# AGENTS.md

## Project scope
- This repo demonstrates the same `Todo` use case using multiple architecture styles, with one style per Git branch.
- Branch map from `README.md`: `main` (Layered), `hexagonal-architecture`, `onion-architecture`, `clean-architecture`.
- Base Java package stays under `com.awwad.example.architecture`.

## What is in this working tree now
- `src/main/java/com/awwad/example/architecture/layered` is the concrete example in this branch.
- Layered flow example: `layered/api/TodoController` -> `layered/service/TodoService` -> `layered/repository/TodoRepository`.
- Layered shopping-cart flow example: `layered/api/ShoppingCartController` -> `layered/service/ShoppingCartService` -> `layered/repository/ProductRepository` + `layered/repository/CartRepository`.
- Current implemented packages are `layered/api`, `layered/service`, `layered/repository`, and `layered/domain`; canonical `application/infrastructure/presentation` packages are not implemented in this branch.
- Exposed endpoints in this branch: `POST /layered/todos`, `GET /layered/todos`, `POST /layered/cart/items`, `GET /layered/cart`.
- Uses in-memory persistence (`AtomicLong`, `List`, and `Map`) for demo behavior.

## Canonical structure for new work
Use this structure inside each architecture example package (for example `com.awwad.example.architecture.<pattern>`):

```text
domain/
  entity/
  repository/

application/
  usecase/

infrastructure/
  database/
  security/

presentation/
  controller/
```

- Put business models and repository contracts in `domain`.
- Put application orchestration/use-case handlers in `application/usecase`.
- Put framework/IO adapters in `infrastructure` (DB adapters, auth/security wiring).
- Put HTTP layer classes in `presentation/controller`.

## Conventions discovered from code
- Validation style: normalize and reject blank title in use-case/service layer, throwing `IllegalArgumentException` with message `title is required`.
- Shopping-cart validation style: reject null product id (`productId is required`), non-positive quantity (`quantity must be greater than zero`), unknown product (`product not found`), and unavailable stock (`insufficient stock`) in `layered/service/ShoppingCartService`.
- API error mapping style: each architecture has a scoped `@RestControllerAdvice(basePackageClasses = TodoController.class)` returning `{ "error": "..." }` with `400`.
- Request DTO style: Java records (for example `CreateTodoRequest`) in the web/API package.
- In-memory ID allocation starts at `1` (`new AtomicLong(1)` in `layered/repository/InMemoryTodoRepository`), and create/list API expectations are asserted in `src/test/java/com/awwad/example/architecture/layered/LayeredTodoControllerTests.java`.
- Shopping-cart pricing style uses `BigDecimal` and applies per-product percentage discounts before cart totals (`layered/service/ShoppingCartService`), with seeded catalog data in `layered/repository/InMemoryProductRepository`.
- Keep startup lightweight: no eager boot logic beyond Spring wiring.

## Build/test/run workflows
- Use Maven Wrapper from repo root.
- Windows:
  - `.\mvnw.cmd test`
  - `.\mvnw.cmd spring-boot:run`
  - `.\mvnw.cmd clean package`
- Test style uses `@SpringBootTest` + `@AutoConfigureMockMvc` integration tests:
  - `src/test/java/com/awwad/example/architecture/layered/LayeredTodoControllerTests.java`
  - `src/test/java/com/awwad/example/architecture/layered/LayeredShoppingCartControllerTests.java`
  - `src/test/java/com/awwad/example/architecture/ArchitectureApplicationTests.java`

## Dependency and integration notes
- Java 21 (`pom.xml`), Spring Boot parent `4.0.5`.
- PostgreSQL driver is declared, but the checked-in implementation currently uses in-memory repositories/gateways.
- Preserve Lombok compiler annotation processor config and the Spring Boot plugin Lombok exclusion when editing `pom.xml`.

## AI guidance source scan
- Searched once using: `**/{.github/copilot-instructions.md,AGENT.md,AGENTS.md,CLAUDE.md,.cursorrules,.windsurfrules,.clinerules,.cursor/rules/**,.windsurf/rules/**,.clinerules/**,README.md}`.
- Found: `AGENTS.md`, `README.md`.
