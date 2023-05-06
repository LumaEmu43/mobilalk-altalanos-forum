# Általános fórum alkalmazás

Fórum alkalmazás, mobilalkalmazás fejelsztés kötelező program.

## Rövid leírás

A repóban elhelyeztem az APK-t, illetve ha minden igaz akkor coospacen is mellékelve lett. Az alábbiakban mellékelek egy rövid javítási útmutatót/segédletet.

## Használati útmutató

1. Az APK fájl telepítése vagy hadrwarre vagy emulátorra
2. Alkalmazás megnyitása
3. Regisztráció
4. Bejegyzés írása stb...

## Javítási segédlet

**FIGYELEM! Ez a segédlet csak egy útmutatás, a valóságtól eltérhet!**



| Elem | Pontszám | Megvalósítás |
| -------- | -------- | -------- |
| Fordítási hiba nincs  | 1 pont | Mivel létre tudtam hozni az APK fájlt és fut is az alkalmazás ezért nincs fordítási hiba. |
| Futtatási hiba | 1 pont | Nem tapasztaltam ilyet. |
| Firebase autentikáció | 4 pont | Megvalósul a regisztráció/bejelentkezés során. |
| Adatmodell definiálása | 2 pont | A User és Comment classban van megvalósítva az adatmodell. |
| Legalább 3 Activity használata | 2 pont | MainActivity, LoginActivity, RegisterActivity, FeedActivity, ProfileActivity. |
| Beviteli mezők helyes típusa | 3 pont | Megvalósul, látható a login és register oldalakon. |
| ConstraintLayout és egy másik layout | 1 pont | Pl.: app/src/main/res/layout/activity_feed.xml ConstraintLayout és LinearLayout is használva van. |
| Reszponzív felület | 3 pont (?) | Igyekeztem megvalósítani a lehető legjobban, megfigyeléseim szerint igényes a megvalósítás. |
| Legalább 2 animáció | 0 pont | Nem foglalkoztam ezzel. |
| Intentek használata | 2 pont | Minden Activity között elérhető a navigáció intentek segítségével, pl.: hírfolyam <--> profil. |
| Egy Lifecycle Hook használata | 2 pont | Pl.: FeedActivity legalján onResume és onStop használata, újratölti/frissíti a bejegyzéseket. |
| Notification/alarm/job scheduler | 0 pont | Nem foglalkoztam vele. |
| Android permission | 0 v 1 pont (?) | Manifestsben internet jogosultság kérése. |
| CRUD műveletek megvalósítása | 5 pont | CommentService, CommentAsyncTask, UserService, UserServiceAsyncTask. Megvalósultak mindegyikben a CRUD műveletek, illetve az aszinkron verziójuk is implementálásra került, esetenként használva is vannak. |
| 2 komplex firestore lekérdezés | 2 pont | Csak egy darab van (2 pont) a CommentService alján a getAllCommentsOrderedByTime(). |
| Szubjektív pontozás | ? | Igyekeztem a legtöbbet kihozni belőle, nyílván nem áll rendelkezésemre a világ összes ideje de ettől függetlenül szerettem volna egy nyomokban igényes applikációt létrehozni. |

