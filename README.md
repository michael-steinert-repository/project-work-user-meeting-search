## Setup

### Application Server:
* The compiled Microservice *User-Meeting-Search* already contains the Application Server and can be executed directly inside a Container that has Java version 11 installed.

* The running Microservices is available at the URL http://localhost:8083.

### Database:

* The Microservice *User-Meeting-Search* needs its own Database. Therefore an Instance of the PostgreSQL Database *user_meeting_search* is set up on a Container.

### API of Microservices

http://localhost:8083/user-meeting-search/

| HTTP-Method | URL | Result |
| --- | --- | --- |
| GET | /search-user/{searchQuery} | Returns all existing Users that match the Search Query |
| GET | /search-meeting/{searchQuery} | Returns all existing Meetings that match the Search Query |
| GET | /fetch-all-users | Returns all existing Users |
___

## Einrichtung
### Anwendungsserver:

* Der kompilierte Microservice *User-Meeting-Search* enthält bereist den Anwendungsserver und kann direkt innerhalb eines Containers ausgeführt werden, welcher die Java-Version 11 installiert hat.

* Der laufende Microservices ist erreichbar unter der URL http://localhost:8083.

### Datenbank:

* Der Microservice *User-Meeting-Search* benötigt seine eigene Datenbank. Dafür wird auf einem Container eine Instanz der Datenbank PostgreSQL *user_meeting_search* eingerichtet.

### Schnittstelle des Microservices

http://localhost:8083/user-meeting-search/


| HTTP-Methode | URL | Ergebnis |
| --- | --- | --- |
| GET | /search-user/{searchQuery} | Liefert alle Nutzer, die die Suchanfrage erfüllen |
| GET | /search-meeting/{searchQuery} | Liefert alle Meetings, die die Suchanfrage erfüllen |
| GET | /fetch-all-users | Liefert alle existierende Nutzer |