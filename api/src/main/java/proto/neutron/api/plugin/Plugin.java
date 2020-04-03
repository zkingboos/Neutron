package proto.neutron.api.plugin;

import proto.neutron.api.Manager;

public interface Plugin {

    public Manager getPluginManager();

    public void onActivate();
    public void onDeactivate();
    public void onCharge();
}
