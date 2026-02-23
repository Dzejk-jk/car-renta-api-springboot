# 🚗 Car Rental API

REST API pro správu autopůjčovny vytvořené v Spring Boot.

## Technologie

- Java 25
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- Swagger UI

## Spuštění projektu

1. Naklonuj repozitář
2. Vytvoř databázi
3. Uprav `application.properties` dle svého nastavení databáze
4. Spusť aplikaci přes `mvn spring-boot:run`
5. Swagger UI dostupné na: `http://localhost:8080/swagger-ui.html`

## Struktura projektu
```
src/
├── controller/    # REST endpointy
├── service/       # Business logika
├── entity/        # JPA entity
├── dto/           # Data Transfer Objects
├── mapper/        # MapStruct mappery
├── repository/    # Spring Data JPA repositories
├── enums/         # CarStatus, ReservationStatus
└── exception/     # GlobalExceptionHandler
```

## Entity

- **Car** – automobil v půjčovně (značka, model, cena za den, status)
- **Customer** – zákazník půjčovny
- **Reservation** – rezervace auta zákazníkem

## API Endpointy

### Auta
| Metoda | Endpoint | Popis |
|--------|----------|-------|
| GET | `/api/cars` | Všechna auta |
| GET | `/api/cars?status=AVAILABLE` | Dostupná auta |
| GET | `/api/cars/{id}` | Detail auta |
| POST | `/api/cars` | Přidat auto |
| PUT | `/api/cars/{id}` | Upravit auto |
| DELETE | `/api/cars/{id}` | Smazat auto |

### Zákazníci
| Metoda | Endpoint | Popis |
|--------|----------|-------|
| GET | `/api/customers` | Všichni zákazníci |
| GET | `/api/customers/{id}` | Detail zákazníka |
| POST | `/api/customers` | Přidat zákazníka |

### Rezervace
| Metoda | Endpoint | Popis |
|--------|----------|-------|
| POST | `/api/reservations` | Vytvořit rezervaci |
| GET | `/api/reservations/{id}` | Detail rezervace |
| GET | `/api/reservations/customer/{customerId}` | Rezervace zákazníka |
| PUT | `/api/reservations/{id}/cancel` | Zrušit rezervaci |
| PUT | `/api/reservations/{id}/complete` | Dokončit rezervaci |

## Business logika

- Auto nelze rezervovat pokud není ve stavu `AVAILABLE`
- Při vytvoření rezervace se automaticky vypočítá cena (počet dní × cena za den)
- Při zrušení nebo dokončení rezervace se auto vrátí do stavu `AVAILABLE`
- Datum konce rezervace musí být po datu začátku
