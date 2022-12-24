## Projektinformationen

**Aufgabe:** Entwicklung einer rudimentären Web-Applikation  
**Ziel:** Die im Unterricht behandelten Konzepte praktisch einsetzen  
**Gruppe:** Amsler Marco, Fankhauser Stefan, Frey Fabio, Karaqi Margareta, Läubin Joel, Wadhawan Elia 

***

## Zielsetzung/Szenario
- Entwicklung eines T-Shirt-Konfigurators
- Kunde kann ein T-Shirt nach bestimmten Attributen konfigurieren und bestellen

***

## Datenmodell

### ER-Modell
![ER-Modell für T-Shirt-Konfigurator](/docs/diagrams/ER-Diagramm.png "ER-Modell für T-Shirt-Konfigurator")

### Relationales Modell
<style>.dot{border-bottom: 1px dotted; text-decoration: none;}</style>
**Address** (<u>id</u>, street, plz, location)  
**Customer** (<u>id</u>, firstName, lastName, email, deleted, <u class="dot">fk_address</u>)  
**Configuration** (<u>id</u>, cut, color, size, pattern, price, deleted)  
**Order** (<u>id</u>, <u class="dot">fk_configuration</u>, <u class="dot">fk_customer</u>, quantity, shippingMethod, price)  

***

## Funktionen

### Presentation Layer
Grundsätzlicher Aufbau:
- Navigationsleiste (navbar) mit Logo und vier Buttons (Home, About, Contact, Login)
- Hauptframe mit zwei Spalten: T-Shirt-Anzeige (preview-column) und Bedienfeld (control-column)
- für T-Shirt-Anzeige wurde ein Karussell (carousel) für die T-Shirt-Ansichten implementiert
- Bedienfeld wechselt per Knopfdruck zwischen folgenden drei Divs:
  - Panel für T-Shirt-Konfiguration (configuration-panel)
    - Klick auf Btn "Order now!" triggert Funktion 
  - Panel für Kundendaten (order-panel)
  - Panel für Bestätigung (aftersales-panel)
- Für die Login-Funktion wurde ein Popup implementiert:
  - Login: um sich mit einem bestehenden Kundenkonto anzumelden
  - Registration: um ein neues Kundenkonto zu erstellen

### Service Layer
- **OrderService:**
  - Bestellung erstellen (*createOrder*):
    - URL: http://localhost:8080/api/order/
    - Method: POST
    - Request Body: MessageNewOrder (customerId, configurationId, quantity, shippingMethod) als JSON
    - Response:
      - HTTP 200 if successful
      - If order valid, response contains order (else null)
  - Bestellungen von Kunden abfragen (*getOrdersForCustomer*):
    - URL: http://localhost:8080/api/orders
    - Method: GET
    - Request Parameter: customerId
    - Response:
        - HTTP 200 if successful, body contains list of found orders as JSON
  - Preis einer Bestellung abfragen (*getOrderPrice*):
    - URL: http://localhost:8080/api/order/getPrice
    - Method: GET
    - Request Body: MessageNewOrder (customerId, configurationId, quantity, shippingMethod) als JSON
    - Response:
      - HTTP 200 if successful
      - If order valid, response contains price (else null)
- **ConfigurationService:**
  - Konfiguration abfragen (*getConfiguration*):
    - URL: http://localhost:8080/api/configuration/{ID}
    - Method: GET
    - Request Parameter: configurationId
    - Response: HTTP 200 if successful, body contains configuration as JSON
  - ID einer Konfiguration abfragen (*getConfigurationId*):
    - URL: http://localhost:8080/api/configuration/search
    - Method: GET
    - Request Body: MessageGetConfigurationId (cut, pattern, size, color)
    - Response: HTTP 200 if successful, response contains configurationId
  - Preis einer Konfiguration abfragen (*getConfigurationPrice*):
    - URL: http://localhost:8080/api/configuration/getPrice
    - Method: GET
    - Request Body: MessageNewConfiguration (cut, pattern, size, color)
    - Response: HTTP 200 if successful, response contains price
- **CustomerService:**
  - Kunde abfragen (*getCustomer*):
    - URL: http://localhost:8080/api/customer/{ID}
    - Method: GET
    - Request Parameter: customerId
    - Response: HTTP 200 if successful, body contains customer as JSON
  - Kunde löschen (*deleteCustomer*):
    - URL: http://localhost:8080/api/customer/{ID}
    - Method: DELETE
    - Response:
      - HTTP 200 if successful
      - If customer was found, response contains true (else false)
  - Kunde aktualisieren (*updateCustomer*):
    - URL: http://localhost:8080/api/customer/{ID}
    - Method: PUT
    - Request Parameter: customerId
    - Request Body: Customer
    - Response:
      - HTTP 200 if successful
      - If customer was found and email is valid, response contains true (else false)
  - Kunde erstellen (*createCustomer*):
    - URL: http://localhost:8080/api/customer/
    - Method: POST
    - Request Body: MessageNewCustomer (firstName, lastName, email, address)
    - Response:
      - HTTP 200 if successful
      - If email is valid, response contains customer (else null)
  - E-Mail eines Kunden validieren (*validateEmail*):
    - URL: http://localhost:8080/api/customer/validateEmail
    - Method: GET
    - Request Body: email as String
    - Response: HTTP 200 if successful, response contains true or false
  - Alle Kunden abfragen (*getCustomers*):
    - URL: http://localhost:8080/api/customers
    - Method: GET
    - Request Parameter: (optional "filter")
    - Response: HTTP 200 if successful, response contains JSON-Array with found customers

