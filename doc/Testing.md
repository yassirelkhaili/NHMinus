# Testing

Dokumentiert über diese Markdown-Datei eure Tests. Führt dazu in einer Tabelle alle Testfälle auf,
und gebt in einer zweiten Spalte das Ergebnis des jeweiligen Tests an. Über die Editor-Ansicht rechts oben
gelangst du in die Ansicht, in der du Inhalte hinzufügen kannst. Die Markdown-Syntax kannst du unter 
https://markdown.de/ einsehen.

# **Pflegekraft Login-System**

Dokumentation der durchgeführten Tests für die User-Story: **Pflegekraft Login-System**

## User-Story
Als Pflegekraft möchte ich mich mit meinen Zugangsdaten am System anmelden können, um auf die für mich freigegebenen Patientendaten zugreifen zu können.

## Testfälle und Ergebnisse

| Testfall | Vorbedingung | Auszuführende Testschritte | Erwartetes Ergebnis | Tatsächliches Ergebnis | Status |
|----------|--------------|----------------------------|-------------------|----------------------|--------|
| **TF1 - Erfolgreiche Anmeldung** | Die Login-Maske ist geöffnet und ein gültiger Benutzer existiert in der Datenbank | 1. Korrekten Benutzernamen eingeben<br>2. Korrektes Passwort eingeben<br>3. Auf "Login" klicken | Die Pflegekraft wird zur Hauptansicht der Anwendung weitergeleitet | Erfolgreiche Weiterleitung zur Hauptanwendung mit Begrüßungsmeldung | ✅ **BESTANDEN** |
| **TF2 - Fehlgeschlagene Anmeldung** | Die Login-Maske ist geöffnet | 1. Falschen Benutzernamen eingeben<br>2. Beliebiges Passwort eingeben<br>3. Auf "Login" klicken | Eine Fehlermeldung erscheint und die Pflegekraft verbleibt auf der Login-Maske | Fehlermeldung "Ungültige Anmeldedaten. Bitte versuchen Sie es erneut." wird angezeigt, Passwort-Feld wird geleert, Fokus kehrt zum Benutzernamen-Feld zurück | ✅ **BESTANDEN** |

## Akzeptanzkriterien - Prüfung

| Kriterium | Beschreibung | Status | Bemerkung |
|-----------|--------------|--------|-----------|
| **A_1** | Eine Login-Maske ermöglicht die Eingabe von Benutzername und Passwort | ✅ **ERFÜLLT** | LoginView.fxml mit TextField für Benutzername und PasswordField für Passwort implementiert |
| **A_2** | Passwörter werden verschlüsselt in der Datenbank gespeichert | ✅ **ERFÜLLT** | HashPassword-Utility mit PBKDF2-Hashing implementiert |
| **A_3** | Bei falscher Eingabe erscheint eine Fehlermeldung | ✅ **ERFÜLLT** | Fehlermeldung wird im statusLabel angezeigt |
| **A_4** | Nach erfolgreicher Anmeldung wird die Pflegekraft zur Hauptanwendung weitergeleitet | ✅ **ERFÜLLT** | Automatische Weiterleitung zur MainWindowView nach erfolgreicher Authentifizierung |
| **A_5** | Es gibt keine Möglichkeit zur Neuregistrierung über die UI | ✅ **ERFÜLLT** | Keine Registrierungs-Buttons oder -Links in der Benutzeroberfläche vorhanden |

## Tasks - Implementierungsstatus

| Task | Beschreibung | Status |
|------|--------------|--------|
| **T_1** | Modelklasse User mit Attributen wie ID, Username, Password (verschlüsselt) erstellen | ✅ **ABGESCHLOSSEN** |
| **T_2** | Datenbankschema erweitern: User-Tabelle anlegen | ✅ **ABGESCHLOSSEN** |
| **T_3** | UserDAO implementieren mit Methode zur Authentifizierung | ✅ **ABGESCHLOSSEN** |
| **T_4** | LoginView mit Eingabefeldern für Benutzername und Passwort sowie Login-Button erstellen | ✅ **ABGESCHLOSSEN** |
| **T_5** | LoginController implementieren: Authentifizierung und Session-Management | ✅ **ABGESCHLOSSEN** |
| **T_6** | Hauptanwendung anpassen: Startpunkt ist nun die Login-Maske | ✅ **ABGESCHLOSSEN** |
| **T_7** | PasswordEncryption-Utility implementieren (z.B. mit BCrypt) | ✅ **ABGESCHLOSSEN** |

## Demo-Zugangsdaten für Tests

| Benutzername | Passwort | Rolle |
|--------------|----------|-------|
| `admin` | `admin123` | ADMIN |
| `jsmith` | `nurse123` | NURSE |

## Fazit

Beide definierten Testfälle wurden erfolgreich bestanden. Alle Akzeptanzkriterien sind erfüllt und alle Tasks wurden vollständig implementiert. Das Login-System funktioniert wie spezifiziert und ist bereit für den produktiven Einsatz.