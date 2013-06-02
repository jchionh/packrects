// create namespace
xlb1.http = xlb1.http || {};

//---------------------------------------------------------------------------------------
//HttpRequest ctor
// callback takes in a param responseText
// responseType is optional and default to 'text'
// available are 'arraybuffer' or 'blob' or 'text'
xlb1.http.HttpRequest = function(url, callback, responseType) {
	
	callback = callback || null;
	responseType = responseType || 'text';
	
	// new out xmlhttprequest object
	this.mXhr = new XMLHttpRequest();
	this.mSendData = null;
	
	// we open the connection
	// TOOD: support both GET and POST method
	//       right now only GET
	this.mXhr.open('GET', url, true);
	this.mXhr.responseType = responseType;
	
	// set header to no cache
	//this.mXhr.setRequestHeader("Access-Control-Allow-Origin", "*");
	//this.mXhr.setRequestHeader("Access-Control-Allow-Headers", "X-Requested-With");
	//this.mXhr.setRequestHeader('Cache-Control', 'private, no-cache');
	//this.mXhr.setRequestHeader('Pragma', 'no-cache');
	
	// create a reference so we do not lose the object refernece in the callback
	var self = this;
	
	// set our async callback
	this.mXhr.onreadystatechange = function() {
		
		if (self.mXhr.readyState === 4) {
			
			if (self.mXhr.status === 200) {  
				if (callback !== null) {
					callback(self.mXhr.response, self.mXhr.responseType);
				}
			} else {  
				console.log("XMLHttpReq Error: ", self.mXhr.statusText); 
			}
		}  
	};
};

//---------------------------------------------------------------------------------------
//HttpRequest Send
xlb1.http.HttpRequest.prototype.Send = function() {
	this.mXhr.send(this.mSendData);
};