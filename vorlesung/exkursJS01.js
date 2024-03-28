function beispiel(){
    alert("Das ist ein Beispiel");
}

function welt(){
    alert("Hallo Welt!");
}

function test(){
    alert("called function test, first argument ist " + arguments[0] + ", type is " + typeof arguments[0]);
}

function testRest(first, ...rest){
    alert(`first: ${first}, rest: ${rest}`);
}

function test2(...args){
    alert(`called function is: first argument is ´${args[0]}´ of type ´${typeof args[0]}´`);
}

function testRest2(first, ...rest){
    alert(`first: ${first}, rest: ${rest}`);
}

function f(value){
    if(arguments.length === 0){
        return "called f without arguments";
    } else {
        return "called f with parameter " + value + " of type " + typeof value;
    }
}

function usingVariables(){
    let i = 1; alert(i);
    i = "Text"; alert(i);
    i = f;
    alert(i()); alert(i(16)); alert(i("17")); alert(i);
}

//let i = 0;
//while(i != 10){
//    i += 0.1;
//}
//alert(`done`);

//let a = 9999999999999999;
//let b =  10000000000000000;
//alert(`a != b: ${a!=b}`);

let s1 = "foo"; console.log(`${s1.length}`);
let s2 =  ""; console.log(`${s2.length}`);

let test5;
console.log(`"${test5}" of type "${typeof test5}"`);

let head = null;
console.log(`type of head: "${typeof head}"`);

// Welche Ausgabe liefert der JavaScript-Code:

// JavaScript-Wrapper "String"
function testString(){
    let a = new String();
    alert("a: \"" + a + "\"");

    let b = new String("Hallo");
    alert("b: \"" + b + "\"");

    let c = new String(42);
    alert("c: \"" + c + "\"");

    let d = new String;
    alert("d: \"" + d + "\"");

    let e = new d("foo");
    alert("e: \"" + e + "\"");
}

// JavaScript-Wrapper "Number"
function testNumber(){
    let a = new Number();
    alert("a: " + a);

    let b = new Number(42);
    alert("b: " + b);

    let c = new Number("42");
    alert("c: " + c);

    let d = new Number("Nummer 42");
    alert("d: " + d);

    let e = new Number(`a`);
    alert("e: " + e);
}

// JavaScript-Wrapper "Boolean"
function testBoolean(){
    let a1 = new Boolean(); alert("a1: " + a1);
    let a2 = new Boolean(true); alert("a2: " + a2);

    let b1 = new Boolean("wahr"); alert("b1: " + b1);
    let b2 = new Boolean("false"); alert("b2: " + b2);
    let b3 = new Boolean("stimmt nicht"); alert("b3: " + b3);
    let b4 = new Boolean(""); alert("b4: " + b4);

    let c1 = new Boolean(1 == 0); alert("c1: " + c1);
    let c2 = new Boolean(1 == 1); alert("c2: " + c2);
}

/**
 * Vorsicht bei Verwendung Boolean in Ausdrücken
 * - false && true liefert false (wie erwartet)
 * - new Boolean(false) && true liefert true!
 *     hier wird nicht der Wert des Wrapper-Objekts sondern das Objekt selbst ausgewertet;
 *     Objekte liefern in JavaScript immer true!
 * - Zugriff auf den eigentlichen Wert mit valueof():
 *  new Boolean(false).valueOf() && true liefert
 *  endlich false!
 *
 *  Bemerkungen zu Wrapper-Objekten:
 *  -   Sie werden in JavaScript implizit für Typanpassungen verwendet
 *
 *  -   Aufgrund der merkwürdigen Eigenschaft davon sollte auf die explizite Verwendung verzichtet werden!
 *      (Number, Boolean, ...)
 */

// JavaScript-Typ "Object"

function testObject(){
    let b1 = new Object();

    b1.eins = "eins";
    b1.zwei = 2;
    b1.drei = new Object;

    alert("b1: " + b1);
    alert("b1.eins: " + b1.eins +
        ", b1.zwei: " + b1.zwei +
        ", b1.drei: " + b1.drei);
}

/** Zugriff auf Objekteigenschaften
 *  Auf Eigenschaften (Properties) von Objekten zugreifen, über Punkt- oder Klammernotation
 */

function testObjectAccess(){
    let a1 = new Object();
    a1.foo = "Eigenschaft foo";
    a1.gnats = 42;
    a1.bar = [10, 11, "test", true, {}];

    try{
        alert(a1.foo);
        alert(a1["gnats"]);
        //alert(a1[gnats]);
        alert(a1.bar);
        alert(a1.bar[0]);
        alert(a1.bar[2]);
    } catch (ex) {
        alert("Exception: " + ex);
    }
    finally{
        alert("finally done");
    }
}

/** for-Schleife
 * Neben for-Schleifen mit expliziten Laufvariablen; in JS, folgende Kurzform:
 */
