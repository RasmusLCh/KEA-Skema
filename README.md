# Intro
GitHub repositoriet indeholder en opgave der er afleveret på KEA (Københavns Erhvervsakademi) på kurset PADC i foråret 2020.

# Hvad indeholder repositoriet?
MicroService Infrastruktur findes i mappen: microserviceinfrastructure<br>
Skema microservice findes i mappen: scheduleservice<br>
I mappen PC_MS1 findes en tidlig version af en microservice der er brugt til Proof of concept - Hvor der især er fokus på registrering af microservices i infrastruktur.

# Video Demonstration
Intro: https://youtu.be/w_8zkK6DN9Q & https://youtu.be/O5W1Qc050RI<br>
Admin menu: https://youtu.be/7Volxnh7jO8<br>
Skema system: https://youtu.be/YPETpkGNYyo<br>
Tilføj microservice: https://youtu.be/a1UhtjMzavk

# Kontakt
Martin Kjeldgaard Nikolajsen (mart56p7@stud.kea.dk, martinnikolajsen@outlook.dk)
Oliver Alexander Varnild (oliv604m@stud.kea.dk)
Rasmus Langelund Christensen (rasm383n@stud.kea.dk)

# MicroService Infrastructure
I mappen infrastructure ligger der en microservice infrastruktur. I infrastrukturen kan der opsættes forskellige microservices, vi har lavet en microservice til at vise skemaer for studerende og undervisere.

## MicroService infrastructure API
API indeholder hvordan en microservice kan kommunikere med infrastrukturen.<br><br>

Note: I følgende bruges {} til at angive en variabel værdi, hvor typen eller variabel navnet angives i {} som f.eks. {String} eller {servicename}.<br><br>

JSON keys i bold, er required.<br><br>

Handling | Beskrivelse
--- | ---
Registrering af MS | En MS registreres ved at poste JSON data til  localhost:7500/serviceregistration<br>I formen<br><br>{<br>&nbsp;&nbsp;“name”: {String},<br>&nbsp;&nbsp;“port”: {int},<br>&nbsp;&nbsp;“version”: {float},<br>&nbsp;&nbsp;“description”: {String},<br>&nbsp;&nbsp;“userRequired”: {boolean},<br>&nbsp;&nbsp;”dependencyMicroserviceId”: {int}<br>}<br><br>name skal angives med små bogstaver og kun tegnene fra a-z er lovlige.<br><br>dependencymicroserviceid kan findes ved at se om en service eksisterer, derfra kan MS id tages.
Afmelding af MS | For at fjerne en service, skal den være disabled i systemet, herefter gå til localhost:7500/serviceremoval/{servicename}

For at kunne fungere, har vi derudover brug for at brugere der tilgår vores service, kan tilgå de microservices som der ligger under servicen, det har vi gjort muligt ved at lave relays.


Relays | Beskrivelse
--- | ---
GET/POST Relay | Efter et plugin er installeret, kan det være nødvendigt at tilgå hjemmesider som ligger i pluginnet, da vi ikke kan tilgå plugins direkte da de køre på en port som der er lukket for i firewallen, så er der i vores spring program opsat så man kan tilgå hjemmesider der ligger i MS gennem<br>localhost/servicepages/{servicename}/{page}<br><br>Maks mappe dybden i relayed er 5, dvs.<br><br>localhost/servicepages/{servicename}/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{page}<br><br>Når man kalder den adresse, vil spring kalde MS på<br><br>localhost:{serviceport}/servicepages/{servicename}/{page}<br><br>Hvis en MS laver redirect gennemt 302 eller 303, er det kun tilladt at lave interne redirects.Når et relay sker, vil userid på brugerne med sendt med kaldet, hvis brugeren ikke er logget ind er userid = 0.<br><br>
Note: content-type multipart/form-data er kun tilgængelig for POST.<br><br>
GET/POST/PUT/DELETE REST Relay | Efter et plugin er installeret, kan det være nødvendigt at tilgå REST data som ligger i pluginnet, da vi ikke kan tilgå plugins direkte da de køre på en port som der er lukket for i firewallen, så er der i vores spring program opsat så man kan tilgå REST data der ligger i MS gennem<br><br>localhost/servicerest/{servicename}/{restpage}<br><br>Maks mappe dybden i rest relay er 5, dvs.<br><br>localhost/servicerest/{servicename}/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{page}<br><br>Når man kalder den adresse, vil spring kalde MS på<br><br>localhost:{serviceport}/servicerest/{servicename}/{restpage}<br><br>Redirects fra MS til Infrastruktur er ikke understøttet for REST.<br><br>Når et relay sker, vil userid på brugerne med sendt med kaldet, hvis brugeren ikke er logget ind er userid = 0.<br><br>Note: content-type multipart/form-data er kun tilgængelig for POST.

Derudover så skal vores microservices kunne ændre på eksisterende hjemmesider og kunne samarbejde med vores service og evt. andre microservices. Det har vi gjort muligt ved at give dem følgende handlemuligheder.

