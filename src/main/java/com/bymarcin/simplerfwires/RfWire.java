package com.bymarcin.simplerfwires;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.apache.commons.lang3.tuple.Pair;
import pl.asie.charset.api.wires.WireFace;
import pl.asie.charset.lib.wires.IWireContainer;
import pl.asie.charset.lib.wires.Wire;
import pl.asie.charset.lib.wires.WireProvider;
import pl.asie.charset.lib.wires.WireUtils;

import javax.annotation.Nonnull;
import java.util.*;

public class RfWire extends Wire {
    private final EnergyStorage[] STORAGE = new EnergyStorage[6];
    public Grid grid;

    protected RfWire(@Nonnull IWireContainer container, @Nonnull WireProvider factory, @Nonnull WireFace location) {
        super(container, factory, location);
        for (EnumFacing value : EnumFacing.values()) {
            STORAGE[value.ordinal()] = new MyEnergyStorage(1024, value, this);
        }
    }

    @Override
    public void onChanged(boolean external) {
        super.onChanged(external);
    }

    @Override
    protected void updateConnections() {
        if (grid != null) {
            grid.invalidate();
        }
        super.updateConnections();
    }

    void updateGrid(EnumFacing face) {
        System.out.println("===========update grid===========");
        grid = new Grid();

        Set<Object> traversed = Collections.newSetFromMap(new IdentityHashMap<>());

        LinkedList<IEnergyStorage> toTraverse = new LinkedList<>();
        toTraverse.add(STORAGE[face.ordinal()]);

        World world = getContainer().world();

        while (!toTraverse.isEmpty()) {
            IEnergyStorage o = toTraverse.remove();
            if (!traversed.add(o)) continue;
            if (o instanceof MyEnergyStorage) {
                for (Pair<ICapabilityProvider, EnumFacing> pair : ((MyEnergyStorage) o).owner.connectedIterator(true)) {

                    ICapabilityProvider provider = pair.getLeft();
                    IEnergyStorage connection = provider.hasCapability(CapabilityEnergy.ENERGY, pair.getRight()) ? provider.getCapability(CapabilityEnergy.ENERGY, pair.getRight()) : null;

                    if (connection == null || !traversed.add(provider)) continue;

                    if (connection instanceof MyEnergyStorage) {
                        ((MyEnergyStorage) connection).owner.grid = grid;
                        toTraverse.add(connection);
                    } else {
                        grid.addOutput(pair);
                        System.out.println("adding output: " + connection);
                    }
                }
            }
        }
        System.out.println("gird stats:");
        System.out.println("outputs: " + grid.getOutputs().size());
        System.out.println("------------");
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
            return facing != null && (connectsAny(facing));
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            if (facing == null) {
                return null;
            }
            return CapabilityEnergy.ENERGY.cast(STORAGE[facing.ordinal()]);
        }

        return super.getCapability(capability, facing);
    }
}
