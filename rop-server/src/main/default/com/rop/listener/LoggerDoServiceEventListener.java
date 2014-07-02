package com.rop.listener;

import com.alibaba.fastjson.JSONObject;
import com.rop.RopRequestContext;
import com.rop.database.IBaseDAO;
import com.rop.event.AfterDoServiceEvent;
import com.rop.event.RopEventListener;
import com.rop.pojo.RopLogger;

import java.util.Date;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 *          <p/>
 *          记录调用日志
 */
public class LoggerDoServiceEventListener implements RopEventListener<AfterDoServiceEvent> {

    private IBaseDAO ropDefaultDAO;

    @Override
    public void onRopEvent(AfterDoServiceEvent ropEvent) {
        RopRequestContext ropRequestContext = ropEvent.getRopRequestContext();
        if (ropRequestContext != null) {
            RopLogger logger = new RopLogger();
            logger.setAppKey(ropRequestContext.getAppKey());
            logger.setMethod(ropRequestContext.getMethod());
            logger.setIp(ropRequestContext.getIp());
            logger.setServiceBeginTime(new Date(ropRequestContext.getServiceBeginTime()));
            logger.setServiceEndTime(new Date(ropRequestContext.getServiceEndTime()));
            logger.setVersion(ropRequestContext.getVersion());
            logger.setRequestContent(JSONObject.toJSONString(ropRequestContext.getAllParams()));
            logger.setResponseContent(JSONObject.toJSONString(ropRequestContext.getRopResponse()));
            logger.setSessionId(ropRequestContext.getSessionId());
            ropDefaultDAO.insert("rop_log",logger);
           // System.out.println("调用日志:" + JSONObject.toJSONString(logger));
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public void setRopDefaultDAO(IBaseDAO ropDefaultDAO) {
        this.ropDefaultDAO = ropDefaultDAO;
    }
}
