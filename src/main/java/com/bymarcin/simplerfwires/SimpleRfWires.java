package com.bymarcin.simplerfwires;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SimpleRfWires.MODID, name = SimpleRfWires.NAME, version = SimpleRfWires.VERSION)
public class SimpleRfWires {
    public static final String MODID = "simplerfwires";
    public static final String NAME = "Simple RF Wires";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
