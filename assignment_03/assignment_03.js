"use strict";

window.addEventListener("load", function(){
    mischButton();
});

// Braucht man später für drag and drop, sowie touch-Event
const MY_TYPE = "text/mytype";

// Braucht man für das Schütteln später, um prüfen, wann schütteln möglich sein soll (Wenn man auf dem Knopf drückt)
let beschleunigungAktiviert = false;
// Braucht man, um durch den Knopf, den Elementtyp an deviceOrientationHandler weiterzugeben
let motionTyp = "";

// Beide Farben für die letzte Teilaufgabe, in der Zusatzaufgabe
let colorA = "#ffffff";  // Farbe A
let colorB = "#0000FF"; // Farbe B

function mischButton(){
    // Alle zu tauschenden Elemente in ein array
    const elementTypen = ["h1", "h2", "img", "p", "a", "li", "tr", "td"];
    const body = document.body;

    // Stuerabschnitt für die normalen Buttons, um zu mischen
    const steuerAbschnitt = document.createElement("div");
    steuerAbschnitt.style.border = "2px solid black";
    steuerAbschnitt.style.padding = "10px";
    steuerAbschnitt.style.marginTop = "20px";
    steuerAbschnitt.style.backgroundColor = "#f0f0f0";

    // Für jedes Element, ...
    elementTypen.forEach(aktTyp => {
        // Elementtyp nehmen, also alle
        const elementName = document.getElementsByTagName(aktTyp);
        // Anzahl in anzahl rein tun
        const anzahl = elementName.length;
        // Für drag and drop später, als draggable setzen
        for(let element of elementName){
            element.classList.add("draggable");
            element.draggable = true;

        }

        if(anzahl > 0){
            // Normale Buttons erstellen
            const button = document.createElement("button");
            button.innerHTML = `Instanz ${aktTyp}, mit Anzahl: ${anzahl}, mischen!`;
            button.addEventListener("click", function(){
                // Mehrmaliges Tauschen, nach dem Klicken auf dem Button.
                for(let i = 0; i < 5; i++){
                    (function(index){
                        setTimeout(function(){
                            // Elementtyp an Tausch-Funktion weitergeben
                            auswahlAktInstanz(aktTyp, true);
                            // Farbe zurücksetzen
                            setTimeout(function(){
                                farbeZurueck(aktTyp);
                            }, 2000 * index + 2000);
                        }, 2000 * index);
                    })(i);
                }

            });
            // Button an Steuerabschnitt hinzufügen
            steuerAbschnitt.appendChild(button);
        }
    });
    // Steuerabschnitt an body
    body.appendChild(steuerAbschnitt);
    // Funktion für drag and drop, sowie touch aufrufen
    initTouchEvents();

    // Ab hier der Code für das Schütteln:
    // dito wie oben, für Schüttelabschnitt
    const sAbschnitt = document.createElement("div");
    sAbschnitt.style.border = "2px solid black";
    sAbschnitt.style.padding = "10px";
    sAbschnitt.style.marginTop = "20px";
    sAbschnitt.style.backgroundColor = "#f0f0f0";

    let buttonErstellt = false;
    const sButton = document.createElement("button");
    sButton.innerHTML = "Steuerabschnitt für das Schütteln aufmachen!";
    sButton.addEventListener("click", function(){
        // Falls kein Button, dann Button erstellen.
        if (!buttonErstellt){
            elementTypen.forEach(aktTyp3 => {
                const elementButton = document.createElement("button");
                elementButton.innerHTML = `Zwei zufällige Instanzen von ${aktTyp3}, durch Schütteln mischen!`;
                elementButton.classList.add("dynamischerButton");
                elementButton.addEventListener("click", function(){
                    motionTyp = aktTyp3;
                });

                sAbschnitt.appendChild(elementButton);
            });
            buttonErstellt = true;
        } else {
            // Falls Button erstellt, alle  Buttons für das Schütteln entfernen!
            const buttonsEntfernen = sAbschnitt.querySelectorAll(".dynamischerButton");
            buttonsEntfernen.forEach(buttonEndlichEntfernen => {
                buttonEndlichEntfernen.remove();
            });
            buttonErstellt = false;
            beschleunigungAktiviert = false;
        }

    });

    sAbschnitt.appendChild(sButton);
    body.appendChild(sAbschnitt);

}

// In Funktion auswahlAktInstanz wird ein Element weitergegeben...
function auswahlAktInstanz(aktTyp2, color){
    const instanzen = document.querySelectorAll(aktTyp2);
    if(instanzen.length < 2){
        console.log(`Zu wenige Elemente vom Typ ${Array.from(instanzen)} !`);
        return;
    }

    let i1, i2, instanzEins, instanzZwei;

    do{
        i1 = Math.floor(Math.random() * instanzen.length); // Index i1 random festlegen
        i2 = Math.floor(Math.random() * instanzen.length); // Index i2 random festlegen
        instanzEins = instanzen[i1]; // Instanz festlegen, basierend auf i1
        instanzZwei = instanzen[i2]; // dito mit i2
        // Solange, bis beide Instanzen ungleich sind && das eine nicht eltern oder großeltern von dem anderen ist
    } while(i1 === i2 || instanzEins.contains(instanzZwei) || instanzZwei.contains(instanzEins)); // Hier werden eltern und großeltern weggenommen

    // Beide Instanzen in Funktion tauscheInstanzen weiterleiten. Hier werden beide endlich vertauscht
    tauscheInstanzen(instanzEins, instanzZwei, color);

}

