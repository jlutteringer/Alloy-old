_.mixin({
	capitalize : function(str){
		str = str == null ? '' : String(str);
		return str.charAt(0).toUpperCase() + str.slice(1);
	},
	isDefined: function(obj) {
		return !_.isNull(obj) && !_.isUndefined(obj);
	},
	valueAt: function(array, index) {
		if(!_.isDefined(array) || !_.isDefined(index)) {
			return null;
		}
		if(!(array.length > index)) {
			return null;
		}
		return array[index];
	},
	isPair: function(object) {
		if(!_.isArray(object)) {
			return false;
		}
		
	}
});