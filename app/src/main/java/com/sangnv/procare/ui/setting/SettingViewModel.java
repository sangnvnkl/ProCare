package com.sangnv.procare.ui.setting;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class SettingViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
