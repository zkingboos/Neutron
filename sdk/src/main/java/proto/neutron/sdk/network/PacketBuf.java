package proto.neutron.sdk.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public final class PacketBuf extends ByteBuf {

    @Delegate
    private ByteBuf buf;

    public int readVarInt() {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public long readVarLong() {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                throw new RuntimeException("VarLong is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public String readString(int maxLength) {
        int size = readVarInt();

        if(size < 0) throw new RuntimeException("String is too small");
        else if(size > maxLength * 4) throw new RuntimeException("String is too big");

        byte[] bytes = new byte[size];
        buf.readBytes(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public UUID readUniqueId() {
        return new UUID(readLong(), readLong());
    }

    public void writeVarInt(int value) {
        do {
            byte temp = (byte)(value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writeByte(temp);
        } while (value != 0);
    }

    public void writeVarLong(long value) {
        do {
            byte temp = (byte)(value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writeByte(temp);
        } while (value != 0);
    }

    public void writeString(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        if(bytes.length > Short.MAX_VALUE) throw new RuntimeException("String is too big");

        writeVarInt(bytes.length);
        writeBytes(bytes);
    }
}
