package com.natamus.wanderingtradermayleave.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class SmallTextButton extends Button {
    protected static final WidgetSprites SPRITES = new WidgetSprites(Identifier.withDefaultNamespace("widget/button"), Identifier.withDefaultNamespace("widget/button_disabled"), Identifier.withDefaultNamespace("widget/button_highlighted"));

    public SmallTextButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));

        float scale = 0.66f;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().scale(scale, scale);

        int scaledX = (int) ((getX() + 0.5 + ((float) getWidth() / 2) - (font.width(getMessage()) * scale / 2)) / scale);
        int scaledY = (int) ((getY() + 0.8 + ((float) getHeight() / 2) - (font.lineHeight * scale / 2)) / scale);

        guiGraphics.drawString(font, getMessage(), scaledX, scaledY, 0xFFFFFFFF);

        guiGraphics.pose().popMatrix();
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }
}
