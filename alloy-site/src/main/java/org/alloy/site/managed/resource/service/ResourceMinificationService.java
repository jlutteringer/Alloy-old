package org.alloy.site.managed.resource.service;

public interface ResourceMinificationService {
	/**
	* Given the source byte[], will return a byte[] that represents the YUI-compressor minified version
	* of the byte[]. The behavior of this method is controlled via the following properties:
	*
	* <ul>
	* <li>minify.enabled - whether or not to actually perform minification</li>
	* <li>minify.linebreak - if set to a value other than -1, will enforce a linebreak at that value</li>
	* <li>minify.munge - if true, will replace variable names with shorter versions</li>
	* <li>minify.verbose - if true, will display extra logging information to the console</li>
	* <li>minify.preserveAllSemiColons - if true, will never remove semi-colons, even if two in a row exist</li>
	* <li>minify.disableOptimizations - if true, will disable some micro-optimizations that are performed</li>
	* </ul>
	*
	* @param filename
	* @param bytes
	* @return the minified bytes
	*/
	public byte[] minify(String filename, byte[] bytes);

	/**
	* Indicates whether or not the system is allowed to minify bundled resources.
	*
	* @return the value of the system property "minify.enabled"
	*/
	public boolean getEnabled();

	/**
	* Indicates whether or not the system is allowed to attempt to minify individual files. This can be useful if
	* the YUI compressor is failing to minify JavaScript/CSS due to syntax errors and you are attempting to track
	* down which file is problematic. It should not be enabled in a production environment.
	*
	* @return the value of the system property "minify.allowSingleMinification"
	*/
	boolean getAllowSingleMinification();
}