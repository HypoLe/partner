/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/

/*
	This is an optimized version of Dojo, built for deployment and not for
	development. To get sources and documentation, please visit:

		http://dojotoolkit.org
*/

if(!dojo._hasResource["dojox.gfx.shape"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.shape"] = true;
dojo.provide("dojox.gfx.shape");



dojo.declare("dojox.gfx.shape.Shape", null, {
	// summary: a Shape object, which knows how to apply
	// graphical attributes and transformations

	constructor: function(){
		// rawNode: Node: underlying node
		this.rawNode = null;

		// shape: Object: an abstract shape object
		//	(see dojox.gfx.defaultPath,
		//	dojox.gfx.defaultPolyline,
		//	dojox.gfx.defaultRect,
		//	dojox.gfx.defaultEllipse,
		//	dojox.gfx.defaultCircle,
		//	dojox.gfx.defaultLine,
		//	or dojox.gfx.defaultImage)
		this.shape = null;

		// matrix: dojox.gfx.Matrix2D: a transformation matrix
		this.matrix = null;

		// fillStyle: Object: a fill object
		//	(see dojox.gfx.defaultLinearGradient,
		//	dojox.gfx.defaultRadialGradient,
		//	dojox.gfx.defaultPattern,
		//	or dojo.Color)
		this.fillStyle = null;

		// strokeStyle: Object: a stroke object
		//	(see dojox.gfx.defaultStroke)
		this.strokeStyle = null;

		// bbox: dojox.gfx.Rectangle: a bounding box of this shape
		//	(see dojox.gfx.defaultRect)
		this.bbox = null;

		// virtual group structure

		// parent: Object: a parent or null
		//	(see dojox.gfx.Surface,
		//	dojox.gfx.shape.VirtualGroup,
		//	or dojox.gfx.Group)
		this.parent = null;

		// parentMatrix: dojox.gfx.Matrix2D
		//	a transformation matrix inherited from the parent
		this.parentMatrix = null;
	},

	// trivial getters

	getNode: function(){
		// summary: returns the current DOM Node or null
		return this.rawNode; // Node
	},
	getShape: function(){
		// summary: returns the current shape object or null
		//	(see dojox.gfx.defaultPath,
		//	dojox.gfx.defaultPolyline,
		//	dojox.gfx.defaultRect,
		//	dojox.gfx.defaultEllipse,
		//	dojox.gfx.defaultCircle,
		//	dojox.gfx.defaultLine,
		//	or dojox.gfx.defaultImage)
		return this.shape; // Object
	},
	getTransform: function(){
		// summary: returns the current transformation matrix or null
		return this.matrix;	// dojox.gfx.Matrix2D
	},
	getFill: function(){
		// summary: returns the current fill object or null
		//	(see dojox.gfx.defaultLinearGradient,
		//	dojox.gfx.defaultRadialGradient,
		//	dojox.gfx.defaultPattern,
		//	or dojo.Color)
		return this.fillStyle;	// Object
	},
	getStroke: function(){
		// summary: returns the current stroke object or null
		//	(see dojox.gfx.defaultStroke)
		return this.strokeStyle;	// Object
	},
	getParent: function(){
		// summary: returns the parent or null
		//	(see dojox.gfx.Surface,
		//	dojox.gfx.shape.VirtualGroup,
		//	or dojox.gfx.Group)
		return this.parent;	// Object
	},
	getBoundingBox: function(){
		// summary: returns the bounding box or null
		//	(see dojox.gfx.defaultRect)
		return this.bbox;	// dojox.gfx.Rectangle
	},
	getTransformedBoundingBox: function(){
		// summary: returns an array of four points or null
		//	four points represent four corners of the untransformed bounding box
		var b = this.getBoundingBox();
		if(!b){
			return null;	// null
		}
		var m = this._getRealMatrix();
			gm = dojox.gfx.matrix;
		return [	// Array
				gm.multiplyPoint(m, b.x, b.y),
				gm.multiplyPoint(m, b.x + b.width, b.y),
				gm.multiplyPoint(m, b.x + b.width, b.y + b.height),
				gm.multiplyPoint(m, b.x, b.y + b.height)
			];
	},
	getEventSource: function(){
		// summary: returns a Node, which is used as
		//	a source of events for this shape

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		return this.rawNode;	// Node
	},

	// empty settings

	setShape: function(shape){
		// summary: sets a shape object
		//	(the default implementation simply ignores it)
		// shape: Object: a shape object
		//	(see dojox.gfx.defaultPath,
		//	dojox.gfx.defaultPolyline,
		//	dojox.gfx.defaultRect,
		//	dojox.gfx.defaultEllipse,
		//	dojox.gfx.defaultCircle,
		//	dojox.gfx.defaultLine,
		//	or dojox.gfx.defaultImage)

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		this.shape = dojox.gfx.makeParameters(this.shape, shape);
		this.bbox = null;
		return this;	// self
	},
	setFill: function(fill){
		// summary: sets a fill object
		//	(the default implementation simply ignores it)
		// fill: Object: a fill object
		//	(see dojox.gfx.defaultLinearGradient,
		//	dojox.gfx.defaultRadialGradient,
		//	dojox.gfx.defaultPattern,
		//	or dojo.Color)

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		if(!fill){
			// don't fill
			this.fillStyle = null;
			return this;	// self
		}
		var f = null;
		if(typeof(fill) == "object" && "type" in fill){
			// gradient or pattern
			switch(fill.type){
				case "linear":
					f = dojox.gfx.makeParameters(dojox.gfx.defaultLinearGradient, fill);
					break;
				case "radial":
					f = dojox.gfx.makeParameters(dojox.gfx.defaultRadialGradient, fill);
					break;
				case "pattern":
					f = dojox.gfx.makeParameters(dojox.gfx.defaultPattern, fill);
					break;
			}
		}else{
			// color object
			f = dojox.gfx.normalizeColor(fill);
		}
		this.fillStyle = f;
		return this;	// self
	},
	setStroke: function(stroke){
		// summary: sets a stroke object
		//	(the default implementation simply ignores it)
		// stroke: Object: a stroke object
		//	(see dojox.gfx.defaultStroke)

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		if(!stroke){
			// don't stroke
			this.strokeStyle = null;
			return this;	// self
		}
		// normalize the stroke
		if(typeof stroke == "string" || dojo.isArray(stroke) || stroke instanceof dojo.Color){
			stroke = {color: stroke};
		}
		var s = this.strokeStyle = dojox.gfx.makeParameters(dojox.gfx.defaultStroke, stroke);
		s.color = dojox.gfx.normalizeColor(s.color);
		return this;	// self
	},
	setTransform: function(matrix){
		// summary: sets a transformation matrix
		// matrix: dojox.gfx.Matrix2D: a matrix or a matrix-like object
		//	(see an argument of dojox.gfx.Matrix2D
		//	constructor for a list of acceptable arguments)

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		this.matrix = dojox.gfx.matrix.clone(matrix ? dojox.gfx.matrix.normalize(matrix) : dojox.gfx.matrix.identity);
		return this._applyTransform();	// self
	},

	_applyTransform: function(){
		// summary: physically sets a matrix

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		return this;	// self
	},

	// z-index

	moveToFront: function(){
		// summary: moves a shape to front of its parent's list of shapes
		var p = this.getParent();
		if(p){
			p._moveChildToFront(this);
			this._moveToFront();	// execute renderer-specific action
		}
		return this;	// self
	},
	moveToBack: function(){
		// summary: moves a shape to back of its parent's list of shapes
		var p = this.getParent();
		if(p){
			p._moveChildToBack(this);
			this._moveToBack();	// execute renderer-specific action
		}
		return this;
	},
	_moveToFront: function(){
		// summary: renderer-specific hook, see dojox.gfx.shape.Shape.moveToFront()

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!
	},
	_moveToBack: function(){
		// summary: renderer-specific hook, see dojox.gfx.shape.Shape.moveToFront()

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!
	},

	// apply left & right transformation

	applyRightTransform: function(matrix){
		// summary: multiplies the existing matrix with an argument on right side
		//	(this.matrix * matrix)
		// matrix: dojox.gfx.Matrix2D: a matrix or a matrix-like object
		//	(see an argument of dojox.gfx.Matrix2D
		//	constructor for a list of acceptable arguments)
		return matrix ? this.setTransform([this.matrix, matrix]) : this;	// self
	},
	applyLeftTransform: function(matrix){
		// summary: multiplies the existing matrix with an argument on left side
		//	(matrix * this.matrix)
		// matrix: dojox.gfx.Matrix2D: a matrix or a matrix-like object
		//	(see an argument of dojox.gfx.Matrix2D
		//	constructor for a list of acceptable arguments)
		return matrix ? this.setTransform([matrix, this.matrix]) : this;	// self
	},
	applyTransform: function(matrix){
		// summary: a shortcut for dojox.gfx.Shape.applyRightTransform
		// matrix: dojox.gfx.Matrix2D: a matrix or a matrix-like object
		//	(see an argument of dojox.gfx.Matrix2D
		//	constructor for a list of acceptable arguments)
		return matrix ? this.setTransform([this.matrix, matrix]) : this;	// self
	},

	// virtual group methods

	removeShape: function(silently){
		// summary: removes the shape from its parent's list of shapes
		// silently: Boolean?: if true, do not redraw a picture yet
		if(this.parent){
			this.parent.remove(this, silently);
		}
		return this;	// self
	},
	_setParent: function(parent, matrix){
		// summary: sets a parent
		// parent: Object: a parent or null
		//	(see dojox.gfx.Surface,
		//	dojox.gfx.shape.VirtualGroup,
		//	or dojox.gfx.Group)
		// matrix: dojox.gfx.Matrix2D:
		//	a 2D matrix or a matrix-like object
		this.parent = parent;
		return this._updateParentMatrix(matrix);	// self
	},
	_updateParentMatrix: function(matrix){
		// summary: updates the parent matrix with new matrix
		// matrix: dojox.gfx.Matrix2D:
		//	a 2D matrix or a matrix-like object
		this.parentMatrix = matrix ? dojox.gfx.matrix.clone(matrix) : null;
		return this._applyTransform();	// self
	},
	_getRealMatrix: function(){
		// summary: returns the cumulative ("real") transformation matrix
		//	by combining the shape's matrix with its parent's matrix
		var m = this.matrix;
		var p = this.parent;
		while(p){
			if(p.matrix){
				m = dojox.gfx.matrix.multiply(p.matrix, m);
			}
			p = p.parent;
		}
		return m;	// dojox.gfx.Matrix2D
	}
});

dojox.gfx.shape._eventsProcessing = {
	connect: function(name, object, method){
		// summary: connects a handler to an event on this shape

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		return arguments.length > 2 ?	// Object
			dojo.connect(this.getEventSource(), name, object, method) :
			dojo.connect(this.getEventSource(), name, object);
	},
	disconnect: function(token){
		// summary: connects a handler by token from an event on this shape

		// COULD BE RE-IMPLEMENTED BY THE RENDERER!

		dojo.disconnect(token);
	}
};

dojo.extend(dojox.gfx.shape.Shape, dojox.gfx.shape._eventsProcessing);

dojox.gfx.shape.Container = {
	// summary: a container of shapes, which can be used
	//	as a foundation for renderer-specific groups, or as a way
	//	to logically group shapes (e.g, to propagate matricies)

	_init: function() {
		// children: Array: a list of children
		this.children = [];
	},

	// group management

	openBatch: function() {
		// summary: starts a new batch, subsequent new child shapes will be held in
		//	the batch instead of appending to the container directly
	},
	closeBatch: function() {
		// summary: submits the current batch, append all pending child shapes to DOM
	},
	add: function(shape){
		// summary: adds a shape to the list
		// shape: dojox.gfx.Shape: a shape
		var oldParent = shape.getParent();
		if(oldParent){
			oldParent.remove(shape, true);
		}
		this.children.push(shape);
		return shape._setParent(this, this._getRealMatrix());	// self
	},
	remove: function(shape, silently){
		// summary: removes a shape from the list
		// silently: Boolean?: if true, do not redraw a picture yet
		for(var i = 0; i < this.children.length; ++i){
			if(this.children[i] == shape){
				if(silently){
					// skip for now
				}else{
					shape.parent = null;
					shape.parentMatrix = null;
				}
				this.children.splice(i, 1);
				break;
			}
		}
		return this;	// self
	},
	clear: function(){
		// summary: removes all shapes from a group/surface
		this.children = [];
		return this;	// self
	},

	// moving child nodes

	_moveChildToFront: function(shape){
		// summary: moves a shape to front of the list of shapes
		for(var i = 0; i < this.children.length; ++i){
			if(this.children[i] == shape){
				this.children.splice(i, 1);
				this.children.push(shape);
				break;
			}
		}
		return this;	// self
	},
	_moveChildToBack: function(shape){
		// summary: moves a shape to back of the list of shapes
		for(var i = 0; i < this.children.length; ++i){
			if(this.children[i] == shape){
				this.children.splice(i, 1);
				this.children.unshift(shape);
				break;
			}
		}
		return this;	// self
	}
};

dojo.declare("dojox.gfx.shape.Surface", null, {
	// summary: a surface object to be used for drawings
	constructor: function(){
		// underlying node
		this.rawNode = null;
		// the parent node
		this._parent = null;
		// the list of DOM nodes to be deleted in the case of destruction
		this._nodes = [];
		// the list of events to be detached in the case of destruction
		this._events = [];
	},
	destroy: function(){
		// summary: destroy all relevant external resources and release all
		//	external references to make this object garbage-collectible
		dojo.forEach(this._nodes, dojo.destroy);
		this._nodes = [];
		dojo.forEach(this._events, dojo.disconnect);
		this._events = [];
		this.rawNode = null;	// recycle it in _nodes, if it needs to be recycled
		if(dojo.isIE){
			while(this._parent.lastChild){
				dojo.destroy(this._parent.lastChild);
			}
		}else{
			this._parent.innerHTML = "";
		}
		this._parent = null;
	},
	getEventSource: function(){
		// summary: returns a node, which can be used to attach event listeners
		return this.rawNode; // Node
	},
	_getRealMatrix: function(){
		// summary: always returns the identity matrix
		return null;	// dojox.gfx.Matrix2D
	},
	isLoaded: true,
	onLoad: function(/*dojox.gfx.Surface*/ surface){
		// summary: local event, fired once when the surface is created
		// asynchronously, used only when isLoaded is false, required
		// only for Silverlight.
	},
	whenLoaded: function(
		/*Object?*/ context,
		/*Function|String*/ method
	){
		var f = dojo.hitch(context, method);
		if(this.isLoaded){
			f(this);
		}else{
			var h = dojo.connect(this, "onLoad", function(surface){
				dojo.disconnect(h);
				f(surface);
			});
		}
	}
});

dojo.extend(dojox.gfx.shape.Surface, dojox.gfx.shape._eventsProcessing);

dojo.declare("dojox.gfx.Point", null, {
	// summary: a hypothetical 2D point to be used for drawings - {x, y}
	// description: This object is defined for documentation purposes.
	//	You should use the naked object instead: {x: 1, y: 2}.
});

dojo.declare("dojox.gfx.Rectangle", null, {
	// summary: a hypothetical rectangle - {x, y, width, height}
	// description: This object is defined for documentation purposes.
	//	You should use the naked object instead: {x: 1, y: 2, width: 100, height: 200}.
});

dojo.declare("dojox.gfx.shape.Rect", dojox.gfx.shape.Shape, {
	// summary: a generic rectangle
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.shape = dojox.gfx.getDefault("Rect");
		this.rawNode = rawNode;
	},
	getBoundingBox: function(){
		// summary: returns the bounding box (its shape in this case)
		return this.shape;	// dojox.gfx.Rectangle
	}
});

dojo.declare("dojox.gfx.shape.Ellipse", dojox.gfx.shape.Shape, {
	// summary: a generic ellipse
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.shape = dojox.gfx.getDefault("Ellipse");
		this.rawNode = rawNode;
	},
	getBoundingBox: function(){
		// summary: returns the bounding box
		if(!this.bbox){
			var shape = this.shape;
			this.bbox = {x: shape.cx - shape.rx, y: shape.cy - shape.ry,
				width: 2 * shape.rx, height: 2 * shape.ry};
		}
		return this.bbox;	// dojox.gfx.Rectangle
	}
});

