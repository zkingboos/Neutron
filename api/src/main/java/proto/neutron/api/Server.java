package proto.neutron.api;

import proto.neutron.api.plugin.Plugin;

import java.util.Set;

public interface Server {

    Set<Plugin> getPlugins();

    void protonServer() throws InterruptedException;
}
