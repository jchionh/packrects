// create namespace
xlb1.render = xlb1.render || {};

// renderer 2D to take care of all the rendering

//---------------------------------------------------------------------------------------
//Renderer2D ctor
// canvasElement : the canvas element to render onto
xlb1.render.Renderer2D = function(canvasElement, dpi) {
	this.mDpi = dpi || 1;
	this.mCanvasElement = canvasElement;
	this.mRenderCtx = canvasElement.getContext('2d');
	this.mWidth = canvasElement.width;
	this.mHeight = canvasElement.height;
	this.mRenderCtx.font = (16 * this.mDpi) + ' sans-serif';
};

//---------------------------------------------------------------------------------------
//Renderer2D DrawText
xlb1.render.Renderer2D.prototype.DrawText = function(text, x, y, color, font) {
	var fontSize = (16 * this.mDpi) + 'px';
	font = font || (fontSize + ' sans-serif');
	color = color || '#000000';
	
	this.mRenderCtx.save();
	this.mRenderCtx.fillStyle = color;
	this.mRenderCtx.font = font;
	this.mRenderCtx.fillText(text, x, y);
	this.mRenderCtx.restore();
};

//---------------------------------------------------------------------------------------
//Renderer2D ClearCanvas
xlb1.render.Renderer2D.prototype.ClearCanvas = function() {
	// Store the current transformation matrix
	this.mRenderCtx.save();

	// Use the identity matrix while clearing the canvas
	this.mRenderCtx.setTransform(1, 0, 0, 1, 0, 0);
	this.mRenderCtx.clearRect(0, 0, this.mCanvasElement.width, this.mCanvasElement.height);

	// Restore the transform
	this.mRenderCtx.restore();
};

//---------------------------------------------------------------------------------------
//Renderer2D FillRect
xlb1.render.Renderer2D.prototype.FillRect = function(minX, minY, maxX, maxY, color) {
	color = color || '#000000';
	
	this.mRenderCtx.save();
	this.mRenderCtx.fillStyle = color;
	this.mRenderCtx.fillRect(minX, minY, maxX, maxY);
	this.mRenderCtx.restore();
};

//---------------------------------------------------------------------------------------
//Renderer2D DrawShape
// 2D point array is an array of {x, y} which starts with a moveTo then a lineTo till the end
xlb1.render.Renderer2D.prototype.DrawShape = function(point2DArray, color, thickness, fill) {
	color = color || null;
	thickness = thickness || 1;
	thickness *= this.mDpi;
	fill = fill || null;
	
	var pointCount = point2DArray.length;
	if (pointCount < 2) {
		return;
	}
	
	this.mRenderCtx.save();
	
	this.mRenderCtx.lineWidth = thickness;
	this.mRenderCtx.strokeStyle = color;
	
	this.mRenderCtx.beginPath();
	// moveTo the first point
	this.mRenderCtx.moveTo(point2DArray[0].x * this.mDpi, point2DArray[0].y * this.mDpi);
	for (var i = 1; i < pointCount; ++i) {
		var point = point2DArray[i];
		this.mRenderCtx.lineTo(point.x * this.mDpi, point.y * this.mDpi);
	}
	// last, close the path
	this.mRenderCtx.closePath();
	
	// check for fill
	if (fill != null) {
		this.mRenderCtx.fillStyle = fill;
		this.mRenderCtx.fill();
	}
	
	this.mRenderCtx.stroke();
	this.mRenderCtx.restore();
};

//---------------------------------------------------------------------------------------
//Renderer2D DrawImage
xlb1.render.Renderer2D.prototype.DrawImage = function(image, x, y) {
	this.mRenderCtx.drawImage(image, x, y);
};