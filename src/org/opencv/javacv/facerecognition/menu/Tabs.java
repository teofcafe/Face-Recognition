package org.opencv.javacv.facerecognition.menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Tabs extends FragmentPagerAdapter {
 
    public Tabs(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new Repositorio();
        case 1:
            return new Configuracoes();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        return 2;
    }
 
}