package com.courier.core.convert;

import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.mq.packet.PacketBody;
import com.courier.commons.mq.packet.PacketHeader;

import java.io.Serializable;

/**
 * Created by admin on 2015/11/14.
 */
public class MqPacketConvert{

    public static MQPacket buildMqPacket(Object req) {
        PacketHeader header = new PacketHeader();
        PacketBody body = new PacketBody(req);

        return new MQPacket(body, header);
    }
}
