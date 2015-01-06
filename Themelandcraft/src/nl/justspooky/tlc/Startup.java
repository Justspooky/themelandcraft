package nl.justspooky.tlc;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
//import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
//import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Startup extends JavaPlugin implements Listener {
	public final Logger l = Logger.getLogger("Minecraft");
	
	public static String prefix = ChatColor.DARK_BLUE + "[" + ChatColor.RED + "ThemeLandCraft" + ChatColor.DARK_BLUE + "]" +ChatColor.WHITE + ": " + ChatColor.RESET;
	
	  public String usage = prefix +  ChatColor.GREEN + "Usage: /nojump <player>";

	  ArrayList<Player> hasnojump = new ArrayList<Player>();
	
	@Override
	public void onEnable(){
		l.info("ThemeLandCraft core is enabled");
		l.info("§4" + "Deze Plugin is gemaakt door: Kevin Wilmsen van Serverdevelopment.nl");
		//PluginManager manager = this.getServer().getPluginManager();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
        saveConfig();
   
	}
	@Override
	public void onDisable(){
		l.info("ThemeLandCraft core is disabled");
		l.info("§4" + "Deze Plugin is gemaakt door: Kevin Wilmsen van Serverdevelopment.nl");
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("tlcadmin")){
			if(args.length == 0){
				sender.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=" + ChatColor.RED +" ThemeLandCraft admin " + ChatColor.GOLD + "-=-=-=-=-=-=-=-=");
				sender.sendMessage(ChatColor.AQUA + "Argumenten: reloadconfig, saveconfig, resetconfig");
				sender.sendMessage(ChatColor.RED + "Usage: /tlcadmin <Argument>");
				return true;
			}
			if (args.length == 1){
				if (args[0].equals("reloadconfig")){
					reloadConfig();
					sender.sendMessage(prefix + ChatColor.YELLOW + "Config Reloaded");
				}
				if (args[0].equals("saveconfig")){
					saveConfig();
					sender.sendMessage(prefix + ChatColor.YELLOW + "Config Saved");
				}
				if (args[0].equals("resetconfig")){
					reloadConfig();
					sender.sendMessage(prefix + ChatColor.YELLOW + "Config Reset");
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("themelandcraft")){
			sender.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-" + ChatColor.RED +" ThemeLandCraft Help " + ChatColor.GOLD + "=-=-=-=-=-=-=-=-=");
			sender.sendMessage(ChatColor.GREEN + "/themelandcraft   " + ChatColor.BLUE  + "(Dit is voor het help menu)");
			sender.sendMessage(ChatColor.GREEN + "/website   " + ChatColor.BLUE  + "(Met dit command krijg je een link naar onze website)");
			sender.sendMessage(ChatColor.GREEN + "/resourcepack   " + ChatColor.BLUE  + "(Dit is voor het handmatig downloaden van ons resourcepack)");
			sender.sendMessage(ChatColor.GREEN + "/nojump   " + ChatColor.BLUE  + "(Dit is om de spring mogelijkheid van een speler uit te zetten.)");
			sender.sendMessage(ChatColor.GREEN + "/author   " + ChatColor.BLUE  + "(Check de maker van deze plugin)");
			sender.sendMessage("");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.GOLD + "Deze Plugin is gemaakt door Kevin Wilmsen van serverdevelopment.nl");
		}
		
		if(cmd.getName().equalsIgnoreCase("author")){
			sender.sendMessage(ChatColor.DARK_PURPLE +"-----------------------------------------------------");
			sender.sendMessage(ChatColor.AQUA + "Deze plugin is gemaakt door: " + ChatColor.RED + "Kevin Wilmsen");
			sender.sendMessage(ChatColor.GREEN + "Kijk ook eens op mijn website: " + ChatColor.RED + "http://www.serverdevelopment.nl");
			sender.sendMessage(ChatColor.DARK_PURPLE +"-----------------------------------------------------");
		}
		
		if(cmd.getName().equalsIgnoreCase("website")){
			String website = getConfig().getString("website");
			website = website.replaceAll("&", "§");
			if (website.contains("adf.ly")){
				getConfig().set("website", "Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly");
				saveConfig();
				l.info("Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly MVG, Kevin");
				sender.sendMessage(prefix + ChatColor.RED + "Er zit een fout in de config controleer deze en los het op");
				return true;
			}else
			sender.sendMessage(prefix + website);
		if(website.equalsIgnoreCase("Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly")){
			sender.sendMessage(prefix + ChatColor.RED + "Er zit een fout in de config controleer deze en los het op");
			l.info("Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly MVG, Kevin");
			return true;
		}
		}
		
		
		if(cmd.getName().equalsIgnoreCase("resourcepack")){
			//sender.sendMessage(prefix + ChatColor.YELLOW + getConfig().getString("resource pack"));
			String rp = getConfig().getString("resource-pack");
			rp = rp.replaceAll("&", "§");
			if (rp.contains("http://adf.ly")){
				getConfig().set("website", "&6Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly");
				saveConfig();
				l.info("Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly MVG, Kevin");
				sender.sendMessage(prefix + ChatColor.RED + "Er zit een fout in de config controleer deze en los het op");
				return true;
			}else
			sender.sendMessage(prefix + rp);
		if(rp.equalsIgnoreCase("Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly")){
			sender.sendMessage(prefix + ChatColor.RED + "Er zit een fout in de config controleer deze en los het op");
			l.info("Je mag geen geld verdienen met een gratis plugin van serverdevelopment dus ook geen adfly MVG, Kevin");
			return true;
		}
		}
		
		 Player p = (Player)sender;
		    if (!(sender instanceof Player)) {
		    	sender.sendMessage(ChatColor.GOLD + "Aleen ingame players kunnen nojump gebruiken");
		    }else
		      if (cmd.getName().equalsIgnoreCase("nojump")) {
		        if (args.length == 0) {
		          if ((p.hasPermission("tlc.nojump.self")) || (p.isOp())) {
		            if (!this.hasnojump.contains(p)) {
		              sender.sendMessage(prefix + ChatColor.AQUA + "Enabling nojump.");
		              p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 128));
		              this.hasnojump.add(p);
		            } else if (this.hasnojump.contains(p)) {
		              sender.sendMessage(prefix + ChatColor.AQUA + "Disabling nojump.");
		              p.removePotionEffect(PotionEffectType.JUMP);
		              this.hasnojump.remove(p);
		            }
		          }
		          else sender.sendMessage(prefix + ChatColor.DARK_RED + "You dont have permission to use that!");

		        }
		        else if (args.length == 1) {
		          if ((p.hasPermission("tlc.nojump.other")) || (p.isOp())) {
		            Player targetplayer = Bukkit.getPlayerExact(args[0]);
		            if (targetplayer == null)
		              sender.sendMessage(prefix + ChatColor.DARK_RED + args[0] + ChatColor.DARK_RED + " is not an online player.");
		            else if (targetplayer != null)
		              if (!this.hasnojump.contains(targetplayer)) {
		                sender.sendMessage(prefix + ChatColor.AQUA + "Enabling nojump for player " + targetplayer.getName() + ".");
		                targetplayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 128));
		                this.hasnojump.add(targetplayer);
		              }
		              else if (this.hasnojump.contains(targetplayer)) {
		                sender.sendMessage(prefix + ChatColor.AQUA + "Disabling nojump for player " + targetplayer.getName() + ".");
		                targetplayer.removePotionEffect(PotionEffectType.JUMP);
		                this.hasnojump.remove(targetplayer);
		              } else {
		                sender.sendMessage(prefix + this.usage);
		              }
		          }
		          else
		          {
		            sender.sendMessage(prefix + ChatColor.RED + "You dont have permission to use that!");
		          }

		        }
		        else
		        {
		          sender.sendMessage(prefix + this.usage);
		        }
		        return true;
		      }
		    
		
		return true;
		
	}
	
	@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
            for (String word : e.getMessage().split(" ")) {
                   // if (getConfig().getStringList("badwords").contains(word)) {
                          //  e.setCancelled(true);
                           // e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_HIT, 5, 1);
                            //e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.BLAZE_SHOOT, 100);
                          // e.getPlayer().sendMessage(prefix + ChatColor.YELLOW + "Je mag niet schelden");
                            //e.getPlayer().kickPlayer(ChatColor.AQUA + "Je mag niet schelden op themelandcraft");
                    //}
                    if(getConfig().getStringList("badwords").contains(word)){
                    	
                    		String scheldkick = getConfig().getString("anti-scheld-msg");
                    		scheldkick.replaceAll("&", "§");
                    		e.setCancelled(true);
                         e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_HIT, 5, 1);
                        // e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.BLAZE_SHOOT, 100);
                         e.getPlayer().sendMessage(prefix + scheldkick);
                         //e.getPlayer().kickPlayer(ChatColor.AQUA + "Je mag niet schelden op themelandcraft");
                    }
            }
          
    }
}

	

