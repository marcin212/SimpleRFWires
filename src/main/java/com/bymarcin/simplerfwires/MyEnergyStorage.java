package com.bymarcin.simplerfwires;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyEnergyStorage extends EnergyStorage {
	RfWire owner;
	EnumFacing facing;

	public MyEnergyStorage(int capacity, EnumFacing face, RfWire owner) {
		super(capacity);
		this.owner = owner;
		this.facing = face;
	}

	private IEnergyStorage getStorage(Pair<ICapabilityProvider, EnumFacing> provider){
		return provider.getKey().hasCapability(CapabilityEnergy.ENERGY, provider.getValue())? provider.getKey().getCapability(CapabilityEnergy.ENERGY, provider.getValue()) : null;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (owner.grid == null || !owner.grid.isValid()) {
			owner.updateGrid(facing);
		}
		if (owner.grid != null) {
			Set<Pair<ICapabilityProvider, EnumFacing>> outputs = owner.grid.getOutputs();
			Map<IEnergyStorage, Integer> mapEnergy = new HashMap<>();
			long sumEnergy = 0;
			for (Pair<ICapabilityProvider, EnumFacing> provider : outputs) {
				IEnergyStorage storage = getStorage(provider);
				if (storage == null) continue;
				int c = storage.receiveEnergy(maxReceive, true);
				sumEnergy += c;
				mapEnergy.put(storage, c);
			}

			int sent = 0;
			if(sumEnergy > 0){
				for(IEnergyStorage storage: mapEnergy.keySet()) {
					if(!simulate) {
						sent += storage.receiveEnergy((int) (maxReceive * (long)mapEnergy.get(storage) / sumEnergy), false);
					}else {
						sent +=(int) (maxReceive * mapEnergy.get(storage) / sumEnergy);
					}
				}
			}
			return sent;
		}
		return 0;
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
