window.onload = function(){
    const elementNamen = ["h1", "h2", "p", "ul", "ol"];

    elementNamen.forEach(function(aktElement){
        let button = document.createElement("button");
        button.innerHTML = `Anzahl von ${aktElement} zählen`;

        let paraVorhanden = false;

        button.addEventListener("click", function(){
            if(paraVorhanden){
                let para = document.querySelector(`p[data-element="${aktElement}"]`);

                if(para){
                    para.remove();
                }

                paraVorhanden = false;
            } else {
                let count = document.getElementsByTagName(aktElement).length;
                let para = document.createElement("p");
                para.textContent = `Anzahl der ${aktElement} Elemente: ${count}`;
                para.setAttribute("data-element", aktElement); // Recherchieren

                button.insertAdjacentElement('afterend', para);
                paraVorhanden = true;
            }
        });

        let deleteButton = document.createElement("button");
        deleteButton.innerHTML = `Alle Instanzen von ${aktElement}, sowie beide Buttons löschen`;

        deleteButton.addEventListener("click", function(){
           const elementInstanz = document.querySelectorAll(aktElement);

           elementInstanz.forEach(function(alleInstanzen){
              alleInstanzen.remove();
           });

           deleteButton.remove();
           button.remove();
        });

        document.body.appendChild(deleteButton);
        document.body.appendChild(button);
    });
}