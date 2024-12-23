package com.alpha.core.core.schedulers;

import com.alpha.core.core.config.SchedulerConfig;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component(service = Job.class, immediate = true)
@Designate(ocd = SchedulerConfig.class)
public class JobSchedulers implements Job {
    private static final Logger log = LoggerFactory.getLogger(JobSchedulers.class);

    @Reference
    private Scheduler scheduler;
    private int schedulerId;

    @Activate
    public void activate(SchedulerConfig config) {
        log.info("\n---activated----");
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    public void deactivate(SchedulerConfig config) {
        removeScheduler();
    }

    private void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
        log.info("\n----scheduler removed----");
    }

    private void addScheduler(SchedulerConfig config) {
        ScheduleOptions s = scheduler.EXPR("0 25 14 1/1 * ? *");
        Map<String, Serializable> a=new HashMap<>();
        a.put("country","in");
        a.put("url","india.com");
        s.config(a);
        scheduler.schedule(this, s);

        ScheduleOptions d= scheduler.EXPR("0 26 14 1/1 * ? *");
        Map<String, Serializable> b=new HashMap<>();
        b.put("country","j");
        b.put("url","japan.com");
        d.config(b);
        scheduler.schedule(this, d);

        ScheduleOptions e = scheduler.EXPR("0 27 14 1/1 * ? *");
        Map<String, Serializable> c=new HashMap<>();
        c.put("country","rus");
        c.put("url","russia.com");
        e.config(c);
        scheduler.schedule(this, e);

    }

    @Override
    public void execute(JobContext jobContext) {
        log.info("\n country {}:url {}",jobContext.getConfiguration().get("country"),jobContext.getConfiguration().get("url"));
    }
}
