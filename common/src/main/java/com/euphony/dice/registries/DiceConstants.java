package com.euphony.dice.registries;

import com.euphony.dice.Dice;
import net.minecraft.resources.Identifier;

import java.awt.*;
import java.util.List;

public final class DiceConstants {
    public static final Color WHITE = Color.WHITE;
    public static final Color ORANGE = new Color(230, 158, 52);
    public static final Color MAGENTA = new Color(219, 122, 213);
    public static final Color LIGHT_BLUE = new Color(143, 185, 244);
    public static final Color YELLOW = new Color(255, 218, 57);
    public static final Color LIME = new Color(129, 206, 27);
    public static final Color PINK = new Color(246, 137, 172);
    public static final Color GRAY = new Color(112, 112, 112);
    public static final Color LIGHT_GRAY = new Color(162, 162, 162);
    public static final Color CYAN = new Color(81, 211, 185);
    public static final Color PURPLE = new Color(176, 100, 216);
    public static final Color BLUE = new Color(74, 124, 215);
    public static final Color BROWN = new Color(151, 103, 70);
    public static final Color GREEN = new Color(86, 150, 49);
    public static final Color RED = new Color(210, 68, 63);
    public static final Color BLACK = new Color(82, 82, 82);

    public static final String DICE_ENTITY_ID = "dice_entity";
    public static final String DICE_TAB_ID = "dice";
    public static final Identifier DICE_ENTITY_IDENTIFIER = Identifier.fromNamespaceAndPath(Dice.MOD_ID, DICE_ENTITY_ID);
    public static final Identifier DICE_TAB_IDENTIFIER = Identifier.fromNamespaceAndPath(Dice.MOD_ID, DICE_TAB_ID);

    public static final DiceItemDefinition WHITE_D6 = new DiceItemDefinition("white_d6", WHITE, 6);
    public static final DiceItemDefinition ORANGE_D6 = new DiceItemDefinition("orange_d6", ORANGE, 6);
    public static final DiceItemDefinition MAGENTA_D6 = new DiceItemDefinition("magenta_d6", MAGENTA, 6);
    public static final DiceItemDefinition LIGHT_BLUE_D6 = new DiceItemDefinition("light_blue_d6", LIGHT_BLUE, 6);
    public static final DiceItemDefinition YELLOW_D6 = new DiceItemDefinition("yellow_d6", YELLOW, 6);
    public static final DiceItemDefinition LIME_D6 = new DiceItemDefinition("lime_d6", LIME, 6);
    public static final DiceItemDefinition PINK_D6 = new DiceItemDefinition("pink_d6", PINK, 6);
    public static final DiceItemDefinition GRAY_D6 = new DiceItemDefinition("gray_d6", GRAY, 6);
    public static final DiceItemDefinition LIGHT_GRAY_D6 = new DiceItemDefinition("light_gray_d6", LIGHT_GRAY, 6);
    public static final DiceItemDefinition CYAN_D6 = new DiceItemDefinition("cyan_d6", CYAN, 6);
    public static final DiceItemDefinition PURPLE_D6 = new DiceItemDefinition("purple_d6", PURPLE, 6);
    public static final DiceItemDefinition BLUE_D6 = new DiceItemDefinition("blue_d6", BLUE, 6);
    public static final DiceItemDefinition BROWN_D6 = new DiceItemDefinition("brown_d6", BROWN, 6);
    public static final DiceItemDefinition GREEN_D6 = new DiceItemDefinition("green_d6", GREEN, 6);
    public static final DiceItemDefinition RED_D6 = new DiceItemDefinition("red_d6", RED, 6);
    public static final DiceItemDefinition BLACK_D6 = new DiceItemDefinition("black_d6", BLACK, 6);

    public static final List<DiceItemDefinition> DICE_ITEMS = List.of(
            WHITE_D6,
            ORANGE_D6,
            MAGENTA_D6,
            LIGHT_BLUE_D6,
            YELLOW_D6,
            LIME_D6,
            PINK_D6,
            GRAY_D6,
            LIGHT_GRAY_D6,
            CYAN_D6,
            PURPLE_D6,
            BLUE_D6,
            BROWN_D6,
            GREEN_D6,
            RED_D6,
            BLACK_D6
    );

    private DiceConstants() {
    }

    public record DiceItemDefinition(String id, Color color, int diceType) {
    }
}
