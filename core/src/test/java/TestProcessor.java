import impl.TestFactory;
import me.gabytm.minecraft.util.requirements.RequirementProcessor;

public class TestProcessor extends RequirementProcessor<String, String> {

    public TestProcessor() {
        register(new TestFactory());
        //RequirementsList<TestParentRequirement, String> list = new RequirementsList<>(List.of((TestParentRequirement) process("bla bla bla")));
        System.out.println(process("bla bla bla").getClass().getSimpleName());
    }

    public static void main(String[] args) {
        new TestProcessor();
    }

}
