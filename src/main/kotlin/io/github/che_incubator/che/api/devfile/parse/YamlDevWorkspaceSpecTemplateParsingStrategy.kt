/*
 * Copyright (c) 2022 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package io.github.che_incubator.che.api.devfile.parse

import io.github.che_incubator.devfile.kubernetes.client.models.V1alpha2DevWorkspaceSpecTemplate
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

class YamlDevWorkspaceSpecTemplateParsingStrategy : DevWorkspaceSpecTemplateParsingStrategy {

    override fun parseTemplateObject(devfileContent: ByteArray): V1alpha2DevWorkspaceSpecTemplate {
        val loadingConfig = LoaderOptions()
        loadingConfig.isEnumCaseSensitive = false

        return Yaml(
            Constructor(
                V1alpha2DevWorkspaceSpecTemplate::class.java,
                loadingConfig
            )
        ).load(String(devfileContent))
    }
}
