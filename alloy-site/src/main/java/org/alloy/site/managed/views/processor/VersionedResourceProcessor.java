package org.alloy.site.managed.views.processor;

import javax.servlet.ServletContext;

import org.alloy.metal.url._Url;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

@Component
public class VersionedResourceProcessor extends AbstractElementProcessor implements AlloyDialectProcessor {
	private static final Logger logger = LogManager.getLogger(VersionedResourceProcessor.class);

	@Value("${project.version}")
	private String version;

	@Autowired
	private ServletContext servletContext;

	public VersionedResourceProcessor() {
		super("resource");
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String type = element.getAttributeValue("type");
		String name = element.getAttributeValue("name");

		String location;
		if (!StringUtils.isBlank(name)) {
			location = servletContext.getContextPath() + _Url.version("/" + type + _Url.normalize(name) + "." + type, version);
		}
		else {
			// FUTURE test
			String path = element.getAttributeValue("path");

			Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
					.parseExpression(arguments.getConfiguration(), arguments, path);

			location = (String) expression.execute(arguments.getConfiguration(), arguments);
			location = _Url.version(_Url.normalize(location), version);
		}

		NestableNode parent = element.getParent();
		logger.info("Processed element with url: [" + location + "]");
		if (type.toLowerCase().equals("css")) {
			parent.addChild(this.getLinkElement(location));
		}
		if (type.toLowerCase().equals("js")) {
			parent.addChild(this.getScriptElement(location));
		}

		parent.removeChild(element);
		return ProcessorResult.OK;
	}

	@Override
	public int getPrecedence() {
		return 10000;
	}

	protected Element getScriptElement(String src) {
		Element e = new Element("script");
		e.setAttribute("type", "text/javascript");
		e.setAttribute("src", src);
		return e;
	}

	protected Element getLinkElement(String src) {
		Element e = new Element("link");
		e.setAttribute("rel", "stylesheet");
		e.setAttribute("href", src);
		return e;
	}
}
