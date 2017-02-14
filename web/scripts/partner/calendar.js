
    function getTime() {
    // 初始化时间
    var now = new Date()
    var hour = now.getHours()
    var minute = now.getMinutes()
    now = null
    var ampm = "" 
    // 时间检测，并设置上下午AM和PM
    if (hour >= 12) {
    hour -= 12
    ampm = "PM"
    } else
    ampm = "AM"
    hour = (hour == 0) ? 12 : hour
    // add zero digit to a one digit minute
    if (minute < 10)
    minute = "0" + minute // do not parse this number!
    // return time string
    return hour + ":" + minute + " " + ampm
    }
    function leapYear(year) {
    if (year % 4 == 0) // basic rule
    return true // is leap year
    /* else */ // else not needed when statement is "return"
    return false // is not leap year
    }
    function getDays(month, year) {
    // 设定每月的天数数组
    var ar = new Array(12)
    ar[0] = 31 // January
    ar[1] = (leapYear(year)) ? 29 : 28 // February
    ar[2] = 31 // March
    ar[3] = 30 // April
    ar[4] = 31 // May
    ar[5] = 30 // June
    ar[6] = 31 // July
    ar[7] = 31 // August
    ar[8] = 30 // September
    ar[9] = 31 // October
    ar[10] = 30 // November
    ar[11] = 31 // December
    
    return ar[month]
    }
    function getMonthName(month) {
    // 为月份名称设定数组
    var ar = new Array(12)
    ar[0] = "1月"
    ar[1] = "2月"
    ar[2] = "3月"
    ar[3] = "4月"
    ar[4] = "5月"
    ar[5] = "6月"
    ar[6] = "7月"
    ar[7] = "8月"
    ar[8] = "9月"
    ar[9] = "10月"
    ar[10] = "11月"
    ar[11] = "12月"
    
    return ar[month]
    }
    function setCal(div) {
    var now = new Date()
    var year = now.getYear()
    var month = now.getMonth()
    var monthName = getMonthName(month)
    var date = now.getDate()
    now = null
    
    var firstDayInstance = new Date(year, month, 1)
    var firstDay = firstDayInstance.getDay()
    firstDayInstance = null
    
    var days = getDays(month, year)
    // 呼叫函数画日历
    drawCal(div,firstDay + 1, days, date, monthName, year)
    }
    function drawCal(div,firstDay, lastDate, date, monthName, year) {
    // 以下设定表格的属性，这些参数可以自己改变，只是注意相互匹配。

    var headerHeight = 25 // height of the table's header cell
    var border = 1 // 3D height of table's border
    var cellspacing = 1 // width of table's border
    var headerColor = "#000000" // color of table's header
    var headerSize = "2" // size of tables header font
    var colWidth = 22 // width of columns in table
    var dayCellHeight = 12 // height of cells containing days of the week
    var dayColor = "000000" // color of font representing week days
    var cellHeight = 20 // height of cells representing dates in the calendar
    var todayColor = "red" // color specifying today's date in the calendar
    var timeColor = "black" // color of font representing current time
    // create basic table structure
    var text = "" // initialize accumulative variable to empty string
    text += '<CENTER>'
    text += '<TABLE bgcolor=ffffff BORDER=' + border + ' CELLSPACING=' + cellspacing + '>' // table settings
    text += '<TH COLSPAN=7 bgcolor=cccccc HEIGHT=' + headerHeight + ' style="text-align:center;">' // create table header cell
    text += '<FONT COLOR="' + headerColor + '" SIZE=' + headerSize + '>' // set font for table header
    text += year +'年'+monthName 
    text += '</FONT>'
    text += '</TH>' // close header cell
    // variables to hold constant settings
    var openCol = '<TD WIDTH=' + colWidth + ' HEIGHT=' + dayCellHeight + '>'
    openCol += '<FONT COLOR="' + dayColor + '">'
    var closeCol = '</FONT></TD>'
    // create array of abbreviated day names
    var weekDay = new Array(7)
    weekDay[0] = "日"
    weekDay[1] = "一"
    weekDay[2] = "二"
    weekDay[3] = "三"
    weekDay[4] = "四"
    weekDay[5] = "五"
    weekDay[6] = "六"
    
    // create first row of table to set column width and specify week day
    text += '<TR ALIGN="center" VALIGN="center">'
    for (var dayNum = 0; dayNum < 7; ++dayNum) {
    text += openCol + weekDay[dayNum] + closeCol 
    }
    text += '</TR>'
    
    var digit = 1
    var curCell = 1
    
    for (var row = 1; row <= Math.ceil((lastDate + firstDay - 1) / 7); ++row) {
    text += '<TR ALIGN="right" VALIGN="top">'
    for (var col = 1; col <= 7; ++col) {
    if (digit > lastDate)
    break
    if (curCell < firstDay) {
    text += '<TD></TD>';
    curCell++
    } else {
    if (digit == date) {
    text += '<TD HEIGHT=' + cellHeight + '>'
    text += '<FONT COLOR="' + todayColor + '">'
    text += digit
    text += '</FONT><BR>'
    
    text += '</TD>'
    } else
    text += '<TD HEIGHT=' + cellHeight + '>' + digit + '</TD>'
    digit++
    }
    }
    text += '</TR>'
    }
    text += '</TABLE>'
    text += '</CENTER>' 
    document.getElementById(div).innerHTML=text;
    }

