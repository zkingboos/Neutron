package proto.neutron.sdk.packet.stage;

import lombok.Getter;
import lombok.ToString;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@ToString
@Getter
public final class LoginPacket extends ProtoPacket {

    private UUID uuid;
    private String username;

    @Override
    public void read(PacketBuf buf) {
        username = buf.readString(16);
        uuid = UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeByte(0x02);
        buf.writeString(uuid.toString());
        buf.writeString(username);
    }
}