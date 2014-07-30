package org.vault.site.managed.views.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.vault.base.url.VUrls;

@Component
public class VersionedResourceProcessor extends AbstractElementProcessor implements VaultDialectProcessor {
	private static final Logger logger = LogManager.getLogger(VersionedResourceProcessor.class);

	@Value("${project.version}")
	private String version;

	public VersionedResourceProcessor() {
		super("resource");
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String type = element.getAttributeValue("type");
		String path = element.getAttributeValue("path");

		NestableNode parent = element.getParent();

		Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
				.parseExpression(arguments.getConfiguration(), arguments, path);

		String processedPath = (String) expression.execute(arguments.getConfiguration(), arguments);
		processedPath = VUrls.version(VUrls.normalize(processedPath), version);

		logger.trace("Processed element with url: [" + processedPath + "]");
		if (type.toLowerCase().equals("css")) {
			parent.addChild(this.getLinkElement(processedPath));
		}
		if (type.toLowerCase().equals("js")) {
			parent.addChild(this.getScriptElement(processedPath));
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
