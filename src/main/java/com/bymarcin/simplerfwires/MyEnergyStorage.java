package com.bymarcin.simplerfwires;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.LinkedList;
import java.util.List;

public class MyEnergyStorage extends EnergyStorage {
    RfWire owner;
    EnumFacing facing;

    public MyEnergyStorage(int capacity, EnumFacing face, RfWire owner) {
        super(capacity);
        this.owner = owner;
        this.facing = face;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energy = super.receiveEnergy(maxReceive, simulate);
        if(owner.grid == null || !owner.grid.isValid()) {
            owner.updateGrid(facing);
        }
        return energy;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
