function showBGColor(){
    //alert(event.srcElement);
    //alert(event.srcElement.tagName);
    if(event && event.srcElement && event.srcElement.tagName=="TD")
    {
        var td = event.srcElement;
        var tr = td.parentElement;
        tr.style.backgroundColor = "navy";
        var tds = tr.cells;
        for (var i = 0; i < tds.length; i++){
            tds[i].style.color = "white";
        }
    }
}

function clearBGColor(){
    if (event && event.srcElement && event.srcElement.tagName == "TD"){
        var td = event.srcElement;
        var tr = td.parentElement;
        tr.style.backgroundColor = "transparent";
        var tds = tr.cells;
        for (var i = 0; i < tds.length; i++){
            tds[i].style.color = "teal";
        }
    }
}

function showHand(){
    if (event && event.srcElement && event.srcElement.tagName == "TD"){
        var td = event.srcElement;
        td.style.cursor = "hand";
    }
}