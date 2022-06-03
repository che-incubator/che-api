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
package io.github.che_incubator.che.api


import io.github.che_incubator.che.api.devfile.DevfileClient
import io.github.che_incubator.che.api.devfile.DevfileContentProvider
import io.github.che_incubator.che.api.devfile.IODevfileContent
import io.github.che_incubator.che.api.devfile.kubernetes.K8sDevfileContentRetainer
import io.github.che_incubator.che.api.devfile.parse.DevfileContentParser
import io.github.che_incubator.che.api.workspace.kubernetes.K8sWorkspaceLifecycleController
import io.github.che_incubator.che.api.workspace.WorkspaceClient
import io.github.che_incubator.devfile.kubernetes.client.models.V1alpha2DevWorkspaceSpecTemplate
import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.openapi.Configuration as k8sConfiguration

object ApiClient {

    private val k8sApi = ClientBuilder.cluster().build()
    private val devfileClient: DevfileClient
    private val workspaceClient: WorkspaceClient

    init {
        k8sConfiguration.setDefaultApiClient(k8sApi)

        this.devfileClient = DevfileClient(
            DevfileContentProvider(
                IODevfileContent(Configuration.devWorkspaceFlattenedDevfilePath),
                DevfileContentParser(Configuration.devWorkspaceFlattenedDevfileParsingStrategy)
            ),
            K8sDevfileContentRetainer(k8sApi)
        )

        this.workspaceClient = WorkspaceClient(
            K8sWorkspaceLifecycleController(
                k8sApi
            )
        )
    }

    object Devfile {
        fun getRawContent(): ByteArray {
            return devfileClient.getRawContent()
        }

        fun getTemplateObject(): V1alpha2DevWorkspaceSpecTemplate {
            return devfileClient.getTemplateObject()
        }

        fun retainTemplateObject(templateObject: V1alpha2DevWorkspaceSpecTemplate) {
            devfileClient.retainTemplateObject(templateObject)
        }
    }

    object Workspace {
        fun getNamespace(): String {
            return workspaceClient.getNamespace()
        }

        fun getWorkspaceId(): String {
            return workspaceClient.getWorkspaceId()
        }

        fun stopWorkspace() {
            workspaceClient.stopWorkspace()
        }
    }
}
