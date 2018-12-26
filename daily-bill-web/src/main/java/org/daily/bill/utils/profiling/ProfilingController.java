package org.daily.bill.utils.profiling;

/**
 * Created by vano on 12.9.17.
 */
public class ProfilingController implements ProfilingControllerMBean {
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
