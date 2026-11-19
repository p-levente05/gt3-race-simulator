# GT3 Race Simulator

Konzolos, Java nyelven készült GT3-versenyszimulátor, amelyben a csapatok teljesítményét az autók tulajdonságai, a versenyzők képessége, az időjárás és a versenyesemények befolyásolják.

A projekt a Programozás 2 tantárgy objektumorientált beadandójaként készül.

## A program célja

A felhasználó egy virtuális GT3-futamot szimulálhat, majd megtekintheti a versenyeseményeket és a végeredményt.

A szimuláció egyszerűsített játékmodell, nem valós autófizikai szimulátor. A csapatok és versenyzők kitaláltak, a teljesítményértékek és alap-köridők játékbeállítások.

## Funkciók

- Hat csapat saját versenyzővel és autóval.
- Három előre definiált pálya:
    - Monza
    - Spa-Francorchamps
    - Hungaroring
- Száraz és esős köridőszámítás.
- Véletlen köridőeltérések.
- Ütközések az egymáshoz közel haladó autók között.
- Egyszeri időveszteség és tartós sérülési lassulás.
- Kiesések, DNF jelöléssel.
- Versenyesemények és rendezett végeredmény megjelenítése.

## Követelmények

- JDK 21
- Maven 3.9 vagy újabb a parancssoros fordításhoz
- Git a repository klónozásához
- Opcionálisan IntelliJ IDEA

## Letöltés

```bash
git clone https://github.com/p-levente05/gt3-race-simulator.git
cd gt3-race-simulator
```

## Fordítás és futtatás

A projekt gyökérkönyvtárából:

```bash
mvn clean package
java -cp target/classes hu.levente.gt3.Main
```

### Futtatás IntelliJ IDEA-ban

1. Nyisd meg a projekt gyökérkönyvtárát.
2. Ha szükséges, importáld a `pom.xml` fájlt Maven-projektként.
3. Állítsd a Project SDK értékét JDK 21-re.
4. Nyisd meg a `Main.java` fájlt.
5. Indítsd el a `Main.main()` metódust.

## A futam beállítása

A jelenlegi változatban a beállítások a `Main.java` fájlban módosíthatók.

```java
Track selectedTrack = tracks.get(0);
int laps = 10;
boolean wetWeather = true;
```

### Pályaválasztás

| Index | Pálya |
|---|---|
| `0` | Monza |
| `1` | Spa-Francorchamps |
| `2` | Hungaroring |

Például a Hungaroring kiválasztása:

```java
Track selectedTrack = tracks.get(2);
```

### Időjárás

- `wetWeather = false`: száraz pálya.
- `wetWeather = true`: esős pálya.

Az időjárás a köridőszámítást és az ütközések valószínűségét is befolyásolja.

A módosítások után a programot újra kell fordítani és futtatni.

## A szimuláció működése

Minden csapat minden aktív körben kap egy számított köridőt.

A köridő összetevői:

- A pálya alap-körideje.
- Az autó tempójából eredő időhátrány.
- A versenyző képességéből eredő időhátrány.
- Véletlen eltérés.
- Korábbi sérülésekből eredő lassulás.

Esőben az alap-köridő hosszabb, a versenyző képessége nagyobb súlyt kap, és nagyobb a véletlen ingadozás.

### Ütközések

A program a körök végén, az összidők alapján vizsgálja a szomszédos aktív nevezőket. Legfeljebb 1,5 másodperces különbségnél történhet ütközés.

A baleset esélyét növeli az agresszivitás és az esős időjárás, a nagyobb versenyzői képesség pedig csökkenti.

Az ütközés két résztvevőjének következményeit külön sorsolja a program:

| Súlyosság | Következmény |
|---|---|
| `MINOR` | Egyszeri, legalább 2 és kevesebb mint 5 másodperces időveszteség. |
| `MAJOR` | Legalább 5 és kevesebb mint 10 másodperces időveszteség, valamint további, körönkénti lassulás. |
| `TERMINAL` | Az autó kiesik a versenyből. |

Egy autó körönként legfeljebb egy ütközésben szerepelhet. Kiesett autó további köröket nem teljesít.

### Végeredmény

A célba érők összidő szerint szerepelnek az eredménylistában. A program megjeleníti a győztestől mért időhátrányt is.

