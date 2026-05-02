# Architecture Patterns Examples

This repository demonstrates the same `Todo` use case in separate Git branches.

## Branch map

- `main` -> Layered Architecture (controller -> service -> repository)
- `hexagonal-architecture` -> Hexagonal Architecture (ports and adapters)
- `onion-architecture` -> Onion Architecture (domain core with outward dependencies)
- `clean-architecture` -> Clean Architecture (use cases and interface adapters)

## Current branch

`clean-architecture` contains the Clean Architecture example:

- Endpoint: `POST /clean/todos`
- Endpoint: `GET /clean/todos`
- Endpoint: `POST /clean/cart/items`
- Endpoint: `GET /clean/cart`
- Package root: `com.awwad.example.architecture.clean`
- Layers: `core` (entities, use-case boundaries, interactors, gateway interfaces) → `dataprovider` (gateway impls) → `entrypoint` (web controllers)

Shopping cart flow in this branch: `entrypoint/web/ShoppingCartController` -> `core/usecase/AddItemToCartInteractor` -> `core/gateway/ProductGateway` + `core/gateway/CartGateway` -> `dataprovider` in-memory adapters.

In-memory catalog products are preloaded in `clean/dataprovider/InMemoryProductGateway`:

- `1` -> Laptop, `1000.00`, `10%` discount, stock `5`
- `2` -> Mouse, `25.00`, no discount, stock `20`
- `3` -> Keyboard, `75.00`, `5%` discount, stock `10`

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

