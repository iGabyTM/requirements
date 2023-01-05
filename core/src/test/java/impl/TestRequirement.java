package impl;

import me.gabytm.minecraft.util.requirements.Arguments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import parent.TestParentRequirement;

public class TestRequirement extends TestParentRequirement {

    public TestRequirement(@NotNull String name, boolean required, boolean optional, boolean negated, int test) {
        super(name, required, optional, negated, test);
    }
    @Override
    public boolean check(@Nullable String s, @NotNull Arguments arguments) {
        return false;
    }

}
