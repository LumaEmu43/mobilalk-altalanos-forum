# Általános fórum alkalmazás

Fórum alkalmazás, mobilalkalmazás fejelsztés kötelező program.

## Rövid leírás

A repóban elhelyeztem az APK-t, illetve ha minden igaz akkor coospacen is mellékelve lett. Az alábbiakban mellékelek egy rövid javítási útmutatót/segédletet.

## Javítási segédlet

Fordítási hiba nincs                    -       Mivel létre tudtam hozni az APK fájlt és fut is az alkalmazás ezért nincs fordítási hiba.

Futtatási hiba                          -       Nem tapasztaltam ilyet.

Firebase autentikáció                   -       Megvalósul a regisztráció/bejelentkezés során.

Adatmodell definiálása                  -       A User és Comment classban van megvalósítva az adatmodell.

Legalább 3 Activity használata          -       MainActivity, LoginActivity, RegisterActivity, FeedActivity, ProfileActivity.

Beviteli mezők helyes típusa            -       Megvalósul, látható a login és register oldalakon.

ConstraintLayout és egy másik layout    -       Pl.: app/src/main/res/layout/activity_feed.xml ConstraintLayout és LinearLayout is használva van.

Reszponzív felület                      -       Igyekeztem megvalósítani a lehető legjobban, megfigyeléseim szerint igényes a megvalósítás.

Legalább 2 animáció                     -       Nem foglalkoztam ezzel.

Intentek használata                     -       Minden Activity között elérhető a navigáció intentek segítségével, pl.: hírfolyam <--> profil.

Egy Lifecycle Hook használata           -       Pl.: FeedActivity legalján onResume és onStop használata, újratölti/frissíti a bejegyzéseket.

Notification/alarm/job scheduler        -       Nem foglalkoztam vele.

Android permission                      -       Manifestsben internet jogosultság kérése.

CRUD műveletek megvalósítása            -       CommentService, CommentAsyncTask, UserService, UserServiceAsyncTask. Megvalósultak mindegyikben a CRUD műveletek, illetve az aszinkron verziójuk is implementálásra került, esetenként használva is vannak.

2 komplex firestore lekérdezés          -       Csak egy darab van (2 pont) a CommentService alján a getAllCommentsOrderedByTime().

Szubjektív pontozás                     -       Igyekeztem a legtöbbet kihozni belőle, nyílván nem áll rendelkezésemre a világ összes ideje de ettől függetlenül szerettem volna egy nyomokban igényes applikációt létrehozni.

| Elem | Pontszám | Megvalósítás |
| -------- | -------- | -------- |
| Fordítási hiba nincs  | Row 1, Cell 2 | Mivel létre tudtam hozni az APK fájlt és fut is az alkalmazás ezért nincs fordítási hiba. |
| Row 2, Cell 1 | Row 2, Cell 2 | Row 2, Cell 3 |
| Row 3, Cell 1 | Row 3, Cell 2 | Row 3, Cell 3 |


## Usage

| Column 1 | Column 2 | Column 3 |
| -------- | -------- | -------- |
| Row 1, Cell 1 | Row 1, Cell 2 | Row 1, Cell 3 |
| Row 2, Cell 1 | Row 2, Cell 2 | Row 2, Cell 3 |
| Row 3, Cell 1 | Row 3, Cell 2 | Row 3, Cell 3 |


1. Navigate to the homepage.
2. Use the navigation bar to access other pages on the website.
3. Read blog posts on the blog page.
4. Submit your information and a message on the contact page.

## Contributing

Contributions to the project are welcome. Please fork the repository, make changes, and submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for more information.