Handling | Beskrivelse
--- | ---
Tilføjelse af javascript på udvalgte hjemmesider | For at tilføje javascript på en vilkårlig hjemmeside, kan man poste JSON data til localhost:7500/serviceaddpageinjection/{servicename} I formen<br>{<br>&nbsp;&nbsp;“type”: “JS”,<br>&nbsp;&nbsp;“page”: {String},<br>&nbsp;&nbsp;“data”: {String},<br>&nbsp;&nbsp;“priority”: {int}<br>}<br><br>Det at vi kan tilføje javascript til en side, gør at vi kan ændre på siden. Og javascript kan evt. kalde vores REST Relay for at få data fra vores MS.<br>Priority angiver hvor vigtigt elementet er, jo højere jo tidligere bliver det tilføjet til javascript koden. 50 er default priority.<br><br>page skal angives som /index hvis siden man tilgår er localhost. Eller så angivet siden som f.eks. /mywebpage hvis stien er localhost/mywebpage
Tilføjelse af CSS på udvalgte hjemmesider | For at tilføje CSS til en vilkårlig hjemmeside, kan man poste JSON data til  localhost:7500/serviceaddpageinjection/{servicename} I formen<br><br>{<br>&nbsp;&nbsp;“type”: “CSS”,<br>&nbsp;&nbsp;“page”: {String},<br>&nbsp;&nbsp;“data”: {String},<br>&nbsp;&nbsp;“priority”: {int}<br>}<br><br>Det gør at vi nu har mulighed til at style de ting vi ønsker at indsætte via javascript. eller vi kan ændre på tidligere design.<br><br>Priority angiver hvor vigtigt elementet er, jo højere jo senere bliver det tilføjet til css koden. 50 er default priority.<br><br>Page skal angives som /index hvis siden man tilgår er localhost. Eller så angivet siden som f.eks. /mywebpage hvis stien er localhost/mywebpage
Tilføjelse af MicroServiceOption | MicroService options giver mulighed for at brugeren kan slå funktionalitet til og fra i den JS som MS injektor.<br><br>MicroServiceOptions bliver indlæst på alle hjemmesider.<br><br>En service kan registrere MicroServiceOptions ved at poste JSON data til localhost:7500/serviceaddmicroserviceoption/{servicename} i formen<br><br>{<br>&nbsp;&nbsp;“variableName”: {String},<br>&nbsp;&nbsp;“variableValue”: {boolean},<br>&nbsp;&nbsp;“description”: {String},<br>&nbsp;&nbsp;“priority”: {int}<br><br>}<br><br>Man kan ved at se på variablename se værdien variablevalue, som brugerne har sat. Hvis brugeren ikke har valgt variablevalue, så sættes den til værdien den var sat til ved oprettelse.<br>variablevalue er default sat til false.<br>priority er default sat til 50, priority angiver ved settings tab i hvilke rækkefølge microserviceoptions vises. Jo før vises microserviceoption.
Tilføjelse af links, i topmenuen. | Vores hjemmeside styres primært via topmenu links (som kan ses i prototype). En service kan registrere topmenu links ved at poste JSON data til localhost:7500/serviceaddtopmenulink/{servicename} i formen<br><br>{<br>&nbsp;&nbsp;“path”: {String},<br>&nbsp;&nbsp;“text”: {String},<br>&nbsp;&nbsp;“description”: {String},<br>&nbsp;&nbsp;“priority”: {int}<br>}<br><br>Path vil ofte pege på det relay der er opsat.<br>Priority angiver hvor vigtigt elementet er, jo højere jo mere mod venstre bliver det placeret. 50 er default priority.
Actions | Nogen gange har man brug for at få at vide hvis der sker noget specifikt, her bruger vi actions - Som er en form for Observer pattern.<br><br>En MS (eller andet) kan registrere de gerne vil have et callback når en action sker, ved at poste JSON til http://localhost:7500/serviceaddaction/{servicename} i formen<br><br>{<br>&nbsp;&nbsp;“callbackurl”: {String},<br>&nbsp;&nbsp;“actionname”: {String},<br>&nbsp;&nbsp;“priority”: {int}<br>}<br><br>Note: callbackurl kan refererer til en lokal port.<br><br>Når vores service så laver en handling der kan være relevant for andre så kaldes doAction(String actionname, JSONObject data), alle der har registreret sig vil derefter modtage data i Json format på deres callbackurl.<br><br>Priority angiver hvor vigtigt elementet er, jo højere tidligere vil det modtage data i forhold til andre actions. 50 er default priority.
Se om microservice eksistere | Nogen microservices kan bygge på andre microservices, derfor kan en microservice se om en anden microservice eksistere ved at kalde<br>localhost:7500/serviceexists/{servicename}<br><br>Og vil derefter modtage JSON retur i formen<br><br>{<br>&nbsp;&nbsp;“id”: {int},<br>&nbsp;&nbsp;“name”: {String}<br>&nbsp;&nbsp;“port”: {int}<br>&nbsp;&nbsp;   “enabled”: {boolean}<br>}
Tilføjelse af ressource | Ved f.eks. billeder eller filer som en MS henviser til, så ligges de i servicen, og MS kan derefter henvise til dem via<br><br>localhost/serviceresource/{servicename]/{resource}
Læsning af models | Eksisterende microservices kan læse data fra infrastrukturen ved at se alle af en bestemt type, eller ved at se en specifik.<br><br>http://localhost:7500/find/all/{Modelname}/<br><br>http://localhost:7500/find/ById/{Modelname}/{id}<br><br>Eksempelvis hvis man ønsker at se alle actions i infrastrukturen:<br><br>http://localhost:7500/find/all/Action/<br><br>Eller hvis man ønsker at se en specifik<br><br>http://localhost:7500/find/ById/Action/12000

