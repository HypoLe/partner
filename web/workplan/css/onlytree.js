

var nodes=new Array();
var openNodes=new Array();
var icons=new Array(6);
var dept = null;
// Loads all icons that are used in the tree
function preloadIcons() {
	icons[0] = new Image();
	icons[0].src = ""+path+"/images/img/plus.gif";
	icons[1] = new Image();
	icons[1].src = ""+path+"/images/img/plusbottom.gif";
	icons[2] = new Image();
	icons[2].src = ""+path+"/images/img/minus.gif";
	icons[3] = new Image();
	icons[3].src = ""+path+"/images/img/minusbottom.gif";
	icons[4] = new Image();
	icons[4].src = ""+path+"/images/img/folder.gif";
	icons[5] = new Image();
	icons[5].src = ""+path+"/images/img/folderopen.gif";
}

/*
����˵����
û��checkBox��Ĳ���������ʾ��ÿ���ڵ㣬���Լ���һ��ê����ֵ,
���沢�ҿ�����������������dept=��ѡ�Ľڵ�ֵ��wsclass=�������͡�
���ң�����ȷ�����ĸ��ڵ�չ�������Լ�������ʾѡ�еĽڵ㣻
��function������com.boco.eoms.common.tree.WKTree.java���е�strWKTree()��������ʹ��
���磺
WKTree wk_tree = new WKTree();
String strTree = wk_tree.strWKTree(1);
����1����ʾ�г��Ըò���Ϊ���ڵ���������в���

ʹ�þ�����
EOMS_J2EE/sysadmin/manager/TawDept/depttree.jsp;

����˵����
arrName��Tree����ֵ���������ͽṹ�ĸ����ڵ�ļ��ϣ�
startNode���Ӹýڵ㿪ʼ��ʾ���ͽṹ��
openNode���Ӹýڵ㿪ʼ��չ�������е��ӽڵ㡣����Ϊһ��С��0��ֵ�����ʾ�����ͽṹȫ��չ����
path��webӦ�õ����ƣ�
url�����ͽṹ�ĸ����ڵ��ê����ֵ����Ϊ��""�����ʾ�Ǳ�����Actionֵ��
deptid�����ͽṹ�б�����ʾ�Ľڵ�ֵ��
wsclass���������ͣ����ڹ���ģ�顣����ʹ�ã�������Ϊ��-1��
*/
function createTree3(arrName, startNode, openNode,path,url,deptid,wsclass)
{
    nodes = arrName;
    dept = deptid;
        if (path !== null) {
            rootPath = path;
        }
    if (nodes.length > 0) {
		preloadIcons();
		if (startNode == null) startNode = 0;

                if (openNode > 0 || openNode !=null) setOpenNodes(openNode);      //�ӣ�openNode���˽ڵ�չ��

                if (openNode < 0) {   //չ�����нڵ�
                  openAllNodes();
                  //alert(openNode);
                }
		if (startNode !=0) {
			var nodeValues = nodes[getArrayId(startNode)].split("|");
                        if (deptid == nodeValues[0]) {
                            document.write("<a href=\"" + url + "?dept="+nodeValues[0]+"&wsclass=" +wsclass+ "\" onmouseover=\"window.status='" + nodeValues[2] + "';return true;\" onmouseout=\"window.status=' ';return true;\"><img src=\""+path+"/images/img/folderopen.gif\" align=\"absbottom\" alt=\"\" /><font color=\"#3300FF\">" + nodeValues[2] + "</font></a><br />");
                        } else {
                            document.write("<a href=\"" + url + "?dept="+nodeValues[0]+"&wsclass=" +wsclass+ "\" onmouseover=\"window.status='" + nodeValues[2] + "';return true;\" onmouseout=\"window.status=' ';return true;\"><img src=\""+path+"/images/img/folderopen.gif\" align=\"absbottom\" alt=\"\" />" + nodeValues[2] + "</a><br />");
                        }
		} else {
                     document.write("<img src=\""+path+"/images/img/base.gif\" align=\"absbottom\" alt=\"\" />Eoms<br />");
                }

		var recursedNodes = new Array();
		addNode3(startNode, recursedNodes);
	}
}
// Returns the position of a node in the array
function getArrayId(node) {
	for (i=0; i<nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[0]==node) return i;
	}
}
// Puts in array nodes that will be open
function setOpenNodes(openNode) {
	for (i=0; i<nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[0]==openNode) {
			openNodes.push(nodeValues[0]);
			setOpenNodes(nodeValues[1]);
		}
	}
}

// open all nodes
function openAllNodes()
{
  for (i=0;i<nodes.length;i++) {
    var nodeValues = nodes[i].split("|");
      //alert(nodeValues[0]);
      //document.write(nodeValues[0]);
    openNodes.push(nodeValues[0]);
      //openAllNodes();
  }

}

