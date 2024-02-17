function $(id){
    return document.getElementById(id);
}

window.onload = function(){
    updateTotal();
    var fruitTbl = $('tbl_fruit');
    var rows = fruitTbl.rows;
    for (var i = 1; i < rows.length-1; i++){
        trBindEvent(rows[i]);
    }
    var button = $('fadd');
    button.onclick = addTableRow;
}

function trBindEvent(tr){
    tr.onmouseover = showBGColor;
    tr.onmouseout = clearBGColor;
    var cells = tr.cells;
    var priceTD = cells[1];
    priceTD.onmouseover = showHand;
    priceTD.onclick = editPrice;
    var img = cells[4].firstChild;
    if (img && img.tagName == "IMG"){
           img.onclick = delFruit;
    }
}

function addTableRow(){
    var name = $('fname').value;
    var price = parseFloat($('fprice').value);
    var quantity = parseInt($('fqty').value);
    var table = $('tbl_fruit');
    var numRows = table.rows.length;
    var row = table.insertRow(numRows-1);
    var name_cell = row.insertCell(0);
    name_cell.innerText = name;
    var price_cell = row.insertCell(1);
    price_cell.innerText = price;
    var qty_cell = row.insertCell(2);
    qty_cell.innerText = quantity;
    var subtotal_cell = row.insertCell(3);
    subtotal_cell.innerText = price*quantity;
    var del_cell = row.insertCell(4);
    del_cell.innerHTML = "<img src=\"imgs/trash.png\" class=\"delImg\">";
    updateTotal();
    trBindEvent(row);
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
                input.onkeydown = checkInput;
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
    var fruit_tbl = $('tbl_fruit');
    var rows = fruit_tbl.rows;
    var sum = 0;
    for (var i = 1; i < rows.length -1; i++){
        var tr = rows[i];
        var subTotal = parseInt(tr.cells[3].innerText);
        sum += subTotal;
    }
    rows[rows.length-1].cells[1].innerText = sum;
}

function delFruit(){
    if (event && event.srcElement && event.srcElement.tagName == "IMG"){
        if (window.confirm("Are you sure delete the selected inventory record?")){
            var img = event.srcElement;
            var tr = img.parentElement.parentElement;
            var fruitTbl = $("tbl_fruit")
            fruitTbl.deleteRow(tr.rowIndex);
            updateTotal();
        }
        
    }
}

function checkInput(){
    var kc = event.keyCode;
    console.log(kc);
    // 0 ~ 9: 48~57
    // backspace: 8
    // enter: 13
    if (!((kc >= 48 && kc <= 57) || kc == 8 || kc == 13)){
        event.returnValue = false;
    }
    if (kc == 13){
        event.srcElement.blur();
    }
}