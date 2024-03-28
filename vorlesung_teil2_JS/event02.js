window.addEventListener("load", init);

const MY_TYPE = "text/mytype";

function init() {
    let draggables = document.querySelectorAll(".draggable");
    let destinationUL = document.getElementById("destination");

    for (let element of draggables) {
        element.draggable = true;

        element.addEventListener("dragstart", function(event) {
            event.dataTransfer.effectAllowed = "move";
            event.dataTransfer.setData(MY_TYPE, event.target.outerHTML);
            appendToOutput(`${event.type} (${event.target.outerHTML})`);
        }, false);

        element.addEventListener("dragend", function(event) {
            event.preventDefault();
            if (event.dataTransfer.dropEffect === "move") {
                element.parentNode.removeChild(element);
                appendToOutput(`dragend: element removed from its parent`);
            }
        }, false);
    }

    destinationUL.addEventListener("drop", function(event) {
        event.preventDefault();
        let data = event.dataTransfer.getData(MY_TYPE);
        if (data) {
            let template = document.createElement('template');
            template.innerHTML = data.trim();
            let dataElement = template.content.firstChild;
            dataElement.draggable = false;
            destinationUL.appendChild(dataElement);
            appendToOutput(`${event.type} (${data})`);
        } else {
            appendToOutput(`${event.type}; no data found in data transfer`);
        }
    }, false);

    destinationUL.addEventListener("dragover", function(event) {
        event.preventDefault();
        event.dataTransfer.dropEffect = "move";
    }, false);

    // ... Behalten Sie die restlichen Event-Handler für 'dragenter' und 'dragleave' bei, falls erforderlich.
}

function appendToOutput(text) {
    let output = document.getElementById("output");
    if (output) {
        output.innerHTML += text + "<br>";
    }
}

