package net.clubedocomputador.portinarimirror.features.users.widgets;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import net.clubedocomputador.portinarimirror.R;
import net.clubedocomputador.portinarimirror.data.local.PreferencesHelper;
import net.clubedocomputador.portinarimirror.features.base.BaseFragment;
import net.clubedocomputador.portinarimirror.features.users.UsersPresenter;
import net.clubedocomputador.portinarimirror.injection.component.FragmentComponent;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by pedromalta on 28/03/18.
 */

public class NewUserFragment extends BaseFragment{

    @BindView(R.id.input_name)
    TextInputEditText username;

    @BindView(R.id.input_nickname)
    TextInputEditText nickName;


    public void onCreate(Bundle save)
    {
        super.onCreate(save);
        setRetainInstance(true);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_new_user;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected void attachView(){

    }

    @Override
    protected void detachPresenter(){

    }

    public int getTitle(){
        return R.string.title_bar_new_user;
    }



    
}
