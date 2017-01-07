# Datenbanken 1 Bonuspunkte Aufgabe

Dieses Projekt ist im Rahmen der Bonuspunktaufgabe im Datenbank1-Praktikums der FH-Dortmund enstanden.

## Installation

Das Projekt wird mit gradle gebaut. Um eine ausführbare Jar zu erhalten müssen Sie folgendes Kommando ausführen: 

```shell
gradle shadowJar
```

Die gebaute Jar liegt dann in build/libs.
Eine vorgebaute Version liegt auch schon im Projektverzeichnis

## Ausführen

Die Jar kann dann per
```shell
java -jar fh-database-*.jar
```
ausgeführt werden.
Die MySQL Datenbank muss dabei auf localhost:3306 laufen und einen root login ohne passwort zulassen.
Diese Daten können in der Datei 
> src/main/java/de/randomerror/fh/database/db/Config.java

geändert werden.

## Abhängigkeiten

Das Projekt hat folgende Abhängigkeiten:

#### Kompilierung

- lombok: https://projectlombok.org

#### Laufzeit

- mysql-connector: https://www.mysql.com/products/connector/

#### Testen

- jUnit: http://junit.org/junit4/

## GitHub

Das Projekt finden Sie auch in GitHub unter

```
https://github.com/hesch/fh-database
```