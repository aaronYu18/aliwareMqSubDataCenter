package com.courier.sdk.manage;

import com.courier.sdk.common.IdEntity;
import com.courier.sdk.common.resp.CheckVersionResp;
import com.courier.sdk.common.resp.StatisticResp;
import com.courier.sdk.manage.req.DeliveryOrderReq;
import com.courier.sdk.manage.req.FeedbackReq;
import com.courier.sdk.manage.req.ManageDetailReq;
import com.courier.sdk.manage.req.ManagePushReq;
import com.courier.sdk.manage.resp.CourierResp;
import com.courier.sdk.manage.resp.DeliveryOrderResp;
import com.courier.sdk.manage.resp.ManageHomePageResp;
import com.courier.sdk.manage.resp.ManageRecentResp;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryan on 15/6/5.
 * 命令源
 */
public class CMDFactory {

    private static Map<Byte, Class> cmd = new HashMap<>();

    static {
        cmd.put((byte)41, ManageDetailReq.class);
        cmd.put((byte)43, ManagePushReq.class);
        cmd.put((byte)44, FeedbackReq.class);
        cmd.put((byte)49, DeliveryOrderReq.class);

        cmd.put((byte)10, StatisticResp.class);
        cmd.put((byte)18, CheckVersionResp.class);
        cmd.put((byte)40, ManageHomePageResp.class);
        cmd.put((byte)42, ManageRecentResp.class);
        cmd.put((byte)45, CourierResp.class);
        cmd.put((byte)48, DeliveryOrderResp.class);

        cmd.put((byte)39, ManagerInfo.class);
    }

    public static Class getClassNameByCMD(byte cmdCode){
        return cmd.get(cmdCode);
    }

    public static Byte getCMDCodeByClassName(String classNamePath){
        for(Map.Entry<Byte, Class> entry : cmd.entrySet())
            if(entry.getValue().equals(classNamePath))
                return entry.getKey();

        return null;
    }

    public static Byte getCMDCodeByClass(Class<?> cls){
        for(Map.Entry<Byte, Class> entry : cmd.entrySet())
            if(entry.getValue() == cls)
                return entry.getKey();

        return null;
    }

    /**
     * 根据cmd 组装 TypeToken
     * @param cmd
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static TypeToken buildTypeTokenByCmd(Byte cmd) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        TypeToken typeToken = new TypeToken<CRequestBody>(){};
        if(cmd == null) return typeToken;

        Class clz = CMDFactory.getClassNameByCMD(cmd);
        IdEntity cls = (IdEntity) Class.forName(clz.getName()).newInstance();


        if(cls instanceof ManageDetailReq)
            typeToken = new TypeToken<CRequestBody<ManageDetailReq>>() { };
        else if(cls instanceof ManagerInfo)
            typeToken = new TypeToken<CRequestBody<ManagerInfo>>() { };
        else if(cls instanceof ManagePushReq)
            typeToken = new TypeToken<CRequestBody<ManagePushReq>>() { };
        else if(cls instanceof FeedbackReq)
            typeToken = new TypeToken<CRequestBody<FeedbackReq>>() { };
        else if(cls instanceof DeliveryOrderReq)
            typeToken = new TypeToken<CRequestBody<DeliveryOrderReq>>() { };

        return typeToken;
    }

    /**
     * 根据cmd 组装 TypeToken
     * @param cmd
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static TypeToken buildRespTypeTokenByCmd(Byte cmd) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        TypeToken typeToken = new TypeToken<CResponseBody>(){};
        if(cmd == null) return typeToken;

        Class clz = CMDFactory.getClassNameByCMD(cmd);
        IdEntity cls = (IdEntity) Class.forName(clz.getName()).newInstance();


        if(cls instanceof StatisticResp)
            typeToken = new TypeToken<CResponseBody<StatisticResp>>() { };
        else if(cls instanceof CheckVersionResp)
            typeToken = new TypeToken<CResponseBody<CheckVersionResp>>() { };
        else if(cls instanceof ManagerInfo)
            typeToken = new TypeToken<CResponseBody<ManagerInfo>>() { };
        else if(cls instanceof ManageHomePageResp)
            typeToken = new TypeToken<CResponseBody<ManageHomePageResp>>() { };
        else if(cls instanceof ManageRecentResp)
            typeToken = new TypeToken<CResponseBody<ManageRecentResp>>() { };
        else if(cls instanceof CourierResp)
            typeToken = new TypeToken<CResponseBody<CourierResp>>() { };
        else if(cls instanceof DeliveryOrderResp)
            typeToken = new TypeToken<CResponseBody<DeliveryOrderResp>>() { };

        return typeToken;
    }

    public static void main(String[] args) {
        System.out.println(CMDFactory.getClassNameByCMD((byte) 0));
        System.out.println(CMDFactory.getCMDCodeByClassName("com.courier.sdk.packet.resp.RegisterSellerResp"));
    }

}
