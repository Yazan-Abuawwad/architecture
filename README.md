# Architecture Patterns Examples

This repository demonstrates the same `Todo` use case in separate Git branches.

## Branch map

- `main` -> Layered Architecture (controller -> service -> repository)
- `hexagonal-architecture` -> Hexagonal Architecture (ports and adapters)
- `onion-architecture` -> Onion Architecture (domain core with outward dependencies)
- `clean-architecture` -> Clean Architecture (use cases and interface adapters)

## Current branch

`hexagonal-architecture` contains the Hexagonal Architecture example:

- Endpoint: `POST /hexagonal/todos`
- Endpoint: `GET /hexagonal/todos`
- Endpoint: `POST /hexagonal/cart/items`
- Endpoint: `GET /hexagonal/cart`
- Package root: `com.awwad.example.architecture.hexagonal`

Shopping cart flow in this branch: `adapter/in/web/ShoppingCartController` -> `application/service/ShoppingCartApplicationService` -> outbound ports (`LoadProductPort`, `ReserveStockPort`, `LoadCartPort`, `SaveCartPort`) -> in-memory adapters.

In-memory catalog products are preloaded in `hexagonal/adapter/out/persistence/InMemoryProductCatalogAdapter`:

- `1` -> Laptop, `1000.00`, `10%` discount, stock `5`
- `2` -> Mouse, `25.00`, no discount, stock `20`
- `3` -> Keyboard, `75.00`, `5%` discount, stock `10`

## Run and test

Use Maven Wrapper from project root.

```powershell
.\\mvnw.cmd test
.\\mvnw.cmd spring-boot:run
```

## Create/switch branches

```powershell
git switch main
git switch hexagonal-architecture
git switch onion-architecture
git switch clean-architecture
```
