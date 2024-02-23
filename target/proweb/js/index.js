function delFruit(fid){
    if(confirm("Do you really want to delete the fruit?")){
        window.location.href="fruit.do?fid="+fid+'&operate=delete';
    }
}

function page(pageNo){
    window.location.href="fruit.do?pageNo="+pageNo;
}