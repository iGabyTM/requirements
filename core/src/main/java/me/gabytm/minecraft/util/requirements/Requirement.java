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

/**
 * @param <T> type
 */
public abstract class Requirement<T> {

    protected final String name;
    protected final boolean optional;
    protected final boolean negated;

    public Requirement(@NotNull final String name, final boolean optional, final boolean negated) {
        this.name = name;
        this.optional = optional;
        this.negated = negated;
    }

    /**
     * Get the name of the requirement, not to be confused with the name of the class
     *
     * @return the name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Whether the requirement is optional
     *
     * @return whether the requirement is optional
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Whether the requirement is negated
     *
     * @return whether the requirement is negated
     */
    public boolean isNegated() {
        return negated;
    }

    /**
     * Check if the requirement is meet
     *
     * @param t         object
     * @param arguments arguments
     * @return whether the requirement is meet
     */
    public abstract boolean check(@Nullable final T t, @NotNull final Arguments arguments);

}
