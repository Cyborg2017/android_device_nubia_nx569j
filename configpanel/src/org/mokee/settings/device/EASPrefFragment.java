/*
 * Copyright (C) 2016 The CyanogenMod Project
 *           (C) 2017 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mokee.settings.device;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Build;
import android.os.SystemProperties;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.MenuItem;

import org.mokee.internal.util.FileUtils;
import org.mokee.internal.util.PackageManagerUtils;

public class EASPrefFragment extends PreferenceFragment
        implements OnPreferenceChangeListener {

    SwitchPreference mOverfreqMode;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.eas_pref);
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mOverfreqMode = (SwitchPreference) findPreference("eas_preformance");
        updateModeValue();
        mOverfreqMode.setOnPreferenceChangeListener(this);
    }

    public void updateModeValue(){
        String mode = SystemProperties.get("persist.eas.mode", "0");
        mOverfreqMode.setChecked(mode.equals("1"));
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        if (pref == mOverfreqMode) {
            boolean enable = (boolean) newValue;
            SystemProperties.set("persist.eas.mode", enable ? "1" : "0");
            updateModeValue();
            return true;         
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }
}