// Checks if a node is open
function isNodeOpen(node) {
	for (i=0; i<openNodes.length; i++)
		if (openNodes[i]==node) return true;
	return false;
}
// Checks if a node has any children
function hasChildNode(parentNode) {
	for (i=0; i< nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[1] == parentNode) return true;
	}
	return false;
}
// Checks if a node is the last sibling
function lastSibling (node, parentNode) {
	var lastChild = 0;
	for (i=0; i< nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[1] == parentNode)
			lastChild = nodeValues[0];
	}
	if (lastChild==node) return true;
	return false;
}
// Adds a new node in the tree3
function addNode3(parentNode, recursedNodes) {
	for (var i = 0; i < nodes.length; i++) {

		var nodeValues = nodes[i].split("|");

		if (nodeValues[1] == parentNode) {

			var ls	= lastSibling(nodeValues[0], nodeValues[1]);
			var hcn	= hasChildNode(nodeValues[0]);
			var ino = isNodeOpen(nodeValues[0]);

			// Write out line & empty icons
			for (g=0; g<recursedNodes.length; g++) {
				if (recursedNodes[g] == 1) document.write("<img src=\""+path+"/images/img/line.gif\" align=\"absbottom\" alt=\"\" />");
				else  document.write("<img src=\""+path+"/images/img/empty.gif\" align=\"absbottom\" alt=\"\" />");
			}

			// put in array line & empty icons
			if (ls) recursedNodes.push(0);
			else recursedNodes.push(1);

			// Write out join icons
			if (hcn) {
				if (ls) {
					document.write("<a href=\"javascript: oc(" + nodeValues[0] + ", 1);\"><img id=\"join" + nodeValues[0] + "\" src=\""+path+"/images/img/");
					 	if (ino) document.write("minus");
						else document.write("plus");
					document.write("bottom.gif\" align=\"absbottom\" alt=\"Open/Close node\" /></a>");
				} else {
					document.write("<a href=\"javascript: oc(" + nodeValues[0] + ", 0);\"><img id=\"join" + nodeValues[0] + "\" src=\""+path+"/images/img/");
						if (ino) document.write("minus");
						else document.write("plus");
					document.write(".gif\" align=\"absbottom\" alt=\"Open/Close node\" /></a>");
				}
			} else {
				if (ls) document.write("<img src=\""+path+"/images/img/join.gif\" align=\"absbottom\" alt=\"\" />");
				else document.write("<img src=\""+path+"/images/img/joinbottom.gif\" align=\"absbottom\" alt=\"\" />");
			}

			// Start link
                            document.write("<a href=\"" + url + "?dept="+ nodeValues[0]+"&wsclass="+wsclass+ "\" onmouseover=\"window.status='" + nodeValues[2] + "';return true;\" onmouseout=\"window.status=' ';return true;\">");
			// Write out folder & page icons
			if (hcn) {
				document.write("<img id=\"icon" + nodeValues[0] + "\" src=\""+path+"/images/img/folder")
					if (ino) document.write("open");
				document.write(".gif\" align=\"absbottom\" alt=\"Folder\" />");
			} else { document.write("<img id=\"icon" + nodeValues[0] + "\" src=\""+path+"/images/img/page.gif\" align=\"absbottom\" alt=\"Page\" />");}

			// Write out node name
                        if (dept == nodeValues[0]) {
			    document.write("<font color=\"#3300FF\">"+nodeValues[2]+"</font>");
                        }
                        else {
			    document.write(nodeValues[2]);
                        }
			// End link
			document.write("</a><br />");

			// If node has children write out divs and go deeper
			if (hcn) {
				document.write("<div nowrap id=\"div" + nodeValues[0] + "\"");
					if (!ino) document.write(" style=\"display: none;\"");
				document.write(">");
				addNode3(nodeValues[0], recursedNodes);
				document.write("</div>");
			}

			// remove last line or empty icon
			recursedNodes.pop();
		}
	}
}
// Opens or closes a node
function oc(node, bottom) {
	var theDiv = document.getElementById("div" + node);
	var theJoin	= document.getElementById("join" + node);
	var theIcon = document.getElementById("icon" + node);

	if (theDiv.style.display == 'none') {
		if (bottom==1) theJoin.src = icons[3].src;
		else theJoin.src = icons[2].src;
		theIcon.src = icons[5].src;
		theDiv.style.display = '';
	} else {
		if (bottom==1) theJoin.src = icons[1].src;
		else theJoin.src = icons[0].src;
		theIcon.src = icons[4].src;
		theDiv.style.display = 'none';
	}
}
// Push and pop not implemented in IE(crap!    don\uFFFDt know about NS though)
if(!Array.prototype.push) {
	function array_push() {
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
	Array.prototype.push = array_push;
}
if(!Array.prototype.pop) {
	function array_pop(){
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
	Array.prototype.pop = array_pop;
}