function forschleife(){
    let a2 = new Object();
    a2.arr = [10, 11, "test", true, {}];
    // Iteriert über die Einträge eines Arrays:
    for(let value of a2.arr){
        alert(`${value}`);
    }

    // Iteriert über die Namen der Eigenschaften eines Objekts
    for(let member in a2){
        alert("member: \"" + member + "\", value: \"" + a2[member] + "\"");
    }

    // Iteriert über die Werte der Eigenschaften eines Objekts mit for each:(Achtung: deprecated!)
    //for each(let val in a2){
    //    alert("object a2 contains value \"" + val + "\"");
    //}
}

// JavaScript-Typ "Array"
// Gegeben sei folgender JavaScript-Code:
function testArray(){
    let a = new Array(5);

    for(let i = 0; i < 5; i++){
        a[a.length+1] = i;
    }

    for(let i = 0; i < a.length; i++) {
        alert("a[" + i + "]: " + a[i]);
    }
}

/** JSON (JavaScript Object Notation)
 *  JSON: textbasiertes Austauschformat für JavaScript Objekte
 *
 *  - Objektdefinitionen in geschweiften Klammern eingeschlossen {}
 *      -> Bestandteile der Objekte durch "," getrennt
 *      -> Member und ihre Werte durch ":" getrennt
 *  - Bestandteile von Arrays in eckigen Klammern "[]" eingeschlossen
 */

// Beispiele zu JSON
function construction01(){
    let a = {"foo" : "a"};
    alert("a.foo: " + a.foo);


    let b = [1, "1", true];
    alert("b: " + b);
    alert("type of b[2] is " + typeof b[2]);

    let c = {"foo": {"gnu": "GNU", "gnats":[1, 2, 3]},
                        "gnu": [1, {"a": "A"}]};
    alert("c: " + c);
    alert("c.foo.gnats[1]: " + c.foo.gnats[1]);
    alert("c.gnu[1].a: " + c.gnu[1].a);
}

/** Bemerkungen zum Erzeugen von Objekten und Arrays
 *   In JS in der Regel wird verzichtet auf new, zum Erzeugen von leeren Objekten- oder Array-Instanzen!
 *
 *   Stattdessen wird die JSON-Notation verwendet:
 *   let a = {} statt let a = new Object()
 *   oder
 *   let a = [] statt let a = new Array()
 *
 *   Ebenso verwende besser
 *   let a = ´foo´ statt let a = new String(´foo´)
 */

/** JavaScript-Objekte aus Strings erzeugen
 * - Objekte in JSON-Notation als Strings ablegen
 * - Durch Methode JSON.parse Strings in JSON-Notation in JavaScript umwandeln
 * - Durch Methode JSON.stringify beliebige Objekte als Strings in JSON-Notation codieren
 */

// Beispiele zu JSON.parse
//(vgl. Beispiele zu JSON(1))
function parsing01(){
    let as = `{"foo": "1"}`;
    alert("as: " + as);
    let a = JSON.parse(as);
    alert("a.foo: " + a.foo);

    let bs = `[1, "1", true]`;
    alert("bs: " + bs);
    let b = JSON.parse(bs);
    alert("b: " + b);
    alert("type b[2] is " + typeof b[2]);
}

// Beispiele zu JSON.stringify
function stringify(){
    let a1 = {};
    a1.foo = "FOO";
    a1.gnats = 42;
    a1.bar = [10, 11, "test", true, {}];
    a1.xxx = undefined; // null;

    alert(`${JSON.stringify(a1)}`);
}

/** Bemerkungen zu JSON
 * - JSON-Strings z.B. auch von anderen Quellen (z.B. Web-Services) laden und mittels
 *  JSON.parse in JavaScript-Objekte umwandeln
 * - Mit JSON nur Daten darstellen (keine Funktionen)
 *
 *  Eventuell gehen vorhandene Funktionen sowie Properties,
 *  mit undefined als Wert, während Anwendung verloren!
 */

/** Bemerkungen zu globalen Variablen
 * - alle globalen Variablen in demselben globalen Kontext deklariert ("global Object")!
 * - dadurch können Objekte desselben Namens aus anderen Paketen/JavaScript-Dateien
 *   (unbeabsichtigt) überschrieben werden!
 * - Abhilfe: In jedem Paket/jeder JavaScript-Datei, ein Containerobjekt definieren,
 *      darin die Variablen, Funktionen und etc. ablegen!
 */

/** Bemerkungen zum Einsatz von JavaScript
 *  - Viele Fehler erst zur Laufzeit erkannt:
 *      -> durch fehlenden Compilerbau und nicht vorhandenen Typsicherheit
 *      -> Unbedingt erforderlich: Ausgiebiges Testen
 *  - Umstieg von compilierten OO-Sprachen zu JavaScript ist "gewöhnungsbedürftig"
 *  - JS aufgrund fehlender Typsicherheit für Umsetzung größerer Vorhaben nur bedingt geeignet?!
 */

























