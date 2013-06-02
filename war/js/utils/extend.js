// create namespace
xlb1.utils = xlb1.utils || {};


//implement the extend using prototype inheritance extend method
xlb1.utils.extend = function(newObject, baseObject) {
	newObject.prototype = new baseObject();
	newObject.prototype.constructor = newObject;
};