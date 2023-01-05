package parent;

import me.gabytm.minecraft.util.requirements.Requirement;
import org.jetbrains.annotations.NotNull;

public abstract class TestParentRequirement extends Requirement<String> {

    public TestParentRequirement(@NotNull String name, boolean required, boolean optional, boolean negated, int test) {
        super(name, required, optional, negated);
    }

}
