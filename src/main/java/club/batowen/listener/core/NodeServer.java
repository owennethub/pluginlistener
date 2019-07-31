package club.batowen.listener.core;

import java.util.Arrays;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import club.batowen.listener.core.server.ServerPacket;
import club.batowen.listener.core.util.NodeReply;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * SIMPLE WRAPPER AROUND
 * JEDIS CLASS
 */
public final class NodeServer {

    private final Jedis redis;
    
    public NodeServer() {
        this.redis = new Jedis("localhost", 6379);
    }

    public boolean connect() {
        try {
            redis.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redis.isConnected();
    }

    public void close() {
        if(redis.isConnected())
            redis.close();
    }



    public void listen(String channels, NodeReply reply){
        Thread thread = new Thread(() ->  redis.subscribe(new NodePubSub(reply), channels));
        thread.start();
    }

    private final class NodePubSub extends JedisPubSub {

        private final NodeReply reply;
        private final Gson gson;

        public NodePubSub(NodeReply reply) {
            this.reply = reply;
            this.gson = new Gson();
        }

        @Override
        public void onMessage(String channel, String message) {

            JsonObject data = gson.fromJson(message, JsonObject.class);
            if(data.has("id")){
                int packetid = data.get("id").getAsInt();
                Optional<ServerPacket> optional = Arrays.stream(ServerPacket.all()).filter(packet-> packet.getId() == packetid).findFirst();
            
                if(optional.isPresent())
                    optional.get().executeReply(data, reply);
                else
                    System.out.println("Invalid Packet ID , Data: "+ message);
            } else
                System.out.println("Invalid Message , Data: "+ message);
        }
    }
}