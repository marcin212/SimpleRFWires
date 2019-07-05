package com.bymarcin.simplerfwires;

import net.minecraft.util.ResourceLocation;
import pl.asie.charset.api.wires.WireFace;
import pl.asie.charset.lib.wires.IWireContainer;
import pl.asie.charset.lib.wires.Wire;
import pl.asie.charset.lib.wires.WireProvider;

public class RfWireProvider extends WireProvider {
    @Override
    public Wire create(IWireContainer wireContainer, WireFace wireFace) {
        return new RfWire(wireContainer, this, wireFace);
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    public ResourceLocation getTexturePrefix() {
        return new ResourceLocation("simplerfwires:blocks/wire/outer");
    }

    @Override
    public boolean hasFreestandingWire() {
        return true;
    }

    @Override
    public boolean hasSidedWire() {
        return false;
    }
}
