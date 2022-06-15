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

import com.google.common.primitives.Doubles;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class DoubleOption {

    private Double doubleValue;
    private String stringValue;

    public DoubleOption(final String input) {
        final Double temp = Doubles.tryParse(input);

        if (temp != null) {
            doubleValue = temp;
        }

        stringValue = input;
    }

    public Double get(@Nullable final Player player) {
        return (doubleValue != null) ? doubleValue : Doubles.tryParse(stringValue.replace("%health%", Double.toString(player.getHealth())));
    }

}
