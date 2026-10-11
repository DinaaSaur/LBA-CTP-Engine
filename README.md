# LBA Campaigns Engine

Spring Boot engine that watches the `campaigns` table and keeps an up-to-date list of campaigns that are currently running.

## Running rule

A campaign is considered running when:

- `status = RUNNING`
- `start_time <= now`
- `end_time` is `null` or `end_time > now`

On every scan, the engine compares the current database result with the previous in-memory set:

- New IDs are published as `STARTED`
- Missing IDs are published as `ENDED`

The default sender logs events. Replace `LoggingCampaignEventSender` with a Kafka, HTTP, or queue implementation when you are ready to integrate with another service.

## Run locally

This project intentionally follows the compatible SMPP engine baseline:

- Spring Boot `2.7.18`
- Java `1.8`

```bash
mvn spring-boot:run
```

The local profile uses an in-memory H2 database. Configure a real database with:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lba
spring.datasource.username=...
spring.datasource.password=...
```

## Endpoints

- `GET /campaigns/running` returns the currently tracked running campaign IDs.