function tauscheInstanzen(instanzEins, instanzZwei, color) {
    // Ich gehen davon aus, dass im schlimmsten Fall, alle Instanzen ungleiche Eltern haben. deshalb arbeite ich beim Vertauschen mit beiden Eltern.
    // Um den Referenz von beiden Instanzen nicht zu verlieren, egal ob geschachtelt oder nicht, arbeite ich mit Array.
    let eltern1 = instanzEins.parentNode;
    let eltern2 = instanzZwei.parentNode;
    let i1, i2;

    if (!eltern1 || !eltern2 || eltern1.isEqualNode(instanzZwei) || eltern2.isEqualNode(instanzEins)) { // Hier werden eltern weggenommen. Wahrscheinlich braucht man das nicht, da man davor die großeltern und eltern gechekt hat
        console.log("Eltern-Kind-Beziehung kann nicht vertauscht werden!")
        return;
    }

    // Von den Kindern von eltern1, den Intex für InstanzEins festlegen, und das in i1 rein tun, um das später zu vertauschen
    for (let i = 0; i < eltern1.children.length; i++) {
        if (eltern1.children[i].isEqualNode(instanzEins)) {
            i1 = i;
        }
    }
    // dito für InstanzZwei
    for (let i = 0; i < eltern2.children.length; i++) {
        if (eltern2.children[i].isEqualNode(instanzZwei)) {
            i2 = i;
        }
    }
    // Falls beide Eltern gleich sind, und i1 vor i2 liegt, position von i2 erhöhen (inkrementieren), um nachher bei insertBefore, vor i2, instanzEins reintun.
    if (eltern1.isEqualNode(eltern2) && i1 < i2) {
        i2++;
    }
    // Farbe festlegen, für Hintergrund
    if(color){
        instanzEins.style.backgroundColor = "blue";
        instanzZwei.style.backgroundColor = "yellow";
    }
    // Hier die Nodes miteinander endlich vertauschen
    eltern1.insertBefore(instanzZwei, eltern1.children[i1]);
    eltern2.insertBefore(instanzEins, eltern2.children[i2]);
}

function farbeZurueck(aktTyp2) {
    // Funktion, um Farbe zurückzusetzen
    const instanzen = document.querySelectorAll(aktTyp2);
    instanzen.forEach(instanz => {
        instanz.style.backgroundColor = "white";
    });
}

function initTouchEvents() {
    // Alle ELemente nehmen, die draggable sind
    const draggables = document.querySelectorAll('.draggable');
    draggables.forEach(elem => {
        // Handler für Drag-And-Drop
        // Austausch findet ab onDrop statt
        elem.addEventListener('dragstart', onDragStart, false);
        elem.addEventListener('dragend', onDragEnd, false);
        elem.addEventListener('drop', onDrop,  false);
        elem.addEventListener('dragover', onDragOver,  false);


        // Touch-Events hinzufügen
        // Austausch findet in ontouchend statt
        elem.addEventListener('touchstart', ontouchstart,  false);
        elem.addEventListener('touchmove', ontouchmove,  false);
        elem.addEventListener('touchend', ontouchend, false);
    });
}

// Variable, um ID von dem ersten Element zu nehmen
let activeElementId = "";

// Tmp für den Tausch
let tmp = document.createElement("div");
// Event-Handler für den Touchstart-Ereignis
function ontouchstart(event){
    // Verhindert standardverhalten
    event.preventDefault();
    // ID von Element 1 speichern
    activeElementId = event.target.id;
    // clonen
    tmp = document.getElementById(activeElementId);
    tmp = tmp.cloneNode(true);
    // Bewegbar machen
    tmp.style.position = "absolute";
    tmp.classList.add("removed");
    // Element hinzufügen
    document.body.appendChild(tmp);
    // Farbe zurücksetzen
    document.querySelectorAll('.draggable').forEach(element => {
        element.style.backgroundColor = "white";
    })

}

// Event-Handler für das Touchmove-Ereignis
function ontouchmove(event){
    // Hierdurch aktuelle Position ermitteln
    let touchLocation = event.targetTouches[0];
    // Position jederzeit aktualisieren
    tmp.style.left = touchLocation.pageX + "px";
    tmp.style.top = touchLocation.pageY + "px";
}

