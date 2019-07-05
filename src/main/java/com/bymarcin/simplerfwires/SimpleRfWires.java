package com.bymarcin.simplerfwires;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.logging.log4j.Logger;
import pl.asie.charset.lib.utils.RecipeUtils;
import pl.asie.charset.lib.utils.RegistryUtils;
import pl.asie.charset.lib.wires.CharsetLibWires;
import pl.asie.charset.lib.wires.ItemWire;
import pl.asie.charset.lib.wires.WireProvider;
import pl.asie.charset.module.power.electric.WireElectricOverlay;

@Mod(modid = SimpleRfWires.MODID, name = SimpleRfWires.NAME, version = SimpleRfWires.VERSION, dependencies = "required-after:charset@[0.5.5.1,)")
public class SimpleRfWires {
    public static final String MODID = "simplerfwires";
    public static final String NAME = "Simple RF Wires";
    public static final String VERSION = "1.0";

    private static RfWireProvider rfWire;
    private static ItemWire itemRfWire;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        logger = event.getModLog();
        rfWire = new RfWireProvider();
        rfWire.setRegistryName(new ResourceLocation(MODID, "rf_wire_provider"));
        itemRfWire = new ItemWire(rfWire);
        itemRfWire.setCreativeTab(CreativeTabs.REDSTONE);
        itemRfWire.setRegistryName(MODID,"rf_wire");
        itemRfWire.setUnlocalizedName("rf_wire");
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(itemRfWire);
    }

    @SubscribeEvent
    public void registerWires(RegistryEvent.Register<WireProvider> event) {
        event.getRegistry().register(rfWire);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

	public static IRecipe createShapedRecipe(ItemStack output, Object... data) {
		return new ShapelessOreRecipe(output.getItem().getRegistryName(), output, data);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		ItemStack redstone = new ItemStack(Items.REDSTONE, 1,0);
		ItemStack iron = new ItemStack(Items.IRON_INGOT, 1,0);
		ItemStack carpet = new ItemStack(Blocks.CARPET, 1,0);
		ItemStack wire = new ItemStack(itemRfWire, 3,1);
		event.getRegistry().register(createShapedRecipe(wire,
				carpet, carpet, carpet,
				iron, redstone, iron,
				carpet, carpet, carpet)
				.setRegistryName(new ResourceLocation(MODID + ":rfsimplewire_recipe")));
	}

    @SubscribeEvent(priority = EventPriority.HIGH)
    @SideOnly(Side.CLIENT)
    public void onTextureStitchPre(TextureStitchEvent.Pre event) {
       // CharsetLibWires.instance.registerRenderer(rfWire, new SimpleWireOverlay(rfWire));
    }
}