A kiesők a célba érők mögé kerülnek, DNF jelöléssel, elsősorban a teljesített körök száma szerint rendezve. Ha minden autó kiesik, a program nem hirdet győztest.

## Objektumorientált felépítés

### Főbb típusok

| Típus | Felelősség |
|---|---|
| `Driver` | A versenyző neve, képessége és agresszivitása. |
| `Car` | Az autó modellje, tempója és megbízhatósága. |
| `Team` | A csapat, valamint a hozzá tartozó versenyző és autó. |
| `Track` | A pálya neve és alap-körideje. |
| `RaceEntry` | Egy csapat futamonként változó állapota. |
| `IncidentSeverity` | A baleseti következmény lehetséges súlyosságai. |
| `IncidentService` | Baleseti következmények alkalmazása egy nevezőre. |
| `RaceSimulator` | A körök szimulálása, az ütközések ellenőrzése és az eredmények rendezése. |
| `LapTimeStrategy` | A köridőszámítás közös interfésze. |
| `DryLapTimeStrategy` | Száraz körülmények köridőszámítása. |
| `WetLapTimeStrategy` | Esős körülmények köridőszámítása. |
| `Main` | A program összeállítása és a konzolos megjelenítés. |

### Egységbezárás és validáció

Az osztályok privát mezőket használnak. A konstruktorok ellenőrzik a megadott adatokat, a futamállapot pedig szabályokat ellenőrző metódusokon keresztül módosítható.

Például:

- A versenyző neve nem lehet üres.
- A képesség, agresszivitás, tempó és megbízhatóság értéke 1–100 közötti.
- A köridő pozitív és véges.
- A kiesett autó nem teljesíthet újabb kört.

### Objektumkapcsolatok

Egy `Team` egy `Driver` és egy `Car` objektumra hivatkozik.

Minden futamhoz új `RaceEntry` objektumok készülnek. Így az előző verseny összideje, sérülése és kiesése nem kerül át a következő futamra.

### Kollekciók és rendezés

A program generikus `List<T>` kollekciókat és `ArrayList<T>` megvalósításokat használ.

A versenyeredményeket `Comparator` segítségével rendezi.

## Tervezési minta: Strategy

A köridőszámítás Strategy mintát használ.

Résztvevők:

- `LapTimeStrategy`: a közös szerződés.
- `DryLapTimeStrategy`: a száraz köridőszámítás megvalósítása.
- `WetLapTimeStrategy`: az esős köridőszámítás megvalósítása.
- `RaceSimulator`: a kiválasztott stratégiát használó osztály.

A szimulátor az interfészen keresztül hívja a köridőszámítást. A konkrét stratégia határozza meg a futás közben végrehajtott számítást, így a futamot lebonyolító ciklust nem kell átírni egy új köridőszámítás bevezetésekor.

Ez az interfészalapú absztrakciót és a futásidejű polimorfizmust is bemutatja.

## Csomagok

| Csomag | Tartalom |
|---|---|
| `hu.levente.gt3` | Belépési pont. |
| `hu.levente.gt3.domain` | A szimuláció adatmodellje és futamállapota. |
| `hu.levente.gt3.service` | A verseny és a balesetek működése. |
| `hu.levente.gt3.strategy` | Köridőszámítási stratégiák. |

## Jelenlegi korlátok

- Nincs interaktív menü; a beállítások a forráskódban módosíthatók.
- Nincs fájlos mentés és visszatöltés.
- Nincsenek automatizált JUnit-tesztek.
- Nincs saját kivételosztály; a validáció beépített kivételeket használ.
- Az UML-diagram és a külön user story dokumentáció még nem készült el.
- Az autó megbízhatósága tárolt adat, de egyelőre nem befolyásolja a futamot.
- Az ütközéseket a program a körök végén ellenőrzi, ezért a baleset köre teljesített körként szerepel.
- Nincsenek boxkiállások, gumistratégiák, lekörözések vagy biztonsági autó.
- A virtuális fogadás későbbi bővítés.

A jelenlegi verzió működő prototípus; a beadási követelmények teljesítéséhez további kiegészítések szükségesek.

## Készítő

Levente — [p-levente05](https://github.com/p-levente05)

## Licenc

A projekt MIT licenc alatt érhető el. Részletek a [LICENSE](LICENSE) fájlban.