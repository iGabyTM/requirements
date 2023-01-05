package me.gabytm.minecraft.util.requirements.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.gabytm.minecraft.util.requirements.Arguments;
import me.gabytm.minecraft.util.requirements.RequirementsList;
import me.gabytm.minecraft.util.requirements.bukkit.PlayerRequirement;
import me.gabytm.minecraft.util.requirements.tests.requirements.DoubleOption;
import me.gabytm.minecraft.util.requirements.tests.requirements.NumberComparisonRequirement;
import me.gabytm.minecraft.util.requirements.tests.requirements.RequirementProcessor;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;
import java.util.Map;

@Testable
public class BasicRequirementsTests {

    private static ServerMock server;
    private static PlayerMock player;
    private static RequirementProcessor processor;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        player = server.addPlayer();
        processor = RequirementProcessor.create("basic-requirements");

        final var world = new WorldMock();
        world.setName("world2");
        player.setLocation(world.getSpawnLocation());
    }

    @Test
    void parseTest() {
        final var requirements = processor.loadRequirements("parse");

        Assertions.assertEquals(1, requirements.size(), "Found more than 1 requirements in config");

        final var requirement = requirements.get(0);

        Assertions.assertNotNull(requirement, "Could not be parsed"); // The requirement was parsed correctly
        Assertions.assertTrue(requirement instanceof NumberComparisonRequirement, "Is not a" + NumberComparisonRequirement.class);

        final var numberComparisonRequirement = (NumberComparisonRequirement) requirement;

        Assertions.assertEquals("one", requirement.getName());
        Assertions.assertFalse(requirement.isNegated(), "Is negated");
        Assertions.assertFalse(requirement.isRequired(), "Is required");
        Assertions.assertTrue(requirement.isOptional(), "Is not optional");
        Assertions.assertEquals(numberComparisonRequirement.getOperation(), NumberComparisonRequirement.Operation.GREATER, "Operation to be GREATER (>)");
        Assertions.assertEquals(numberComparisonRequirement.getLeft(), new DoubleOption("5"), "Left number to be 5");
        Assertions.assertEquals(numberComparisonRequirement.getRight(), new DoubleOption("3"), "Right number to be 3");
        Assertions.assertTrue(requirement.check(player, Arguments.of(Map.of())));
    }

    @Test
    void minimumRequirementsTest() {
        final var requirements = processor.loadRequirements("minimumRequirements");

        Assertions.assertEquals(3, requirements.size(), "requirements#size()");

        final var list = new RequirementsList<>(requirements, 2);
        Assertions.assertFalse(list.check(player, Arguments.of(Map.of())), "list#check()");
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

}
