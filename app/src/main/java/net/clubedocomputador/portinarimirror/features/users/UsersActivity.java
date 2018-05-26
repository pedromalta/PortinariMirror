package net.clubedocomputador.portinarimirror.features.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sa90.materialarcmenu.ArcMenu;

import net.clubedocomputador.portinarimirror.R;
import net.clubedocomputador.portinarimirror.data.local.PreferencesHelper;
import net.clubedocomputador.portinarimirror.features.base.BaseActivity;
import net.clubedocomputador.portinarimirror.features.base.BaseFragment;
import net.clubedocomputador.portinarimirror.features.users.widgets.NewUserFragment;
import net.clubedocomputador.portinarimirror.features.users.widgets.UserListFragment;
import net.clubedocomputador.portinarimirror.injection.component.ActivityComponent;

import javax.inject.Inject;

import butterknife.BindView;


public class UsersActivity extends BaseActivity implements UsersMvpView {

    private static final String TAG_FRAGMENT_NEW_USER = "new_user";
    private static final String TAG_FRAGMENT_LIST_USERS = "list_users";

    @Inject
    PreferencesHelper preferences;

    @BindView(R.id.fab_menu)
    ArcMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            changeMainFragment(TAG_FRAGMENT_LIST_USERS);
        }
    }

    public void newUser(View view){
        changeMainFragment(TAG_FRAGMENT_NEW_USER);
    }

    public void listUsers(View view){
        changeMainFragment(TAG_FRAGMENT_LIST_USERS);
    }


    private void changeMainFragment(String fragmentTag){
        BaseFragment fragment = getBaseReportFragmentByTag(fragmentTag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        if (menu.isMenuOpened()){
            menu.toggleMenu();
        }
    }

    private BaseFragment getBaseReportFragmentByTag(String fragmentTag){
        BaseFragment fragment = (BaseFragment)
                getSupportFragmentManager().findFragmentByTag(fragmentTag);

        if (null == fragment){
            switch(fragmentTag) {
                case TAG_FRAGMENT_NEW_USER:
                    fragment = new NewUserFragment();
                    break;
                case TAG_FRAGMENT_LIST_USERS:
                    fragment = new UserListFragment();
                    break;
                default:
                    fragment = new NewUserFragment();
            }
        }

        return fragment;
    }

    @Override
    public void userCreated(String userCreated){

    }

    @Override
    public int getLayout() {
        return R.layout.activity_users;
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

    public static Intent getStartIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }


    public void showError(Throwable error) {

    }


}

