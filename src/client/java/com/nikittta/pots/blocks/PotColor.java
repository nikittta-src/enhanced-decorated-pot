package com.nikittta.pots.blocks;

import net.minecraft.util.DyeColor;

import java.util.Map;

public enum PotColor {
    ORIGINAL(10443081),
    WHITE(16383998),
    LIGHT_GRAY(10329495),
    GRAY(4673362),
    BLACK(1908001),
    BROWN(4993571),
    RED(11546150),
    ORANGE(16351261),
    YELLOW(16701501),
    LIME(8439583),
    GREEN(6192150),
    CYAN(1481884),
    LIGHT_BLUE(3847130),
    BLUE(3949738),
    PURPLE(8991416),
    MAGENTA(13061821),
    PINK(15961002);
    private final float[] colorComponents;

    public static final Map<DyeColor, PotColor> DYE_COLOR_POT_COLOR_MAP = Map.ofEntries(
            Map.entry(DyeColor.WHITE, PotColor.WHITE),
            Map.entry(DyeColor.LIGHT_GRAY, PotColor.LIGHT_GRAY),
            Map.entry(DyeColor.GRAY, PotColor.GRAY),
            Map.entry(DyeColor.BLACK, PotColor.BLACK),
            Map.entry(DyeColor.BROWN, PotColor.BROWN),
            Map.entry(DyeColor.RED, PotColor.RED),
            Map.entry(DyeColor.ORANGE, PotColor.ORANGE),
            Map.entry(DyeColor.YELLOW, PotColor.YELLOW),
            Map.entry(DyeColor.LIME, PotColor.LIME),
            Map.entry(DyeColor.GREEN, PotColor.GREEN),
            Map.entry(DyeColor.CYAN, PotColor.CYAN),
            Map.entry(DyeColor.LIGHT_BLUE, PotColor.LIGHT_BLUE),
            Map.entry(DyeColor.BLUE, PotColor.BLUE),
            Map.entry(DyeColor.PURPLE, PotColor.PURPLE),
            Map.entry(DyeColor.MAGENTA, PotColor.MAGENTA),
            Map.entry(DyeColor.PINK, PotColor.PINK)
    );
    PotColor(int color) {
        int j = (color & 16711680) >> 16;
        int k = (color & '\uff00') >> 8;
        int l = (color & 255);
        this.colorComponents = new float[]{(float)j / 255.0F, (float)k / 255.0F, (float)l / 255.0F};
    }

    public static PotColor byId(int id){
        return PotColor.values()[id];
    }

    public int getId(){
        return this.ordinal();
    }

    public float[] getColorComponents(){
        return this.colorComponents;
    }

    public static PotColor fromDyeColor(DyeColor dyeColor){
        return DYE_COLOR_POT_COLOR_MAP.get(dyeColor);
    }

}