### Business Logic Layer
- **ConfigurationVerification:**
  - *calculateConfigurationPrice* nimmt eine Konfiguration entgegen und berechnet ihren Preis
    - gibt den Preis als Double zurück
- **OrderVerification:**
  - *validateOrder* nimmt eine Bestellung entgegen und prüft die maximale T-Shirt-Anzahl
    - gibt entsprechend true (bei Anzahl < MAX_QUANTITY) oder false (bei Anzahl > MAX_QUANTITY)
  - *calculateOrderPrice* nimmt eine Bestellung entgegen und berechnet ihren Preis
    - gibt den Preis als Double zurück
    - ruft *calculateShippingCosts* auf
  - *calculateShippingCosts* nimmt eine Bestellung entgegen und berechnet ihre Versandkosten
    - gibt die Kosten als Double zurück
- **CustomerVerification:**
  - *validateEmailAddress* nimmt eine E-Mail-Adresse als String entgegen und prüft, ob diese valide ist
    - gibt entsprechend true oder false zurück

### Persistence Layer
- Für jede Entität wurde ein entsprechendes Repository erstellt.
- Die Entitäten wurden mit den passenden Annotationen versehen.
- Das Datenmodell wurde gemäss Abschnitt "Datenmodell" umgesetzt.
- Für die Datenhaltung haben wir uns für die eingebettete DB H2 entschieden.

***

## Reflexion
Ziel der Projektarbeit war es, die im Unterricht behandelten Konzepte (HTML, CSS, JavaScript, Layer-Architektur, REST-  
Services) zu vertiefen und praktisch umzusetzen. Da Server-seitig eine Java App mit DB-Zugriff implementiert werden  
sollte, kamen neben den Inhalten des Moduls "Internettechnologien" auch noch Aspekte aus "Programmierung", "Software  
Engineering" und "Datenbanken" zum Tragen. Dieses Projekt diente also auch gleichzeitig dem Aufarbeiten von früherem  
Unterrichtsstoff.

Wir konnten uns bereits im ersten Team-Meeting auf den **T-Shirt-Konfigurator** und die grundlegende Konzeption einigen.  
Von Vornherein war klar, dass wir die Entitäten *Order*, *Customer* und *Configuration* benötigen, wobei eine Configuration  
als eine festgelegte Kombination von T-Shirt-Attributen (Schnitt, Farbe, Grösse, Muster) zu verstehen ist. Der Bestell-  
vorgang sollte in einem ersten Wurf relativ einfach gehalten werden:
1. Farbe, Schnitt, Grösse und Muster wählen
2. Menge und Versandbedingung wählen
3. Kundendaten eingeben
4. Bestellung abschicken
5. Info "Vielen Dank für die Bestellung" anzeigen

Noch im gleichen Meeting wurden die benötigten Entitätsattribute und REST-Services definiert. Nach und nach kamen dann  
weitere Ideen und somit auch zusätzliche Entitäten (bspw. Address) und Services (bspw. getPrice) dazu.

Wir trafen uns einmal wöchentlich, um das weitere Vorgehen zu besprechen, neue Tasks zu definieren, bestehende Pro-  
bleme anzusprechen sowie bisher Gemachtes zu präsentieren. Nach den Meetings gaben wir uns jeweils eine Woche Zeit,  
um die neuen Tasks abzuarbeiten.

Für die Arbeit haben wir folgende Applikationen/Frameworks eingesetzt:
- **IntelliJ:** Programmierumgebung
- **GitHub:** Versionsverwaltung (Remote Repository)
- **Azure DevOps:** zur Prozessunterstützung (Tasks zusammentragen, zuweisen, etc.)
- **Bootstrap:** als Frontend Toolkit
- **Photoshop:** zur Gestaltung der T-Shirts
- **diagrams.net:** zur Erstellung des ER-Modells

Schwierigkeiten hatten wir insofern, als bei der Erstellung eines Projekts mit sechs Personen die inhaltliche Abstim-  
mung durchaus seine Tücken haben kann. Es gab keinen "Product Owner", welcher den Gesamtüberblick hatte und den Code  
inhaltlich laufend verifiziert hat. Somit mussten beispielsweise bestehende Services überarbeitet oder wieder gelöscht  
werden, die Entitäten um weitere, fehlende Attribute ergänzt werden und die Projektstruktur angepasst werden. Des Wei-  
teren war für uns anfangs nicht klar, was alles in die Business Logik gehört und wie wir diese konkret umsetzen möch-  
ten. Schlussendlich haben wir nun alle Preis-spezifischen Dinge sowie E-Mail-Validierung und Maximal-Bestellung in der  
Business Logik realisiert.

Alles in allem war dies ein sehr aufschlussreiches Projekt, in welchem viele im Unterricht behandelten Aspekte zusam-  
menkamen. Es war spannend zu sehen, wie diese Aspekte zusammenwirken und was es alles braucht, um eine funktionierende  
Web-Applikation zu entwickeln. Die Zusammenarbeit im Team klappte gut und die eingesetzten Applikationen/Frameworks  
erwiesen sich als sehr hilfreich.