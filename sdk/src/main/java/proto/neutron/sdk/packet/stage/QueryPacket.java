package proto.neutron.sdk.packet.stage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.ToString;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;

@ToString
public final class QueryPacket extends ProtoPacket {

    @Override
    public void read(PacketBuf buf) {
        super.read(buf);
    }

    @Override
    public void write(PacketBuf buf) {
        JsonObject mainContent = new JsonObject();

        JsonObject version = new JsonObject();
        version.addProperty("name", "1.8.9");
        version.addProperty("protocol", 47);

        JsonObject players = new JsonObject();
        players.addProperty("max", 100);
        players.addProperty("online", 1);

        JsonArray playersArray = new JsonArray();
        JsonObject playerObject = new JsonObject();
        playerObject.addProperty("name", "zKingBoos_");
        playerObject.addProperty("id", "1ea54d24-ee7f-4fdb-8b53-ba475c2a0fb6");

        playersArray.add(playerObject);

        players.add("sample", playersArray);

        mainContent.add("players", players);

        JsonObject description = new JsonObject();
        description.addProperty("text", "Hello World!");
        mainContent.add("description", description);
        mainContent.add("version", version);

        String jsonContent = new Gson().toJson(mainContent);
        System.out.println(jsonContent);

        buf.writeByte(0x00);
        buf.writeString(jsonContent);

        System.out.println("Enviado");
    }
}
