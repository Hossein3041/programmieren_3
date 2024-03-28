/** 10.2 JavaScript und DOM
 *  Browser bieten eine DOM-Schnittstelle zur Manipulation des geladenen HTML-Dokuments
 *
 *  Aufgaben:
 *  1. Ein fester Bereich eines HTML-Dokuments soll durch Anklicken um einen zufällig
 *     gewählten Wert horizontal verschoben werden
 *
 *  2. Eine Textpassage soll durch Anklicken ihre Farbe ändern
 *
 *  3. Einer Liste sollen durch Anklicken neue Elemente hinzugefügt werden
 */

// Zur Abkürzung verwende für das Containerobjekt innerhalb der Webseite den gültigen JavaScript-Namen $$:
// use a container object instead of the global object
let First01 = {};
window.$$ = First01;

First01.moveAround = function(){
    let x = Math.round(Math.random() * 85);
    //document.getElementById("moveIt").setAttribute("style","position: relative; left: " + x + "%;");
    let target = document.getElementById("moveIt").style;
    target.position = "relative";
    target.left = x + "%";
}

First01.colors = ["#FFFFFF", "#FFA0A0", "#A0FFA0"];
First01.colorIndex = 0;

First01.changeColor = function(){
    First01.colorIndex = (First01.colorIndex + 1) % First01.colors.length;
    document.getElementById("colorP").setAttribute("style", "background-color:" + First01.colors[First01.colorIndex]);
}

First01.addItem = function(){
    let li = document.createElement("li");
    li.appendChild(document.createTextNode(Math.round(Math.random() * 1000)));
    document.getElementById("list").appendChild(li);
}
