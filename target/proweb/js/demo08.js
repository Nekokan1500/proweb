window.onload = function(){
    updateTotal();
    var fruitTbl = document.getElementById('tbl_fruit');
    var rows = fruitTbl.rows;
    for (var i = 1; i < rows.length-1; i++){
        var tr = rows[i];
        tr.onmouseover = showBGColor;
        tr.onmouseout = clearBGColor;
        var cells = tr.cells;
        var priceTD = cells[1];
        priceTD.onmouseover = showHand;
        priceTD.onclick = editPrice;
    }
}

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

function editPrice(){
    if (event && event.srcElement && event.srcElement.tagName == "TD"){
        var priceTD = event.srcElement;
        if (priceTD.firstChild && priceTD.firstChild.nodeType == 3){
            var oldPrice = priceTD.innerText;
            priceTD.innerHTML="<input type='text' size='4' value/>";
            var input = priceTD.firstChild;
            if (input.tagName=="INPUT"){
                input.value = oldPrice;
                input.select();
                input.onblur = updatePrice;
            }
        }
        
    }
}

function updatePrice(){
    if (event && event.srcElement && event.srcElement.tagName == "INPUT"){
        var input = event.srcElement;
        var newPrice = input.value;
        var priceTD = input.parentElement;
        priceTD.innerText = newPrice;
        updateSubTotal(priceTD.parentElement);
    }
}

function updateSubTotal(tr){
    if (tr && tr.tagName == "TR"){
        var tds = tr.cells;
        var price = tds[1].innerText;
        var count = tds[2].innerText;
        var subTotal = parseInt(price)*parseInt(count)
        tds[3].innerText = subTotal;
        updateTotal();
    }
}

function updateTotal(){
    var fruit_tbl = document.getElementById('tbl_fruit');
    var rows = fruit_tbl.rows;
    var sum = 0;
    for (var i = 1; i < rows.length -1; i++){
        var tr = rows[i];
        var subTotal = parseInt(tr.cells[3].innerText);
        sum += subTotal;
    }
    rows[rows.length-1].cells[1].innerText = sum;
}