package me.gabytm.minecraft.util.requirements.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.gabytm.minecraft.util.requirements.Arguments;
import me.gabytm.minecraft.util.requirements.bukkit.PlayerRequirement;
import me.gabytm.minecraft.util.requirements.tests.requirements.RequirementProcessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
        final List<PlayerRequirement> requirements = processor.loadRequirements("parse");

        Assertions.assertEquals(1, requirements.size());

        final PlayerRequirement requirement = requirements.get(0);

        Assertions.assertNotNull(requirement); // The requirement was parsed correctly
        Assertions.assertEquals("one", requirement.getName());
        Assertions.assertFalse(requirement.isNegated());
        Assertions.assertTrue(requirement.isOptional());
        Assertions.assertTrue(requirement.check(player, Arguments.of(Map.of())));
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

}
