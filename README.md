# Projektinformationen
Topic: creating a rudimentary webApp<br>
Deadline: Sunday, 15. January 2023, 23:59

# T-Shirt Configurator
- Kunde kann ein T-Shirt konfigurieren und bestellen.

## Features
### Grund-Features
- Schnitt wählen (Rundkragen, V-Ausschnitt, Polo, eng/weit etc.)
- Farbe wählen
- Aufdruck/Muster wählen
- Grösse wählen
- Anzahl wählen
- Kundendaten eingeben
- Bestellungszusammenfassung (inkl. Preis) anzeigen
- Bestellung abschicken
    - Attribute: ID

### Optionale Features
- Warenkorb mit mehreren Items ermöglichen
    - Items aus Warenkorb löschen
    - Anzahl verändern
- Kundenkonto erstellen und anmelden
    - bisherige Bestellungen anzeigen
    - Attribute: ID
- Newsletter abonnieren
- Zahlungsmöglichkeiten wählen

## REST-Services
### Grund-Features
- Bestellung übermitteln:
    - URL: http://localhost:8081/api/order
    - Method: POST
    - Parameters:
        - Kundendaten, Konfigurations-ID, Anzahl
    - Response:
        - HTTP 200 if successfull
        - HTTP 400 bad request
        - Response contains ID of the order
- Konfigurations-ID abrufen:
    - URL: http://localhost:8081/api/configuration/{configuration}
    - Method: GET
    - Parameters: Konfigurationsattribute
    - Response:
        - HTTP 200 if successfull
        - HTTP 404 if configuration ID was not found
        - Response contains configuration ID
- Konfiguration abrufen:
    - URL: http://localhost:8081/api/configuration/{ID}
    - Method: GET
    - Parameters: Konfigurations-ID
    - Response:
        - HTTP 200 if successfull
        - HTTP 404 if configuration was not found
        - Response contains JSON-object with configuration

### Optionale Features
- Abfragen eines einzelnen Kunden:
    - URL: http://localhost:8081/api/customer/{ID}
    - Method: GET
    - Response:
        - HTTP 200 if successful
        - HTTP 404 if customer was not found
        - Response contains the customer information as JSON
- Erstellen eines neuen Kunden:
    - URL: http://localhost:8081/api/customer/
    - Method: POST
    - Request Body: Customer Information als JSON
    - Response:
        - HTTP 200 if the customer was created
        - Response contains the ID of the new customer.
- Löschen eines Kunden:
    - URL: http://localhost:8081/api/customer/{ID}
    - Method: DELETE
    - Response:
        - HTTP 200 if successful
        - HTTP 404 if customer was not found
- Preis einer Konfiguration anfragen:
    - URL: http://localhost:8081/api/configuration/getPrice
    - Method: GET
    - Response:
        - HTTP 200 if successful
        - Response contains the price of the configuration
        - HTTP 404 if configuration was not found
- Preis einer Bestellung anfragen:
    - URL: http://localhost:8081/api/order/getPrice
    - Method: GET
    - Response:
        - HTTP 200 if successful
        - Response contains the price of the order
        - HTTP 404 if configuration was not found
- Email eines Kunden validieren:
    - URL: http://localhost:8081/api/customer/validateEmail
    - Method: GET
    - Response:
        - HTTP 200 if successful
        - Response contains boolean with true or false

## Benötigte Entitäten
- Konfiguration:
    - ID (wird automatisch generiert aus gewählten Attributen)
    - Schnitt: Rundhals, V-Ausschnitt, Polo
    - Farbe: weiss, schwarz, rot, blau, grün
    - Grösse: S, M, L
    - Muster: einfarbig, kariert, gestreift
- Kunde:
    - ID (automatisch aufzählend)
    - Vorname
    - Name
    - Strasse
    - PLZ
    - Ort
    - E-Mail-Adresse
- Bestellung:
    - ID (automatisch aufzählend)
    - Konfiguration
    - Kunde