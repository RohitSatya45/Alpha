package com.alpha.core.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Iterator;
import java.util.Set;

@Component(service= WorkflowProcess.class, property={"process.label=Adding Property"})
public class AddingPropertyWorkflow implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(AddingPropertyWorkflow.class);


    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOG.info("--------------------------------------------");
        try {
            WorkflowData workflowData = workItem.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class);
                String path = workflowData.getPayload().toString() + "/jcr:content";
                Node node = (Node) session.getItem(path);
                String[] processArgs = metaDataMap.get("PROCESS_ARGS", String.class).toString().split(",");
                MetaDataMap mdm=workItem.getWorkflow().getWorkflowData().getMetaDataMap();
                for (String wfArgs : processArgs) {
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];
                    if (node != null) {
                        mdm.put(prop,value);
                        node.setProperty(prop, value);
                    }
                }
                Set<String> keyset=mdm.keySet();
                Iterator<String>i= keyset.iterator();
                while(i.hasNext()){
                    String key=i.next();
                    LOG.info("\n Item Key:{},value:{}",key,mdm.get(key));
            }
            }
        } catch (Exception e) {
            LOG.info("\n error message {}", e.getMessage());
        }
    }
}

