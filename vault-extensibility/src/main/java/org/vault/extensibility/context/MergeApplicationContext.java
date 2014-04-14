package org.vault.extensibility.context;

import org.springframework.context.ConfigurableApplicationContext;
import org.vault.extensibility.PatchableConfiguration;

public interface MergeApplicationContext extends ConfigurableApplicationContext, PatchableConfiguration {

}