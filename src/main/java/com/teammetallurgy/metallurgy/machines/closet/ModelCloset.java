package com.teammetallurgy.metallurgy.machines.closet;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelCloset extends ModelBase {
    public ModelRenderer cabnetMiddle;
    public ModelRenderer cabnetTop;
    public ModelRenderer cabnetFoot1;
    public ModelRenderer cabnetFoot2;
    public ModelRenderer cabnetFoot3;
    public ModelRenderer cabnetFoot4;
    public ModelRenderer cabnetDraw1;
    public ModelRenderer cabnetfootCrossbar1;
    public ModelRenderer cabnetfootCrossbar2;
    public ModelRenderer cabnetfootCrossbar3;
    public ModelRenderer cabnetfootCrossbar4;
    public ModelRenderer cabnetDraw2;
    public ModelRenderer cabnetRightHandle;
    public ModelRenderer cabnetLeftHandle;

    public ModelCloset() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.cabnetfootCrossbar1 = new ModelRenderer(this, 56, 17);
        this.cabnetfootCrossbar1.setRotationPoint(-5.0F, 22.0F, -7.0F);
        this.cabnetfootCrossbar1.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.cabnetFoot4 = new ModelRenderer(this, 48, 42);
        this.cabnetFoot4.setRotationPoint(-7.0F, 21.0F, 5.0F);
        this.cabnetFoot4.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.cabnetFoot2 = new ModelRenderer(this, 48, 42);
        this.cabnetFoot2.setRotationPoint(5.0F, 21.0F, -7.0F);
        this.cabnetFoot2.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.cabnetfootCrossbar3 = new ModelRenderer(this, 56, 17);
        this.cabnetfootCrossbar3.setRotationPoint(-5.0F, 22.0F, 6.0F);
        this.cabnetfootCrossbar3.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.cabnetDraw1 = new ModelRenderer(this, 79, 17);
        this.cabnetDraw1.setRotationPoint(-6.0F, -6.0F, -7.0F);
        this.cabnetDraw1.addBox(0.0F, 0.0F, -0.5F, 6, 24, 1, 0.0F);
        this.cabnetRightHandle = new ModelRenderer(this, 0, 0);
        this.cabnetRightHandle.setRotationPoint(6.0F, -6.0F, -7.0F);
        this.cabnetRightHandle.addBox(-5.0F, 11.0F, -1.0F, 2, 2, 1, 0.0F);
        this.cabnetMiddle = new ModelRenderer(this, 0, 0);
        this.cabnetMiddle.setRotationPoint(-7.0F, -7.0F, -7.0F);
        this.cabnetMiddle.addBox(0.0F, 0.0F, 0.0F, 14, 28, 14, 0.0F);
        this.cabnetDraw2 = new ModelRenderer(this, 79, 17);
        this.cabnetDraw2.setRotationPoint(6.0F, -6.0F, -7.0F);
        this.cabnetDraw2.addBox(-6.0F, 0.0F, -0.5F, 6, 24, 1, 0.0F);
        this.cabnetTop = new ModelRenderer(this, 56, 0);
        this.cabnetTop.setRotationPoint(0.0F, -8.0F, -8.0F);
        this.cabnetTop.addBox(-8.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
        this.cabnetFoot1 = new ModelRenderer(this, 48, 42);
        this.cabnetFoot1.setRotationPoint(5.0F, 21.0F, 5.0F);
        this.cabnetFoot1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.cabnetfootCrossbar2 = new ModelRenderer(this, 56, 19);
        this.cabnetfootCrossbar2.setRotationPoint(6.0F, 22.0F, -5.0F);
        this.cabnetfootCrossbar2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 10, 0.0F);
        this.cabnetLeftHandle = new ModelRenderer(this, 0, 0);
        this.cabnetLeftHandle.setRotationPoint(-6.0F, -6.0F, -7.0F);
        this.cabnetLeftHandle.addBox(3.0F, 11.0F, -1.0F, 2, 2, 1, 0.0F);
        this.cabnetFoot3 = new ModelRenderer(this, 48, 42);
        this.cabnetFoot3.setRotationPoint(-7.0F, 21.0F, -7.0F);
        this.cabnetFoot3.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.cabnetfootCrossbar4 = new ModelRenderer(this, 56, 19);
        this.cabnetfootCrossbar4.setRotationPoint(-7.0F, 22.0F, -5.0F);
        this.cabnetfootCrossbar4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 10, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.cabnetfootCrossbar1.render(f5);
        this.cabnetFoot4.render(f5);
        this.cabnetFoot2.render(f5);
        this.cabnetfootCrossbar3.render(f5);
        this.cabnetDraw1.render(f5);
        this.cabnetRightHandle.render(f5);
        this.cabnetMiddle.render(f5);
        this.cabnetDraw2.render(f5);
        this.cabnetTop.render(f5);
        this.cabnetFoot1.render(f5);
        this.cabnetfootCrossbar2.render(f5);
        this.cabnetLeftHandle.render(f5);
        this.cabnetFoot3.render(f5);
        this.cabnetfootCrossbar4.render(f5);
    }
    
    public void renderAllModels()
    {
        this.cabnetfootCrossbar1.render(0.0625F);
        this.cabnetFoot4.render(0.0625F);
        this.cabnetFoot2.render(0.0625F);
        this.cabnetfootCrossbar3.render(0.0625F);
        this.cabnetDraw1.render(0.0625F);
        this.cabnetRightHandle.render(0.0625F);
        this.cabnetMiddle.render(0.0625F);
        this.cabnetDraw2.render(0.0625F);
        this.cabnetTop.render(0.0625F);
        this.cabnetFoot1.render(0.0625F);
        this.cabnetfootCrossbar2.render(0.0625F);
        this.cabnetLeftHandle.render(0.0625F);
        this.cabnetFoot3.render(0.0625F);
        this.cabnetfootCrossbar4.render(0.0625F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
