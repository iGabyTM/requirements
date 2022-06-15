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

package me.gabytm.minecraft.util.requirements.tests.requirements;

import me.gabytm.minecraft.util.requirements.RequirementFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NumberComparisonRequirementFactory implements RequirementFactory<ConfigurationSection, NumberComparisonRequirement> {

    @Override
    public boolean matches(@NotNull ConfigurationSection source) {
        final String type = source.getString("type");

        // Get rid of IDE warnings
        if (type == null) {
            return false;
        }

        return NumberComparisonRequirement.Operation.find(type) != null;
    }

    @Override
    public @Nullable NumberComparisonRequirement create(@NotNull ConfigurationSection source) {
        String type = source.getString("type");

        // Get rid of IDE warnings
        if (type == null) {
            return null;
        }

        final boolean negated = type.startsWith("!");

        if (negated) {
            type = type.substring(1);
        }

        if (source.getString("left") == null || source.getString("right") == null) {
            return null;
        }

        final NumberComparisonRequirement.Operation operation = NumberComparisonRequirement.Operation.find(type);
        final DoubleOption left = new DoubleOption(source.getString("left"));
        final DoubleOption right = new DoubleOption(source.getString("right"));
        return new NumberComparisonRequirement(source.getName(), source.getBoolean("optional"), negated, left, right, operation);
    }

}
