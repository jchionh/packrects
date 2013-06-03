function mainInit() {
	console.log('main init called.');
	
	xlb1.gCanvasElement = document.getElementById('renderCanvas');
    xlb1.gCanvasContext = xlb1.gCanvasElement.getContext('2d');
    
    xlb1.gCanvasAreaElement = document.getElementById('renderArea');
    xlb1.gHudElement = document.getElementById('hudBeforeCanvas');
    
    // get refernce to the title element, just to update fps etc
    xlb1.gTitleElement = document.getElementsByTagName('title')[0];
    
    xlb1.gDevicePixelRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
    xlb1.gCanvasElement.width = xlb1.gCanvasElement.clientWidth * xlb1.gDevicePixelRatio;
    xlb1.gCanvasElement.height = xlb1.gCanvasElement.clientHeight * xlb1.gDevicePixelRatio;
    console.log('devicePixelRatio: ' + xlb1.gDevicePixelRatio);
    
    // create our renderer
    xlb1.gRenderer2D = new xlb1.render.Renderer2D(xlb1.gCanvasElement, xlb1.gDevicePixelRatio);
    
    // clear the canvas first time
    mainClearCanvas();
    // draw the balck background
    xlb1.gRenderer2D.FillRect(0, 0, xlb1.gCanvasElement.width, xlb1.gCanvasElement.height, '#000000');
	
    xlb1.gRenderer2D.DrawText('-- jPacker --', 800*xlb1.gDevicePixelRatio, 30*xlb1.gDevicePixelRatio, '#00FF00');
    
}

//Store the function in a global property referenced by a string:
window['mainInit'] = mainInit;

function mainClearCanvas() {
	// Store the current transformation matrix
	xlb1.gCanvasContext.save();

	// Use the identity matrix while clearing the canvas
	xlb1.gCanvasContext.setTransform(1, 0, 0, 1, 0, 0);
	xlb1.gCanvasContext.clearRect(0, 0, xlb1.gCanvasElement.width, xlb1.gCanvasElement.height);

	// Restore the transform
	xlb1.gCanvasContext.restore();
};

var progressIndex = 0;

function drawProgress() {
	mainClearCanvas();
	var progressChar = xlb1.spinner.PROGRESS_CHARS[progressIndex];
	xlb1.gRenderer2D.DrawText('Packing rects... ' + progressChar, 800*xlb1.gDevicePixelRatio, 30*xlb1.gDevicePixelRatio, '#00FF00');
	progressIndex++;
	if (progressIndex > xlb1.spinner.PROGRESS_CHARS.length - 1) {
		progressIndex = 0;
	}
}

var intervalHandle;

function GetPacked() {
	//xlb1.gRenderer2D.DrawText('Packing rects........', 800*xlb1.gDevicePixelRatio, 30*xlb1.gDevicePixelRatio, '#00FF00');
	intervalHandle = setInterval(drawProgress, 100);
	var httpReq = new xlb1.http.HttpRequest('randomboxes', dataLoaded);
	httpReq.Send();
};

//Store the function in a global property referenced by a string:
window['GetPacked'] = GetPacked;

function dataLoaded(dataText, responseType) {
	clearInterval(intervalHandle);
	//xlb1.gRenderer2D.DrawText(dataText, 0, 30, '#00FF00');
	var boxes = JSON.parse(dataText);
	drawBoxes(boxes);
}

function drawBoxes(boxData) {
	// the first entry is the container
	var containerData = boxData.shift();
	var container = new xlb1.packer.BasicRect(containerData['x'], containerData['y'], containerData['w'], containerData['h'], containerData['id'], containerData['color']);
	var i = 0;
	var SCALE = 10;
	var ren = xlb1.gRenderer2D;
	var textOffsetX = 800 * xlb1.gDevicePixelRatio;
	var textOffsetY = 50 * xlb1.gDevicePixelRatio;
	var textLineOffset = 20 * xlb1.gDevicePixelRatio;
	
	// clean out our rects
	var rects = [];
	rects.length = 0;
	
	// clear the canvas
	ren.ClearCanvas();
	
	ren.DrawText('-- jPacker --', 800*xlb1.gDevicePixelRatio, 30*xlb1.gDevicePixelRatio, '#00FF00');
	
	// iterate and create our rects
	for (i = 0; i < boxData.length; ++i) {
		var data = boxData[i];
		var rect = new xlb1.packer.BasicRect(data['x'], data['y'], data['w'], data['h'], data['id'], data['color']);
		rects.push(rect);
	}
	var z = 0;
	
	// draw the rects
	var totalRectArea = 0;
	for (i = 0; i < rects.length; ++i) {
		var drect = rects[i];
		totalRectArea += drect.GetArea();
		// render the shape
		ren.DrawShape(drect.GetRenderPointArray(), '#000000', 1, drect.color);
		// render their dimensions
		ren.DrawText('[' + i + '] w: ' + (drect.w / SCALE) + ' h: ' + (drect.h / SCALE) + ' a: ' + (drect.GetArea() / (SCALE * SCALE)), textOffsetX, textOffsetY + (i*textLineOffset), '#00FF00');
		//ren.DrawText('[' + i + '] w: ' + (drect.w) + ' h: ' + (drect.h) + ' a: ' + (drect.GetArea()), textOffsetX, textOffsetY + (i*textLineOffset), '#00FF00');
		// render the number
		var midpoint = drect.GetMidPoint();
		ren.DrawText('' + i, (midpoint.x * xlb1.gDevicePixelRatio), (midpoint.y * xlb1.gDevicePixelRatio), '#FFFFFF');
	}
	
	// draw the container
	if (container != null) {
		ren.DrawShape(container.GetRenderPointArray(), '#FFFFFF', 1);
		var containerArea = container.GetArea();
		// render the container width height area
		ren.DrawText('-- packed box --', textOffsetX, textOffsetY + (i*textLineOffset), '#FFFFFF');
		++i;
		ren.DrawText('w: ' + (container.w / SCALE) + ' h: ' + (container.h / SCALE) + ' a: ' + (containerArea / (SCALE * SCALE)), textOffsetX, textOffsetY + (i*textLineOffset), '#FFFFFF');
		//ren.DrawText('w: ' + (container.w) + ' h: ' + (container.h) + ' a: ' + (containerArea), textOffsetX, textOffsetY + (i*textLineOffset), '#FFFFFF');
		++i;
		ren.DrawText('space efficiency: ', textOffsetX, textOffsetY + (i*textLineOffset), '#FF00FF' );
		++i;
		ren.DrawText('' + (totalRectArea / containerArea * 100.0).toFixed(2) + '%', textOffsetX, textOffsetY + (i*textLineOffset), '#FF00FF' );	
	}
}

