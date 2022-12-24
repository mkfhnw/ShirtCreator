## Anpassungen Klassen
- **Order:**
	- Item-Liste:
		- @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
		- List<Item> items = new ArrayList<>(); --> inkl. Getter and Setter!
	- Bestelldatum (Date orderDate --> inkl. Getter and Setter!)
	- Quantity Order neu = Summe aus Quantities der Items
- **Item (neu)**:
	- Attribute: configuration, quantity (inkl. Getter and Setter!)
	- braucht keine id, muss aber mit Configuration-Id verknüpft werden

## Message-Klassen
- MessageOrderItem: configurationId, quantity
- MessageAddItemToOrder: configurationId, quantity
- MessageOrderDetails: orderId, orderDate, customerId, items, shippingMethod
	- ev. MessageOrder entsprechend anpassen und umbenennen

## Anpassungen Services
- **OrderService:**
	- addItemToOrder (mithilfe von MessageAddItemToOrder)
	- deleteItemFromOrder
	- getOrder (mithilfe von MessageOrderItem und MessageOrderDetails)
- **ItemService (neu):**
	- createItem
	- updateItem
	- deleteItem
	- getItem

## Frontend:
- Warenkorb (als Popup oder Div) implementieren:
	- Configuration Div ausbauen, dass auch aktueller Warenkorb angezeigt wird
	- führt alle Items des Warenkorbs auf:
		- innerhalb Warenkorbs: Anzahl der Items anpassen und Items löschen können
	- Button Order neu unterhalb von Warenkorb -> bei Klick wechselt auf OrderDetailsDiv
- Configuration-Div anpassen:
	- zusätzlicher Input für Menge
	- Button "Add to Basket" ergänzen
	- Button "Order" neu unterhalb von Warenkorb
- Order Details Div anpssen:
	- Input für Menge entfernen

## Datenmodell anpassen
- Entität Item ergänzen
- Beziehung zu Order ergänzen
- README anpassen