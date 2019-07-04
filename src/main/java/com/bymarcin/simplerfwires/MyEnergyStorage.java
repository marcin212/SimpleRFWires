package com.bymarcin.simplerfwires;

import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class MyEnergyStorage extends EnergyStorage {
    RfWire owner;

    public MyEnergyStorage(int capacity, RfWire owner) {
        super(capacity);
        this.owner = owner;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energy  = super.receiveEnergy(maxReceive, simulate);
        return  energy;
    }
}
