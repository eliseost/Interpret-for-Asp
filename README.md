# Interpret-for-Asp

En interpret er et program som leser og analyserer et program i et gitt
programmeringsspråk, og som deretter utfører det som dette programmet
angir skal gjøres. Her er programmeringsspråket Asp som er laget av en 
professor på UiO. 

1.4.1 Del 1: Skanneren
Første skritt, del 1, består i å få Asps skanner til å virke. Skanneren
er den modulen som fjerner kommentarer fra programmet, og så deler
den gjenstående teksten i en veldefinert sekvens av såkalte symboler (på
engelsk «tokens»). Symbolene er de «ordene» programmet er bygget opp
av, så som navn, tall, nøkkelord, ‘+’, ‘>=’, ‘(’ og alle de andre tegnene og
tegnkombinasjonene som har en bestemt betydning i Asp-språket.
Denne «renskårne» sekvensen av symboler vil være det grunnlaget som
resten av interpreten eller kompilatoren skal arbeide videre med. Noe av
programmet til del 1 vil være ferdig laget eller skissert, og dette vil kunne
hentes på angitt sted.

1.4.2 Del 2: Parseren
Del 2 vil ta imot den symbolsekvensen som blir produsert av del 1, og det
sentrale arbeidet her vil være å sjekke at denne sekvensen har den formen
et riktig Asp-program skal ha (altså, at den følger Asps syntaks).
Om alt er i orden, skal del 2 bygge opp et syntakstre, en trestruktur
av objekter som direkte representerer det aktuelle Asp-programmet, altså
hvordan det er satt sammen av «expr» inne i «stmt» inne i «func def» osv.

1.4.3 Del 3: Interpretering av uttrykk
I del 3 skal man ta imot et syntakstre for et uttrykk og så evaluere det, dvs
beregne resultatverdien. Man må også sjekke at uttrykket ikke har typefeil.

1.4.4 Del 4: Full interpretering
Siste del er å kunne evaluere alle mulige Asp-programmer, dvs programmer
med funksjonsdefinisjoner samt setninger med løkker, tester og uttrykk. 
Del 4 er ikke ferdig på dette tidspunkt. 

For å kjøre interpreten skrives: $java -jar asp.jar [-test{expr|parser|scanner}] filename.asp, 
alt ettersom det er, scanner, parser eller utrykk som vil testes. 

Testprogrammer finnes her: http://inf2100.at.ifi.uio.no/oblig/
