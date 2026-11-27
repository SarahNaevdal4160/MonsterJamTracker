## Problem och lösning

**Problem:** Från början hamnade den senast tillagda trucken längst ner i listan. När listan blev längre än vad som fick plats på skärmen behövde 
användaren scrolla ner varje gång för att se om trucken hade lagts till.

**Lösning:** Jag ändrade så att listan ritas ut i omvänd ordning med *trucks.reversed()* i *LazyColumn*. För att se till att rätt truck fortfarande 
redigeras, bockas av eller raderas när ordningen vänds, identifieras varje rad med ett unikt ID, *UUID* istället för sin plats i listan.


## Hjälpmedel

Jag använde mig av tre olika hjälpmedel; *studieguiden*, *egna anteckningar* och *AI*. Studieguiden hjälpte mig att strukturera upp arbetet medan AI fungerade 
som ett bollplank när jag stötte på problem, ville veta om jag tänkt rätt eller om jag hade funderingar. Anteckningarna använde jag främst för att kolla 
upp olika operatorer och villkor som jag inte lärt mig utantill än. AI var det snabbaste sättet medan studieguiden var mest tillförlitlig.     
