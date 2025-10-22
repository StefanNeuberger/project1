# Shop Management System

Ein Java-basiertes Shop-Management-System mit Lagerverwaltung und Bestellabwicklung.

## Funktionsweise des Programms

Das Programm simuliert einen Online-Shop mit integrierter Lagerverwaltung. Es besteht aus mehreren Hauptkomponenten:

### Hauptkomponenten

1. **ShopService** - Zentrale Geschäftslogik
   - Verwaltet Produkte und Bestellungen
   - Integriert Lagerbestände in den Bestellprozess
   - Validiert Bestellungen gegen verfügbare Lagerbestände

2. **Warehouse** - Lagerverwaltung
   - Speichert Produktbestände in einer HashMap
   - Überwacht Verfügbarkeit von Produkten
   - Aktualisiert Lagerbestände bei Bestellungen

3. **Product** - Produktmodell
   - Definiert Produkte mit ID, Name und Preis
   - Verwendet BigDecimal für präzise Preisberechnung

4. **Order** - Bestellmodell
   - Speichert Bestelldetails (ID, Produkt, Menge)
   - Berechnet Gesamtpreis automatisch

5. **ShopCLI** - Benutzeroberfläche
   - Interaktive Kommandozeilen-Oberfläche
   - Farbige Ausgabe mit ANSI-Unterstützung
   - Zeigt Produktverfügbarkeit an

### Programmablauf

1. **Initialisierung** (`Main.java`):
   - Erstellt Beispieldaten für 6 Produkte (Maus, Tastatur, USB-Hub, etc.)
   - Initialisiert Lager mit verschiedenen Beständen
   - Startet Shop-Service mit Lagerintegration

2. **Produktauswahl**:
   - Zeigt alle verfügbaren Produkte mit Preisen und Lagerbeständen an
   - Farbkodierung: Verfügbare Produkte in Weiß, ausverkaufte in Rot
   - Benutzer wählt Produkt durch Eingabe der Nummer

3. **Bestellabwicklung**:
   - Eingabe der gewünschten Menge
   - Validierung gegen verfügbaren Lagerbestand
   - Automatische Lagerbestandsreduzierung bei erfolgreicher Bestellung
   - Anzeige der Bestellbestätigung mit Details

4. **Lagerintegration**:
   - Echtzeitprüfung der Verfügbarkeit
   - Verhindert Überverkauf durch Lagerbestandskontrolle
   - Automatische Aktualisierung der Lagerbestände

### Technische Details

- **Java 17+** mit modernen Features (Records, BigDecimal)
- **Maven** als Build-Tool
- **ANSI-Farben** für verbesserte Benutzererfahrung
- **Exception-Handling** für robuste Fehlerbehandlung
- **Repository-Pattern** für Datenverwaltung

### Verwendung

```bash
# Projekt kompilieren
mvn compile

# Programm ausführen
mvn exec:java -Dexec.mainClass="org.stefanneuberger.Main"
```

Das Programm läuft interaktiv und ermöglicht es Benutzern, Produkte auszuwählen, Mengen einzugeben und Bestellungen zu erstellen, während gleichzeitig die Lagerbestände verwaltet werden.

