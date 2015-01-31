package funwayguy.esm.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class ESM_Settings
{
	//Mod Data
	public static final String Version = "FWG_ESM_VER";
	public static final String ID = "ESM";
	public static final String Channel = "ESM";
	public static final String Name = "Epic Siege Mod";
	public static final String Proxy = "funwayguy.esm.core.proxies";
	
	//Main
	public static int Awareness; //DONE
	public static boolean Xray; //DONE
	public static int TargetCap; //DONE
	public static boolean VillagerTarget; //DONE
	public static boolean Apocalypse; //DONE
	public static boolean Chaos; //DONE
	public static boolean AllowSleep; //DONE
	public static boolean QuickPathing; //DONE
	public static int ResistanceCoolDown; //DONE
	
	//Creeper
	public static boolean CreeperBreaching; //DONE
	public static boolean CreeperNapalm; //DONE
	public static boolean CreeperPowered; //DONE
	public static int CreeperPoweredRarity; //DONE
	
	//Blaze
	public static boolean BlazeSpawn; //DONE
	public static int BlazeRarity; //DONE
	public static int BlazeFireballs; //DONE
	
	//Ghast
	public static boolean GhastSpawn; //DONE
	public static int GhastRarity; //DONE
	public static double GhastFireDelay; //DONE
	public static boolean GhastBreaching; //DONE
	public static double GhastFireDist; //DONE
	
	//Skeleton
	public static int SkeletonDistance; //DONE
	public static int SkeletonAccuracy; //DONE
	
	//Zombie
	public static boolean ZombieInfectious; //DONE
	public static boolean ZombieDiggers; //DONE
	public static boolean ZombieDiggerTools; //DONE
	
	//Enderman
	public static String EndermanMode; //DONE
	public static boolean EndermanPlayerTele; //DONE
	
	//Advanced
	public static ArrayList<Integer> MobBombs; //DONE
	public static int MobBombRarity; //DONE
	public static boolean MobBombAll; // DONE
	public static boolean WitherSkeletons; //DONE
	public static int WitherSkeletonRarity; //DONE
	
	//Generation
    public static boolean NewEnd; //DONE
    public static boolean NewHell; //DONE
    public static boolean SpawnForts;
    public static int fortRarity = 2;
    public static int fortDistance = 16;
    public static ArrayList<Integer> fortDimensions = new ArrayList<Integer>();
    public static boolean fallFromEnd = true;
    
    //Non-configurables
    public static ArrayList<String> fortDB = new ArrayList<String>();
    public static WorldServer[] currentWorlds = null;
    public static File worldDir = null;
	public static boolean ambiguous_AI = true;
	public static Configuration defConfig;

	public static void LoadMainConfig(File file)
	{
		Configuration config = new Configuration(file);
		ESM.log.log(Level.INFO, "Loading ESM Global Config");
        
        defConfig = config;
		
        config.load();
        
        config.setCategoryComment("World", "For the main list of options please refer to the ESM_Options.cfg file in your world directory.");
        
        NewEnd = config.get("World", "Use New End", false).getBoolean(false);
        NewHell = config.get("World", "Use New Nether", false).getBoolean(false);
        
        config.save();
        
        ResetToDefault();
	}
	
	public static void ResetToDefault()
	{
		if(defConfig == null)
		{
			ESM.log.log(Level.ERROR, "Failed to reset options to default! Global default is null");
			return;
		}
		
		defConfig.load();
		
        //Main
        Awareness = defConfig.get("Main", "Awareness Radius", 64).getInt(64);
        Xray = defConfig.get("Main", "Xray Mobs", true).getBoolean(true);
        TargetCap = defConfig.get("Main", "Pathing Cap", 16).getInt(16);
        VillagerTarget = defConfig.get("Main", "Villager Targeting", true).getBoolean(true);
        Apocalypse = defConfig.get("Main", "Apocalypse Mode", false).getBoolean(false);
        Chaos = defConfig.get("Main", "Chaos Mode", false).getBoolean(false);
        AllowSleep = defConfig.get("Main", "Allow Sleep", false).getBoolean(false);
        ambiguous_AI = defConfig.get("Main", "Ambiguous AI", true, "If set to true, ESM will not check whether the entity is a mob or not when setting up new AI").getBoolean(true);
        QuickPathing = defConfig.get("Main", "Quick Pathing", false, "If set to fales, mobs can use much longer routes to get to their target").getBoolean(false);
        ResistanceCoolDown = defConfig.get("Main", "Resistance Cooldown", 200, "The amount of ticks of resistance given to the player after changing dimensions").getInt(200);
        
        //Creeper
        CreeperBreaching = defConfig.get("Creeper", "Breaching", true).getBoolean(true);
        CreeperNapalm = defConfig.get("Creeper", "Napalm", true).getBoolean(true);
        CreeperPowered = defConfig.get("Creeper", "Powered", true).getBoolean(true);
        CreeperPoweredRarity = defConfig.get("Creeper", "Powered Rarity", 9).getInt(9);
        
        //Skeletons
        SkeletonAccuracy = defConfig.get("Skeleton", "Arrow Error", 0).getInt(0);
        SkeletonDistance = defConfig.get("Skeleton", "Fire Distance", 64).getInt(64);
        
        //Zombies
        ZombieInfectious = defConfig.get("Zombie", "Infectious", true).getBoolean(true);
        ZombieDiggers = defConfig.get("Zombie", "Diggers", true).getBoolean(true);
        ZombieDiggerTools = defConfig.get("Zombie", "Need Required Tools", true).getBoolean(true);
        
        //Blazes
        BlazeSpawn = defConfig.get("Blaze", "Spawn", true).getBoolean(true);
        BlazeRarity = defConfig.get("Blaze", "Rarity", 9).getInt(9);
        BlazeFireballs = defConfig.get("Blaze", "Fireballs", 9).getInt(9);
        
        //Ghasts
        GhastSpawn = defConfig.get("Ghast", "Spawn", false).getBoolean(false);
        GhastRarity = defConfig.get("Ghast", "Rarity", 9).getInt(9);
        GhastFireDelay = defConfig.get("Ghast", "Fire Delay", 1.0D).getDouble(1.0D);
        GhastBreaching = defConfig.get("Ghast", "Breaching", true).getBoolean(true);
        GhastFireDist = defConfig.get("Ghast", "Fire Distance", 64.0D).getDouble(64.0D);
        
        //Endermen
        EndermanMode = defConfig.get("Enderman", "Mode", "Slender", "Valid Endermen Modes (Slender, Normal)").getString();
    	EndermanPlayerTele = defConfig.get("Enderman", "Player Teleport", true).getBoolean(true);
        
        
        //Advanced
        int[] tmp = defConfig.get("Advanced Mobs", "Mob Bombs", new int[]{52}).getIntList();
        MobBombs = new ArrayList<Integer>();
        for(int id : tmp)
        {
        	MobBombs.add(id);
        }
        MobBombRarity = defConfig.get("Advanced Mobs", "Mob Bomb Rarity", 9).getInt(9);
        MobBombAll = defConfig.get("Advanced Mobs", "Mob Bomb All", true, "Skip the Mob Bomb list and allow everything!").getBoolean(true);
        WitherSkeletons = defConfig.get("Advanced Mobs", "Wither Skeletons", true).getBoolean(true);
        WitherSkeletonRarity = defConfig.get("Advanced Mobs", "Wither Skeleton Rarity", 9).getInt(9);
        
        //World
        SpawnForts = defConfig.get("World", "Spawn Forts", true).getBoolean(true);
        fortRarity = defConfig.get("World", "Fort Rarity", 100).getInt(100);
        fortDistance = defConfig.get("World", "Fort Distance", 1024).getInt(1024);
        fallFromEnd = defConfig.get("World", "Fall From End", true, "Whether the player should fall into the overworld from the new End").getBoolean(true);
        int[] tmpFD = defConfig.get("World", "Fort Dimensions", new int[]{0}).getIntList();
        
        for(int dimID : tmpFD)
        {
        	fortDimensions.add(dimID);
        }
        
        defConfig.save();
	}
	
	public static void LoadWorldConfig()
	{
		if(worldDir == null)
		{
			ESM.log.log(Level.ERROR, "Failed to load world configs! Directory is null");
			return;
		}
		
		ResetToDefault();
		
		File conFile = new File(worldDir, "ESM_Options.cfg");
		
		if(!conFile.exists())
		{
			try
			{
				conFile.createNewFile();
			} catch(Exception e)
			{
				ESM.log.log(Level.INFO, "Failed to load ESM Config: " + conFile.getPath(), e);
				return;
			}
		}
		
		Configuration config = new Configuration(conFile, true);
		ESM.log.log(Level.INFO, "Loading ESM Config: " + conFile.getPath());
		
        config.load();
        
        //Main
        Awareness = config.get("Main", "Awareness Radius", Awareness).getInt(Awareness);
        Xray = config.get("Main", "Xray Mobs", Xray).getBoolean(Xray);
        TargetCap = config.get("Main", "Pathing Cap", TargetCap).getInt(TargetCap);
        VillagerTarget = config.get("Main", "Villager Targeting", VillagerTarget).getBoolean(VillagerTarget);
        Apocalypse = config.get("Main", "Apocalypse Mode", Apocalypse).getBoolean(Apocalypse);
        Chaos = config.get("Main", "Chaos Mode", Chaos).getBoolean(Chaos);
        AllowSleep = config.get("Main", "Allow Sleep", AllowSleep).getBoolean(AllowSleep);
        ambiguous_AI = config.get("Main", "Ambiguous AI", ambiguous_AI, "If set to true, ESM will not check whether the entity is a mob or not when setting up new AI").getBoolean(ambiguous_AI);
        QuickPathing = config.get("Main", "Quick Pathing", QuickPathing, "If set to fales, mobs can use much longer routes to get to their target").getBoolean(QuickPathing);
        ResistanceCoolDown = config.get("Main", "Resistance Cooldown", ResistanceCoolDown, "The amount of ticks of resistance given to the player after changing dimensions").getInt(ResistanceCoolDown);
        
        //Creeper
        CreeperBreaching = config.get("Creeper", "Breaching", CreeperBreaching).getBoolean(CreeperBreaching);
        CreeperNapalm = config.get("Creeper", "Napalm", CreeperNapalm).getBoolean(CreeperNapalm);
        CreeperPowered = config.get("Creeper", "Powered", CreeperPowered).getBoolean(CreeperPowered);
        CreeperPoweredRarity = config.get("Creeper", "Powered Rarity", CreeperPoweredRarity).getInt(CreeperPoweredRarity);
        
        //Skeletons
        SkeletonAccuracy = config.get("Skeleton", "Arrow Error", SkeletonAccuracy).getInt(SkeletonAccuracy);
        SkeletonDistance = config.get("Skeleton", "Fire Distance", SkeletonDistance).getInt(SkeletonDistance);
        
        //Zombies
        ZombieInfectious = config.get("Zombie", "Infectious", ZombieInfectious).getBoolean(ZombieInfectious);
        ZombieDiggers = config.get("Zombie", "Diggers", ZombieDiggers).getBoolean(ZombieDiggers);
        ZombieDiggerTools = config.get("Zombie", "Need Required Tools", ZombieDiggerTools).getBoolean(ZombieDiggerTools);
        
        //Blazes
        BlazeSpawn = config.get("Blaze", "Spawn", BlazeSpawn).getBoolean(BlazeSpawn);
        BlazeRarity = config.get("Blaze", "Rarity", BlazeRarity).getInt(BlazeRarity);
        BlazeFireballs = config.get("Blaze", "Fireballs", BlazeFireballs).getInt(BlazeFireballs);
        
        //Ghasts
        GhastSpawn = config.get("Ghast", "Spawn", GhastSpawn).getBoolean(GhastSpawn);
        GhastRarity = config.get("Ghast", "Rarity", GhastRarity).getInt(GhastRarity);
        GhastFireDelay = config.get("Ghast", "Fire Delay", GhastFireDelay).getDouble(GhastFireDelay);
        GhastBreaching = config.get("Ghast", "Breaching", GhastBreaching).getBoolean(GhastBreaching);
        GhastFireDist = config.get("Ghast", "Fire Distance", GhastFireDist).getDouble(GhastFireDist);
        
        //Endermen
        EndermanMode = config.get("Enderman", "Mode", EndermanMode, "Valid Endermen Modes (Slender, Normal)").getString();
    	EndermanPlayerTele = config.get("Enderman", "Player Teleport", EndermanPlayerTele).getBoolean(EndermanPlayerTele);
        
        //Advanced
    	int[] tmpDef = new int[MobBombs.size()];
    	
    	for(int i = 0; i < MobBombs.size(); i++)
    	{
    		tmpDef[i] = MobBombs.get(i);
    	}
        int[] tmp = config.get("Advanced Mobs", "Mob Bombs", tmpDef).getIntList();
        MobBombs = new ArrayList<Integer>();
        for(int id : tmp)
        {
        	MobBombs.add(id);
        }
        MobBombRarity = config.get("Advanced Mobs", "Mob Bomb Rarity", MobBombRarity).getInt(MobBombRarity);
        MobBombAll = config.get("Advanced Mobs", "Mob Bomb All", MobBombAll, "Skip the Mob Bomb list and allow everything!").getBoolean(MobBombAll);
        WitherSkeletons = config.get("Advanced Mobs", "Wither Skeletons", WitherSkeletons).getBoolean(WitherSkeletons);
        WitherSkeletonRarity = config.get("Advanced Mobs", "Wither Skeleton Rarity", WitherSkeletonRarity).getInt(WitherSkeletonRarity);
        
        //World
        SpawnForts = config.get("World", "Spawn Forts", SpawnForts).getBoolean(SpawnForts);
        fortRarity = config.get("World", "Fort Rarity", fortRarity).getInt(fortRarity);
        fortDistance = config.get("World", "Fort Distance", fortDistance).getInt(fortDistance);
        fallFromEnd = config.get("World", "Fall From End", fallFromEnd, "Whether the player should fall into the overworld from the new End").getBoolean(fallFromEnd);
    	int[] tmpFDDef = new int[fortDimensions.size()];
    	
    	for(int i = 0; i < fortDimensions.size(); i++)
    	{
    		tmpFDDef[i] = fortDimensions.get(i);
    	}
        int[] tmpFD = config.get("World", "Fort Dimensions", tmpFDDef).getIntList();
        
        for(int dimID : tmpFD)
        {
        	fortDimensions.add(dimID);
        }
        
        config.save();
        
        ESM_Utils.UpdateBiomeSpawns();
        
        fortDB = loadFortDB();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> loadFortDB()
	{
		File fileFortDB = new File(worldDir, "ESM_fortDB");
		ESM.log.log(Level.INFO, "Loading fortDB from " + fileFortDB.getPath());
		
		if(!fileFortDB.exists())
		{
			return new ArrayList<String>();
		} else
		{
			try
			{
				FileInputStream fileIn = new FileInputStream(fileFortDB);
				BufferedInputStream buffer = new BufferedInputStream(fileIn);
				ObjectInputStream objIn = new ObjectInputStream(buffer);
				
				ArrayList<String> savedDB = (ArrayList<String>)objIn.readObject();
				
				objIn.close();
				buffer.close();
				fileIn.close();
				
				return savedDB;
			} catch(Exception e)
			{
				return new ArrayList<String>();
			}
		}
	}
	
	public static void saveFortDB()
	{
		if(fortDB == null || fortDB.size() <= 0)
		{
			return;
		}
		
		File fileFortDB = new File(worldDir, "ESM_fortDB");
		
		ESM.log.log(Level.INFO, "Saving fortDB to " + fileFortDB.getPath());
		
		try
		{
			if(!fileFortDB.exists())
			{
				fileFortDB.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(fileFortDB);
			BufferedOutputStream buffer = new BufferedOutputStream(fileOut);
			ObjectOutputStream objOut = new ObjectOutputStream(buffer);
			
			objOut.writeObject(fortDB);
			
			objOut.close();
			buffer.close();
			fileOut.close();
		} catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
}
