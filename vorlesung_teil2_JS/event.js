function handleClick(event){
    alert("Button  wurde geklickt!");
}

const button = document.getElementById("myButton");

button.addEventListener("click", handleClick, false);

button.addEventListener("click", function(){
    alert("Der Event-Handler wird jetzt entfernt.");
    button.removeEventListener("click", handleClick, false);
}, false);

const parent = document.getElementById("parent");
const child = document.getElementById("child");

parent.addEventListener("click", function(event) {
    console.log("Capturing-Phase auf Eltern-Element (parent)");
}, true);

parent.addEventListener("click", function(event){
    console.log("Bubbling-Phase auf Eltern-Element (parent)");
});

child.addEventListener("click", function(event){
    console.log("Bubbling-Phase auf Kind-Element (child)");
});