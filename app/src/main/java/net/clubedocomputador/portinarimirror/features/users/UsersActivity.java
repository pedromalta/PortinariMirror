package net.clubedocomputador.portinarimirror.features.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import net.clubedocomputador.portinarimirror.R;
import net.clubedocomputador.portinarimirror.data.local.PreferencesHelper;
import net.clubedocomputador.portinarimirror.data.model.User;
import net.clubedocomputador.portinarimirror.features.base.BaseActivity;
import net.clubedocomputador.portinarimirror.features.base.BaseFragment;
import net.clubedocomputador.portinarimirror.features.users.widgets.NewUserFragment;
import net.clubedocomputador.portinarimirror.injection.component.ActivityComponent;

import javax.inject.Inject;

import butterknife.BindView;


public class UsersActivity extends BaseActivity implements UsersMvpView {

    private static final String TAG_FRAGMENT_NEW_USER = "new_user";

    @Inject
    UsersPresenter presenter;

    @Inject
    PreferencesHelper preferences;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            changeMainFragment(TAG_FRAGMENT_NEW_USER);
        }
    }

    public void newUser(View view){
        changeMainFragment(TAG_FRAGMENT_NEW_USER);
    }

    private void changeMainFragment(String fragmentTag){
        BaseFragment fragment = getBaseReportFragmentByTag(fragmentTag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private BaseFragment getBaseReportFragmentByTag(String fragmentTag){
        BaseFragment fragment = (BaseFragment)
                getSupportFragmentManager().findFragmentByTag(fragmentTag);

        if (null == fragment){
            switch(fragmentTag) {
                case TAG_FRAGMENT_NEW_USER:
                    fragment = new NewUserFragment();
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
        return R.layout.activity_face;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        presenter.detachView();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }


    public void showError(Throwable error) {

    }


}

