package proto.neutron.sdk.plugin;

import lombok.Getter;
import proto.neutron.api.Manager;
import proto.neutron.api.plugin.Plugin;
import proto.neutron.sdk.manager.ProtoPluginManager;

public class ProtoPlugin implements Plugin {

    @Getter
    private Manager pluginManager = new ProtoPluginManager();

    @Override
    public void onCharge() { }

    @Override
    public void onActivate() { }

    @Override
    public void onDeactivate() { }
}
