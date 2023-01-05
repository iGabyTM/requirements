package impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import parent.TestParentFactory;

public class TestFactory implements TestParentFactory<TestRequirement> {

    @Override
    public boolean matches(@NotNull String source) {
        return true;
    }

    @Override
    public @Nullable TestRequirement create(@NotNull String source) {
        return new TestRequirement("aa", false,true, false, 1);
    }

}