dojo.declare("dojox.gfx.shape.Circle", dojox.gfx.shape.Shape, {
	// summary: a generic circle
	//	(this is a helper object, which is defined for convenience)
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.shape = dojox.gfx.getDefault("Circle");
		this.rawNode = rawNode;
	},
	getBoundingBox: function(){
		// summary: returns the bounding box
		if(!this.bbox){
			var shape = this.shape;
			this.bbox = {x: shape.cx - shape.r, y: shape.cy - shape.r,
				width: 2 * shape.r, height: 2 * shape.r};
		}
		return this.bbox;	// dojox.gfx.Rectangle
	}
});

dojo.declare("dojox.gfx.shape.Line", dojox.gfx.shape.Shape, {
	// summary: a generic line
	//	(this is a helper object, which is defined for convenience)
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.shape = dojox.gfx.getDefault("Line");
		this.rawNode = rawNode;
	},
	getBoundingBox: function(){
		// summary: returns the bounding box
		if(!this.bbox){
			var shape = this.shape;
			this.bbox = {
				x:		Math.min(shape.x1, shape.x2),
				y:		Math.min(shape.y1, shape.y2),
				width:	Math.abs(shape.x2 - shape.x1),
				height:	Math.abs(shape.y2 - shape.y1)
			};
		}
		return this.bbox;	// dojox.gfx.Rectangle
	}
});

dojo.declare("dojox.gfx.shape.Polyline", dojox.gfx.shape.Shape, {
	// summary: a generic polyline/polygon
	//	(this is a helper object, which is defined for convenience)
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.shape = dojox.gfx.getDefault("Polyline");
		this.rawNode = rawNode;
	},
	setShape: function(points, closed){
		// summary: sets a polyline/polygon shape object
		// points: Object: a polyline/polygon shape object
		// closed: Boolean: close the polyline to make a polygon
		if(points && points instanceof Array){
			// points: Array: an array of points
			this.inherited(arguments, [{points: points}]);
			if(closed && this.shape.points.length){
				this.shape.points.push(this.shape.points[0]);
			}
		}else{
			this.inherited(arguments, [points]);
		}
		return this;	// self
	},
	_normalizePoints: function(){
		// summary: normalize points to array of {x:number, y:number}
		var p = this.shape.points, l = p && p.length;
		if(l && typeof p[0] == "number"){
			var points = [];
			for(var i = 0; i < l; i += 2){
				points.push({x: p[i], y: p[i + 1]});
			}
			this.shape.points = points;
		}
	},
	getBoundingBox: function(){
		// summary: returns the bounding box
		if(!this.bbox && this.shape.points.length){
			var p = this.shape.points;
			var l = p.length;
			var t = p[0];
			var bbox = {l: t.x, t: t.y, r: t.x, b: t.y};
			for(var i = 1; i < l; ++i){
				t = p[i];
				if(bbox.l > t.x) bbox.l = t.x;
				if(bbox.r < t.x) bbox.r = t.x;
				if(bbox.t > t.y) bbox.t = t.y;
				if(bbox.b < t.y) bbox.b = t.y;
			}
			this.bbox = {
				x:		bbox.l,
				y:		bbox.t,
				width:	bbox.r - bbox.l,
				height:	bbox.b - bbox.t
			};
		}
		return this.bbox;	// dojox.gfx.Rectangle
	}
});

