window.onload = init;
function init(){
    /* init DOM Level 0 event handler */
    let button = document.getElementById("myButtonLevel0");
    button.onclick = function(event){
        alert(`${this}
            this.id: "${this.id}"
            event.value: "${this.value}"
            event: "${event}"
            this.form.foo.value: "${this.form.foo.value}"
            this.form.method: "${this.form.method}"
            this.form.action: "${this.form.action}"
            document.title: "${document.title}"`);
    }
}