package club.batowen.listener.bungeecord;

import club.batowen.listener.core.NodeServer;
import club.batowen.listener.core.util.NodeReply;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ListenerMain extends Plugin implements NodeReply{

    public final NodeServer node;

    private ListenerMain(){
        node = new NodeServer();
    }

    @Override
    public void onEnable() {
        node.connect();
        node.listen("bungeecord", this); // later i will allow multi server
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
      executeConsolCommand("/bungeeperms user "+player+" setgroup "+group);
    }

    @Override
    public void announceGlobalMessage(String message) {
        BungeeCord.getInstance().broadcast(message);
    }

    @Override
    public void executeConsolCommand(String command) {
        ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
    }
}
