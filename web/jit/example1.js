var labelType, useGradients, nativeTextSupport, animate;

(function() {
  var ua = navigator.userAgent,
      iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
      typeOfCanvas = typeof HTMLCanvasElement,
      nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
      textSupport = nativeCanvasSupport 
        && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
  //I'm setting this based on the fact that ExCanvas provides text support for IE
  //and that as of today iPhone/iPad current text support is lame
  labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
  nativeTextSupport = labelType == 'Native';
  useGradients = nativeCanvasSupport;
  animate = !(iStuff || !nativeCanvasSupport);
})();

var Log = {
  elem: false,
  write: function(text){
    if (!this.elem) 
     	 this.elem = document.getElementById('log');
    	this.elem.innerHTML = text;
    	this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
  }
};


function init(json){
    var st = new $jit.ST({
    	orientation:'left',
        injectInto: 'infovis',
        duration: 800,
        transition: $jit.Trans.Quart.easeInOut,
        levelDistance: 80,
        levelsToShow:2,
        Navigation: {
          enable:true,
          panning:true
        },
        Node: {
            height: 20,
            width: 300,
            type: 'rectangle',
            color: '#aaa',
            overridable: true
        },
        Edge: {
            type: 'bezier',
            overridable: true,
            color:'black'
        },
        Tips:{
        	enable: true,
    		offsetX: 20,
    		offsetY: 3,
    		onShow: function(tip, node) {
      			tip.innerHTML = node.name;
    		}
        },
        onBeforeCompute: function(node){
            //Log.write("加载中...");
        },
        onAfterCompute: function(){
            //Log.write("加载完成");
        },
        onCreateLabel: function(label, node){
        	node.Config.height = 20;
            label.id = node.id;
            var nodename=node.name;
            if(nodename.length>50){
          	 	nodename=nodename.substring(0,50)+"......";
           }
            label.innerHTML = nodename;
            if(node.id!=null&&((node.id).length<10)){
	            label.onclick = function(){
	            	  st.onClick(node.id);
	            };
            };
            var style = label.style;
            style.width = 300;
            style.height = 20;            
            style.cursor = 'pointer';
            style.color = 'black';
            style.fontSize = '1.1em';
            style.textAlign= 'center';
            style.paddingTop = '5px';
        },
        onBeforePlotNode: function(node){
            if (node.selected) {
                node.data.$color = "lightblue";
            }
            else {
                delete node.data.$color;
                if(!node.anySubnode("exist")) {
                    var count = 0;
                    node.eachSubnode(function(n) { count++; });
                }
            }
        },
        onBeforePlotLine: function(adj){
            if (adj.nodeFrom.selected && adj.nodeTo.selected) {
                adj.data.$color = "lightblue";
                adj.data.$lineWidth = 5;
            }
            else {
                delete adj.data.$color;
                delete adj.data.$lineWidth;
            }
        }
    });
    st.loadJSON(json);
    st.compute();
    st.geom.translate(new $jit.Complex(-200, 0), "current");
    st.onClick(st.root);
}
