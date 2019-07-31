package club.batowen.listener.core.util;

import com.google.gson.JsonObject;
import club.batowen.listener.core.util.NodeReply;

@FunctionalInterface
public interface ReplyRunnable {

    void exceute(JsonObject object , NodeReply reply);
}