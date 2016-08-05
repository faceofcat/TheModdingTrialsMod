package com.trials.modsquad.gui;

import com.trials.modsquad.ModSquad;
import com.trials.modsquad.block.TileEntities.TileCharger;
import com.trials.modsquad.block.TileEntities.TileFurnaceGenerator;
import com.trials.modsquad.block.containers.ContainerCharger;
import com.trials.modsquad.block.containers.ContainerFurnaceGenerator;
import net.darkhax.tesla.lib.PowerBar;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUICharger extends GuiContainer {

    private TileCharger charger;
    private PowerBar p;

    public GUICharger(InventoryPlayer player, TileCharger charger) {
        super(new ContainerCharger(player, charger));
        this.charger = charger;
        p = new PowerBar(this, width / 2 - 14, height / 2 -50, PowerBar.BackgroundType.LIGHT);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Charger", 8, 6, 4210751);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        ResourceLocation l;
        mc.renderEngine.getTexture(l=new ResourceLocation(ModSquad.MODID, "/textures/gui/container/fgen.png"));
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(l);
        drawTexturedModalRect((width - xSize)/2, (height-ySize)/2, 0, 0, xSize, ySize);
        p.draw(charger);
    }

    @Override
    public void updateScreen() {
        p.draw(charger);
        super.updateScreen();
    }
}