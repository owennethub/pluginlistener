package club.batowen.listener.core.util;



public interface NodeReply {



    void setGroup(String player, String group);


    void announceGlobalMessage(String message);

    
    void executeConsolCommand(String command);

}