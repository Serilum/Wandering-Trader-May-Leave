package com.natamus.wanderingtradermayleave.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SmallTextButton extends Button {
    protected static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.withDefaultNamespace("widget/button"), ResourceLocation.withDefaultNamespace("widget/button_disabled"), ResourceLocation.withDefaultNamespace("widget/button_highlighted"));

    public SmallTextButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        Font font = Minecraft.getInstance().font;

        graphics.blitSprite(SPRITES.get(this.active, isHovered(mouseX, mouseY)), getX(), getY(), getWidth(), getHeight());

        float scale = 0.66f;
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, 1.0f);

        int scaledX = (int) ((getX() + 0.5 + ((float) getWidth() / 2) - (font.width(getMessage()) * scale / 2)) / scale);
        int scaledY = (int) ((getY() + 0.8 + ((float) getHeight() / 2) - (font.lineHeight * scale / 2)) / scale);

        graphics.drawString(font, getMessage(), scaledX, scaledY, 0xFFFFFF);

        graphics.pose().popPose();
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }
}
