package net.clubedocomputador.portinarimirror.features.splash;

import android.os.Bundle;

import javax.inject.Inject;

import net.clubedocomputador.portinarimirror.data.local.PreferencesHelper;
import net.clubedocomputador.portinarimirror.data.model.User;
import net.clubedocomputador.portinarimirror.features.base.BaseActivity;
import net.clubedocomputador.portinarimirror.features.dashboard.DashboardActivity;
import net.clubedocomputador.portinarimirror.features.faces.FaceActivity;
import net.clubedocomputador.portinarimirror.features.users.UsersActivity;
import net.clubedocomputador.portinarimirror.injection.component.ActivityComponent;

import java.util.ArrayList;
import java.util.List;

import static net.clubedocomputador.portinarimirror.Constants.Preferences.USERS_REGISTERED;


public class SplashActivity extends BaseActivity {

    @Inject
    PreferencesHelper preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<User> usersList = preferences.get(USERS_REGISTERED, new ArrayList<User>());

        if (usersList.isEmpty()) {
            startActivity(UsersActivity.getStartIntent(this));
        } else {
            startActivity(DashboardActivity.getStartIntent(this));
        }

        finish();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {

    }

    @Override
    protected void detachPresenter() {

    }
}