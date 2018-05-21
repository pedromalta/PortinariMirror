package net.clubedocomputador.portinarimirror.data.local;

import javax.inject.Inject;

import net.clubedocomputador.portinarimirror.data.model.User;
import net.clubedocomputador.portinarimirror.injection.ApplicationContext;

import static net.clubedocomputador.portinarimirror.Constants.Preferences.USER_ONLINE;

@ApplicationContext
public class InstanceHolder {

    private PreferencesHelper preferences;

    @Inject
    public InstanceHolder(PreferencesHelper preferences){
        this.preferences = preferences;
    }

    public synchronized User getUser(){
        return preferences.get(USER_ONLINE);
    }

    public synchronized void setUser(User user){
        preferences.put(USER_ONLINE, user);
    }

    public synchronized void releaseUser(){
        preferences.delete(USER_ONLINE);
    }

}
