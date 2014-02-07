package tc.oc.flair;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Flair extends JavaPlugin {

    private static final @Nonnull FlairManager flairManager = new FlairManager(null);

    public static FlairManager getManager() {
        return flairManager;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();

        FlairManager.parseConfiguration(this.getConfig());
        this.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            public void quit(final PlayerQuitEvent event) {
                flairManager.flairCache.remove(event.getPlayer().getName());
            }
        }, this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("flair.reload")) {
            sender.sendMessage(ChatColor.RED + "No permission");
            return true;
        }

        this.reloadConfig();
        FlairManager.parseConfiguration(this.getConfig());
        sender.sendMessage(ChatColor.GREEN + "Config reloaded");

        return true;
    }

}
