/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-6-2
 */
package com.rop.listener;

import com.rop.RopRequestContext;
import com.rop.event.AfterDoServiceEvent;
import com.rop.event.RopEventListener;
import com.rop.marshaller.MessageMarshallerUtils;

import java.util.Map;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 *
 *记录调用日志
 */
public class LoggerDoServiceEventListener implements RopEventListener<AfterDoServiceEvent> {

    @Override
    public void onRopEvent(AfterDoServiceEvent ropEvent) {
        RopRequestContext ropRequestContext = ropEvent.getRopRequestContext();
        if(ropRequestContext != null){
            ropRequestContext.getAppKey();
            ropRequestContext.getMethod();
            ropRequestContext.getVersion();
            ropRequestContext.getIp();
            ropRequestContext.getServiceBeginTime();
            ropRequestContext.getServiceEndTime();
            Map<String,String> allParams = ropRequestContext.getAllParams();
            String message = MessageMarshallerUtils.asUrlString(allParams);
            System.out.println("message("+ropEvent.getServiceEndTime()+")"+message);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

