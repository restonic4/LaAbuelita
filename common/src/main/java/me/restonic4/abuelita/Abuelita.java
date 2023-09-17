package me.restonic4.abuelita;

import me.restonic4.abuelita.creative_tab.CreativeTabRegister;
import me.restonic4.abuelita.event.RightClickItem;
import me.restonic4.abuelita.item.ItemRegister;
import me.restonic4.abuelita.villager.VillagerRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Abuelita
{
	//GENERAL STUFF
	public static final String MOD_ID = "abuelita";

	public static final Logger LOGGER = LogManager.getLogger("Abuelita");

	//ON START
	public static void init() {
		LOGGER.info("[" + MOD_ID + "] Starting mod");

		ItemRegister.register();
		CreativeTabRegister.register();
		VillagerRegister.register();

		RightClickItem.registerEvent();

		LOGGER.info("[" + MOD_ID + "] Items and creative tabs registered");

		//LOGGER.info("[" + MOD_ID + "] " + ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
	}
}
