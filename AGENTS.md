# AGENTS.md

## Project at a glance
- This is a minimal Spring Boot service scaffold using Maven Wrapper (`mvnw`, `mvnw.cmd`).
- Entry point: `src/main/java/com/awwad/example/architecture/ArchitectureApplication.java`.
- Base package: `com.awwad.example.architecture` (keep new code under this root for component scanning).
- Java level is 21 (`pom.xml` -> `<java.version>21</java.version>`).
- Spring Boot parent is `4.0.5` (`pom.xml`), so align dependencies/plugins with Boot 4 APIs.

## Current architecture and boundaries
- Architecture is currently single-module and monolith; there are no separate services/modules yet.
- Runtime flow today is only: `main()` -> `SpringApplication.run(...)`.
- There are no controllers/services/repositories yet; adding these is the next expected expansion path.
- Configuration footprint is intentionally small: `src/main/resources/application.properties` only sets `spring.application.name=architecture`.

## Build, test, and run workflows
- Prefer Maven Wrapper over system Maven:
  - Windows: `./mvnw.cmd test`, `./mvnw.cmd spring-boot:run`, `./mvnw.cmd clean package`
  - Unix/macOS: `./mvnw test`, `./mvnw spring-boot:run`, `./mvnw clean package`
- The only existing test is `contextLoads()` in `src/test/java/com/awwad/example/architecture/ArchitectureApplicationTests.java`.
- `@SpringBootTest` in that test means full application context startup is the baseline smoke check.

## Dependency and integration notes
- Declared runtime DB driver: PostgreSQL (`org.postgresql:postgresql`) in `pom.xml`.
- No datasource URL/credentials are committed yet, so DB integration is declared but not configured in repo.
- Lombok is enabled as an optional dependency plus explicit annotation processor config in `maven-compiler-plugin`; preserve this setup if changing compiler plugin config.
- `spring-boot-maven-plugin` excludes Lombok from final artifact; keep this behavior unless packaging requirements change.

## Conventions to follow in this repo
- Keep package naming consistent with `com.awwad.example.architecture` when adding classes.
- Keep startup behavior lightweight: avoid introducing eager startup logic unless required.
- Update `application.properties` only for committed defaults; environment-specific values should stay external.
- Use the existing smoke-test style (`@SpringBootTest`) for first-pass validation when adding framework wiring.
- Respect ignored/generated paths in `.gitignore` (`target/`, IDE metadata, wrapper JAR handling).

## AI-agent discovery notes
- A glob search for existing AI guidance files (`.github/copilot-instructions.md`, `AGENT.md`, `AGENTS.md`, `CLAUDE.md`, Cursor/Windsurf/Cline rule files, `README.md`) returned no matches in this repository at generation time.
- Treat this `AGENTS.md` as the primary in-repo AI workflow guide until project-specific docs are added.

