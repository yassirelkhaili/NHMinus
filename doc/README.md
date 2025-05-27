# NHPlus

Dieses Projekt umfasst zwei voneinander unabhängige Anwendungen:

1. Die eigentliche Anwendung NHPlus mit der ausführbaren Klasse `Main`. 
2. Ein Hilfsprogramm, mit dem die gegebene Datenbank in den ursprünglichen Zustand versetzt werden kann. Das Hilfsprogramm ist für dich 
dann interessant, wenn ihr eure Datenbank durch viele Tests zugemüllt habt und den ursprünglichen Zustand wiederherstellen wollt.
Sie ist mit Hilfe der Klasse `SetUpDB` (im Ordner utils) ausführbar.

Bitte beachte, dass das Programm `SetUpDB` nicht nur die Datensätze löscht (DML-Befehl `DELETE`), sondern die
 Relationen selbst löscht (DDL-Befehl `DROP`). Solltest du Veränderungen an der Struktur der Datenbank vorgenommen
haben, werden diese nach dem Aufruf von `SetUpDB` nicht mehr vorhanden sein.

### Datenbank in IntelliJ einsehen

1. Klicke rechts oben auf das Datenbanksymbol. Nun sollte dir im Datenbankbereich die Datenbank `nursingHome.db` angezeigt werden.
Klappe die Pfeile auf und lasse dir die Tabellen anzeigen. 

Falls dir die Datenbank, nicht aber die Tabellen angezeigt werden, 
1. klicke mit rechts auf die Datenbank und wähle `Tools-Manage Shown Schemas...` aus.
2. Wähle All Schemas aus.

Solltest du die Datenbank nicht sehen, 
1. klicke im Datenbankbereich in der Symbolleiste auf das Datenbanksymbol
2. Trage im Fenster Data Sources and Drivers den Namen der Datenbank `nursingHome.db` ein.
3. Gib unter File den Pfad der Datenbank an. Sie ist im Ordner `db` deines Projektes gespeichert.
4. Gib unter URL `jdbc:sqlite:` gefolgt vom Pfad deiner Datenbank an (z. B. `jdbc:sqlite:C:/Users/sschw/NHPlus/nhplus/db/nursingHome.db`).
5. Klicke auf `Apply` und `OK`

### SQLite

Für die Erweiterung von NHPlus wirst du Tabellen erstellen (create table), Tabellen ändern (alter table), Daten einfügen (insert into), 
aktualisieren (update), löschen (delete) und anzeigen (select) müssen. Um dich mit dem speziellen SQL-Dialekt von SQLite
vertraut zu machen, verwende die Dokumentation: https://www.sqlite.org/lang.html.

### SQL-Statements absetzen

1. Klicke rechts oben auf das Datenbank-Symbol
2. Klicke in der Symbolleiste des Datenbankmoduls auf das Symbol Jump to Query Console...
3. Trage im Konsolenfenster den SQL-Befehl ein (zum Beispiel `Select * from patient`)
4. Klicke auf den Ausführen-Pfeil in der Symbolleiste der Query-Ansicht.

### Module-Info

Im Hauptpackage findest du die Datei `module-info.java`. Sie dient dazu, ein Java-Projekt in Module zu strukturieren
und den Zugriff auf Abhängigkeiten explizit zu steuern. Das bietet folgende Vorteile:
- Explizite Abhängigkeitsverwaltung: Statt dass das gesamte Projekt Zugriff auf alle Klassen hat, werden nur die benötigten 
Module eingebunden (requires).
- Bessere Kapselung & Sicherheit: Nur die in exports angegebenen Pakete sind von anderen Modulen aus zugänglich.
Das Schlüsselwort opens ermöglicht Reflexion für Frameworks wie JavaFX.
- Verbesserte Laufzeit- und Startzeit-Performance: Die JVM kann nur die tatsächlich benötigten Module laden.
- Bessere Wartbarkeit: Der Code ist klarer strukturiert, weil klar definiert wird, welche Teile des Codes von außen genutzt werden können.

Das Schlüsselwort `requires` gibt die Abhängigkeiten an, die im Modul de.hitec.nhplus benötigt werden. Zum Beispiel werden 
javafx.controls, javafx.fxml für JavaFX oder java.sql, org.xerial.sqlitejdbc für den SQLite-Datenbankzugriff benötigt.

Die Schlüsselwörter `opens` und `exports`: 

`opens ... to javafx.fxml`: Erlaubt Reflexion für JavaFX (z. B. FXML-Dateien).

`exports ...`: Diese Pakete dürfen von anderen Modulen genutzt werden.

Du wirst also eigene Pakete hier eintragen müssen, um sie woanders benutzen zu können!

### SceneBuilder

Es bietet sich an, die JavaFX-Oberflächen mithilfe des SceneBuilders zu bearbeiten. Er ist als Freeware unter  https://gluonhq.com/products/scene-builder/#download erhältlich. 
Der Installations-Wizard wird mit den vorgeschlagenen Einstellungen durchlaufen.

Damit FXML-Dateien aus IntelliJ heraus mit dem SceneBuilder geöffnet werden können, ist in IntelliJ folgende Einstellung nötig:

1) Klicke in der Menüleiste auf File - Settings

2) Wähle den eintrag Languages & Frameworks - JavaFX aus

3) Trage unter Path to SceneBuilder den Dateipfad zum SceneBuilder ein und markiere dort SceneBuilder.exe

4) Klicke auf OK

5) Beende den Dialog, indem du auf Apply klickst

Die Einbindung in IntelliJ ist nicht nötig. Die FXML-Dateien können auch direkt im SceneBuilder bearbeitet werden.

### Javadoc

Für jede Klasse, die du neu implementierst, muss eine Javadoc erstellt werden. Besonderer Schwerpunkt soll dabei darauf liegen,
den Zweck der Klasse sowie jeder Methode anzugeben. Auf diese Weise sollt ihr sicherstellen, dass ihr das Single Responsibility-Prinzip einhaltet
sowie Klassen und Methoden mit hoher Kohäsion schreibt. Wenn ihr beim Dokumentieren entdeckt, dass das nicht gegeben ist, ändert es!

### Passwörter

Gib hier nötige Benutzernamen und Passwörter an!