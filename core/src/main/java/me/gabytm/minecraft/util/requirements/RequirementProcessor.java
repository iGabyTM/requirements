/**
 * MIT License
 *
 * Copyright (c) 2022 GabyTM
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.gabytm.minecraft.util.requirements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @param <T> type
 * @param <S> source
 */
public abstract class RequirementProcessor<T, S> {

    protected final Set<RequirementFactory<S, ? extends Requirement<T>>> factories = new HashSet<>();
    protected final Map<String, Class<? extends Requirement<T>>> requirements = new HashMap<>();

    /**
     * Find the factory matching the {@code source}. The default implementation filter factories by
     * {@link RequirementFactory#matches(Object)}
     *
     * @param source source
     * @return factory or null if none matched
     */
    protected @Nullable RequirementFactory<S, ? extends Requirement<T>> findFactory(@NotNull final S source) {
        return this.factories.stream().filter(it -> it.matches(source)).findFirst().orElse(null);
    }

    /**
     * Register a new requirement
     *
     * @param factory the factory of the requirement
     */
    protected void register(@NotNull final RequirementFactory<S, ? extends Requirement<T>> factory) {
        this.factories.add(factory);
    }

    /**
     * Attempt to create a {@link Requirement} off the given {@code source}
     *
     * @param source source
     * @return requirement or null if it couldn't be created
     */
    protected @Nullable Requirement<T> process(@NotNull final S source) {
        final RequirementFactory<S, ? extends Requirement<T>> factory = findFactory(source);

        if (factory == null) {
            return null;
        }

        return factory.create(source);
    }

}
