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

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RequirementsList<T> {

    private static final int ALL_REQUIREMENTS = -1;

    private final List<Requirement<T>> requirements;
    private final int minimumRequirements;

    public RequirementsList(@NotNull final List<Requirement<T>> requirements, final int minimumRequirements) {
        this.requirements = requirements;
        this.minimumRequirements = minimumRequirements;
    }

    public boolean check(@Nullable final T t, @NotNull final Arguments arguments) {
        if (this.minimumRequirements == ALL_REQUIREMENTS) {
            for (final Requirement<T> requirement : this.requirements) {
                if (!requirement.check(t, arguments) && !requirement.isOptional()) {
                    return false;
                }
            }

            return true;
        }

        int requirementsMeet = 0;

        for (final Requirement<T> requirement : requirements) {
            final boolean result = requirement.check(t, arguments);

            if (result) {
                requirementsMeet++;
            }
        }

        return requirementsMeet > minimumRequirements;
    }

    public CompletableFuture<Boolean> checkAsync(@Nullable final T t, @NotNull final Arguments arguments) {
        return CompletableFuture.supplyAsync(() -> check(t, arguments));
    }

}
