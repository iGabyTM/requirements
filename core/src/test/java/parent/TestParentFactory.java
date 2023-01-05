package parent;

import me.gabytm.minecraft.util.requirements.Requirement;
import me.gabytm.minecraft.util.requirements.RequirementFactory;

public interface TestParentFactory<R extends Requirement<?>> extends RequirementFactory<String, R> { }
