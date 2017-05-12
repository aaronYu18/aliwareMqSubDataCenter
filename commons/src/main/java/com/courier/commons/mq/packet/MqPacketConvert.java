package com.courier.commons.mq.packet;

import com.courier.commons.api.pay.alipay.param.AlipayEntity;
import com.courier.commons.api.pay.alipay.param.AlipayReq;
import com.courier.commons.push.JPush;
import com.courier.sdk.constant.Enumerate;

import java.util.Map;

/**
 * Created by admin on 2015/11/14.
 */
public class MqPacketConvert{

    public static MQPacket buildMqPacket(Object req) {
        PacketHeader header = new PacketHeader();
        PacketBody body = new PacketBody(req);

        return new MQPacket(body, header);
    }

    public static MQPacket buildAlipayMQPacket(AlipayReq alipayReq){
        return buildAlipayMQPacket(alipayReq, null);
    }

    public static MQPacket buildAlipayMQPacket(AlipayReq alipayReq, Map<String, String> ext) {
        PacketHeader header = new PacketHeader();
        AlipayEntity alipayEntity = new AlipayEntity();
        alipayEntity.setAlipayReq(alipayReq);
        PacketBody body = new PacketBody(alipayEntity, ext);

        return new MQPacket(body, header);
    }

    public static MQPacket buildPushMQPacket(String jobNo, String title, String content){
        JPush jPush = new JPush();
        jPush.setAlias(jobNo);
        jPush.setContentType(Enumerate.ContentType.TEXTMESSAGE);
        jPush.setContent(title);
        jPush.getExtMap().put(JPush.JPushExtType.extcontent.toString(), content);

        MQPacket packet = new MQPacket();
        packet.setHeader(new PacketHeader());
        PacketBody body = new PacketBody();
        body.setContent(jPush);
        packet.setBody(body);
        return packet;
    }
}