dojo.declare("dojox.gfx.shape.Image", dojox.gfx.shape.Shape, {
	// summary: a generic image
	//	(this is a helper object, which is defined for convenience)
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.shape = dojox.gfx.getDefault("Image");
		this.rawNode = rawNode;
	},
	getBoundingBox: function(){
		// summary: returns the bounding box (its shape in this case)
		return this.shape;	// dojox.gfx.Rectangle
	},
	setStroke: function(){
		// summary: ignore setting a stroke style
		return this;	// self
	},
	setFill: function(){
		// summary: ignore setting a fill style
		return this;	// self
	}
});

dojo.declare("dojox.gfx.shape.Text", dojox.gfx.shape.Shape, {
	// summary: a generic text
	constructor: function(rawNode){
		// rawNode: Node: a DOM Node
		this.fontStyle = null;
		this.shape = dojox.gfx.getDefault("Text");
		this.rawNode = rawNode;
	},
	getFont: function(){
		// summary: returns the current font object or null
		return this.fontStyle;	// Object
	},
	setFont: function(newFont){
		// summary: sets a font for text
		// newFont: Object: a font object (see dojox.gfx.defaultFont) or a font string
		this.fontStyle = typeof newFont == "string" ? dojox.gfx.splitFontString(newFont) :
			dojox.gfx.makeParameters(dojox.gfx.defaultFont, newFont);
		this._setFont();
		return this;	// self
	}
});

dojox.gfx.shape.Creator = {
	// summary: shape creators
	createShape: function(shape){
		// summary: creates a shape object based on its type; it is meant to be used
		//	by group-like objects
		// shape: Object: a shape descriptor object
		var gfx = dojox.gfx;
		switch(shape.type){
			case gfx.defaultPath.type:		return this.createPath(shape);
			case gfx.defaultRect.type:		return this.createRect(shape);
			case gfx.defaultCircle.type:	return this.createCircle(shape);
			case gfx.defaultEllipse.type:	return this.createEllipse(shape);
			case gfx.defaultLine.type:		return this.createLine(shape);
			case gfx.defaultPolyline.type:	return this.createPolyline(shape);
			case gfx.defaultImage.type:		return this.createImage(shape);
			case gfx.defaultText.type:		return this.createText(shape);
			case gfx.defaultTextPath.type:	return this.createTextPath(shape);
		}
		return null;
	},
	createGroup: function(){
		// summary: creates a group shape
		return this.createObject(dojox.gfx.Group);	// dojox.gfx.Group
	},
	createRect: function(rect){
		// summary: creates a rectangle shape
		// rect: Object: a path object (see dojox.gfx.defaultRect)
		return this.createObject(dojox.gfx.Rect, rect);	// dojox.gfx.Rect
	},
	createEllipse: function(ellipse){
		// summary: creates an ellipse shape
		// ellipse: Object: an ellipse object (see dojox.gfx.defaultEllipse)
		return this.createObject(dojox.gfx.Ellipse, ellipse);	// dojox.gfx.Ellipse
	},
	createCircle: function(circle){
		// summary: creates a circle shape
		// circle: Object: a circle object (see dojox.gfx.defaultCircle)
		return this.createObject(dojox.gfx.Circle, circle);	// dojox.gfx.Circle
	},
	createLine: function(line){
		// summary: creates a line shape
		// line: Object: a line object (see dojox.gfx.defaultLine)
		return this.createObject(dojox.gfx.Line, line);	// dojox.gfx.Line
	},
	createPolyline: function(points){
		// summary: creates a polyline/polygon shape
		// points: Object: a points object (see dojox.gfx.defaultPolyline)
		//	or an Array of points
		return this.createObject(dojox.gfx.Polyline, points);	// dojox.gfx.Polyline
	},
	createImage: function(image){
		// summary: creates a image shape
		// image: Object: an image object (see dojox.gfx.defaultImage)
		return this.createObject(dojox.gfx.Image, image);	// dojox.gfx.Image
	},
	createText: function(text){
		// summary: creates a text shape
		// text: Object: a text object (see dojox.gfx.defaultText)
		return this.createObject(dojox.gfx.Text, text);	// dojox.gfx.Text
	},
	createPath: function(path){
		// summary: creates a path shape
		// path: Object: a path object (see dojox.gfx.defaultPath)
		return this.createObject(dojox.gfx.Path, path);	// dojox.gfx.Path
	},
	createTextPath: function(text){
		// summary: creates a text shape
		// text: Object: a textpath object (see dojox.gfx.defaultTextPath)
		return this.createObject(dojox.gfx.TextPath, {}).setText(text);	// dojox.gfx.TextPath
	},
	createObject: function(shapeType, rawShape){
		// summary: creates an instance of the passed shapeType class
		// shapeType: Function: a class constructor to create an instance of
		// rawShape: Object: properties to be passed in to the classes "setShape" method

		// SHOULD BE RE-IMPLEMENTED BY THE RENDERER!

		return null;	// dojox.gfx.Shape
	}
};

}

