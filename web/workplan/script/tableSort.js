function sort(tableId, sortColumn,TextType) {   

    var table = document.getElementById(tableId);   
    var tableBody = table.tBodies[0];   
    var tableRows = tableBody.rows;   
      
       
    var rowsArr = new Array();   
    for (var i = 0; i < tableRows.length; i++) {   
       rowsArr.push(tableRows[i]);   
    }   
    
    if (table.sortColumn == sortColumn) {   
        rowsArr.reverse();   
    } else {      
       if( TextType.toLowerCase() == 'number' )
            {
                rowsArr.sort(sortNumberAsc);
            }else if(TextType.toLowerCase() == 'char')
            { 
                rowsArr.sort(sortCharAsc);
            } 
    }  
     
    var tbodyFragment = document.createDocumentFragment();   
    for (var i = 0; i < rowsArr.length; i++) {   
        tbodyFragment.appendChild(rowsArr[i]);   
    }   
    tableBody.appendChild(tbodyFragment);   
    table.sortColumn = sortColumn;   
   

function sortNumberAsc(a,b)
{
     var aaa = a.cells[sortColumn].innerText?a.cells[sortColumn].innerText:a.cells[sortColumn].innerHTML;
     var bbb = b.cells[sortColumn].innerText?b.cells[sortColumn].innerText:b.cells[sortColumn].innerHTML;
       
     if(parseFloat(aaa)>parseFloat(bbb))
          return 1;
     else if( parseFloat(aaa)< parseFloat(bbb))
          return -1;
     else return 0;
}
            
function sortCharAsc(a,b)
{
    var aaa = a.cells[sortColumn].innerText?a.cells[sortColumn].innerText:a.cells[sortColumn].innerHTML;
    var bbb = b.cells[sortColumn].innerText?b.cells[sortColumn].innerText:b.cells[sortColumn].innerHTML;
      
    return aaa.localeCompare(bbb);
}
 }           
