package com.alpha.core.core.schedulers;

import com.alpha.core.core.config.SchedulerConfig;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = SchedulerConfig.class)
public class RunnableScheduler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RunnableScheduler.class);
    private int schedulerId;

    @Reference
    private Scheduler scheduler;

    private ScheduleOptions scheduleOptions;

    @Override
    public void run() {
        log.info("\n-----executed-----");
    }

    @Activate
    protected void activate(SchedulerConfig config) {
        log.info("\n-----Activating Scheduler-----");
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(SchedulerConfig config) {
        removeScheduler();
    }

    private void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
        log.info("\n-----Scheduler removed----");
    }

    private void addScheduler(SchedulerConfig config) {
        scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(String.valueOf(schedulerId));
        scheduleOptions.canRunConcurrently(true);
        scheduler.schedule(this, scheduleOptions);
        log.info("\n-----Scheduler added----");
        scheduleOptions = scheduler.NOW(3,5);
        scheduler.schedule(this, scheduleOptions);

    }
}
