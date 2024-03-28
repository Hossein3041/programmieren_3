window.addEventListener("load", init);

function init(){
    let draggables = document.querySelectorAll(".draggable");
    for(let element of draggables){
        element.draggable = true;

        element.addEventListener("dragstart", function(event){
            event.dataTransfer.effectAllowed = "copy";
            event.dataTransfer.setData("text/plain", event.target.innerHTML);
            appendToOutput(
                `${event.type} (${event.type}: ${event.target.innerHTML})`
            );
        }, false);

        element.addEventListener("dragend", function(event){
            event.preventDefault();
            appendToOutput(`${event.type} (${event.target}
            effect performed: "${event.dataTransfer.dropEffect}")`);
        }, false);
    }

    let destination = document.getElementById("destination");

    destination.addEventListener("dragover", function(event){
        event.preventDefault();
        event.dataTransfer.dropEffect = "copy";
        appendToOutput(event.type);
    }, false);

    destination.addEventListener("dragenter", function(event){
        event.preventDefault();
        event.dataTransfer.dropEffect = "copy";
        appendToOutput(event.type);
    }, false);

    destination.addEventListener("dragleave", function(event){
        event.preventDefault();
        appendToOutput(event.type);
    }, false);

    destination.addEventListener("drop", function(event){
        event.preventDefault();
        let data = event.dataTransfer.getData("text/plain");
        let li = document.createElement("li");
        li.appendChild(document.createTextNode(data));
        destination.appendChild(li);
        appendToOutput(`${event.type} (${data})`);
    }, false);
}

function appendToOutput(text){
    let output = document.getElementById("output");
    if(output){
        output.innerHTML += text + "<br>";
    }
}