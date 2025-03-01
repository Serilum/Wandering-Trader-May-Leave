package com.natamus.wanderingtradermayleave.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class SmallTextButton extends Button {

    public SmallTextButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        Font font = Minecraft.getInstance().font;

        graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        graphics.pose().pushPose();

        graphics.blitNineSliced(WIDGETS_LOCATION, getX(), getY(), getWidth(), getHeight(), 20, 4, 200, 20, 0, getTextureY());

        float scale = 0.66f;
        graphics.pose().scale(scale, scale, 1.0f);

        int scaledX = (int) ((getX() + 0.5 + ((float) getWidth() / 2) - (font.width(getMessage()) * scale / 2)) / scale);
        int scaledY = (int) ((getY() + 0.8 + ((float) getHeight() / 2) - (font.lineHeight * scale / 2)) / scale);

        int textColor = this.active ? 16777215 : 10526880;
        graphics.drawString(font, getMessage(), scaledX, scaledY, textColor | Mth.ceil(this.alpha * 255.0F) << 24);

        graphics.pose().popPose();
    }

    private int getTextureY() {
        int textureY = 66;
        if (!this.active) {
            textureY = 66;
        } else if (this.isHoveredOrFocused()) {
            textureY = 46;
        }
        return textureY;
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }
}
