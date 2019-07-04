package com.bymarcin.simplerfwires;

import net.minecraftforge.energy.IEnergyStorage;

import java.util.HashSet;

public class Grid {
    private Boolean valid = true;
    private HashSet<IEnergyStorage> outputs = new HashSet<>();

    public void addOutput(IEnergyStorage output) {
        outputs.add(output);
    }

    public Boolean isValid() {
        return valid;
    }

    public void invalidate() {
        this.valid = false;
    }

    public HashSet<IEnergyStorage> getOutputs() {
        return outputs;
    }
}
