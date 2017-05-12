package com.courier.core.jingang;

import com.caucho.hessian.client.HessianProxyFactory;
import com.courier.commons.model.soaJinGang.ExpIssueIn;
import com.courier.commons.model.soaJinGang.ExpWaybillIssueIn;
import com.courier.commons.model.soaJinGang.Trace;
import com.courier.commons.util.json.GsonUtil;
import com.courier.core.jingang.common.IExpIsSoaIn;
import com.courier.core.jingang.common.IExpIsSoaOut;
import com.courier.core.jingang.common.IExpTrackSoa;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Created by beyond on 2016/6/15.
 */
public class SoaInterface {
    private Logger logger = LoggerFactory.getLogger(SoaInterface.class);
    public Map<String, Trace> trace(String url ,String mailNo){
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        try {
            IExpTrackSoa soa = (IExpTrackSoa)hessianProxyFactory.create(IExpTrackSoa .class, url);
            String issue = soa.track(1,mailNo);
            Map<String, Trace> bean = GsonUtil.getBean(issue, new TypeToken<Map<String, List<Trace>>>(){}.getType());
            logger.info("\"action\":\"trace\",\"msg\":\"success\", \"url\":{}, \"js\":{}, \"result\":{}", url,mailNo, issue);
            return bean;
        } catch (MalformedURLException e) {
            logger.error("\"action\":\"trace\",\"msg\":\"error\", \"url\":{}, \"js\":{}, \"result\":{}", url,mailNo, e.getMessage());
        }
        return  null;
    }

    public Long soaIn(String url, ExpWaybillIssueIn want, String clientId){
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        try {
            IExpIsSoaIn soa = (IExpIsSoaIn)hessianProxyFactory.create(IExpIsSoaIn.class, url);
            String wantJson = GsonUtil.toJson(want);
            Long reTurn = soa.saveIssue(wantJson, clientId);
            logger.info("\"action\":\"soaIn\",\"msg\":\"success\", \"url\":{}, \"clientId\":{}, \"js\":{}, \"result\":{}", url,clientId,GsonUtil.toJson(want), reTurn );
                    return reTurn;
        } catch (MalformedURLException e) {
            logger.error("\"action\":\"soaIn\",\"msg\":\"error\", \"url\":{}, \"clientId\":{}, \"js\":{}, \"result\":{}", url,clientId,GsonUtil.toJson(want) );
        }
        return -1l;
    }

    /**
     * @param dealOrMedia 是否显示处理信息和媒体信息(0/1)
     * @param url
     * @param mailNo
     * @return
     */
    public  Map<String, ExpIssueIn> soaOut(String url,int dealOrMedia ,String mailNo){
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        try {
            IExpIsSoaOut soa = (IExpIsSoaOut)hessianProxyFactory.create(IExpIsSoaOut.class,url);

            String issue = soa.issue(dealOrMedia, mailNo);
            Map<String, ExpIssueIn> bean = GsonUtil.getBean(issue, new TypeToken<Map<String, List<ExpIssueIn>>>() {
            }.getType());
            logger.info("\"action\":\"soaOut\",\"msg\":\"success\", \"url\":{}, \"js\":{}, \"result\":{}", url,mailNo, issue);
            return bean;
        } catch (MalformedURLException e) {
            logger.error("\"action\":\"soaOut\",\"msg\":\"error\", \"url\":{}, \"js\":{}, \"result\":{}", url,mailNo);
        }
        return null;
    }
}
