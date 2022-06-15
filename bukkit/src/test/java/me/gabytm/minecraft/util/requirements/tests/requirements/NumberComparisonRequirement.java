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

import me.gabytm.minecraft.util.requirements.Arguments;
import me.gabytm.minecraft.util.requirements.bukkit.PlayerRequirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class NumberComparisonRequirement extends PlayerRequirement {

    private final DoubleOption left;
    private final DoubleOption right;
    private final Operation operation;

    public NumberComparisonRequirement(@NotNull String name, boolean optional, boolean negated, DoubleOption left, DoubleOption right, Operation operation) {
        super(name, optional, negated);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public boolean check(@Nullable Player player, @NotNull Arguments arguments) {
        final Double leftValue = left.get(player);

        if (leftValue == null) {
            return false;
        }

        final Double rightValue = right.get(player);

        if (rightValue == null) {
            return false;
        }

        return operation.check(leftValue, rightValue);
    }

    public enum Operation {

        EQUAL("==") {
            @Override
            boolean check(double left, double right) {
                return left == right;
            }
        },

        GREATER(">") {
            @Override
            boolean check(double left, double right) {
                return left > right;
            }
        },

        GREATER_OR_EQUAL(">=") {
            @Override
            boolean check(double left, double right) {
                return left >= right;
            }
        },

        SMALLER("<") {
            @Override
            boolean check(double left, double right) {
                return left < right;
            }
        },

        SMALLER_OR_EQUAL("<=") {
            @Override
            boolean check(double left, double right) {
                return left <= right;
            }
        };

        private static final EnumSet<Operation> VALUES = EnumSet.allOf(Operation.class);

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        abstract boolean check(final double left, final double right);

        public static @Nullable Operation find(@NotNull final String input) {
            for (final Operation it : VALUES) {
                if (input.equals(it.symbol) || input.equalsIgnoreCase(it.name()) || input.equalsIgnoreCase(it.name().replace("_", ""))) {
                    return it;
                }
            }

            return null;
        }

    }

}
