package com.bymarcin.simplerfwires;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import pl.asie.charset.api.wires.WireFace;
import pl.asie.charset.lib.wires.IWireContainer;
import pl.asie.charset.lib.wires.Wire;
import pl.asie.charset.lib.wires.WireProvider;
import pl.asie.charset.lib.wires.WireUtils;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

public class RfWire extends Wire implements ITickable {
    private final EnergyStorage STORAGE = new MyEnergyStorage(1024, this);

    protected RfWire(@Nonnull IWireContainer container, @Nonnull WireProvider factory, @Nonnull WireFace location) {
        super(container, factory, location);
    }

    @Override
    public void onChanged(boolean external) {

        super.onChanged(external);
    }

    @Override
    protected void updateConnections() {
        System.out.println("CHANGE" + this);
        super.updateConnections();
    }

    @Override
    public void update() {
        Set<ICapabilityProvider> visited = new HashSet<>();
        StreamSupport.stream(connectedIterator(true).spliterator(), false)
                .filter((t) -> visited.add(t.getKey()))
                .filter((t) -> t.getValue() != null)
                .filter((t) -> getContainer().world().getTileEntity(getContainer().pos().offset(t.getValue())) != t.getKey())
                .filter((t) -> t.getKey().hasCapability(CapabilityEnergy.ENERGY, t.getValue()))
                .map((t) -> t.getKey().getCapability(CapabilityEnergy.ENERGY, t.getValue()))
                .filter((t) -> t.canReceive())
                .forEach((t) -> {
                    if (STORAGE.getEnergyStored() != 0)
                        STORAGE.extractEnergy(t.receiveEnergy(STORAGE.getEnergyStored(), false), false);
                });

    }

    @Override
    public String getDisplayName() {
        return "WIRE";
    }


    @Override
    public boolean canConnectBlock(BlockPos pos, EnumFacing direction) {
        return WireUtils.hasCapability(this, pos, CapabilityEnergy.ENERGY, direction, true);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return facing != null;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            if (facing == null) {
                return null;
            }
            return CapabilityEnergy.ENERGY.cast(STORAGE);
        }

        return super.getCapability(capability, facing);
    }
}
