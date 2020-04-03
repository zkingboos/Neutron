package proto.neutron;

import proto.neutron.sdk.plugin.ProtoPlugin;

public class Teste extends ProtoPlugin {

    @Override
    public void onActivate() {
        getPluginManager().getPlayers();
    }
}