function ontouchend(event){

    let changedTouch = event.changedTouches[0];
    // Locatin von Element 1 deaktieren
    tmp.style.pointerEvents = 'none';
    // Location von Element 2 ermitteln
    let elem = document.elementFromPoint(changedTouch.clientX, changedTouch.clientY);
    // id von Element 2 nehmen
    let elementIdOfTarget = elem.id;
    // Variable deklarieren
    let instanzEins = document.getElementById(activeElementId);
    let instanzZwei = document.getElementById(elementIdOfTarget);
    tmp.remove();
    // tauschen lassen
    if (!instanzEins.contains(instanzZwei) && !instanzZwei.contains(instanzEins) &&
        instanzEins.tagName === instanzZwei.tagName){
        tauscheInstanzen(instanzEins, instanzZwei, true);
    }

}

function onDragStart(event){
    // Soll bewegt werden
    event.dataTransfer.effectAllowed = "move";
    // Daten setzen
    event.dataTransfer.setData(MY_TYPE, event.target.id);
    // Farbe zurücksetzen
    document.querySelectorAll('.draggable').forEach(element => {
        element.style.backgroundColor = "white";
    })


}

function onDrop(event){
    event.preventDefault();
    // ID von Element 1
    let id = event.dataTransfer.getData(MY_TYPE);
    // Variable setzen, + Id von Element 2 nehmen
    let instanzEins = document.getElementById(id);
    let instanzZwei = document.getElementById(event.target.id);
    // Tauschen lassen
    if (!instanzEins.contains(instanzZwei) && !instanzZwei.contains(instanzEins) &&
        instanzEins.tagName === instanzZwei.tagName){
        tauscheInstanzen(instanzEins, instanzZwei, true);
    }

}

function onDragEnd(event){
    event.preventDefault();
    // Ermittelt die ID des gezogenen Elements aus den übertragenen Daten
    // Das ist überflüssig
    let id = event.dataTransfer.getData(MY_TYPE);
    console.log(id,  `this is id ${id} onDragEnd`, id)

}

function onDragOver(event){
    event.preventDefault();
    event.dataTransfer.dropEffect = "move";
}

// Ab hier Code für deviceorientation
// Mit devicemotion konnte ich nicht arbeiten, da die Beschleunigung mit Browser nicht emuliert werden konnte.
// Firefox konnte auch nicht helfen
window.addEventListener('deviceorientation', deviceOrientationHandler, true);

function deviceOrientationHandler(event){
    // Die Werte alphaOrientation, betaOrientation und gammaOrientation geben die Orientierung des Geräts an
    let alphaOrientation = event.alpha; // Drehung um die Z-Achse (Kompassrichtung)
    let betaOrientation = event.beta;   // Neigung vorwärts/rückwärts (Vor- und Rückneigung)
    let gammaOrientation = event.gamma; // Neigung links/rechts (Seitliche Neigung)

    let angle = event.alpha;
    let color = updateBackgroundColor(angle);
    document.body.style.backgroundColor =   `rgb(${color[0]},${color[1]},${color[2]})`;

    if (motionTyp === ""){
        return;
    } else if (wertePruefen(alphaOrientation, betaOrientation, gammaOrientation)){
        auswahlAktInstanz(motionTyp, false);
    }

}

function wertePruefen(alphaOrientation, betaOrientation, gammaOrientation){
    const SchwellenwertAlpha = 10;  // Beispielswert für die Alpha-Schwelle in Grad
    const SchwellenwertBeta = 10;   // dito für beta
    const SchwellenwertGamma = 10;  // dito für gamma

    return (alphaOrientation > SchwellenwertAlpha && betaOrientation > SchwellenwertBeta && gammaOrientation > SchwellenwertGamma);
}

function updateBackgroundColor(angle) {
    // basierend auf ausrichtung von alpha, interpolierten farbwert berechnen
    // Es wird berechnet, wie weit der Winkel (alpha) von der Mittel (180 Grad) entfernt ist
    let factor = Math.abs(angle - 180) / 180; // Lineare Interpolation
    // Hier werden die zwei Farben, und der aktuelle Winkel übergeben
    return interpolateColor(colorA,colorB,factor)

}

function interpolateColor(color1, color2, factor) {
    // Interpolieren zwischen Farben basierend auf dem Faktor und dem Kanal (r, g oder b)
    // aus den Farben, rot, blau und etc. extrahieren

    // r1, g1 und b1 extrahieren die Werte für Rot, Grün und Blau
    // Aus der hexadezimalen Farbe color1
    let r1 = parseInt(color1.substring(1, 3), 16);
    let g1 = parseInt(color1.substring(3, 5), 16);
    let b1 = parseInt(color1.substring(5, 7), 16);

    // dito für color2
    let r2 = parseInt(color2.substring(1, 3), 16);
    let g2 = parseInt(color2.substring(3, 5), 16);
    let b2 = parseInt(color2.substring(5, 7), 16);

    // Eigentliche Interpolation: Basierend auf dem Factor Winkel:
    // Berechnet interpolierten Wert für rot
    let r = Math.round(r1 * factor + r2 * (1 - factor));
    // dito für grün
    let g = Math.round(g1 * factor + g2 * (1 - factor));
    // dito für blau
    let b = Math.round(b1 * factor + b2 * (1 - factor));
    // array zurückgeben
    return [r, g, b];
}
