function readFile(name) {
    // read a file into a byte array
    var file = new java.io.File(name);
    var stream = new java.io.FileInputStream(file);
    var output = org.alloy.metal.utilities._Stream.toString(stream);
    
    return output;
}

function createArgsArray(arguments) {
	return java.lang.reflect.Array.newInstance(java.lang.String.class, arguments.length - 1);
}