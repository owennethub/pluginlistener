package club.batowen.listener.core.server;

import com.google.gson.JsonObject;

import club.batowen.listener.core.util.NodeReply;
import club.batowen.listener.core.util.ReplyRunnable;

public enum ServerPacket {

    /**
     * ANNOUNCE GLOBALLY A MESSAGE
     * USED MORE IN BUNGEE
     */
    ANNOUNCE_GLOBALMESSAGE(1, (data,reply)-> {
        if(data.has("message")) {
            reply.announceGlobalMessage(data.get("message").getAsString());
        }
    }),

    /**
     * SET A GROUP ON A PLAYER
     * 
     */
    SETGROUP(2, (data,reply)-> {
        if(data.has("group") && data.has("player")) {
            reply.setGroup(data.get("group").getAsString(), data.get("player").getAsString());
        }
    }),

    /**
     * EXECUTE A CONSOLE COMMAND
     * 
     */
    CONSOLE_COMMAND(99, (data,reply)-> {
        if(data.has("command")) {
            reply.executeConsolCommand(data.get("command").getAsString());
        }
    });

    private int id;
    private ReplyRunnable executor;

    ServerPacket(int id, ReplyRunnable execution) {
        this.id = id;
        this.executor=execution;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }


    public void executeReply(JsonObject object, NodeReply reply){
        executor.exceute(object, reply);
    }

    public static ServerPacket[] all(){
        return values();
    }
}