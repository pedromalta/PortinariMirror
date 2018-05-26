package net.clubedocomputador.portinarimirror.features.users.widgets;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import net.clubedocomputador.portinarimirror.R;
import net.clubedocomputador.portinarimirror.features.base.BaseFragment;
import net.clubedocomputador.portinarimirror.injection.component.FragmentComponent;

import butterknife.BindView;


/**
 * Created by pedromalta on 28/03/18.
 */

public class UserListFragment extends BaseFragment{

    @BindView(R.id.list_users)
    RecyclerView listUsers;


    public void onCreate(Bundle save)
    {
        super.onCreate(save);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list_users;
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