if(!dojo._hasResource["dojox.gfx.path"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.path"] = true;
dojo.provide("dojox.gfx.path");




dojo.declare("dojox.gfx.path.Path", dojox.gfx.shape.Shape, {
	// summary: a generalized path shape

	constructor: function(rawNode){
		// summary: a path constructor
		// rawNode: Node: a DOM node to be used by this path object
		this.shape = dojo.clone(dojox.gfx.defaultPath);
		this.segments = [];
		this.tbbox = null;
		this.absolute = true;
		this.last = {};
		this.rawNode = rawNode;
		this.segmented = false;
	},

	// mode manipulations
	setAbsoluteMode: function(mode){
		// summary: sets an absolute or relative mode for path points
		// mode: Boolean: true/false or "absolute"/"relative" to specify the mode
		this._confirmSegmented();
		this.absolute = typeof mode == "string" ? (mode == "absolute") : mode;
		return this; // self
	},
	getAbsoluteMode: function(){
		// summary: returns a current value of the absolute mode
		this._confirmSegmented();
		return this.absolute; // Boolean
	},

	getBoundingBox: function(){
		// summary: returns the bounding box {x, y, width, height} or null
		this._confirmSegmented();
		return (this.bbox && ("l" in this.bbox)) ? {x: this.bbox.l, y: this.bbox.t, width: this.bbox.r - this.bbox.l, height: this.bbox.b - this.bbox.t} : null; // dojox.gfx.Rectangle
	},

	_getRealBBox: function(){
		// summary: returns an array of four points or null
		//	four points represent four corners of the untransformed bounding box
		this._confirmSegmented();
		if(this.tbbox){
			return this.tbbox;	// Array
		}
		var bbox = this.bbox, matrix = this._getRealMatrix();
		this.bbox = null;
		for(var i = 0, len = this.segments.length; i < len; ++i){
			this._updateWithSegment(this.segments[i], matrix);
		}
		var t = this.bbox;
		this.bbox = bbox;
		this.tbbox = t ? [
			{x: t.l, y: t.t},
			{x: t.r, y: t.t},
			{x: t.r, y: t.b},
			{x: t.l, y: t.b}
		] : null;
		return this.tbbox;	// Array
	},

	getLastPosition: function(){
		// summary: returns the last point in the path, or null
		this._confirmSegmented();
		return "x" in this.last ? this.last : null; // Object
	},

	_applyTransform: function(){
		this.tbbox = null;
		return this.inherited(arguments);
	},

	// segment interpretation
	_updateBBox: function(x, y, matrix){
		// summary: updates the bounding box of path with new point
		// x: Number: an x coordinate
		// y: Number: a y coordinate
		
		if(matrix){
			var t = dojox.gfx.matrix.multiplyPoint(matrix, x, y);
			x = t.x;
			y = t.y;
		}

		// we use {l, b, r, t} representation of a bbox
		if(this.bbox && ("l" in this.bbox)){
			if(this.bbox.l > x) this.bbox.l = x;
			if(this.bbox.r < x) this.bbox.r = x;
			if(this.bbox.t > y) this.bbox.t = y;
			if(this.bbox.b < y) this.bbox.b = y;
		}else{
			this.bbox = {l: x, b: y, r: x, t: y};
		}
	},
	_updateWithSegment: function(segment, matrix){
		// summary: updates the bounding box of path with new segment
		// segment: Object: a segment
		var n = segment.args, l = n.length;
		// update internal variables: bbox, absolute, last
		switch(segment.action){
			case "M":
			case "L":
			case "C":
			case "S":
			case "Q":
			case "T":
				for(var i = 0; i < l; i += 2){
					this._updateBBox(n[i], n[i + 1], matrix);
				}
				this.last.x = n[l - 2];
				this.last.y = n[l - 1];
				this.absolute = true;
				break;
			case "H":
				for(var i = 0; i < l; ++i){
					this._updateBBox(n[i], this.last.y, matrix);
				}
				this.last.x = n[l - 1];
				this.absolute = true;
				break;
			case "V":
				for(var i = 0; i < l; ++i){
					this._updateBBox(this.last.x, n[i], matrix);
				}
				this.last.y = n[l - 1];
				this.absolute = true;
				break;
			case "m":
				var start = 0;
				if(!("x" in this.last)){
					this._updateBBox(this.last.x = n[0], this.last.y = n[1], matrix);
					start = 2;
				}
				for(var i = start; i < l; i += 2){
					this._updateBBox(this.last.x += n[i], this.last.y += n[i + 1], matrix);
				}
				this.absolute = false;
				break;
			case "l":
			case "t":
				for(var i = 0; i < l; i += 2){
					this._updateBBox(this.last.x += n[i], this.last.y += n[i + 1], matrix);
				}
				this.absolute = false;
				break;
			case "h":
				for(var i = 0; i < l; ++i){
					this._updateBBox(this.last.x += n[i], this.last.y, matrix);
				}
				this.absolute = false;
				break;
			case "v":
				for(var i = 0; i < l; ++i){
					this._updateBBox(this.last.x, this.last.y += n[i], matrix);
				}
				this.absolute = false;
				break;
			case "c":
				for(var i = 0; i < l; i += 6){
					this._updateBBox(this.last.x + n[i], this.last.y + n[i + 1], matrix);
					this._updateBBox(this.last.x + n[i + 2], this.last.y + n[i + 3], matrix);
					this._updateBBox(this.last.x += n[i + 4], this.last.y += n[i + 5], matrix);
				}
				this.absolute = false;
				break;
			case "s":
			case "q":
				for(var i = 0; i < l; i += 4){
					this._updateBBox(this.last.x + n[i], this.last.y + n[i + 1], matrix);
					this._updateBBox(this.last.x += n[i + 2], this.last.y += n[i + 3], matrix);
				}
				this.absolute = false;
				break;
			case "A":
				for(var i = 0; i < l; i += 7){
					this._updateBBox(n[i + 5], n[i + 6], matrix);
				}
				this.last.x = n[l - 2];
				this.last.y = n[l - 1];
				this.absolute = true;
				break;
			case "a":
				for(var i = 0; i < l; i += 7){
					this._updateBBox(this.last.x += n[i + 5], this.last.y += n[i + 6], matrix);
				}
				this.absolute = false;
				break;
		}
		// add an SVG path segment
		var path = [segment.action];
		for(var i = 0; i < l; ++i){
			path.push(dojox.gfx.formatNumber(n[i], true));
		}
		if(typeof this.shape.path == "string"){
			this.shape.path += path.join("");
		}else{
			Array.prototype.push.apply(this.shape.path, path); //FIXME: why not simple push()?
		}
	},

	// a dictionary, which maps segment type codes to a number of their arguments
	_validSegments: {m: 2, l: 2, h: 1, v: 1, c: 6, s: 4, q: 4, t: 2, a: 7, z: 0},

	_pushSegment: function(action, args){
		// summary: adds a segment
		// action: String: valid SVG code for a segment's type
		// args: Array: a list of parameters for this segment
		this.tbbox = null;
		var group = this._validSegments[action.toLowerCase()];
		if(typeof group == "number"){
			if(group){
				if(args.length >= group){
					var segment = {action: action, args: args.slice(0, args.length - args.length % group)};
					this.segments.push(segment);
					this._updateWithSegment(segment);
				}
			}else{
				var segment = {action: action, args: []};
				this.segments.push(segment);
				this._updateWithSegment(segment);
			}
		}
	},

	_collectArgs: function(array, args){
		// summary: converts an array of arguments to plain numeric values
		// array: Array: an output argument (array of numbers)
		// args: Array: an input argument (can be values of Boolean, Number, dojox.gfx.Point, or an embedded array of them)
		for(var i = 0; i < args.length; ++i){
			var t = args[i];
			if(typeof t == "boolean"){
				array.push(t ? 1 : 0);
			}else if(typeof t == "number"){
				array.push(t);
			}else if(t instanceof Array){
				this._collectArgs(array, t);
			}else if("x" in t && "y" in t){
				array.push(t.x, t.y);
			}
		}
	},

	// segments
	moveTo: function(){
		// summary: formes a move segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "M" : "m", args);
		return this; // self
	},
	lineTo: function(){
		// summary: formes a line segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "L" : "l", args);
		return this; // self
	},
	hLineTo: function(){
		// summary: formes a horizontal line segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "H" : "h", args);
		return this; // self
	},
	vLineTo: function(){
		// summary: formes a vertical line segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "V" : "v", args);
		return this; // self
	},
	curveTo: function(){
		// summary: formes a curve segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "C" : "c", args);
		return this; // self
	},
	smoothCurveTo: function(){
		// summary: formes a smooth curve segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "S" : "s", args);
		return this; // self
	},
	qCurveTo: function(){
		// summary: formes a quadratic curve segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "Q" : "q", args);
		return this; // self
	},
	qSmoothCurveTo: function(){
		// summary: formes a quadratic smooth curve segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "T" : "t", args);
		return this; // self
	},
	arcTo: function(){
		// summary: formes an elliptic arc segment
		this._confirmSegmented();
		var args = [];
		this._collectArgs(args, arguments);
		this._pushSegment(this.absolute ? "A" : "a", args);
		return this; // self
	},
	closePath: function(){
		// summary: closes a path
		this._confirmSegmented();
		this._pushSegment("Z", []);
		return this; // self
	},

	_confirmSegmented: function() {
		if (!this.segmented) {
			var path = this.shape.path;
			// switch to non-updating version of path building
			this.shape.path = [];
			this._setPath(path);
			// switch back to the string path
			this.shape.path = this.shape.path.join("");
			// become segmented
			this.segmented = true;
		}
	},

	// setShape
	_setPath: function(path){
		// summary: forms a path using an SVG path string
		// path: String: an SVG path string
		var p = dojo.isArray(path) ? path : path.match(dojox.gfx.pathSvgRegExp);
		this.segments = [];
		this.absolute = true;
		this.bbox = {};
		this.last = {};
		if(!p) return;
		// create segments
		var action = "",	// current action
			args = [],		// current arguments
			l = p.length;
		for(var i = 0; i < l; ++i){
			var t = p[i], x = parseFloat(t);
			if(isNaN(x)){
				if(action){
					this._pushSegment(action, args);
				}
				args = [];
				action = t;
			}else{
				args.push(x);
			}
		}
		this._pushSegment(action, args);
	},
	setShape: function(newShape){
		// summary: forms a path using a shape
		// newShape: Object: an SVG path string or a path object (see dojox.gfx.defaultPath)
		this.inherited(arguments, [typeof newShape == "string" ? {path: newShape} : newShape]);
		
		this.segmented = false;
		this.segments = [];
		if(!dojox.gfx.lazyPathSegmentation){
			this._confirmSegmented();
		}
		return this; // self
	},

	// useful constant for descendants
	_2PI: Math.PI * 2
});

dojo.declare("dojox.gfx.path.TextPath", dojox.gfx.path.Path, {
	// summary: a generalized TextPath shape

	constructor: function(rawNode){
		// summary: a TextPath shape constructor
		// rawNode: Node: a DOM node to be used by this TextPath object
		if(!("text" in this)){
			this.text = dojo.clone(dojox.gfx.defaultTextPath);
		}
		if(!("fontStyle" in this)){
			this.fontStyle = dojo.clone(dojox.gfx.defaultFont);
		}
	},
	getText: function(){
		// summary: returns the current text object or null
		return this.text;	// Object
	},
	setText: function(newText){
		// summary: sets a text to be drawn along the path
		this.text = dojox.gfx.makeParameters(this.text,
			typeof newText == "string" ? {text: newText} : newText);
		this._setText();
		return this;	// self
	},
	getFont: function(){
		// summary: returns the current font object or null
		return this.fontStyle;	// Object
	},
	setFont: function(newFont){
		// summary: sets a font for text
		this.fontStyle = typeof newFont == "string" ?
			dojox.gfx.splitFontString(newFont) :
			dojox.gfx.makeParameters(dojox.gfx.defaultFont, newFont);
		this._setFont();
		return this;	// self
	}
});

}

if(!dojo._hasResource["dojox.gfx.arc"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.arc"] = true;
dojo.provide("dojox.gfx.arc");



(function(){
	var m = dojox.gfx.matrix,
		twoPI = 2 * Math.PI, pi4 = Math.PI / 4, pi8 = Math.PI / 8,
		pi48 = pi4 + pi8, curvePI4 = unitArcAsBezier(pi8);

	function unitArcAsBezier(alpha){
		// summary: return a start point, 1st and 2nd control points, and an end point of
		//		a an arc, which is reflected on the x axis
		// alpha: Number: angle in radians, the arc will be 2 * angle size
		var cosa  = Math.cos(alpha), sina  = Math.sin(alpha),
			p2 = {x: cosa + (4 / 3) * (1 - cosa), y: sina - (4 / 3) * cosa * (1 - cosa) / sina};
		return {	// Object
			s:  {x: cosa, y: -sina},
			c1: {x: p2.x, y: -p2.y},
			c2: p2,
			e:  {x: cosa, y: sina}
		};
	}

	dojox.gfx.arc = {
		unitArcAsBezier: unitArcAsBezier,
		curvePI4: curvePI4,
		arcAsBezier: function(last, rx, ry, xRotg, large, sweep, x, y){
			// summary: calculates an arc as a series of Bezier curves
			//	given the last point and a standard set of SVG arc parameters,
			//	it returns an array of arrays of parameters to form a series of
			//	absolute Bezier curves.
			// last: Object: a point-like object as a start of the arc
			// rx: Number: a horizontal radius for the virtual ellipse
			// ry: Number: a vertical radius for the virtual ellipse
			// xRotg: Number: a rotation of an x axis of the virtual ellipse in degrees
			// large: Boolean: which part of the ellipse will be used (the larger arc if true)
			// sweep: Boolean: direction of the arc (CW if true)
			// x: Number: the x coordinate of the end point of the arc
			// y: Number: the y coordinate of the end point of the arc

			// calculate parameters
			large = Boolean(large);
			sweep = Boolean(sweep);
			var xRot = m._degToRad(xRotg),
				rx2 = rx * rx, ry2 = ry * ry,
				pa = m.multiplyPoint(
					m.rotate(-xRot),
					{x: (last.x - x) / 2, y: (last.y - y) / 2}
				),
				pax2 = pa.x * pa.x, pay2 = pa.y * pa.y,
				c1 = Math.sqrt((rx2 * ry2 - rx2 * pay2 - ry2 * pax2) / (rx2 * pay2 + ry2 * pax2));
			if(isNaN(c1)){ c1 = 0; }
			var	ca = {
					x:  c1 * rx * pa.y / ry,
					y: -c1 * ry * pa.x / rx
				};
			if(large == sweep){
				ca = {x: -ca.x, y: -ca.y};
			}
			// the center
			var c = m.multiplyPoint(
				[
					m.translate(
						(last.x + x) / 2,
						(last.y + y) / 2
					),
					m.rotate(xRot)
				],
				ca
			);
			// calculate the elliptic transformation
			var elliptic_transform = m.normalize([
				m.translate(c.x, c.y),
				m.rotate(xRot),
				m.scale(rx, ry)
			]);
			// start, end, and size of our arc
			var inversed = m.invert(elliptic_transform),
				sp = m.multiplyPoint(inversed, last),
				ep = m.multiplyPoint(inversed, x, y),
				startAngle = Math.atan2(sp.y, sp.x),
				endAngle   = Math.atan2(ep.y, ep.x),
				theta = startAngle - endAngle;	// size of our arc in radians
			if(sweep){ theta = -theta; }
			if(theta < 0){
				theta += twoPI;
			}else if(theta > twoPI){
				theta -= twoPI;
			}

			// draw curve chunks
			var alpha = pi8, curve = curvePI4, step  = sweep ? alpha : -alpha,
				result = [];
			for(var angle = theta; angle > 0; angle -= pi4){
				if(angle < pi48){
					alpha = angle / 2;
					curve = unitArcAsBezier(alpha);
					step  = sweep ? alpha : -alpha;
					angle = 0;	// stop the loop
				}
				var c1, c2, e,
					M = m.normalize([elliptic_transform, m.rotate(startAngle + step)]);
				if(sweep){
					c1 = m.multiplyPoint(M, curve.c1);
					c2 = m.multiplyPoint(M, curve.c2);
					e  = m.multiplyPoint(M, curve.e );
				}else{
					c1 = m.multiplyPoint(M, curve.c2);
					c2 = m.multiplyPoint(M, curve.c1);
					e  = m.multiplyPoint(M, curve.s );
				}
				// draw the curve
				result.push([c1.x, c1.y, c2.x, c2.y, e.x, e.y]);
				startAngle += 2 * step;
			}
			return result;	// Array
		}
	};
})();

}

if(!dojo._hasResource["dojox.gfx.decompose"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.decompose"] = true;
dojo.provide("dojox.gfx.decompose");



(function(){
	var m = dojox.gfx.matrix;

	function eq(/* Number */ a, /* Number */ b){
		// summary: compare two FP numbers for equality
		return Math.abs(a - b) <= 1e-6 * (Math.abs(a) + Math.abs(b));	// Boolean
	}

	function calcFromValues(/* Number */ r1, /* Number */ m1, /* Number */ r2, /* Number */ m2){
		// summary: uses two close FP ration and their original magnitudes to approximate the result
		if(!isFinite(r1)){
			return r2;	// Number
		}else if(!isFinite(r2)){
			return r1;	// Number
		}
		m1 = Math.abs(m1), m2 = Math.abs(m2);
		return (m1 * r1 + m2 * r2) / (m1 + m2);	// Number
	}

	function transpose(/* dojox.gfx.matrix.Matrix2D */ matrix){
		// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix-like object
		var M = new m.Matrix2D(matrix);
		return dojo.mixin(M, {dx: 0, dy: 0, xy: M.yx, yx: M.xy});	// dojox.gfx.matrix.Matrix2D
	}

	function scaleSign(/* dojox.gfx.matrix.Matrix2D */ matrix){
		return (matrix.xx * matrix.yy < 0 || matrix.xy * matrix.yx > 0) ? -1 : 1;	// Number
	}

	function eigenvalueDecomposition(/* dojox.gfx.matrix.Matrix2D */ matrix){
		// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix-like object
		var M = m.normalize(matrix),
			b = -M.xx - M.yy,
			c = M.xx * M.yy - M.xy * M.yx,
			d = Math.sqrt(b * b - 4 * c),
			l1 = -(b + (b < 0 ? -d : d)) / 2,
			l2 = c / l1,
			vx1 = M.xy / (l1 - M.xx), vy1 = 1,
			vx2 = M.xy / (l2 - M.xx), vy2 = 1;
		if(eq(l1, l2)){
			vx1 = 1, vy1 = 0, vx2 = 0, vy2 = 1;
		}
		if(!isFinite(vx1)){
			vx1 = 1, vy1 = (l1 - M.xx) / M.xy;
			if(!isFinite(vy1)){
				vx1 = (l1 - M.yy) / M.yx, vy1 = 1;
				if(!isFinite(vx1)){
					vx1 = 1, vy1 = M.yx / (l1 - M.yy);
				}
			}
		}
		if(!isFinite(vx2)){
			vx2 = 1, vy2 = (l2 - M.xx) / M.xy;
			if(!isFinite(vy2)){
				vx2 = (l2 - M.yy) / M.yx, vy2 = 1;
				if(!isFinite(vx2)){
					vx2 = 1, vy2 = M.yx / (l2 - M.yy);
				}
			}
		}
		var d1 = Math.sqrt(vx1 * vx1 + vy1 * vy1),
			d2 = Math.sqrt(vx2 * vx2 + vy2 * vy2);
		if(!isFinite(vx1 /= d1)){ vx1 = 0; }
		if(!isFinite(vy1 /= d1)){ vy1 = 0; }
		if(!isFinite(vx2 /= d2)){ vx2 = 0; }
		if(!isFinite(vy2 /= d2)){ vy2 = 0; }
		return {	// Object
			value1: l1,
			value2: l2,
			vector1: {x: vx1, y: vy1},
			vector2: {x: vx2, y: vy2}
		};
	}

	function decomposeSR(/* dojox.gfx.matrix.Matrix2D */ M, /* Object */ result){
		// summary: decomposes a matrix into [scale, rotate]; no checks are done.
		var sign = scaleSign(M),
			a = result.angle1 = (Math.atan2(M.yx, M.yy) + Math.atan2(-sign * M.xy, sign * M.xx)) / 2,
			cos = Math.cos(a), sin = Math.sin(a);
		result.sx = calcFromValues(M.xx / cos, cos, -M.xy / sin, sin);
		result.sy = calcFromValues(M.yy / cos, cos,  M.yx / sin, sin);
		return result;	// Object
	}

	function decomposeRS(/* dojox.gfx.matrix.Matrix2D */ M, /* Object */ result){
		// summary: decomposes a matrix into [rotate, scale]; no checks are done
		var sign = scaleSign(M),
			a = result.angle2 = (Math.atan2(sign * M.yx, sign * M.xx) + Math.atan2(-M.xy, M.yy)) / 2,
			cos = Math.cos(a), sin = Math.sin(a);
		result.sx = calcFromValues(M.xx / cos, cos,  M.yx / sin, sin);
		result.sy = calcFromValues(M.yy / cos, cos, -M.xy / sin, sin);
		return result;	// Object
	}

	dojox.gfx.decompose = function(matrix){
		// summary: decompose a 2D matrix into translation, scaling, and rotation components
		// description: this function decompose a matrix into four logical components:
		//	translation, rotation, scaling, and one more rotation using SVD.
		//	The components should be applied in following order:
		//	| [translate, rotate(angle2), scale, rotate(angle1)]
		// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix-like object
		var M = m.normalize(matrix),
			result = {dx: M.dx, dy: M.dy, sx: 1, sy: 1, angle1: 0, angle2: 0};
		// detect case: [scale]
		if(eq(M.xy, 0) && eq(M.yx, 0)){
			return dojo.mixin(result, {sx: M.xx, sy: M.yy});	// Object
		}
		// detect case: [scale, rotate]
		if(eq(M.xx * M.yx, -M.xy * M.yy)){
			return decomposeSR(M, result);	// Object
		}
		// detect case: [rotate, scale]
		if(eq(M.xx * M.xy, -M.yx * M.yy)){
			return decomposeRS(M, result);	// Object
		}
		// do SVD
		var	MT = transpose(M),
			u  = eigenvalueDecomposition([M, MT]),
			v  = eigenvalueDecomposition([MT, M]),
			U  = new m.Matrix2D({xx: u.vector1.x, xy: u.vector2.x, yx: u.vector1.y, yy: u.vector2.y}),
			VT = new m.Matrix2D({xx: v.vector1.x, xy: v.vector1.y, yx: v.vector2.x, yy: v.vector2.y}),
			S = new m.Matrix2D([m.invert(U), M, m.invert(VT)]);
		decomposeSR(VT, result);
		S.xx *= result.sx;
		S.yy *= result.sy;
		decomposeRS(U, result);
		S.xx *= result.sx;
		S.yy *= result.sy;
		return dojo.mixin(result, {sx: S.xx, sy: S.yy});	// Object
	};
})();

}

if(!dojo._hasResource["dojox.gfx.canvas"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.canvas"] = true;
dojo.provide("dojox.gfx.canvas");







dojo.experimental("dojox.gfx.canvas");

(function(){
	var d = dojo, g = dojox.gfx, gs = g.shape, ga = g.arc, canvas = g.canvas,
		m = g.matrix, mp = m.multiplyPoint, pi = Math.PI, twoPI = 2 * pi, halfPI = pi /2,
		pattrnbuffer = null;

	d.declare("dojox.gfx.canvas.Shape", gs.Shape, {
		_render: function(/* Object */ ctx){
			// summary: render the shape
			ctx.save();
			this._renderTransform(ctx);
			this._renderShape(ctx);
			this._renderFill(ctx, true);
			this._renderStroke(ctx, true);
			ctx.restore();
		},
		_renderTransform: function(/* Object */ ctx){
			if("canvasTransform" in this){
				var t = this.canvasTransform;
				ctx.translate(t.dx, t.dy);
				ctx.rotate(t.angle2);
				ctx.scale(t.sx, t.sy);
				ctx.rotate(t.angle1);
				// The future implementation when vendors catch up with the spec:
				// var t = this.matrix;
				// ctx.transform(t.xx, t.yx, t.xy, t.yy, t.dx, t.dy);
			}
		},
		_renderShape: function(/* Object */ ctx){
			// nothing
		},
		_renderFill: function(/* Object */ ctx, /* Boolean */ apply){
			if("canvasFill" in this){
				var fs = this.fillStyle;
				if("canvasFillImage" in this){
					var w = fs.width, h = fs.height,
						iw = this.canvasFillImage.width, ih = this.canvasFillImage.height,
						// let's match the svg default behavior wrt. aspect ratio: xMidYMid meet
						sx = w == iw ? 1 : w / iw,
						sy = h == ih ? 1 : h / ih,
						s = Math.min(sx,sy), //meet->math.min , slice->math.max
						dx = (w - s * iw)/2,
						dy = (h - s * ih)/2;
					// the buffer used to scaled the image
					pattrnbuffer.width = w; pattrnbuffer.height = h;
					var copyctx = pattrnbuffer.getContext("2d");
					copyctx.clearRect(0, 0, w, h);
					copyctx.drawImage(this.canvasFillImage, 0, 0, iw, ih, dx, dy, s*iw, s*ih);
					this.canvasFill = ctx.createPattern(pattrnbuffer, "repeat");
					delete this.canvasFillImage;
				}
				ctx.fillStyle = this.canvasFill;
				if(apply){
					// offset the pattern
					if (fs.type==="pattern" && (fs.x !== 0 || fs.y !== 0)) {
						ctx.translate(fs.x,fs.y);
					}
					ctx.fill();
				}
			}else{
				ctx.fillStyle = "rgba(0,0,0,0.0)";
			}
		},
		_renderStroke: function(/* Object */ ctx, /* Boolean */ apply){
			var s = this.strokeStyle;
			if(s){
				ctx.strokeStyle = s.color.toString();
				ctx.lineWidth = s.width;
				ctx.lineCap = s.cap;
				if(typeof s.join == "number"){
					ctx.lineJoin = "miter";
					ctx.miterLimit = s.join;
				}else{
					ctx.lineJoin = s.join;
				}
				if(apply){ ctx.stroke(); }
			}else if(!apply){
				ctx.strokeStyle = "rgba(0,0,0,0.0)";
			}
		},

		// events are not implemented
		getEventSource: function(){ return null; },
		connect:		function(){},
		disconnect:		function(){}
	});

	var modifyMethod = function(shape, method, extra){
			var old = shape.prototype[method];
			shape.prototype[method] = extra ?
				function(){
					this.surface.makeDirty();
					old.apply(this, arguments);
					extra.call(this);
					return this;
				} :
				function(){
					this.surface.makeDirty();
					return old.apply(this, arguments);
				};
		};

	modifyMethod(canvas.Shape, "setTransform",
		function(){
			// prepare Canvas-specific structures
			if(this.matrix){
				this.canvasTransform = g.decompose(this.matrix);
			}else{
				delete this.canvasTransform;
			}
		});

	modifyMethod(canvas.Shape, "setFill",
		function(){
			// prepare Canvas-specific structures
			var fs = this.fillStyle, f;
			if(fs){
				if(typeof(fs) == "object" && "type" in fs){
					var ctx = this.surface.rawNode.getContext("2d");
					switch(fs.type){
						case "linear":
						case "radial":
							f = fs.type == "linear" ?
								ctx.createLinearGradient(fs.x1, fs.y1, fs.x2, fs.y2) :
								ctx.createRadialGradient(fs.cx, fs.cy, 0, fs.cx, fs.cy, fs.r);
							d.forEach(fs.colors, function(step){
								f.addColorStop(step.offset, g.normalizeColor(step.color).toString());
							});
							break;
						case "pattern":
							if (!pattrnbuffer) {
								pattrnbuffer = document.createElement("canvas");
							}
							// no need to scale the image since the canvas.createPattern uses
							// the original image data and not the scaled ones (see spec.)
							// the scaling needs to be done at rendering time in a context buffer
							var img =new Image();
							this.surface.downloadImage(img, fs.src);
							this.canvasFillImage = img;
					}
				}else{
					// Set fill color using CSS RGBA func style
					f = fs.toString();
				}
				this.canvasFill = f;
			}else{
				delete this.canvasFill;
			}
		});

	modifyMethod(canvas.Shape, "setStroke");
	modifyMethod(canvas.Shape, "setShape");

	dojo.declare("dojox.gfx.canvas.Group", canvas.Shape, {
		// summary: a group shape (Canvas), which can be used
		//	to logically group shapes (e.g, to propagate matricies)
		constructor: function(){
			gs.Container._init.call(this);
		},
		_render: function(/* Object */ ctx){
			// summary: render the group
			ctx.save();
			this._renderTransform(ctx);
			for(var i = 0; i < this.children.length; ++i){
				this.children[i]._render(ctx);
			}
			ctx.restore();
		}
	});

	dojo.declare("dojox.gfx.canvas.Rect", [canvas.Shape, gs.Rect], {
		// summary: a rectangle shape (Canvas)
		_renderShape: function(/* Object */ ctx){
			var s = this.shape, r = Math.min(s.r, s.height / 2, s.width / 2),
				xl = s.x, xr = xl + s.width, yt = s.y, yb = yt + s.height,
				xl2 = xl + r, xr2 = xr - r, yt2 = yt + r, yb2 = yb - r;
			ctx.beginPath();
			ctx.moveTo(xl2, yt);
			if(r){
				ctx.arc(xr2, yt2, r, -halfPI, 0, false);
				ctx.arc(xr2, yb2, r, 0, halfPI, false);
				ctx.arc(xl2, yb2, r, halfPI, pi, false);
				ctx.arc(xl2, yt2, r, pi, pi + halfPI, false);
			}else{
				ctx.lineTo(xr2, yt);
				ctx.lineTo(xr, yb2);
				ctx.lineTo(xl2, yb);
				ctx.lineTo(xl, yt2);
			}
	 		ctx.closePath();
		}
	});

	var bezierCircle = [];
	(function(){
		var u = ga.curvePI4;
		bezierCircle.push(u.s, u.c1, u.c2, u.e);
		for(var a = 45; a < 360; a += 45){
			var r = m.rotateg(a);
			bezierCircle.push(mp(r, u.c1), mp(r, u.c2), mp(r, u.e));
		}
	})();

	dojo.declare("dojox.gfx.canvas.Ellipse", [canvas.Shape, gs.Ellipse], {
		// summary: an ellipse shape (Canvas)
		setShape: function(){
			this.inherited(arguments);
			// prepare Canvas-specific structures
			var s = this.shape, t, c1, c2, r = [],
				M = m.normalize([m.translate(s.cx, s.cy), m.scale(s.rx, s.ry)]);
			t = mp(M, bezierCircle[0]);
			r.push([t.x, t.y]);
			for(var i = 1; i < bezierCircle.length; i += 3){
				c1 = mp(M, bezierCircle[i]);
				c2 = mp(M, bezierCircle[i + 1]);
				t  = mp(M, bezierCircle[i + 2]);
				r.push([c1.x, c1.y, c2.x, c2.y, t.x, t.y]);
			}
			this.canvasEllipse = r;
			return this;
		},
		_renderShape: function(/* Object */ ctx){
			var r = this.canvasEllipse;
			ctx.beginPath();
			ctx.moveTo.apply(ctx, r[0]);
			for(var i = 1; i < r.length; ++i){
				ctx.bezierCurveTo.apply(ctx, r[i]);
			}
			ctx.closePath();
		}
	});

	dojo.declare("dojox.gfx.canvas.Circle", [canvas.Shape, gs.Circle], {
		// summary: a circle shape (Canvas)
		_renderShape: function(/* Object */ ctx){
			var s = this.shape;
			ctx.beginPath();
			ctx.arc(s.cx, s.cy, s.r, 0, twoPI, 1);
		}
	});

	dojo.declare("dojox.gfx.canvas.Line", [canvas.Shape, gs.Line], {
		// summary: a line shape (Canvas)
		_renderShape: function(/* Object */ ctx){
			var s = this.shape;
			ctx.beginPath();
			ctx.moveTo(s.x1, s.y1);
			ctx.lineTo(s.x2, s.y2);
		}
	});

	dojo.declare("dojox.gfx.canvas.Polyline", [canvas.Shape, gs.Polyline], {
		// summary: a polyline/polygon shape (Canvas)
		setShape: function(){
			this.inherited(arguments);
			// prepare Canvas-specific structures
			var p = this.shape.points, f = p[0], r = [], c, i;
			if(p.length){
				if(typeof f == "number"){
					r.push(f, p[1]);
					i = 2;
				}else{
					r.push(f.x, f.y);
					i = 1;
				}
				for(; i < p.length; ++i){
					c = p[i];
					if(typeof c == "number"){
						r.push(c, p[++i]);
					}else{
						r.push(c.x, c.y);
					}
				}
			}
			this.canvasPolyline = r;
			return this;
		},
		_renderShape: function(/* Object */ ctx){
			var p = this.canvasPolyline;
			if(p.length){
				ctx.beginPath();
				ctx.moveTo(p[0], p[1]);
				for(var i = 2; i < p.length; i += 2){
					ctx.lineTo(p[i], p[i + 1]);
				}
			}
		}
	});

	dojo.declare("dojox.gfx.canvas.Image", [canvas.Shape, gs.Image], {
		// summary: an image shape (Canvas)
		setShape: function(){
			this.inherited(arguments);
			// prepare Canvas-specific structures
			var img = new Image();
			this.surface.downloadImage(img, this.shape.src);
			this.canvasImage = img;
			return this;
		},
		_renderShape: function(/* Object */ ctx){
			var s = this.shape;
			ctx.drawImage(this.canvasImage, s.x, s.y, s.width, s.height);
		}
	});
	
	dojo.declare("dojox.gfx.canvas.Text", [canvas.Shape, gs.Text], {
		_setFont:function(){
			if (this.fontStyle){
				this.canvasFont = g.makeFontString(this.fontStyle);
			} else {
				delete this.canvasFont;
			}
		},
		
		getTextWidth: function(){
			// summary: get the text width in pixels
			var s = this.shape, w = 0, ctx;
			if(s.text && s.text.length > 0){
				ctx = this.surface.rawNode.getContext("2d");
				ctx.save();
				this._renderTransform(ctx);
				this._renderFill(ctx, false);
				this._renderStroke(ctx, false);
				if (this.canvasFont)
					ctx.font = this.canvasFont;
				w = ctx.measureText(s.text).width;
				ctx.restore();
			}
			return w;
		},
		
		// override to apply first fill and stroke (
		// the base implementation is for path-based shape that needs to first define the path then to fill/stroke it.
		// Here, we need the fillstyle or strokestyle to be set before calling fillText/strokeText.
		_render: function(/* Object */ctx){
			// summary: render the shape
			// ctx : Object: the drawing context.
			ctx.save();
			this._renderTransform(ctx);
			this._renderFill(ctx, false);
			this._renderStroke(ctx, false);
			this._renderShape(ctx);
			ctx.restore();
		},
		
		_renderShape: function(ctx){
			// summary: a text shape (Canvas)
			// ctx : Object: the drawing context.
			var ta, s = this.shape;
			if(!s.text || s.text.length == 0){
				return;
			}
			// text align
			ta = s.align === 'middle' ? 'center' : s.align;
			ctx.textAlign = ta;
			if(this.canvasFont){
				ctx.font = this.canvasFont;
			}
			if(this.canvasFill){
				ctx.fillText(s.text, s.x, s.y);
			}
			if(this.strokeStyle){
				ctx.beginPath(); // fix bug in FF3.6. Fixed in FF4b8
				ctx.strokeText(s.text, s.x, s.y);
				ctx.closePath();
			}
		}
	});
	modifyMethod(canvas.Text, "setFont");
	
	// the next test is from https://github.com/phiggins42/has.js
	if(typeof dojo.doc.createElement("canvas").getContext("2d").fillText != "function"){
		canvas.Text.extend({
			getTextWidth: function(){
				return 0;
			},
			_renderShape: function(){}
		});
	}
	

	var pathRenderers = {
			M: "_moveToA", m: "_moveToR",
			L: "_lineToA", l: "_lineToR",
			H: "_hLineToA", h: "_hLineToR",
			V: "_vLineToA", v: "_vLineToR",
			C: "_curveToA", c: "_curveToR",
			S: "_smoothCurveToA", s: "_smoothCurveToR",
			Q: "_qCurveToA", q: "_qCurveToR",
			T: "_qSmoothCurveToA", t: "_qSmoothCurveToR",
			A: "_arcTo", a: "_arcTo",
			Z: "_closePath", z: "_closePath"
		};

	dojo.declare("dojox.gfx.canvas.Path", [canvas.Shape, g.path.Path], {
		// summary: a path shape (Canvas)
		constructor: function(){
			this.lastControl = {};
		},
		setShape: function(){
			this.canvasPath = [];
			return this.inherited(arguments);
		},
		_updateWithSegment: function(segment){
			var last = d.clone(this.last);
			this[pathRenderers[segment.action]](this.canvasPath, segment.action, segment.args);
			this.last = last;
			this.inherited(arguments);
		},
		_renderShape: function(/* Object */ ctx){
			var r = this.canvasPath;
			ctx.beginPath();
			for(var i = 0; i < r.length; i += 2){
				ctx[r[i]].apply(ctx, r[i + 1]);
			}
		},
		_moveToA: function(result, action, args){
			result.push("moveTo", [args[0], args[1]]);
			for(var i = 2; i < args.length; i += 2){
				result.push("lineTo", [args[i], args[i + 1]]);
			}
			this.last.x = args[args.length - 2];
			this.last.y = args[args.length - 1];
			this.lastControl = {};
		},
		_moveToR: function(result, action, args){
			if("x" in this.last){
				result.push("moveTo", [this.last.x += args[0], this.last.y += args[1]]);
			}else{
				result.push("moveTo", [this.last.x = args[0], this.last.y = args[1]]);
			}
			for(var i = 2; i < args.length; i += 2){
				result.push("lineTo", [this.last.x += args[i], this.last.y += args[i + 1]]);
			}
			this.lastControl = {};
		},
		_lineToA: function(result, action, args){
			for(var i = 0; i < args.length; i += 2){
				result.push("lineTo", [args[i], args[i + 1]]);
			}
			this.last.x = args[args.length - 2];
			this.last.y = args[args.length - 1];
			this.lastControl = {};
		},
		_lineToR: function(result, action, args){
			for(var i = 0; i < args.length; i += 2){
				result.push("lineTo", [this.last.x += args[i], this.last.y += args[i + 1]]);
			}
			this.lastControl = {};
		},
		_hLineToA: function(result, action, args){
			for(var i = 0; i < args.length; ++i){
				result.push("lineTo", [args[i], this.last.y]);
			}
			this.last.x = args[args.length - 1];
			this.lastControl = {};
		},
		_hLineToR: function(result, action, args){
			for(var i = 0; i < args.length; ++i){
				result.push("lineTo", [this.last.x += args[i], this.last.y]);
			}
			this.lastControl = {};
		},
		_vLineToA: function(result, action, args){
			for(var i = 0; i < args.length; ++i){
				result.push("lineTo", [this.last.x, args[i]]);
			}
			this.last.y = args[args.length - 1];
			this.lastControl = {};
		},
		_vLineToR: function(result, action, args){
			for(var i = 0; i < args.length; ++i){
				result.push("lineTo", [this.last.x, this.last.y += args[i]]);
			}
			this.lastControl = {};
		},
		_curveToA: function(result, action, args){
			for(var i = 0; i < args.length; i += 6){
				result.push("bezierCurveTo", args.slice(i, i + 6));
			}
			this.last.x = args[args.length - 2];
			this.last.y = args[args.length - 1];
			this.lastControl.x = args[args.length - 4];
			this.lastControl.y = args[args.length - 3];
			this.lastControl.type = "C";
		},
		_curveToR: function(result, action, args){
			for(var i = 0; i < args.length; i += 6){
				result.push("bezierCurveTo", [
					this.last.x + args[i],
					this.last.y + args[i + 1],
					this.lastControl.x = this.last.x + args[i + 2],
					this.lastControl.y = this.last.y + args[i + 3],
					this.last.x + args[i + 4],
					this.last.y + args[i + 5]
				]);
				this.last.x += args[i + 4];
				this.last.y += args[i + 5];
			}
			this.lastControl.type = "C";
		},
		_smoothCurveToA: function(result, action, args){
			for(var i = 0; i < args.length; i += 4){
				var valid = this.lastControl.type == "C";
				result.push("bezierCurveTo", [
					valid ? 2 * this.last.x - this.lastControl.x : this.last.x,
					valid ? 2 * this.last.y - this.lastControl.y : this.last.y,
					args[i],
					args[i + 1],
					args[i + 2],
					args[i + 3]
				]);
				this.lastControl.x = args[i];
				this.lastControl.y = args[i + 1];
				this.lastControl.type = "C";
			}
			this.last.x = args[args.length - 2];
			this.last.y = args[args.length - 1];
		},
		_smoothCurveToR: function(result, action, args){
			for(var i = 0; i < args.length; i += 4){
				var valid = this.lastControl.type == "C";
				result.push("bezierCurveTo", [
					valid ? 2 * this.last.x - this.lastControl.x : this.last.x,
					valid ? 2 * this.last.y - this.lastControl.y : this.last.y,
					this.last.x + args[i],
					this.last.y + args[i + 1],
					this.last.x + args[i + 2],
					this.last.y + args[i + 3]
				]);
				this.lastControl.x = this.last.x + args[i];
				this.lastControl.y = this.last.y + args[i + 1];
				this.lastControl.type = "C";
				this.last.x += args[i + 2];
				this.last.y += args[i + 3];
			}
		},
		_qCurveToA: function(result, action, args){
			for(var i = 0; i < args.length; i += 4){
				result.push("quadraticCurveTo", args.slice(i, i + 4));
			}
			this.last.x = args[args.length - 2];
			this.last.y = args[args.length - 1];
			this.lastControl.x = args[args.length - 4];
			this.lastControl.y = args[args.length - 3];
			this.lastControl.type = "Q";
		},
		_qCurveToR: function(result, action, args){
			for(var i = 0; i < args.length; i += 4){
				result.push("quadraticCurveTo", [
					this.lastControl.x = this.last.x + args[i],
					this.lastControl.y = this.last.y + args[i + 1],
					this.last.x + args[i + 2],
					this.last.y + args[i + 3]
				]);
				this.last.x += args[i + 2];
				this.last.y += args[i + 3];
			}
			this.lastControl.type = "Q";
		},
		_qSmoothCurveToA: function(result, action, args){
			for(var i = 0; i < args.length; i += 2){
				var valid = this.lastControl.type == "Q";
				result.push("quadraticCurveTo", [
					this.lastControl.x = valid ? 2 * this.last.x - this.lastControl.x : this.last.x,
					this.lastControl.y = valid ? 2 * this.last.y - this.lastControl.y : this.last.y,
					args[i],
					args[i + 1]
				]);
				this.lastControl.type = "Q";
			}
			this.last.x = args[args.length - 2];
			this.last.y = args[args.length - 1];
		},
		_qSmoothCurveToR: function(result, action, args){
			for(var i = 0; i < args.length; i += 2){
				var valid = this.lastControl.type == "Q";
				result.push("quadraticCurveTo", [
					this.lastControl.x = valid ? 2 * this.last.x - this.lastControl.x : this.last.x,
					this.lastControl.y = valid ? 2 * this.last.y - this.lastControl.y : this.last.y,
					this.last.x + args[i],
					this.last.y + args[i + 1]
				]);
				this.lastControl.type = "Q";
				this.last.x += args[i];
				this.last.y += args[i + 1];
			}
		},
		_arcTo: function(result, action, args){
			var relative = action == "a";
			for(var i = 0; i < args.length; i += 7){
				var x1 = args[i + 5], y1 = args[i + 6];
				if(relative){
					x1 += this.last.x;
					y1 += this.last.y;
				}
				var arcs = ga.arcAsBezier(
					this.last, args[i], args[i + 1], args[i + 2],
					args[i + 3] ? 1 : 0, args[i + 4] ? 1 : 0,
					x1, y1
				);
				d.forEach(arcs, function(p){
					result.push("bezierCurveTo", p);
				});
				this.last.x = x1;
				this.last.y = y1;
			}
			this.lastControl = {};
		},
		_closePath: function(result, action, args){
			result.push("closePath", []);
			this.lastControl = {};
		}
	});
	d.forEach(["moveTo", "lineTo", "hLineTo", "vLineTo", "curveTo",
		"smoothCurveTo", "qCurveTo", "qSmoothCurveTo", "arcTo", "closePath"],
		function(method){ modifyMethod(canvas.Path, method); }
	);

	dojo.declare("dojox.gfx.canvas.TextPath", [canvas.Shape, g.path.TextPath], {
		// summary: a text shape (Canvas)
		_renderShape: function(/* Object */ ctx){
			var s = this.shape;
			// nothing for the moment
		},
		_setText: function(){
			// not implemented
		},
		_setFont: function(){
			// not implemented
		}
	});

	dojo.declare("dojox.gfx.canvas.Surface", gs.Surface, {
		// summary: a surface object to be used for drawings (Canvas)
		constructor: function(){
			gs.Container._init.call(this);
			this.pendingImageCount = 0;
			this.makeDirty();
		},
		setDimensions: function(width, height){
			// summary: sets the width and height of the rawNode
			// width: String: width of surface, e.g., "100px"
			// height: String: height of surface, e.g., "100px"
			this.width  = g.normalizedLength(width);	// in pixels
			this.height = g.normalizedLength(height);	// in pixels
			if(!this.rawNode) return this;
			this.rawNode.width = width;
			this.rawNode.height = height;
			this.makeDirty();
			return this;	// self
		},
		getDimensions: function(){
			// summary: returns an object with properties "width" and "height"
			return this.rawNode ? {width:  this.rawNode.width, height: this.rawNode.height} : null;	// Object
		},
		_render: function(){
			// summary: render the all shapes
			if(this.pendingImageCount){ return; }
			var ctx = this.rawNode.getContext("2d");
			ctx.save();
			ctx.clearRect(0, 0, this.rawNode.width, this.rawNode.height);
			for(var i = 0; i < this.children.length; ++i){
				this.children[i]._render(ctx);
			}
			ctx.restore();
			if("pendingRender" in this){
				clearTimeout(this.pendingRender);
				delete this.pendingRender;
			}
		},
		makeDirty: function(){
			// summary: internal method, which is called when we may need to redraw
			if(!this.pendingImagesCount && !("pendingRender" in this)){
				this.pendingRender = setTimeout(d.hitch(this, this._render), 0);
			}
		},
		downloadImage: function(img, url){
			// summary:
			//		internal method, which starts an image download and renders, when it is ready
			// img: Image:
			//		the image object
			// url: String:
			//		the url of the image
			var handler = d.hitch(this, this.onImageLoad);
			if(!this.pendingImageCount++ && "pendingRender" in this){
				clearTimeout(this.pendingRender);
				delete this.pendingRender;
			}
			img.onload  = handler;
			img.onerror = handler;
			img.onabort = handler;
			img.src = url;
		},
		onImageLoad: function(){
			if(!--this.pendingImageCount){ this._render(); }
		},

		// events are not implemented
		getEventSource: function(){ return null; },
		connect:		function(){},
		disconnect:		function(){}
	});

	canvas.createSurface = function(parentNode, width, height){
		// summary: creates a surface (Canvas)
		// parentNode: Node: a parent node
		// width: String: width of surface, e.g., "100px"
		// height: String: height of surface, e.g., "100px"

		if(!width && !height){
			var pos = d.position(parentNode);
			width  = width  || pos.w;
			height = height || pos.h;
		}
		if(typeof width == "number"){
			width = width + "px";
		}
		if(typeof height == "number"){
			height = height + "px";
		}

		var s = new canvas.Surface(),
			p = d.byId(parentNode),
			c = p.ownerDocument.createElement("canvas");

		c.width  = g.normalizedLength(width);	// in pixels
		c.height = g.normalizedLength(height);	// in pixels

		p.appendChild(c);
		s.rawNode = c;
		s._parent = p;
		s.surface = s;
		return s;	// dojox.gfx.Surface
	};

	// Extenders

	var C = gs.Container, Container = {
		add: function(shape){
			this.surface.makeDirty();
			return C.add.apply(this, arguments);
		},
		remove: function(shape, silently){
			this.surface.makeDirty();
			return C.remove.apply(this, arguments);
		},
		clear: function(){
			this.surface.makeDirty();
			return C.clear.apply(this, arguments);
		},
		_moveChildToFront: function(shape){
			this.surface.makeDirty();
			return C._moveChildToFront.apply(this, arguments);
		},
		_moveChildToBack: function(shape){
			this.surface.makeDirty();
			return C._moveChildToBack.apply(this, arguments);
		}
	};

	var Creator = {
		// summary: Canvas shape creators
		createObject: function(shapeType, rawShape) {
			// summary: creates an instance of the passed shapeType class
			// shapeType: Function: a class constructor to create an instance of
			// rawShape: Object: properties to be passed in to the classes "setShape" method
			// overrideSize: Boolean: set the size explicitly, if true
			var shape = new shapeType();
			shape.surface = this.surface;
			shape.setShape(rawShape);
			this.add(shape);
			return shape;	// dojox.gfx.Shape
		}
	};

	d.extend(canvas.Group, Container);
	d.extend(canvas.Group, gs.Creator);
	d.extend(canvas.Group, Creator);

	d.extend(canvas.Surface, Container);
	d.extend(canvas.Surface, gs.Creator);
	d.extend(canvas.Surface, Creator);
	
	// see if we are required to initilize
	if(g.loadAndSwitch === "canvas"){
		g.switchTo("canvas");
		delete g.loadAndSwitch;
	}
})();

}

