/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.model.internal.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.lang.StringUtils;
import org.gradle.api.Nullable;
import org.gradle.internal.service.Service;
import org.gradle.model.internal.type.ModelType;

/**
 * A model reference is a speculative reference to a potential model element.
 * <p>
 * Rule subjects/inputs are defined in terms of references, as opposed to concrete identity.
 * The reference may be by type only, or by path only.
 * <p>
 * A reference doesn't include the notion of readonly vs. writable as the context of the reference implies this.
 * Having this be part of the reference would open opportunities for mismatch of that flag in the context.
 *
 * @param <T> the type of the reference.
 */
@ThreadSafe
public class ModelReference<T> {
    @Nullable
    private final ModelPath path;
    private final ModelType<T> type;
    @Nullable
    private final ModelPath scope;
    private final ModelNode.State state;
    @Nullable
    private final String description;

    private ModelReference(@Nullable ModelPath path, ModelType<T> type, @Nullable ModelPath scope, @Nullable ModelNode.State state, @Nullable String description) {
        this.path = validatePath(path, type);
        this.type = Preconditions.checkNotNull(type, "type");
        this.scope = scope;
        this.description = description;
        this.state = state != null ? state : ModelNode.State.GraphClosed;
    }

    private static ModelPath validatePath(@Nullable ModelPath path, ModelType<?> type) {
        ModelPath servicePath = findServicePath(type);
        if (servicePath != null) {
            if (path == null) {
                return servicePath;
            } else if (!path.equals(servicePath)) {
                throw invalidServicePath(type, servicePath, path);
            }
        }
        return path;
    }

    public static ModelReference<Object> any() {
        return of(ModelType.untyped());
    }

    public static <T> ModelReference<T> of(ModelPath path, ModelType<T> type, String description) {
        return new ModelReference<T>(path, type, null, null, description);
    }

    public static <T> ModelReference<T> of(String path, ModelType<T> type, String description) {
        return of(ModelPath.path(path), type, description);
    }

    public static <T> ModelReference<T> of(ModelPath path, ModelType<T> type) {
        return new ModelReference<T>(path, type, null, null, null);
    }

    public static <T> ModelReference<T> of(ModelPath path, ModelType<T> type, ModelNode.State state) {
        return new ModelReference<T>(path, type, null, state, null);
    }

    public static <T> ModelReference<T> of(ModelPath path, Class<T> type) {
        return of(path, ModelType.of(type));
    }

    public static <T> ModelReference<T> of(String path, Class<T> type) {
        return of(ModelPath.path(path), ModelType.of(type));
    }

    public static <T> ModelReference<T> of(String path, ModelType<T> type) {
        return of(path == null ? null : ModelPath.path(path), type);
    }

    public static <T> ModelReference<T> of(Class<T> type) {
        return of((ModelPath) null, ModelType.of(type));
    }

    public static <T> ModelReference<T> of(ModelType<T> type) {
        return of((ModelPath) null, type);
    }

    public static ModelReference<Object> of(String path) {
        return of(ModelPath.path(path), ModelType.UNTYPED);
    }

    public static ModelReference<Object> of(ModelPath path) {
        return of(path, ModelType.UNTYPED);
    }

    public static ModelReference<Object> untyped(ModelPath path) {
        return untyped(path, null);
    }

    public static ModelReference<Object> untyped(ModelPath path, String description) {
        return of(path, ModelType.UNTYPED, description);
    }

    @Nullable
    public ModelPath getPath() {
        return path;
    }

    /**
     * Return the path of the scope of the node to select, or null if scope is not relevant.
     *
     * <p>A node will be selected if its path or its parent's path equals the specified path.</p>
     */
    @Nullable
    public ModelPath getScope() {
        return scope;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public ModelType<T> getType() {
        return type;
    }

    public ModelNode.State getState() {
        return state;
    }

    public boolean isUntyped() {
        return type.equals(ModelType.UNTYPED);
    }

    public ModelReference<T> inScope(ModelPath scope) {
        if (scope.equals(this.scope)) {
            return this;
        }
        return new ModelReference<T>(path, type, scope, state, description);
    }

    public ModelReference<T> withPath(ModelPath path) {
        ModelPath servicePath = findServicePath(type);
        if (servicePath != null) {
            if (!servicePath.equals(path)) {
                throw invalidServicePath(type, servicePath, path);
            }
            return this;
        }
        return new ModelReference<T>(path, type, scope, state, description);
    }

    public ModelReference<T> atState(ModelNode.State state) {
        if (state.equals(this.state)) {
            return this;
        }
        return new ModelReference<T>(path, type, scope, state, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModelReference<?> that = (ModelReference<?>) o;
        return Objects.equal(path, that.path) && Objects.equal(scope, that.scope) && type.equals(that.type) && state.equals(that.state);
    }

    @Override
    public int hashCode() {
        int result = path == null ? 0 : path.hashCode();
        result = 31 * result + (scope == null ? 0 : scope.hashCode());
        result = 31 * result + type.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ModelReference{path=" + path + ", scope=" + scope + ", type=" + type + ", state=" + state + '}';
    }

    @Nullable
    public static ModelPath findServicePath(ModelType<?> type) {
        Service service = type.getConcreteClass().getAnnotation(Service.class);
        if (service == null) {
            return null;
        }
        String servicePath = service.value();
        if (servicePath == null || servicePath.equals(Service.UNSPECIFIED)) {
            servicePath = StringUtils.uncapitalize(type.getSimpleName());
        }
        return ModelPath.path(servicePath);
    }

    private static IllegalArgumentException invalidServicePath(ModelType<?> type, ModelPath servicePath, @Nullable ModelPath path) {
        return new IllegalArgumentException(String.format("Service '%s' must be referenced via path '%s' instead of '%s'", type, servicePath, path));
    }
}
