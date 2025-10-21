# project1

Ein kleines Java/Maven-Projekt mit Passwort-Validierung und -Generierung.

## Ziel & Abgabe

- JDK 21 (oder höher). Das Projekt verwendet den Maven Compiler mit `release 21`.
- Apache Maven 3.8+.

## Voraussetzungen

- JDK 21 (oder höher). Das Projekt verwendet den Maven Compiler mit `release 21`.
- Apache Maven 3.8+.

Optional, aber empfohlen:

- Eine IDE mit Maven-Unterstützung (z. B. IntelliJ IDEA, Eclipse, VS Code mit Maven-Plugin).

## Build

- Kompletten Build inkl. Tests:
    - `mvn clean verify`
- Build ohne Tests (schneller):
    - `mvn clean package -DskipTests`

Artefakte:

- Kompilierte Klassen: `target/classes`
- Testklassen: `target/test-classes`
- JAR: `target/project1-1.0-SNAPSHOT.jar` (ohne Manifest für einen „fat“/executable JAR)

## Ausführen (Run)

Es gibt eine Main-Klasse `org.stefanneuberger.Main`, die ein Passwort von STDIN einliest und prüft, ob es gültig ist.

- Kompilieren (ggf. ohne Tests): `mvn -q -DskipTests compile` oder `mvn compile`
- Starten:
    - `mvn exec:java`
    - Hinweis: Um Tests beim Start zu überspringen, kann `mvn -DskipTests exec:java` verwendet werden.

Bei Start erscheint eine Eingabeaufforderung:

```
Enter password: 
```

Die Anwendung gibt anschließend `Valid` oder `Invalid` aus.

## Tests

- Alle Tests ausführen:
    - `mvn test`

Die Tests basieren auf JUnit 5 (Jupiter) und werden über den Surefire-Plugin ausgeführt. Testberichte:
`target/surefire-reports/`.

## Hinweise zur Konfiguration

Die `Main`-Klasse erstellt standardmäßig einen PasswordValidator mit:

Der PasswordValidator wird über den Konsturkor erstellt.
Die Policies für die Validierung werder per Parameter übergeben:

- Mindestlänge: 8
- Erlaubte Sonderzeichen: `!@#$%^&*()`
- Muss Großbuchstaben, Kleinbuchstaben und Ziffern enthalten

Der Validator initialisiert bei eigener Initialisierung einen PasswordGenerator der mit den selben
Einstellungen erstellt wird. Der Generator ist über die Instanz des Validators nutzbar:

`validator.generateValidPassword()`

instanziert werden kann auch indivduel:

`new PasswordValidator(minLength, allowedSpecialCharacters, mustContainUpperCase, mustContainLowerCase, mustContainDigit)`

`new PasswordGenerator(minLength, allowedSpecialCharacters, mustContainUpperCase, mustContainLowerCase, mustContainDigit)`

## Bonusumfang

- Sonderzeichen in Policy
- Passwort-Generator
- CLI-Tool
- JUnit @ParameterizedTest

## Lizenz

Free For All