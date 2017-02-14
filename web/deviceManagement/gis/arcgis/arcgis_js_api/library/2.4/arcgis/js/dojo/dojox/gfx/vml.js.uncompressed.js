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

if(!dojo._hasResource["dojox.gfx.gradient"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.gradient"] = true;
dojo.provide("dojox.gfx.gradient");



// Various utilities to deal with a linear gradient (mostly VML-specific)

(function(){
	var d = dojo, m = dojox.gfx.matrix, C = d.Color;
	
	dojox.gfx.gradient.rescale = function(stops, from, to){
		// summary:
		//		recalculates a gradient from 0-1 window to
		//		"from"-"to" window blending and replicating colors,
		//		if necessary
		// stops: Array:
		//		input gradient as a list of colors with offsets
		//		(see dojox.gfx.defaultLinearGradient and dojox.gfx.defaultRadialGradient)
		// from: Number:
		//		the beginning of the window, should be less than "to"
		// to: Number:
		//		the end of the window, should be more than "from"

		var len = stops.length, reverseFlag = (to < from), newStops;

		// do we need to reverse the color table?
		if(reverseFlag){
			var tmp = from;
			from = to;
			to = tmp;
		}
		
		// various edge cases
		if(!len){
			// no colors
			return [];
		}
		if(to <= stops[0].offset){
			// all colors are before the color table
			newStops = [
				{offset: 0, color: stops[0].color},
				{offset: 1, color: stops[0].color}
			];
		}else if(from >= stops[len - 1].offset){
			// all colors are after the color table
			newStops = [
				{offset: 0, color: stops[len - 1].color},
				{offset: 1, color: stops[len - 1].color}
			];
		}else{
			// main scanning algorithm
			var span = to - from, stop, prev, i;
			newStops = [];
			if(from < 0){
				newStops.push({offset: 0, color: new C(stops[0].color)});
			}
			for(i = 0; i < len; ++i){
				stop = stops[i];
				if(stop.offset >= from){
					break;
				}
				// skip this color
			}
			if(i){
				prev = stops[i - 1];
				newStops.push({
					offset: 0,
					color: d.blendColors(new C(prev.color), new C(stop.color), (from - prev.offset) / (stop.offset - prev.offset))
				});
			}else{
				newStops.push({offset: 0, color: new C(stop.color)});
			}
			for(; i < len; ++i){
				stop = stops[i];
				if(stop.offset >= to){
					break;
				}
				newStops.push({offset: (stop.offset - from) / span, color: new C(stop.color)});
			}
			if(i < len){
				prev = stops[i - 1];
				newStops.push({
					offset: 1,
					color: d.blendColors(new C(prev.color), new C(stop.color), (to - prev.offset) / (stop.offset - prev.offset))
				});
			}else{
				newStops.push({offset: 1, color: new C(stops[len - 1].color)});
			}
		}
		
		// reverse the color table, if needed
		if(reverseFlag){
			newStops.reverse();
			for(i = 0, len = newStops.length; i < len; ++i){
				stop = newStops[i];
				stop.offset = 1 - stop.offset;
			}
		}
		
		return newStops;
	};
	
	function getPoint(x, y, matrix, project, shiftAndRotate, scale){
		var r = m.multiplyPoint(matrix, x, y),
			p = m.multiplyPoint(project, r);
		return {r: r, p: p, o: m.multiplyPoint(shiftAndRotate, p).x / scale};
	}
	
	function sortPoints(a, b){
		return a.o - b.o;
	}
	
	dojox.gfx.gradient.project = function(matrix, grad, tl, rb, ttl, trb){
		// summary:
		//		return a new gradient using the "VML algorithm" and suitable for VML
		// matrix: dojox.gfx.Matrix2D|Null:
		//		matrix to apply to a shape and its gradient
		// grad: Object:
		//		a linear gradient object to be transformed
		// tl: dojox.gfx.Point:
		//		top-left corner of shape's bounding box
		// rb: dojox.gfx.Point:
		//		right-bottom corner of shape's bounding box
		// ttl: dojox.gfx.Point:
		//		top-left corner of shape's transformed bounding box
		// trb: dojox.gfx.Point:
		//		right-bottom corner of shape's transformed bounding box
		
		matrix = matrix || m.identity;

		var f1 = m.multiplyPoint(matrix, grad.x1, grad.y1),
			f2 = m.multiplyPoint(matrix, grad.x2, grad.y2),
			angle = Math.atan2(f2.y - f1.y, f2.x - f1.x),
			project = m.project(f2.x - f1.x, f2.y - f1.y),
			pf1 = m.multiplyPoint(project, f1),
			pf2 = m.multiplyPoint(project, f2),
			shiftAndRotate = new m.Matrix2D([m.rotate(-angle), {dx: -pf1.x, dy: -pf1.y}]),
			scale = m.multiplyPoint(shiftAndRotate, pf2).x,
			//comboMatrix = new m.Matrix2D([shiftAndRotate, project, matrix]),
			// bbox-specific calculations
			points = [
					getPoint(tl.x, tl.y, matrix, project, shiftAndRotate, scale),
					getPoint(rb.x, rb.y, matrix, project, shiftAndRotate, scale),
					getPoint(tl.x, rb.y, matrix, project, shiftAndRotate, scale),
					getPoint(rb.x, tl.y, matrix, project, shiftAndRotate, scale)
				].sort(sortPoints),
			from = points[0].o,
			to   = points[3].o,
			stops = dojox.gfx.gradient.rescale(grad.colors, from, to),
			//angle2 = Math.atan2(Math.abs(points[3].r.y - points[0].r.y) * (f2.y - f1.y), Math.abs(points[3].r.x - points[0].r.x) * (f2.x - f1.x));
			angle2 = Math.atan2(points[3].r.y - points[0].r.y, points[3].r.x - points[0].r.x);

		return {
			type: "linear",
			x1: points[0].p.x, y1: points[0].p.y, x2: points[3].p.x, y2: points[3].p.y,
			colors: stops,
			// additional helpers (for VML)
			angle: angle
		};
	};
})();

}

if(!dojo._hasResource["dojox.gfx.vml"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.vml"] = true;
dojo.provide("dojox.gfx.vml");







(function(){
	var d = dojo, g = dojox.gfx, m = g.matrix, gs = g.shape, vml = g.vml;

	// dojox.gfx.vml.xmlns: String: a VML's namespace
	vml.xmlns = "urn:schemas-microsoft-com:vml";

	// dojox.gfx.vml.text_alignment: Object: mapping from SVG alignment to VML alignment
	vml.text_alignment = {start: "left", middle: "center", end: "right"};

	vml._parseFloat = function(str) {
		// summary: a helper function to parse VML-specific floating-point values
		// str: String: a representation of a floating-point number
		return str.match(/^\d+f$/i) ? parseInt(str) / 65536 : parseFloat(str);	// Number
	};

	vml._bool = {"t": 1, "true": 1};

	d.declare("dojox.gfx.vml.Shape", gs.Shape, {
		// summary: VML-specific implementation of dojox.gfx.Shape methods

		setFill: function(fill){
			// summary: sets a fill object (VML)
			// fill: Object: a fill object
			//	(see dojox.gfx.defaultLinearGradient,
			//	dojox.gfx.defaultRadialGradient,
			//	dojox.gfx.defaultPattern,
			//	or dojo.Color)

			if(!fill){
				// don't fill
				this.fillStyle = null;
				this.rawNode.filled = "f";
				return this;
			}
			var i, f, fo, a, s;
			if(typeof fill == "object" && "type" in fill){
				// gradient
				switch(fill.type){
					case "linear":
						var matrix = this._getRealMatrix(), bbox = this.getBoundingBox(),
							tbbox = this._getRealBBox ? this._getRealBBox() : this.getTransformedBoundingBox();
						s = [];
						if(this.fillStyle !== fill){
							this.fillStyle = g.makeParameters(g.defaultLinearGradient, fill);
						}
						f = g.gradient.project(matrix, this.fillStyle,
								{x: bbox.x, y: bbox.y},
								{x: bbox.x + bbox.width, y: bbox.y + bbox.height},
								tbbox[0], tbbox[2]);
						a = f.colors;
						if(a[0].offset.toFixed(5) != "0.00000"){
							s.push("0 " + g.normalizeColor(a[0].color).toHex());
						}
						for(i = 0; i < a.length; ++i){
							s.push(a[i].offset.toFixed(5) + " " + g.normalizeColor(a[i].color).toHex());
						}
						i = a.length - 1;
						if(a[i].offset.toFixed(5) != "1.00000"){
							s.push("1 " + g.normalizeColor(a[i].color).toHex());
						}
						fo = this.rawNode.fill;
						fo.colors.value = s.join(";");
						fo.method = "sigma";
						fo.type = "gradient";
						fo.angle = (270 - m._radToDeg(f.angle)) % 360;
						fo.on = true;
						break;
					case "radial":
						f = g.makeParameters(g.defaultRadialGradient, fill);
						this.fillStyle = f;
						var l = parseFloat(this.rawNode.style.left),
							t = parseFloat(this.rawNode.style.top),
							w = parseFloat(this.rawNode.style.width),
							h = parseFloat(this.rawNode.style.height),
							c = isNaN(w) ? 1 : 2 * f.r / w;
						a = [];
						// add a color at the offset 0 (1 in VML coordinates)
						if(f.colors[0].offset > 0){
							a.push({offset: 1, color: g.normalizeColor(f.colors[0].color)});
						}
						// massage colors
						d.forEach(f.colors, function(v, i){
							a.push({offset: 1 - v.offset * c, color: g.normalizeColor(v.color)});
						});
						i = a.length - 1;
						while(i >= 0 && a[i].offset < 0){ --i; }
						if(i < a.length - 1){
							// correct excessive colors
							var q = a[i], p = a[i + 1];
							p.color = d.blendColors(q.color, p.color, q.offset / (q.offset - p.offset));
							p.offset = 0;
							while(a.length - i > 2) a.pop();
						}
						// set colors
						i = a.length - 1, s = [];
						if(a[i].offset > 0){
							s.push("0 " + a[i].color.toHex());
						}
						for(; i >= 0; --i){
							s.push(a[i].offset.toFixed(5) + " " + a[i].color.toHex());
						}
						fo = this.rawNode.fill;
						fo.colors.value = s.join(";");
						fo.method = "sigma";
						fo.type = "gradientradial";
						if(isNaN(w) || isNaN(h) || isNaN(l) || isNaN(t)){
							fo.focusposition = "0.5 0.5";
						}else{
							fo.focusposition = ((f.cx - l) / w).toFixed(5) + " " + ((f.cy - t) / h).toFixed(5);
						}
						fo.focussize = "0 0";
						fo.on = true;
						break;
					case "pattern":
						f = g.makeParameters(g.defaultPattern, fill);
						this.fillStyle = f;
						fo = this.rawNode.fill;
						fo.type = "tile";
						fo.src = f.src;
						if(f.width && f.height){
							// in points
							fo.size.x = g.px2pt(f.width);
							fo.size.y = g.px2pt(f.height);
						}
						fo.alignShape = "f";
						fo.position.x = 0;
						fo.position.y = 0;
						fo.origin.x = f.width  ? f.x / f.width  : 0;
						fo.origin.y = f.height ? f.y / f.height : 0;
						fo.on = true;
						break;
				}
				this.rawNode.fill.opacity = 1;
				return this;
			}
			// color object
			this.fillStyle = g.normalizeColor(fill);
			fo = this.rawNode.fill;
			if(!fo){
				fo = this.rawNode.ownerDocument.createElement("v:fill");
			}
			fo.method = "any";
			fo.type = "solid";
			fo.opacity = this.fillStyle.a;
			var alphaFilter = this.rawNode.filters["DXImageTransform.Microsoft.Alpha"];
			if(alphaFilter){
				alphaFilter.opacity = Math.round(this.fillStyle.a * 100);
			}
			this.rawNode.fillcolor = this.fillStyle.toHex();
			this.rawNode.filled = true;
			return this;	// self
		},

		setStroke: function(stroke){
			// summary: sets a stroke object (VML)
			// stroke: Object: a stroke object
			//	(see dojox.gfx.defaultStroke)

			if(!stroke){
				// don't stroke
				this.strokeStyle = null;
				this.rawNode.stroked = "f";
				return this;
			}
			// normalize the stroke
			if(typeof stroke == "string" || d.isArray(stroke) || stroke instanceof d.Color){
				stroke = {color: stroke};
			}
			var s = this.strokeStyle = g.makeParameters(g.defaultStroke, stroke);
			s.color = g.normalizeColor(s.color);
			// generate attributes
			var rn = this.rawNode;
			rn.stroked = true;
			rn.strokecolor = s.color.toCss();
			rn.strokeweight = s.width + "px";	// TODO: should we assume that the width is always in pixels?
			if(rn.stroke) {
				rn.stroke.opacity = s.color.a;
				rn.stroke.endcap = this._translate(this._capMap, s.cap);
				if(typeof s.join == "number") {
					rn.stroke.joinstyle = "miter";
					rn.stroke.miterlimit = s.join;
				}else{
					rn.stroke.joinstyle = s.join;
					// rn.stroke.miterlimit = s.width;
				}
				rn.stroke.dashstyle = s.style == "none" ? "Solid" : s.style;
			}
			return this;	// self
		},

		_capMap: { butt: 'flat' },
		_capMapReversed: { flat: 'butt' },

		_translate: function(dict, value) {
			return (value in dict) ? dict[value] : value;
		},

		_applyTransform: function() {
			var matrix = this._getRealMatrix();
			if(matrix){
				var skew = this.rawNode.skew;
				if(typeof skew == "undefined"){
					for(var i = 0; i < this.rawNode.childNodes.length; ++i){
						if(this.rawNode.childNodes[i].tagName == "skew"){
							skew = this.rawNode.childNodes[i];
							break;
						}
					}
				}
				if(skew){
					skew.on = "f";
					var mt = matrix.xx.toFixed(8) + " " + matrix.xy.toFixed(8) + " " +
						matrix.yx.toFixed(8) + " " + matrix.yy.toFixed(8) + " 0 0",
						offset = Math.floor(matrix.dx).toFixed() + "px " + Math.floor(matrix.dy).toFixed() + "px",
						s = this.rawNode.style,
						l = parseFloat(s.left),
						t = parseFloat(s.top),
						w = parseFloat(s.width),
						h = parseFloat(s.height);
					if(isNaN(l)) l = 0;
					if(isNaN(t)) t = 0;
					if(isNaN(w) || !w) w = 1;
					if(isNaN(h) || !h) h = 1;
					var origin = (-l / w - 0.5).toFixed(8) + " " + (-t / h - 0.5).toFixed(8);
					skew.matrix =  mt;
					skew.origin = origin;
					skew.offset = offset;
					skew.on = true;
				}
			}
			if(this.fillStyle && this.fillStyle.type == "linear"){
				this.setFill(this.fillStyle);
			}
			return this;
		},

		_setDimensions: function(width, height){
			// summary: sets the width and height of the rawNode,
			//	if the surface sixe has been changed
			// width: String: width in pixels
			// height: String: height in pixels

			// default implementation does nothing
			return this; // self
		},

		setRawNode: function(rawNode){
			// summary:
			//	assigns and clears the underlying node that will represent this
			//	shape. Once set, transforms, gradients, etc, can be applied.
			//	(no fill & stroke by default)
			rawNode.stroked = "f";
			rawNode.filled  = "f";
			this.rawNode = rawNode;
		},

		// move family

		_moveToFront: function(){
			// summary: moves a shape to front of its parent's list of shapes (VML)
			this.rawNode.parentNode.appendChild(this.rawNode);
			return this;
		},
		_moveToBack: function(){
			// summary: moves a shape to back of its parent's list of shapes (VML)
			var r = this.rawNode, p = r.parentNode, n = p.firstChild;
			p.insertBefore(r, n);
			if(n.tagName == "rect"){
				// surface has a background rectangle, which position should be preserved
				n.swapNode(r);
			}
			return this;
		},

		_getRealMatrix: function(){
			// summary: returns the cumulative ("real") transformation matrix
			//	by combining the shape's matrix with its parent's matrix
			return this.parentMatrix ? new g.Matrix2D([this.parentMatrix, this.matrix]) : this.matrix;	// dojox.gfx.Matrix2D
		}
	});

	dojo.declare("dojox.gfx.vml.Group", vml.Shape, {
		// summary: a group shape (VML), which can be used
		//	to logically group shapes (e.g, to propagate matricies)
		constructor: function(){
			gs.Container._init.call(this);
		},
		// apply transformation
		_applyTransform: function(){
			// summary: applies a transformation matrix to a group
			var matrix = this._getRealMatrix();
			for(var i = 0; i < this.children.length; ++i){
				this.children[i]._updateParentMatrix(matrix);
			}
			return this;	// self
		},
		_setDimensions: function(width, height){
			// summary: sets the width and height of the rawNode,
			//	if the surface sixe has been changed
			// width: String: width in pixels
			// height: String: height in pixels
			var r = this.rawNode, rs = r.style,
				bs = this.bgNode.style;
			rs.width = width;
			rs.height = height;
			r.coordsize = width + " " + height;
			bs.width = width;
			bs.height = height;
			for(var i = 0; i < this.children.length; ++i){
				this.children[i]._setDimensions(width, height);
			}
			return this; // self
		}
	});
	vml.Group.nodeType = "group";

	dojo.declare("dojox.gfx.vml.Rect", [vml.Shape, gs.Rect], {
		// summary: a rectangle shape (VML)
		setShape: function(newShape){
			// summary: sets a rectangle shape object (VML)
			// newShape: Object: a rectangle shape object
			var shape = this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			var r = Math.min(1, (shape.r / Math.min(parseFloat(shape.width), parseFloat(shape.height)))).toFixed(8);
			// a workaround for the VML's arcsize bug: cannot read arcsize of an instantiated node
			var parent = this.rawNode.parentNode, before = null;
			if(parent){
				if(parent.lastChild !== this.rawNode){
					for(var i = 0; i < parent.childNodes.length; ++i){
						if(parent.childNodes[i] === this.rawNode){
							before = parent.childNodes[i + 1];
							break;
						}
					}
				}
				parent.removeChild(this.rawNode);
			}
			if(d.isIE > 7){
				var node = this.rawNode.ownerDocument.createElement("v:roundrect");
				node.arcsize = r;
				node.style.display = "inline-block";
				this.rawNode = node;
			}else{
				this.rawNode.arcsize = r;
			}
			if(parent){
				if(before){
					parent.insertBefore(this.rawNode, before);
				}else{
					parent.appendChild(this.rawNode);
				}
			}
			var style = this.rawNode.style;
			style.left   = shape.x.toFixed();
			style.top    = shape.y.toFixed();
			style.width  = (typeof shape.width == "string" && shape.width.indexOf("%") >= 0)  ? shape.width  : shape.width.toFixed();
			style.height = (typeof shape.height == "string" && shape.height.indexOf("%") >= 0) ? shape.height : shape.height.toFixed();
			// set all necessary styles, which are lost by VML (yes, it's a VML's bug)
			return this.setTransform(this.matrix).setFill(this.fillStyle).setStroke(this.strokeStyle);	// self
		}
	});
	vml.Rect.nodeType = "roundrect"; // use a roundrect so the stroke join type is respected

	dojo.declare("dojox.gfx.vml.Ellipse", [vml.Shape, gs.Ellipse], {
		// summary: an ellipse shape (VML)
		setShape: function(newShape){
			// summary: sets an ellipse shape object (VML)
			// newShape: Object: an ellipse shape object
			var shape = this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			var style = this.rawNode.style;
			style.left   = (shape.cx - shape.rx).toFixed();
			style.top    = (shape.cy - shape.ry).toFixed();
			style.width  = (shape.rx * 2).toFixed();
			style.height = (shape.ry * 2).toFixed();
			return this.setTransform(this.matrix);	// self
		}
	});
	vml.Ellipse.nodeType = "oval";

	dojo.declare("dojox.gfx.vml.Circle", [vml.Shape, gs.Circle], {
		// summary: a circle shape (VML)
		setShape: function(newShape){
			// summary: sets a circle shape object (VML)
			// newShape: Object: a circle shape object
			var shape = this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			var style = this.rawNode.style;
			style.left   = (shape.cx - shape.r).toFixed();
			style.top    = (shape.cy - shape.r).toFixed();
			style.width  = (shape.r * 2).toFixed();
			style.height = (shape.r * 2).toFixed();
			return this;	// self
		}
	});
	vml.Circle.nodeType = "oval";

	dojo.declare("dojox.gfx.vml.Line", [vml.Shape, gs.Line], {
		// summary: a line shape (VML)
		constructor: function(rawNode){
			if(rawNode) rawNode.setAttribute("dojoGfxType", "line");
		},
		setShape: function(newShape){
			// summary: sets a line shape object (VML)
			// newShape: Object: a line shape object
			var shape = this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			this.rawNode.path.v = "m" + shape.x1.toFixed() + " " + shape.y1.toFixed() +
				"l" + shape.x2.toFixed() + " " + shape.y2.toFixed() + "e";
			return this.setTransform(this.matrix);	// self
		}
	});
	vml.Line.nodeType = "shape";

	dojo.declare("dojox.gfx.vml.Polyline", [vml.Shape, gs.Polyline], {
		// summary: a polyline/polygon shape (VML)
		constructor: function(rawNode){
			if(rawNode) rawNode.setAttribute("dojoGfxType", "polyline");
		},
		setShape: function(points, closed){
			// summary: sets a polyline/polygon shape object (VML)
			// points: Object: a polyline/polygon shape object
			// closed: Boolean?: if true, close the polyline explicitely
			if(points && points instanceof Array){
				// branch
				// points: Array: an array of points
				this.shape = g.makeParameters(this.shape, { points: points });
				if(closed && this.shape.points.length) this.shape.points.push(this.shape.points[0]);
			}else{
				this.shape = g.makeParameters(this.shape, points);
			}
			this.bbox = null;
			this._normalizePoints();
			var attr = [], p = this.shape.points;
			if(p.length > 0){
				attr.push("m");
				attr.push(p[0].x.toFixed(), p[0].y.toFixed());
				if(p.length > 1){
					attr.push("l");
					for(var i = 1; i < p.length; ++i){
						attr.push(p[i].x.toFixed(), p[i].y.toFixed());
					}
				}
			}
			attr.push("e");
			this.rawNode.path.v = attr.join(" ");
			return this.setTransform(this.matrix);	// self
		}
	});
	vml.Polyline.nodeType = "shape";

	dojo.declare("dojox.gfx.vml.Image", [vml.Shape, gs.Image], {
		// summary: an image (VML)
		setShape: function(newShape){
			// summary: sets an image shape object (VML)
			// newShape: Object: an image shape object
			var shape = this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			this.rawNode.firstChild.src = shape.src;
			return this.setTransform(this.matrix);	// self
		},
		_applyTransform: function() {
			var matrix = this._getRealMatrix(),
				rawNode = this.rawNode,
				s = rawNode.style,
				shape = this.shape;
			if(matrix){
				matrix = m.multiply(matrix, {dx: shape.x, dy: shape.y});
			}else{
				matrix = m.normalize({dx: shape.x, dy: shape.y});
			}
			if(matrix.xy == 0 && matrix.yx == 0 && matrix.xx > 0 && matrix.yy > 0){
				// special case to avoid filters
				s.filter = "";
				s.width  = Math.floor(matrix.xx * shape.width);
				s.height = Math.floor(matrix.yy * shape.height);
				s.left   = Math.floor(matrix.dx);
				s.top    = Math.floor(matrix.dy);
			}else{
				var ps = rawNode.parentNode.style;
				s.left   = "0px";
				s.top    = "0px";
				s.width  = ps.width;
				s.height = ps.height;
				matrix = m.multiply(matrix,
					{xx: shape.width / parseInt(s.width), yy: shape.height / parseInt(s.height)});
				var f = rawNode.filters["DXImageTransform.Microsoft.Matrix"];
				if(f){
					f.M11 = matrix.xx;
					f.M12 = matrix.xy;
					f.M21 = matrix.yx;
					f.M22 = matrix.yy;
					f.Dx = matrix.dx;
					f.Dy = matrix.dy;
				}else{
					s.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + matrix.xx +
						", M12=" + matrix.xy + ", M21=" + matrix.yx + ", M22=" + matrix.yy +
						", Dx=" + matrix.dx + ", Dy=" + matrix.dy + ")";
				}
			}
			return this; // self
		},
		_setDimensions: function(width, height){
			// summary: sets the width and height of the rawNode,
			//	if the surface sixe has been changed
			// width: String: width in pixels
			// height: String: height in pixels

			var r = this.rawNode, f = r.filters["DXImageTransform.Microsoft.Matrix"];
			if(f){
				var s = r.style;
				s.width  = width;
				s.height = height;
				return this._applyTransform(); // self
			}
			return this;	// self
		}
	});
	vml.Image.nodeType = "rect";

	dojo.declare("dojox.gfx.vml.Text", [vml.Shape, gs.Text], {
		// summary: an anchored text (VML)
		constructor: function(rawNode){
			if(rawNode){rawNode.setAttribute("dojoGfxType", "text");}
			this.fontStyle = null;
		},
		_alignment: {start: "left", middle: "center", end: "right"},
		setShape: function(newShape){
			// summary: sets a text shape object (VML)
			// newShape: Object: a text shape object
			this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			var r = this.rawNode, s = this.shape, x = s.x, y = s.y.toFixed(), path;
			switch(s.align){
				case "middle":
					x -= 5;
					break;
				case "end":
					x -= 10;
					break;
			}
			path = "m" + x.toFixed() + "," + y + "l" + (x + 10).toFixed() + "," + y + "e";
			// find path and text path
			var p = null, t = null, c = r.childNodes;
			for(var i = 0; i < c.length; ++i){
				var tag = c[i].tagName;
				if(tag == "path"){
					p = c[i];
					if(t) break;
				}else if(tag == "textpath"){
					t = c[i];
					if(p) break;
				}
			}
			if(!p){
				p = r.ownerDocument.createElement("v:path");
				r.appendChild(p);
			}
			if(!t){
				t = r.ownerDocument.createElement("v:textpath");
				r.appendChild(t);
			}
			p.v = path;
			p.textPathOk = true;
			t.on = true;
			var a = vml.text_alignment[s.align];
			t.style["v-text-align"] = a ? a : "left";
			t.style["text-decoration"] = s.decoration;
			t.style["v-rotate-letters"] = s.rotated;
			t.style["v-text-kern"] = s.kerning;
			t.string = s.text;
			return this.setTransform(this.matrix);	// self
		},
		_setFont: function(){
			// summary: sets a font object (VML)
			var f = this.fontStyle, c = this.rawNode.childNodes;
			for(var i = 0; i < c.length; ++i){
				if(c[i].tagName == "textpath"){
					c[i].style.font = g.makeFontString(f);
					break;
				}
			}
			this.setTransform(this.matrix);
		},
		_getRealMatrix: function(){
			// summary: returns the cumulative ("real") transformation matrix
			//	by combining the shape's matrix with its parent's matrix;
			//	it makes a correction for a font size
			var matrix = this.inherited(arguments);
			// It appears that text is always aligned vertically at a middle of x-height (???).
			// It is impossible to obtain these metrics from VML => I try to approximate it with
			// more-or-less util value of 0.7 * FontSize, which is typical for European fonts.
			if(matrix){
				matrix = m.multiply(matrix,
					{dy: -g.normalizedLength(this.fontStyle ? this.fontStyle.size : "10pt") * 0.35});
			}
			return matrix;	// dojox.gfx.Matrix2D
		},
		getTextWidth: function(){
			// summary: get the text width, in px
			var rawNode = this.rawNode, _display = rawNode.style.display;
			rawNode.style.display = "inline";
			var _width = g.pt2px(parseFloat(rawNode.currentStyle.width));
			rawNode.style.display = _display;
			return _width;
		}
	});
	vml.Text.nodeType = "shape";

	dojo.declare("dojox.gfx.vml.Path", [vml.Shape, g.path.Path], {
		// summary: a path shape (VML)
		constructor: function(rawNode){
			if(rawNode && !rawNode.getAttribute("dojoGfxType")){
				rawNode.setAttribute("dojoGfxType", "path");
			}
			this.vmlPath = "";
			this.lastControl = {};
		},
		_updateWithSegment: function(segment){
			// summary: updates the bounding box of path with new segment
			// segment: Object: a segment
			var last = d.clone(this.last);
			this.inherited(arguments);
			if(arguments.length > 1){ return; } // skip transfomed bbox calculations
			// add a VML path segment
			var path = this[this.renderers[segment.action]](segment, last);
			if(typeof this.vmlPath == "string"){
				this.vmlPath += path.join("");
				this.rawNode.path.v = this.vmlPath + " r0,0 e";
			}else{
				Array.prototype.push.apply(this.vmlPath, path); //FIXME: why not push()?
			}
		},
		setShape: function(newShape){
			// summary: forms a path using a shape (VML)
			// newShape: Object: an VML path string or a path object (see dojox.gfx.defaultPath)
			this.vmlPath = [];
			this.lastControl.type = "";	// no prior control point
			this.inherited(arguments);
			this.vmlPath = this.vmlPath.join("");
			this.rawNode.path.v = this.vmlPath + " r0,0 e";
			return this;
		},
		_pathVmlToSvgMap: {m: "M", l: "L", t: "m", r: "l", c: "C", v: "c", qb: "Q", x: "z", e: ""},
		// VML-specific segment renderers
		renderers: {
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
		},
		_addArgs: function(path, segment, from, upto){
			var n = segment instanceof Array ? segment : segment.args;
			for(var i = from; i < upto; ++i){
				path.push(" ", n[i].toFixed());
			}
		},
		_adjustRelCrd: function(last, segment, step){
			var n = segment instanceof Array ? segment : segment.args, l = n.length,
				result = new Array(l), i = 0, x = last.x, y = last.y;
			if(typeof x != "number"){
				// there is no last coordinate =>
				// treat the first pair as an absolute coordinate
				result[0] = x = n[0];
				result[1] = y = n[1];
				i = 2;
			}
			if(typeof step == "number" && step != 2){
				var j = step;
				while(j <= l){
					for(; i < j; i += 2){
						result[i] = x + n[i];
						result[i + 1] = y + n[i + 1];
					}
					x = result[j - 2];
					y = result[j - 1];
					j += step;
				}
			}else{
				for(; i < l; i += 2){
					result[i] = (x += n[i]);
					result[i + 1] = (y += n[i + 1]);
				}
			}
			return result;
		},
		_adjustRelPos: function(last, segment){
			var n = segment instanceof Array ? segment : segment.args, l = n.length,
				result = new Array(l);
			for(var i = 0; i < l; ++i){
				result[i] = (last += n[i]);
			}
			return result;
		},
		_moveToA: function(segment){
			var p = [" m"], n = segment instanceof Array ? segment : segment.args, l = n.length;
			this._addArgs(p, n, 0, 2);
			if(l > 2){
				p.push(" l");
				this._addArgs(p, n, 2, l);
			}
			this.lastControl.type = "";	// no control point after this primitive
			return p;
		},
		_moveToR: function(segment, last){
			return this._moveToA(this._adjustRelCrd(last, segment));
		},
		_lineToA: function(segment){
			var p = [" l"], n = segment instanceof Array ? segment : segment.args;
			this._addArgs(p, n, 0, n.length);
			this.lastControl.type = "";	// no control point after this primitive
			return p;
		},
		_lineToR: function(segment, last){
			return this._lineToA(this._adjustRelCrd(last, segment));
		},
		_hLineToA: function(segment, last){
			var p = [" l"], y = " " + last.y.toFixed(),
				n = segment instanceof Array ? segment : segment.args, l = n.length;
			for(var i = 0; i < l; ++i){
				p.push(" ", n[i].toFixed(), y);
			}
			this.lastControl.type = "";	// no control point after this primitive
			return p;
		},
		_hLineToR: function(segment, last){
			return this._hLineToA(this._adjustRelPos(last.x, segment), last);
		},
		_vLineToA: function(segment, last){
			var p = [" l"], x = " " + last.x.toFixed(),
				n = segment instanceof Array ? segment : segment.args, l = n.length;
			for(var i = 0; i < l; ++i){
				p.push(x, " ", n[i].toFixed());
			}
			this.lastControl.type = "";	// no control point after this primitive
			return p;
		},
		_vLineToR: function(segment, last){
			return this._vLineToA(this._adjustRelPos(last.y, segment), last);
		},
		_curveToA: function(segment){
			var p = [], n = segment instanceof Array ? segment : segment.args, l = n.length,
				lc = this.lastControl;
			for(var i = 0; i < l; i += 6){
				p.push(" c");
				this._addArgs(p, n, i, i + 6);
			}
			lc.x = n[l - 4];
			lc.y = n[l - 3];
			lc.type = "C";
			return p;
		},
		_curveToR: function(segment, last){
			return this._curveToA(this._adjustRelCrd(last, segment, 6));
		},
		_smoothCurveToA: function(segment, last){
			var p = [], n = segment instanceof Array ? segment : segment.args, l = n.length,
				lc = this.lastControl, i = 0;
			if(lc.type != "C"){
				p.push(" c");
				this._addArgs(p, [last.x, last.y], 0, 2);
				this._addArgs(p, n, 0, 4);
				lc.x = n[0];
				lc.y = n[1];
				lc.type = "C";
				i = 4;
			}
			for(; i < l; i += 4){
				p.push(" c");
				this._addArgs(p, [
					2 * last.x - lc.x,
					2 * last.y - lc.y
				], 0, 2);
				this._addArgs(p, n, i, i + 4);
				lc.x = n[i];
				lc.y = n[i + 1];
			}
			return p;
		},
		_smoothCurveToR: function(segment, last){
			return this._smoothCurveToA(this._adjustRelCrd(last, segment, 4), last);
		},
		_qCurveToA: function(segment){
			var p = [], n = segment instanceof Array ? segment : segment.args, l = n.length,
				lc = this.lastControl;
			for(var i = 0; i < l; i += 4){
				p.push(" qb");
				this._addArgs(p, n, i, i + 4);
			}
			lc.x = n[l - 4];
			lc.y = n[l - 3];
			lc.type = "Q";
			return p;
		},
		_qCurveToR: function(segment, last){
			return this._qCurveToA(this._adjustRelCrd(last, segment, 4));
		},
		_qSmoothCurveToA: function(segment, last){
			var p = [], n = segment instanceof Array ? segment : segment.args, l = n.length,
				lc = this.lastControl, i = 0;
			if(lc.type != "Q"){
				p.push(" qb");
				this._addArgs(p, [
					lc.x = last.x,
					lc.y = last.y
				], 0, 2);
				lc.type = "Q";
				this._addArgs(p, n, 0, 2);
				i = 2;
			}
			for(; i < l; i += 2){
				p.push(" qb");
				this._addArgs(p, [
					lc.x = 2 * last.x - lc.x,
					lc.y = 2 * last.y - lc.y
				], 0, 2);
				this._addArgs(p, n, i, i + 2);
			}
			return p;
		},
		_qSmoothCurveToR: function(segment, last){
			return this._qSmoothCurveToA(this._adjustRelCrd(last, segment, 2), last);
		},
		_arcTo: function(segment, last){
			var p = [], n = segment.args, l = n.length, relative = segment.action == "a";
			for(var i = 0; i < l; i += 7){
				var x1 = n[i + 5], y1 = n[i + 6];
				if(relative){
					x1 += last.x;
					y1 += last.y;
				}
				var result = g.arc.arcAsBezier(
					last, n[i], n[i + 1], n[i + 2],
					n[i + 3] ? 1 : 0, n[i + 4] ? 1 : 0,
					x1, y1
				);
				for(var j = 0; j < result.length; ++j){
					p.push(" c");
					var t = result[j];
					this._addArgs(p, t, 0, t.length);
					this._updateBBox(t[0], t[1]);
					this._updateBBox(t[2], t[3]);
					this._updateBBox(t[4], t[5]);
				}
				last.x = x1;
				last.y = y1;
			}
			this.lastControl.type = "";	// no control point after this primitive
			return p;
		},
		_closePath: function(){
			this.lastControl.type = "";	// no control point after this primitive
			return ["x"];
		}
	});
	vml.Path.nodeType = "shape";

	dojo.declare("dojox.gfx.vml.TextPath", [vml.Path, g.path.TextPath], {
		// summary: a textpath shape (VML)
		constructor: function(rawNode){
			if(rawNode){rawNode.setAttribute("dojoGfxType", "textpath");}
			this.fontStyle = null;
			if(!("text" in this)){
				this.text = d.clone(g.defaultTextPath);
			}
			if(!("fontStyle" in this)){
				this.fontStyle = d.clone(g.defaultFont);
			}
		},
		setText: function(newText){
			// summary: sets a text to be drawn along the path
			this.text = g.makeParameters(this.text,
				typeof newText == "string" ? {text: newText} : newText);
			this._setText();
			return this;	// self
		},
		setFont: function(newFont){
			// summary: sets a font for text
			this.fontStyle = typeof newFont == "string" ?
				g.splitFontString(newFont) :
				g.makeParameters(g.defaultFont, newFont);
			this._setFont();
			return this;	// self
		},

		_setText: function(){
			// summary: sets a text shape object (VML)
			this.bbox = null;
			var r = this.rawNode, s = this.text,
				// find path and text path
				p = null, t = null, c = r.childNodes;
			for(var i = 0; i < c.length; ++i){
				var tag = c[i].tagName;
				if(tag == "path"){
					p = c[i];
					if(t) break;
				}else if(tag == "textpath"){
					t = c[i];
					if(p) break;
				}
			}
			if(!p){
				p = this.rawNode.ownerDocument.createElement("v:path");
				r.appendChild(p);
			}
			if(!t){
				t = this.rawNode.ownerDocument.createElement("v:textpath");
				r.appendChild(t);
			}
			p.textPathOk = true;
			t.on = true;
			var a = vml.text_alignment[s.align];
			t.style["v-text-align"] = a ? a : "left";
			t.style["text-decoration"] = s.decoration;
			t.style["v-rotate-letters"] = s.rotated;
			t.style["v-text-kern"] = s.kerning;
			t.string = s.text;
		},
		_setFont: function(){
			// summary: sets a font object (VML)
			var f = this.fontStyle, c = this.rawNode.childNodes;
			for(var i = 0; i < c.length; ++i){
				if(c[i].tagName == "textpath"){
					c[i].style.font = g.makeFontString(f);
					break;
				}
			}
		}
	});
	vml.TextPath.nodeType = "shape";

	dojo.declare("dojox.gfx.vml.Surface", gs.Surface, {
		// summary: a surface object to be used for drawings (VML)
		constructor: function(){
			gs.Container._init.call(this);
		},
		setDimensions: function(width, height){
			// summary: sets the width and height of the rawNode
			// width: String: width of surface, e.g., "100px"
			// height: String: height of surface, e.g., "100px"
			this.width  = g.normalizedLength(width);	// in pixels
			this.height = g.normalizedLength(height);	// in pixels
			if(!this.rawNode) return this;
			var cs = this.clipNode.style,
				r = this.rawNode, rs = r.style,
				bs = this.bgNode.style,
				ps = this._parent.style, i;
			ps.width = width;
			ps.height = height;
			cs.width  = width;
			cs.height = height;
			cs.clip = "rect(0px " + width + "px " + height + "px 0px)";
			rs.width = width;
			rs.height = height;
			r.coordsize = width + " " + height;
			bs.width = width;
			bs.height = height;
			for(i = 0; i < this.children.length; ++i){
				this.children[i]._setDimensions(width, height);
			}
			return this;	// self
		},
		getDimensions: function(){
			// summary: returns an object with properties "width" and "height"
			var t = this.rawNode ? {
				width:  g.normalizedLength(this.rawNode.style.width),
				height: g.normalizedLength(this.rawNode.style.height)} : null;
			if(t.width  <= 0){ t.width  = this.width; }
			if(t.height <= 0){ t.height = this.height; }
			return t;	// Object
		}
	});

	vml.createSurface = function(parentNode, width, height){
		// summary: creates a surface (VML)
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

		var s = new vml.Surface(), p = d.byId(parentNode),
			c = s.clipNode = p.ownerDocument.createElement("div"),
			r = s.rawNode = p.ownerDocument.createElement("v:group"),
			cs = c.style, rs = r.style;

		if(d.isIE > 7){
			rs.display = "inline-block";
		}

		s._parent = p;
		s._nodes.push(c);	// other elements will be deleted as parts of "c"

		p.style.width  = width;
		p.style.height = height;

		cs.position = "absolute";
		cs.width  = width;
		cs.height = height;
		cs.clip = "rect(0px " + width + " " + height + " 0px)";
		rs.position = "absolute";
		rs.width  = width;
		rs.height = height;
		r.coordsize = (width === "100%" ? width : parseFloat(width)) + " " +
			(height === "100%" ? height : parseFloat(height));
		r.coordorigin = "0 0";

		// create a background rectangle, which is required to show all other shapes
		var b = s.bgNode = r.ownerDocument.createElement("v:rect"), bs = b.style;
		bs.left = bs.top = 0;
		bs.width  = rs.width;
		bs.height = rs.height;
		b.filled = b.stroked = "f";

		r.appendChild(b);
		c.appendChild(r);
		p.appendChild(c);

		s.width  = g.normalizedLength(width);	// in pixels
		s.height = g.normalizedLength(height);	// in pixels

		return s;	// dojox.gfx.Surface
	};

	// Extenders
	
	// copied from dojox.gfx.utils
	function forEach(object, f, o){
		o = o || d.global;
		f.call(o, object);
		if(object instanceof g.Surface || object instanceof g.Group){
			d.forEach(object.children, function(shape){
				forEach(shape, f, o);
			});
		}
	}

	var C = gs.Container, Container = {
		add: function(shape){
			// summary: adds a shape to a group/surface
			// shape: dojox.gfx.Shape: an VML shape object
			if(this != shape.getParent()){
				// cleanup from old parent
				var oldParent = shape.getParent();
				if(oldParent) { oldParent.remove(shape); }
				// then move the raw node
				this.rawNode.appendChild(shape.rawNode);
        
        // https://support.sitepen.com/issues/21093
        if(!oldParent){
          // reapply visual attributes
          shape.setFill(shape.getFill());
          shape.setStroke(shape.getStroke());
        }        
        
				C.add.apply(this, arguments);
        
        // https://support.sitepen.com/issues/21093
        /*
				// reapply visual attributes (slow..)
				forEach(this, function(s){
					if (typeof(s.getFont) == 'function'){ // text shapes need to be completely refreshed
						s.setShape(s.getShape());
						s.setFont(s.getFont());
					}
					if (typeof(s.setFill) == 'function'){ // if setFill is available a setStroke should be safe to assume also
						s.setFill(s.getFill());
						s.setStroke(s.getStroke());
					}
				});
				*/
			}
			return this;	// self
		},
		remove: function(shape, silently){
			// summary: remove a shape from a group/surface
			// shape: dojox.gfx.Shape: an VML shape object
			// silently: Boolean?: if true, regenerate a picture
			if(this == shape.getParent()){
				if(this.rawNode == shape.rawNode.parentNode){
					this.rawNode.removeChild(shape.rawNode);
				}
				C.remove.apply(this, arguments);
			}
			return this;	// self
		},
		clear: function(){
			// summary: removes all shapes from a group/surface
			var r = this.rawNode;
			while(r.firstChild != r.lastChild){
				if(r.firstChild != this.bgNode){
					r.removeChild(r.firstChild);
				}
				if(r.lastChild != this.bgNode){
					r.removeChild(r.lastChild);
				}
			}
			return C.clear.apply(this, arguments);
		},
		_moveChildToFront: C._moveChildToFront,
		_moveChildToBack:  C._moveChildToBack
	};

	var Creator = {
		// summary: VML shape creators
		createGroup: function(){
			// summary: creates a VML group shape
			var node = this.createObject(vml.Group, null);	// dojox.gfx.Group
			// create a background rectangle, which is required to show all other shapes
			var r = node.rawNode.ownerDocument.createElement("v:rect");
			r.style.left = r.style.top = 0;
			r.style.width  = node.rawNode.style.width;
			r.style.height = node.rawNode.style.height;
			r.filled = r.stroked = "f";
			node.rawNode.appendChild(r);
			node.bgNode = r;
			return node;	// dojox.gfx.Group
		},
		createImage: function(image){
			// summary: creates a VML image shape
			// image: Object: an image object (see dojox.gfx.defaultImage)
			if(!this.rawNode) return null;
			var shape = new vml.Image(),
				doc = this.rawNode.ownerDocument,
				node = doc.createElement('v:rect');
			node.stroked = "f";
			node.style.width  = this.rawNode.style.width;
			node.style.height = this.rawNode.style.height;
			var img  = doc.createElement('v:imagedata');
			node.appendChild(img);
			shape.setRawNode(node);
			this.rawNode.appendChild(node);
			shape.setShape(image);
			this.add(shape);
			return shape;	// dojox.gfx.Image
		},
		createRect: function(rect){
			// summary: creates a rectangle shape
			// rect: Object: a path object (see dojox.gfx.defaultRect)
			if(!this.rawNode) return null;
			var shape = new vml.Rect,
				node = this.rawNode.ownerDocument.createElement("v:roundrect");
			if(d.isIE > 7){
				node.style.display = "inline-block";
			}
			shape.setRawNode(node);
			this.rawNode.appendChild(node);
			shape.setShape(rect);
			this.add(shape);
			return shape;	// dojox.gfx.Rect
		},
		createObject: function(shapeType, rawShape) {
			// summary: creates an instance of the passed shapeType class
			// shapeType: Function: a class constructor to create an instance of
			// rawShape: Object: properties to be passed in to the classes "setShape" method
			// overrideSize: Boolean: set the size explicitly, if true
			if(!this.rawNode) return null;
			var shape = new shapeType(),
				node = this.rawNode.ownerDocument.createElement('v:' + shapeType.nodeType);
			shape.setRawNode(node);
			this.rawNode.appendChild(node);
			switch(shapeType){
				case vml.Group:
				case vml.Line:
				case vml.Polyline:
				case vml.Image:
				case vml.Text:
				case vml.Path:
				case vml.TextPath:
					this._overrideSize(node);
			}
			shape.setShape(rawShape);
			this.add(shape);
			return shape;	// dojox.gfx.Shape
		},
		_overrideSize: function(node){
			var s = this.rawNode.style, w = s.width, h = s.height;
			node.style.width  = w;
			node.style.height = h;
			node.coordsize = parseInt(w) + " " + parseInt(h);
		}
	};

	d.extend(vml.Group, Container);
	d.extend(vml.Group, gs.Creator);
	d.extend(vml.Group, Creator);

	d.extend(vml.Surface, Container);
	d.extend(vml.Surface, gs.Creator);
	d.extend(vml.Surface, Creator);

	// see if we are required to initilize
	if(g.loadAndSwitch === "vml"){
		g.switchTo("vml");
		delete g.loadAndSwitch;
	}
})();

}

