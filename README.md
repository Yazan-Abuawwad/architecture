# Architecture Patterns Examples

This repository demonstrates the same `Todo` use case in separate Git branches.

## Branch map

- `main` -> Layered Architecture (controller -> service -> repository)
- `hexagonal-architecture` -> Hexagonal Architecture (ports and adapters)
- `onion-architecture` -> Onion Architecture (domain core with outward dependencies)
- `clean-architecture` -> Clean Architecture (use cases and interface adapters)

## Current branch

`main` contains the Layered Architecture example:

- Endpoint: `POST /layered/todos`
- Endpoint: `GET /layered/todos`
- Endpoint: `POST /layered/cart/items`
- Endpoint: `GET /layered/cart`
- Package root: `com.awwad.example.architecture.layered`

Shopping cart flow in this branch: `ShoppingCartController` -> `ShoppingCartService` -> `ProductRepository` + `CartRepository`.

In-memory catalog products are preloaded in `InMemoryProductRepository`:

- `1` -> Laptop, `1000.00`, `10%` discount, stock `5`
- `2` -> Mouse, `25.00`, no discount, stock `20`
- `3` -> Keyboard, `75.00`, `5%` discount, stock `10`

## Canonical structure for each architecture branch

Use this package layout under `com.awwad.example.architecture.<pattern>`:

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

## Run and test

Use Maven Wrapper from project root.

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Create/switch branches

```powershell
git switch main
git switch hexagonal-architecture
git switch onion-architecture
git switch clean-architecture
```
