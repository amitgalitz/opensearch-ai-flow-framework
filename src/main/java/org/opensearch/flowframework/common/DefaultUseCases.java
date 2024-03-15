/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.flowframework.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.core.rest.RestStatus;
import org.opensearch.flowframework.exception.FlowFrameworkException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum encapsulating the different default use cases and templates we have stored
 */
public enum DefaultUseCases {

    /** defaults file and substitution ready template for OpenAI embedding model */
    OPEN_AI_EMBEDDING_MODEL_DEPLOY(
        "open_ai_embedding_model_deploy",
        "defaults/open-ai-embedding-defaults.json",
        "substitutionTemplates/deploy-remote-model-template.json"
    ),
    /** defaults file and substitution ready template for cohere embedding model */
    COHERE_EMBEDDING_MODEL_DEPLOY(
        "cohere-embedding_model_deploy",
        "defaults/cohere-embedding-defaults.json",
        "substitutionTemplates/deploy-remote-model-template-extra-params.json"
    ),
    LOCAL_NEURAL_SPARSE_SEARCH(
        "local_neural_sparse_search",
        "defaults/local-sparse-search-defaults.json",
        "substitutionTemplates/neural-sparse-local-template.json"
    ),
    /** defaults file and substitution ready template for cohere embedding model */ // TODO: not finalized
    COHERE_EMBEDDING_MODEL_DEPLOY_SEMANTIC_SEARCH(
        "cohere-embedding_model_deploy_semantic_search",
        "defaults/cohere-embedding-defaults.json",
        "substitutionTemplates/deploy-remote-model-template-v1.json"
    );

    private final String useCaseName;
    private final String defaultsFile;
    private final String substitutionReadyFile;
    private static final Logger logger = LogManager.getLogger(DefaultUseCases.class);
    private static final Set<String> allResources = Stream.of(values()).map(DefaultUseCases::getDefaultsFile).collect(Collectors.toSet());

    DefaultUseCases(String useCaseName, String defaultsFile, String substitutionReadyFile) {
        this.useCaseName = useCaseName;
        this.defaultsFile = defaultsFile;
        this.substitutionReadyFile = substitutionReadyFile;
    }

    /**
     * Returns the useCaseName for the given enum Constant
     * @return the useCaseName of this use case.
     */
    public String getUseCaseName() {
        return useCaseName;
    }

    /**
     * Returns the defaultsFile for the given enum Constant
     * @return the defaultsFile of this for the given useCase.
     */
    public String getDefaultsFile() {
        return defaultsFile;
    }

    /**
     * Returns the substitutionReadyFile for the given enum Constant
     * @return the substitutionReadyFile of the given useCase
     */
    public String getSubstitutionReadyFile() {
        return substitutionReadyFile;
    }

    /**
     * Gets the defaultsFile based on the given use case.
     * @param useCaseName name of the given use case
     * @return the deafultsFile for that usecase
     * @throws FlowFrameworkException if the use case doesn't exist in enum
     */
    public static String getDefaultsFileByUseCaseName(String useCaseName) throws FlowFrameworkException {
        if (useCaseName != null && !useCaseName.isEmpty()) {
            for (DefaultUseCases mapping : values()) {
                if (useCaseName.equals(mapping.getUseCaseName())) {
                    return mapping.getDefaultsFile();
                }
            }
        }
        logger.error("Unable to find defaults file for use case: {}", useCaseName);
        throw new FlowFrameworkException("Unable to find defaults file for use case: " + useCaseName, RestStatus.BAD_REQUEST);
    }

    /**
     * Gets the substitutionReadyFile based on the given use case
     * @param useCaseName name of the given use case
     * @return the substitutionReadyFile which has the template
     * @throws FlowFrameworkException if the use case doesn't exist in enum
     */
    public static String getSubstitutionReadyFileByUseCaseName(String useCaseName) throws FlowFrameworkException {
        if (useCaseName != null && !useCaseName.isEmpty()) {
            for (DefaultUseCases mapping : values()) {
                if (mapping.getUseCaseName().equals(useCaseName)) {
                    return mapping.getSubstitutionReadyFile();
                }
            }
        }
        logger.error("Unable to find substitution ready file for use case: {}", useCaseName);
        throw new FlowFrameworkException("Unable to find substitution ready file for use case: " + useCaseName, RestStatus.BAD_REQUEST);
    }
}