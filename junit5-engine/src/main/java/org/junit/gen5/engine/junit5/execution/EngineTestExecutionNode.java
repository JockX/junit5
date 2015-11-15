/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.engine.junit5.execution;

import org.junit.gen5.api.extension.TestExecutionContext;
import org.junit.gen5.engine.EngineDescriptor;
import org.junit.gen5.engine.ExecutionRequest;

/**
 * @author Stefan Bechtold
 * @author Sam Brannen
 * @since 5.0
 */
public class EngineTestExecutionNode extends TestExecutionNode {

	private final EngineDescriptor testDescriptor;

	EngineTestExecutionNode(EngineDescriptor testDescriptor) {
		this.testDescriptor = testDescriptor;
	}

	@Override
	EngineDescriptor getTestDescriptor() {
		return this.testDescriptor;
	}

	public void executeRequest(ExecutionRequest request) {
		execute(request, createTopLevelContext());
	}

	@Override
	void execute(ExecutionRequest request, TestExecutionContext context) {
		for (TestExecutionNode child : getChildren()) {
			executeChild(child, request, context, null);
		}
	}

	private TestExecutionContext createTopLevelContext() {
		return new DescriptorBasedTestExecutionContext(getTestDescriptor(), null, null);
	}

}
