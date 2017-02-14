//控制字符大小
function testCharSize(col,size)
{
if(col.value.length>size)
{
alert("不能超过"+size+"个字符！");
col.focus();
return false;
}
}