Til alle handlinger, bliver der registreret hvis det er en MS som har lavet dem. Hvis man afmelder MS så fjernes de handlinger der hører til denne.

## Actions
følgende actions er indbygget i infrastrukturen

###P lacering: ActionService
Action | Note
--- | ---
ActionService.create | Sker når en ny Action bliver oprettet.
ActionService.edit | Sker når en Action bliver editeret.
ActionService.delete | Sker når en Action bliver slettet.
### Placering: AuthenticationService
Action | Note
--- | ---
AuthenticationService.Authenticate | Sker når en bruger bliver authentificeret. Indeholder data on authentificeringen lykkedes og om brugeren.
### FileResourceService
Action | Note
--- | ---
FileResourceService.create | Sker når en ny FileResource bliver oprettet.
FileResourceService.edit | Sker når en FileResource bliver editeret.
FileResourceService.delete | Sker når en FileResource bliver slettet.
### GroupService
Action | Note
--- | ---
GroupService.create | Sker når en ny Group bliver oprettet.
GroupService.edit | Sker når en Group bliver editeret.
GroupService.delete | Sker når en Group bliver slettet.
### MicroServiceService
Action | Note
--- | ---
MicroServiceService.create | Sker når en ny MicroService bliver oprettet.
MicroServiceService.edit | Sker når en MicroService bliver editeret.
MicroServiceService.delete | Sker når en MicroService bliver slettet.
### PageInjectionService
Action | Note
--- | ---
PageInjectionService.create | Sker når en ny PageInjection bliver oprettet.
PageInjectionService.edit | Sker når en PageInjection bliver editeret.
PageInjectionService.delete | Sker når en PageInjection bliver slettet.
### TopMenuLinkService
Action | Note
--- | ---
TopMenuLinkService.create | Sker når en ny TopMenuLink bliver oprettet.
TopMenuLinkService.edit | Sker når en TopMenuLink bliver editeret.
TopMenuLinkService.delete | Sker når en TopMenuLink bliver slettet.
### UserService
Action | Note
--- | ---
UserService.create | Sker når en ny User erction bliver oprettet.
UserService.edit | Sker når en User bliver editeret.
UserService.delete | Sker når en User bliver slettet.

## Eksempel på opsætning af microservice
Koden i dette eksempel er bygget på koden fra scheduleservice\Service\Setup som findes i github.

I koden er port numrene defineret ved 

```
@Value("${infrastructure.port:7500}")
int infrastructureport;
```

```
@Value("${ms.port.service:7510}")
int serviceport;
```

En microservice opsættes ved at kommunikere med infrastrukturen, det første der gøres er at registrere microservicen på infrastrukturen

```
RestTemplate restTemplate = new RestTemplate();
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
HttpEntity<?> entity;
JSONObject json;
json = new JSONObject();
json.appendField("name", "KEA-Schedule-admin");
json.appendField("port", 7510);
json.appendField("enabled", false);
json.appendField("description", "admin module");
entity = new HttpEntity<JSONObject>(json, headers);
restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceregistration", HttpMethod.POST, entity, String.class);
```

Herefter kan de elementer som lægges under en microservice tilføjes, f.eks. topmenu links

```
//Add topmenulink eng
json = new JSONObject();
json.appendField("path", "/servicepages/KEA-Schedule-Admin/index.eng");
json.appendField("text", "Course admin");
json.appendField("priority", "100");
json.appendField("language", "eng");
json.appendField("description", "");
entity = new HttpEntity<JSONObject>(json, headers);
restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
```

Eller actions

```
//Action: UserService
json = new JSONObject();
json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/create/");
json.appendField("actionname", "UserService.create");
entity = new HttpEntity<JSONObject>(json, headers);
restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
json = new JSONObject();
json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/edit/");
json.appendField("actionname", "UserService.edit");
entity = new HttpEntity<JSONObject>(json, headers);
restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
json = new JSONObject();
json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/delete/");
json.appendField("actionname", "UserService.delete");
entity = new HttpEntity<JSONObject>(json, headers);
restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
```

Tilføjelse af funktionalitet til en microservice i infrastrukturen, foregår via rest kald, derfor er det også muligt at bruge ajax kald, hvis man ikke ønsker at implementere opsætningen af microservicen i Java. Det er illustreret i projektet PC_MS1 i PC_MS1\src\main\resources\templates\setup.html
