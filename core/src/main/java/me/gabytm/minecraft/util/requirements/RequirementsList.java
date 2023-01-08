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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RequirementsList<R extends Requirement<T>, T> {

    public static final int ALL_REQUIREMENTS = -1;

    private final List<R> requirements;
    private final int minimumRequirements;
    private final List<R> requirementsSorted;

    public RequirementsList(@NotNull final List<R> requirements, final int minimumRequirements) {
        if (minimumRequirements > requirements.size()) {
            throw new IllegalArgumentException("minimumRequirements > requirements.size()");
        }

        this.requirements = new ArrayList<>(requirements);
        this.minimumRequirements = minimumRequirements;
        this.requirementsSorted = new ArrayList<>(requirements);
        requirementsSorted.sort((a, b) -> Boolean.compare(a.isOptional(), b.isOptional()));
    }

    public RequirementsList(@NotNull final List<R> requirements) {
        this(requirements, ALL_REQUIREMENTS);
    }

    public boolean check(@Nullable final T t, @NotNull final Arguments arguments) {
        // All requirements must match
        if (this.minimumRequirements == ALL_REQUIREMENTS) {
            for (final R requirement : this.requirements) {
                // The requirement failed
                if (!requirement.check(t, arguments)) {
                    requirement.onFail(t);
                    return false;
                }
            }

            return true;
        }

        int requirementsMeet = 0;

        for (final R requirement : requirementsSorted) {
            final boolean result = requirement.check(t, arguments);

            // The requirement is required and it failed
            if (requirement.isRequired() && !result) {
                requirement.onFail(t);
                return false;
            }

            // Early return if all required requirements were passed and the minimum amount of passed requirements was reached
            if (!requirement.isRequired() && requirementsMeet >= minimumRequirements) {
                return true;
            }

            // The requirement was passed or is optional
            if (result || requirement.isOptional()) {
                requirementsMeet++;
                continue;
            }

            requirement.onFail(t);
        }

        return requirementsMeet > minimumRequirements;
    }

    public CompletableFuture<Boolean> checkAsync(@Nullable final T t, @NotNull final Arguments arguments) {
        return CompletableFuture.supplyAsync(() -> check(t, arguments));
    }

}
