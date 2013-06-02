xlb1.packer = xlb1.packer || {};

//---------------------------------------------------------------------------------------
//BasicRect ctor
xlb1.packer.colors = [
               '#DEADFA',
               '#C0C0C0',
               '#808080',
               '#BADF00',
               '#BF0000',
               '#800000',
               '#BFBF00',
               '#808000',
               '#00FF00',
               '#008000',
               '#00BFBF',
               '#008080',
               '#0000BF',
               '#000080',
               '#BF00BF',
               '#800080'
               ];

//---------------------------------------------------------------------------------------
//BasicRect ctor
xlb1.packer.BasicRect = function(x, y, w, h, id, color) {
	
	// default values
	x = x || 0;
	y = y || 0;
	w = w || 0;
	h = h || 0;
	id = id || -1;
	
	// if no color is passed in, randomize the colors
	color = color || xlb1.packer.colors[Math.floor(Math.random() * xlb1.packer.colors.length)];
	
	// setup the members
	this.id = id;
	this.x = x;
	this.y = y;
	this.w = w;
	this.h = h;
	this.color = color;
};

//---------------------------------------------------------------------------------------
//BasicRect GetArea
xlb1.packer.BasicRect.prototype.GetArea = function() {
	return this.w * this.h;
};

//---------------------------------------------------------------------------------------
//BasicRect GetRenderPointArray
xlb1.packer.BasicRect.prototype.GetRenderPointArray = function(scale, tx, ty) {
	
	scale = scale || 1;
	tx = tx || 0;
	ty = ty || 0;
	
	var sWidth = scale * this.w;
	var sHeight = scale * this.h;
	
	var pointArray = [];
	
	pointArray.push({x: tx + (scale * this.x), y: ty + (scale *this.y)});
	pointArray.push({x: tx + (scale * (this.x + this.w)), y: ty + (scale * this.y)});
	pointArray.push({x: tx + (scale * (this.x + this.w)), y: ty + (scale * (this.y + this.h))});
	pointArray.push({x: tx + (scale * this.x), y: ty + (scale * (this.y + this.h))});
	
	return pointArray;
};

//---------------------------------------------------------------------------------------
//BasicRect GetMidPoint
xlb1.packer.BasicRect.prototype.GetMidPoint = function() {
	return { x: Math.floor(this.x + (this.w / 2)), y: Math.floor(this.y + (this.h / 2)) };
};


