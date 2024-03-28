window.onload = function(){
    nodeList();
};

function nodeList(){

    let nodeList = document.getElementsByTagName("li");
    for(let i = 0; i < nodeList.length; i++){
        alert("Mit item(i): " + nodeList.item(i).textContent);
    }

    for(let i = 0; i < nodeList.length; i++){
        alert("Mit [i]: " + nodeList[i].textContent);
    }
}