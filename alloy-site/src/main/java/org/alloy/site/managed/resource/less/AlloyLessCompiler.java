package org.alloy.site.managed.resource.less;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.resource._Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;

@Service
public class AlloyLessCompiler {
	@Autowired
	private AlloyResourceManager resourceManager;

	private ScriptEngine engine;

	@PostConstruct
	private void initialize() {
		ScriptEngineManager engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("nashorn");
	}

	public Resource compile(Resource resource) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(os);
			engine.getContext().setWriter(writer);

			String[] args = new String[] {
					_Resource.getPath(resource)
			};

			engine.getBindings(ScriptContext.ENGINE_SCOPE).put("arguments", args);

			List<InputStream> streams = new ArrayList<InputStream>();
			streams.add(resourceManager.getResource("assets/js/less/less-nashorn.js").getInputStream());
			streams.add(resourceManager.getResource("assets/js/less/less.js").getInputStream());
			streams.add(resourceManager.getResource("assets/js/less/lessc.js").getInputStream());
			InputStreamReader reader = new InputStreamReader(new SequenceInputStream(Collections.enumeration(streams)));

			engine.eval(reader);

			writer.close();
			return _Resource.toResource(os);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
}
