function delFruit(fid){
    if(confirm("Do you really want to delete the fruit?")){
        window.location.href="del.do?fid="+fid;
    }
}

function page(pageNo){
    window.location.href="index?pageNo="+pageNo;
}