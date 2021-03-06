package com.bymarcin.simplerfwires;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;

public class Grid {
	private Boolean valid = true;
	private HashSet<Pair<ICapabilityProvider, EnumFacing>> outputs = new HashSet<>();
	private Boolean locked = false;

	public void addOutput(Pair<ICapabilityProvider, EnumFacing> output) {
		outputs.add(output);
	}

	public Boolean isValid() {
		return valid;
	}

	public void invalidate() {
		this.valid = false;
	}

	public void lock() {
		locked = true;
	}

	public void unLock() {
		locked = false;
	}

	public Boolean isLocked() {
		return locked;
	}

	public HashSet<Pair<ICapabilityProvider, EnumFacing>> getOutputs() {
		return outputs;
	}
}
