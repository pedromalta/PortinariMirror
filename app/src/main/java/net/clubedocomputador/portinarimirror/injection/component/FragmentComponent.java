package net.clubedocomputador.portinarimirror.injection.component;

import dagger.Subcomponent;

import net.clubedocomputador.portinarimirror.features.users.widgets.NewUserFragment;
import net.clubedocomputador.portinarimirror.injection.PerFragment;
import net.clubedocomputador.portinarimirror.injection.module.FragmentModule;


/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(NewUserFragment newUserFragment);

}
