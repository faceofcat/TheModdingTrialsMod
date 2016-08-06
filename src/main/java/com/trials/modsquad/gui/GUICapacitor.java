package com.trials.modsquad.gui;

import com.trials.modsquad.ModSquad;
import com.trials.modsquad.block.TileEntities.TileCharger;
import com.trials.modsquad.block.containers.ContainerCharger;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.PowerBar;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUICapacitor extends GuiContainer {

    private ITeslaHolder charger;
    private PowerBar p;

    public GUICapacitor(InventoryPlayer player, TileCharger charger) {
        super(new ContainerCharger(player, charger));
        this.charger = charger.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, EnumFacing.DOWN);
        p = new PowerBar(this, xSize / 2, ySize / 2 + 25, PowerBar.BackgroundType.LIGHT);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Capacitor", 8, 6, 4210751);
        String power;
        int count = (power = TeslaUtils.getDisplayableTeslaCount(charger.getStoredPower())).length();
        fontRendererObj.drawString(power, xSize-45-count/2, 35, 4210751);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        ResourceLocation l;
        mc.renderEngine.getTexture(l=new ResourceLocation(ModSquad.MODID, "textures/gui/container/cap.png"));
        mc.renderEngine.bindTexture(l);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        drawTexturedModalRect((width - xSize)/2, (height-ySize)/2, 0, 0, xSize, ySize);
        p.draw(charger);
    }

    @Override
    public void updateScreen() {
        p.draw(charger);
        super.updateScreen();
    }

}