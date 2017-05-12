package com.courier.sdk.packet;

import com.courier.sdk.common.IdEntity;
import com.courier.sdk.common.resp.AppConfigResp;
import com.courier.sdk.common.resp.CheckVersionResp;
import com.courier.sdk.common.resp.DayBothResp;
import com.courier.sdk.common.resp.StatisticResp;
import com.courier.sdk.packet.req.*;
import com.courier.sdk.packet.resp.*;
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
        cmd.put((byte)1, BatchSignReq.class);
        cmd.put((byte)2, LoginReq.class);
        cmd.put((byte)3, CaptchaReq.class);
        cmd.put((byte)4, CollectQueryReq.class);
        cmd.put((byte)5, DeliveryQueryReq.class);
        cmd.put((byte)16, UploadLocationReq.class);
        cmd.put((byte)17, CheckVersionReq.class);
        cmd.put((byte)20, OperationReq.class);
        cmd.put((byte)25, AliReq.class);
        cmd.put((byte)26, ExpressNoReq.class);
        cmd.put((byte)28, FeedbackReq.class);
        cmd.put((byte)33, PayReq.class);
        cmd.put((byte)35, RefundReq.class);
        cmd.put((byte)36, HistoryPayReq.class);

        cmd.put((byte)7, DayBothResp.class);
        cmd.put((byte)8, HomePageResp.class);
        cmd.put((byte)9, LoginResp.class);
        cmd.put((byte)10, StatisticResp.class);
        cmd.put((byte)15, BothOrderResp.class);
        cmd.put((byte)18, CheckVersionResp.class);
        cmd.put((byte)19, AppConfigResp.class);
        cmd.put((byte)21, OperationResp.class);
        cmd.put((byte)22, TrajectoryResp.class);
        cmd.put((byte)23, BatchSignResp.class);
        cmd.put((byte)24, ExpressDetailResp.class);
        cmd.put((byte)27, ExpressNoResp.class);
        cmd.put((byte)29, VReportCharts.class);
        cmd.put((byte)30, VReportTables.class);
        cmd.put((byte)31, FaqVersionResp.class);
        cmd.put((byte)32, FaqResp.class);
        cmd.put((byte)34, PayStatusResp.class);
        cmd.put((byte)37, MyPayResp.class);
        cmd.put((byte)38, AlipayInfoResp.class);
        cmd.put((byte)47, AuthInfoResp.class);

        cmd.put((byte)12, BankCard.class);
        cmd.put((byte)13, CollectOrder.class);
        cmd.put((byte)14, DeliveryOrder.class);
        cmd.put((byte)46, AuthCollectOrder.class);
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


        if(cls instanceof BatchSignReq)
            typeToken = new TypeToken<CRequestBody<BatchSignReq>>() { };
        else if(cls instanceof LoginReq)
            typeToken = new TypeToken<CRequestBody<LoginReq>>() { };
        else if(cls instanceof CaptchaReq)
            typeToken = new TypeToken<CRequestBody<CaptchaReq>>() { };
        else if(cls instanceof CollectQueryReq)
            typeToken = new TypeToken<CRequestBody<CollectQueryReq>>() { };
        else if(cls instanceof DeliveryQueryReq)
            typeToken = new TypeToken<CRequestBody<DeliveryQueryReq>>() { };
        else if(cls instanceof BankCard)
            typeToken = new TypeToken<CRequestBody<BankCard>>() { };
        else if(cls instanceof CollectOrder)
            typeToken = new TypeToken<CRequestBody<CollectOrder>>() { };
        else if(cls instanceof DeliveryOrder)
            typeToken = new TypeToken<CRequestBody<DeliveryOrder>>() { };
        else if(cls instanceof UploadLocationReq)
            typeToken = new TypeToken<CRequestBody<UploadLocationReq>>() { };
        else if(cls instanceof CheckVersionReq)
            typeToken = new TypeToken<CRequestBody<CheckVersionReq>>() { };
        else if(cls instanceof OperationReq)
            typeToken = new TypeToken<CRequestBody<OperationReq>>() { };
        else if(cls instanceof AliReq)
            typeToken = new TypeToken<CRequestBody<AliReq>>() { };
        else if(cls instanceof ExpressNoReq)
            typeToken = new TypeToken<CRequestBody<ExpressNoReq>>() { };
        else if(cls instanceof FeedbackReq)
            typeToken = new TypeToken<CRequestBody<FeedbackReq>>() { };
        else if(cls instanceof PayReq)
            typeToken = new TypeToken<CRequestBody<PayReq>>() { };
        else if(cls instanceof RefundReq)
            typeToken = new TypeToken<CRequestBody<RefundReq>>() { };
        else if(cls instanceof HistoryPayReq)
            typeToken = new TypeToken<CRequestBody<HistoryPayReq>>() { };
        else if(cls instanceof AuthCollectOrder)
            typeToken = new TypeToken<CRequestBody<AuthCollectOrder>>() { };

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


        if(cls instanceof DayBothResp)
            typeToken = new TypeToken<CResponseBody<DayBothResp>>() { };
        else if(cls instanceof HomePageResp)
            typeToken = new TypeToken<CResponseBody<HomePageResp>>() { };
        else if(cls instanceof LoginResp)
            typeToken = new TypeToken<CResponseBody<LoginResp>>() { };
        else if(cls instanceof StatisticResp)
            typeToken = new TypeToken<CResponseBody<StatisticResp>>() { };
        else if(cls instanceof BankCard)
            typeToken = new TypeToken<CResponseBody<BankCard>>() { };
        else if(cls instanceof CollectOrder)
            typeToken = new TypeToken<CResponseBody<CollectOrder>>() { };
        else if(cls instanceof DeliveryOrder)
            typeToken = new TypeToken<CResponseBody<DeliveryOrder>>() { };
        else if(cls instanceof BothOrderResp)
            typeToken = new TypeToken<CResponseBody<BothOrderResp>>() { };
        else if(cls instanceof CheckVersionResp)
            typeToken = new TypeToken<CResponseBody<CheckVersionResp>>() { };
        else if(cls instanceof AppConfigResp)
            typeToken = new TypeToken<CResponseBody<AppConfigResp>>() { };
        else if(cls instanceof OperationResp)
            typeToken = new TypeToken<CResponseBody<OperationResp>>() { };
        else if(cls instanceof TrajectoryResp)
            typeToken = new TypeToken<CResponseBody<TrajectoryResp>>() { };
        else if(cls instanceof BatchSignResp)
            typeToken = new TypeToken<CResponseBody<BatchSignResp>>() { };
        else if(cls instanceof ExpressDetailResp)
            typeToken = new TypeToken<CResponseBody<ExpressDetailResp>>() { };
        else if(cls instanceof ExpressNoResp)
            typeToken = new TypeToken<CResponseBody<ExpressNoResp>>() { };
        else if(cls instanceof VReportCharts)
            typeToken = new TypeToken<CResponseBody<VReportCharts>>() { };
        else if(cls instanceof VReportTables)
            typeToken = new TypeToken<CResponseBody<VReportTables>>() { };
        else if(cls instanceof FaqVersionResp)
            typeToken = new TypeToken<CResponseBody<FaqVersionResp>>() { };
        else if(cls instanceof FaqResp)
            typeToken = new TypeToken<CResponseBody<FaqResp>>() { };
        else if(cls instanceof PayStatusResp)
            typeToken = new TypeToken<CResponseBody<PayStatusResp>>() { };
        else if(cls instanceof MyPayResp)
            typeToken = new TypeToken<CResponseBody<MyPayResp>>() { };
        else if(cls instanceof AlipayInfoResp)
            typeToken = new TypeToken<CResponseBody<AlipayInfoResp>>() { };
        else if(cls instanceof AuthCollectOrder)
            typeToken = new TypeToken<CResponseBody<AuthCollectOrder>>() { };
        else if(cls instanceof AuthInfoResp)
            typeToken = new TypeToken<CResponseBody<AuthInfoResp>>() { };

        return typeToken;
    }

    public static void main(String[] args) {
        System.out.println(CMDFactory.getClassNameByCMD((byte) 0));
        System.out.println(CMDFactory.getCMDCodeByClassName("com.courier.sdk.packet.resp.RegisterSellerResp"));
    }

}
