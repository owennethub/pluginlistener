package club.batowen.listener.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import club.batowen.listener.core.NodeServer;
import club.batowen.listener.core.util.NodeReply;

public class ListenerMain extends JavaPlugin implements NodeReply{

    public final NodeServer node;

    private ListenerMain(){
        node = new NodeServer();
    }

    @Override
    public void onEnable() {
        node.connect();
        node.listen("rankup", this); // later i will allow multi server
    }

    @Override
    public void onDisable() {
        node.close();
    }


    @Override
    public void setGroup(String player, String group) {
        /***
         * permissionex set 
         * for now i will use console :)
         */
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/pex user "+player+" group set "+group);
    }

    @Override
    public void announceGlobalMessage(String message) {
        Bukkit.broadcastMessage(message);
    }

    @Override
    public void executeConsolCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
