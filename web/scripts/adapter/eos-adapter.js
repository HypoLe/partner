/**
 * eos需求下的特殊处理
 */ 
Chooser.prototype.update = function(){
	this.chosenView.jsonData = [];
	this.chosen.each(function(nodeInfo) {
		//如果未指定组长，则设定leaderId为选中对象的id
		if(!nodeInfo.leaderId || nodeInfo.leaderId==""){
			nodeInfo.leaderId = nodeInfo.id;
		}
		//如果有leaderName，则info为组长信息
		nodeInfo.info = nodeInfo.leaderName ? '(组长:'+nodeInfo.leaderName+')' : '';

		this.chosenView.jsonData.push(nodeInfo);
	},this);

	this.chosenView.refresh();
	
	function toStr(o,isShowLeader){
		var str = '{'
			+'id:\''+o.id+'\''
			+',nodeType:\''+o.nodeType+'\''
		if(o.nodeType=='subrole' && isShowLeader && o.leaderId){
			str += ',leaderId:\''+o.leaderId+'\'';
		}
		str += '}';
		return str;
	}
	
	Ext.each(this.category, function(c) {
		var arrId = [],arrNodeType = [], arrLeaderId = [], arrJSON = [];	
		var t = new Ext.MasterTemplate(
			'<list>',
				'<tpl name="participant">',
					'<participant><id>{id}</id><name>{text}</name><type>{nodeType}</type></participant>',
				'</tpl>',
			'</list>'
		);
		this.chosen.each(function(nodeInfo) {
			if (nodeInfo.categoryId == c.id) {
				arrId.push(nodeInfo.id);
				t.add('participant',nodeInfo);
				arrNodeType.push(nodeInfo.nodeType);
				arrLeaderId.push(nodeInfo.leaderId);
				if(this.returnJSON){
					arrJSON.push(toStr(nodeInfo,this.showLeader));				
				}
			}
		}, this);
		
		c['hiddenEl'].dom.value = t.apply();
		c['hiddenEl_nodeType'].dom.value = arrNodeType.toString();
		c['hiddenEl_leaderId'].dom.value = arrLeaderId.toString();
		if(this.returnJSON){
			c['hiddenEl_JSON'].dom.value = "[" + arrJSON.toString() + "]";
		}
				
	}, this);		
}