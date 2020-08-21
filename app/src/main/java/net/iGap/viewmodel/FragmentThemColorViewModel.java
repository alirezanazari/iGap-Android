package net.iGap.viewmodel;
/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.iGap.G;
import net.iGap.databinding.FragmentThemColorBinding;
import net.iGap.fragments.FragmentThemColor;
import net.iGap.model.ChangeTheme;
import net.iGap.module.SHP_SETTING;
import net.iGap.module.Theme;

import static android.content.Context.MODE_PRIVATE;

public class FragmentThemColorViewModel extends ViewModel {

    private SharedPreferences sharedPreferences;
    private FragmentThemColor fragmentThemColor;
    private FragmentThemColorBinding fragmentThemColorBinding;
    public MutableLiveData<Boolean> goToThemeColorCustomPage = new MutableLiveData<>();
    public MutableLiveData<Boolean> goToDarkThemePage = new MutableLiveData<>();
    public MutableLiveData<ChangeTheme> showDialogChangeTheme = new MutableLiveData<>();
    public MutableLiveData<Boolean> reCreateApp = new MutableLiveData<>();


    public FragmentThemColorViewModel(FragmentThemColor fragmentThemColor, FragmentThemColorBinding fragmentThemColorBinding) {
        this.fragmentThemColor = fragmentThemColor;
        this.fragmentThemColorBinding = fragmentThemColorBinding;
        getInfo();
    }

    //===============================================================================
    //================================Event Listeners================================
    //===============================================================================


    public void onClickThemCustom(View v) {
        goToThemeColorCustomPage.setValue(true);
    }

    public void onClickThemeDefault(View v) {
        setSetting(Theme.DEFAULT, false);

    }

    public void onClickThemeDark(View v) {
        goToDarkThemePage.setValue(true);
    }

    public void onClickThemeRed(View v) {
        setSetting(Theme.RED, false);

    }

    public void onClickThemePink(View v) {
        setSetting(Theme.PINK, false);
    }

    public void onClickThemePurple(View v) {
        setSetting(Theme.PURPLE, false);
    }

    public void onClickThemeDeepPurple(View v) {

    }

    public void onClickThemeIndigo(View v) {
    }

    public void onClickThemeBlue(View v) {
        setSetting(Theme.BLUE, false);
    }

    public void onClickThemeLightBlue(View v) {

    }

    public void onClickThemeCyan(View v) {

    }

    public void onClickThemeTeal(View v) {

    }

    public void onClickThemeGreen(View v) {
        setSetting(Theme.GREEN, false);
    }

    public void onClickThemeLightGreen(View v) {

    }

    public void onClickThemeLime(View v) {

    }

    public void onClickThemeYellow(View v) {

    }

    public void onClickThemeAmber(View v) {
        setSetting(Theme.AMBER, false);
    }

    public void onClickThemeOrange(View v) {
        setSetting(Theme.ORANGE, false);
    }

    public void onClickThemeDeepOrange(View v) {

    }

    public void onClickThemeBrown(View v) {

    }

    public void onClickThemeGrey(View v) {
        setSetting(Theme.GREY, false);

    }

    public void onClickThemeBlueGrey(View v) {

    }

    public void onClickThemeBlueGreyComplete(View v) {

    }

    public void onClickThemeIndigoComplete(View v) {

    }

    public void onClickThemeBrownComplete(View v) {

    }

    public void onClickThemeTealComplete(View v) {

    }

    public void onClickThemeGreyComplete(View v) {

    }

    private void setSetting(int config, boolean isDark) {
        showDialogChangeTheme.setValue(new ChangeTheme(config, isDark));
    }

    public void setNewTheme(ChangeTheme newTheme, boolean applyColorsToCustomize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHP_SETTING.KEY_THEME_COLOR, newTheme.getConfig());
        editor.putBoolean(SHP_SETTING.KEY_THEME_DARK, newTheme.isDark());
        editor.apply();
        if (applyColorsToCustomize) {
//           editor.putString(SHP_SETTING.KEY_APP_BAR_COLOR, G.appBarColor);
//           editor.putString(SHP_SETTING.KEY_NOTIFICATION_COLOR, G.notificationColor);
//            editor.putString(SHP_SETTING.KEY_TOGGLE_BOTTON_COLOR, G.toggleButtonColor);
//            editor.putString(SHP_SETTING.KEY_SEND_AND_ATTACH_ICON_COLOR, G.attachmentColor);
//            editor.putString(SHP_SETTING.KEY_FONT_HEADER_COLOR, G.headerTextColor);
//            editor.putString(SHP_SETTING.KEY_PROGRES_COLOR, G.progressColor);
            editor.apply();
        }
        reCreateApp.setValue(true);
    }

    private void getInfo() {
        sharedPreferences = G.context.getSharedPreferences(SHP_SETTING.FILE_NAME, MODE_PRIVATE);

        String appBarColor = sharedPreferences.getString(SHP_SETTING.KEY_APP_BAR_COLOR, Theme.default_appBarColor);
        if (fragmentThemColor != null) {
            GradientDrawable circleCustomColor = (GradientDrawable) fragmentThemColorBinding.themeCustom1.getBackground();
            circleCustomColor.setColor(Color.parseColor(appBarColor));

            GradientDrawable circleDefaultColor = (GradientDrawable) fragmentThemColorBinding.themeDefault1.getBackground();
            circleDefaultColor.setColor(Color.parseColor(Theme.default_appBarColor));

            GradientDrawable circleDarkColor = (GradientDrawable) fragmentThemColorBinding.themeDark1.getBackground();
            circleDarkColor.setColor(Color.parseColor(Theme.default_dark_appBarColor));

            GradientDrawable circleRedColor = (GradientDrawable) fragmentThemColorBinding.themeRed1.getBackground();
            circleRedColor.setColor(Color.parseColor(Theme.default_red_appBarColor));

            GradientDrawable circlePinkColor = (GradientDrawable) fragmentThemColorBinding.themePink1.getBackground();
            circlePinkColor.setColor(Color.parseColor(Theme.default_Pink_appBarColor));

            GradientDrawable circlePurpleColor = (GradientDrawable) fragmentThemColorBinding.themePurple1.getBackground();
            circlePurpleColor.setColor(Color.parseColor(Theme.default_purple_appBarColor));

            GradientDrawable circleDeepPurpleColor = (GradientDrawable) fragmentThemColorBinding.themeDeepPurple1.getBackground();
            circleDeepPurpleColor.setColor(Color.parseColor(Theme.default_deepPurple_appBarColor));

            GradientDrawable circleDeepIndigoColor = (GradientDrawable) fragmentThemColorBinding.themeIndigo.getBackground();
            circleDeepIndigoColor.setColor(Color.parseColor(Theme.default_indigo_appBarColor));

            GradientDrawable circleBlueColor = (GradientDrawable) fragmentThemColorBinding.themeBlue.getBackground();
            circleBlueColor.setColor(Color.parseColor(Theme.default_blue_appBarColor));

            GradientDrawable circleLightBlueColor = (GradientDrawable) fragmentThemColorBinding.themeLightBlue.getBackground();
            circleLightBlueColor.setColor(Color.parseColor(Theme.default_lightBlue_appBarColor));

            GradientDrawable circleCyanColor = (GradientDrawable) fragmentThemColorBinding.themeCyan.getBackground();
            circleCyanColor.setColor(Color.parseColor(Theme.default_cyan_appBarColor));

            GradientDrawable circleTealColor = (GradientDrawable) fragmentThemColorBinding.themeTeal.getBackground();
            circleTealColor.setColor(Color.parseColor(Theme.default_teal_appBarColor));

            GradientDrawable circleGreenColor = (GradientDrawable) fragmentThemColorBinding.themeGreen.getBackground();
            circleGreenColor.setColor(Color.parseColor(Theme.default_green_appBarColor));

            GradientDrawable circleLightGreenColor = (GradientDrawable) fragmentThemColorBinding.themeLightGreen.getBackground();
            circleLightGreenColor.setColor(Color.parseColor(Theme.default_lightGreen_appBarColor));

            GradientDrawable circleLimeColor = (GradientDrawable) fragmentThemColorBinding.themeLime.getBackground();
            circleLimeColor.setColor(Color.parseColor(Theme.default_lime_appBarColor));

            GradientDrawable circleYellowColor = (GradientDrawable) fragmentThemColorBinding.themeYellow.getBackground();
            circleYellowColor.setColor(Color.parseColor(Theme.default_yellow_appBarColor));

            GradientDrawable circleAmberColor = (GradientDrawable) fragmentThemColorBinding.themeAmber.getBackground();
            circleAmberColor.setColor(Color.parseColor(Theme.default_amber_appBarColor));

            GradientDrawable circleOrangeColor = (GradientDrawable) fragmentThemColorBinding.themeOrange.getBackground();
            circleOrangeColor.setColor(Color.parseColor(Theme.default_orange_appBarColor));

            GradientDrawable circleDeepOrangeColor = (GradientDrawable) fragmentThemColorBinding.themeDeepOrange.getBackground();
            circleDeepOrangeColor.setColor(Color.parseColor(Theme.default_deepOrange_appBarColor));

            GradientDrawable circleBrownColor = (GradientDrawable) fragmentThemColorBinding.themeBrown.getBackground();
            circleBrownColor.setColor(Color.parseColor(Theme.default_brown_appBarColor));

            GradientDrawable circleGreyColor = (GradientDrawable) fragmentThemColorBinding.themeGrey.getBackground();
            circleGreyColor.setColor(Color.parseColor(Theme.default_grey_appBarColor));

            GradientDrawable circleBlueGreyColor = (GradientDrawable) fragmentThemColorBinding.themeBlueGrey.getBackground();
            circleBlueGreyColor.setColor(Color.parseColor(Theme.default_blueGrey_appBarColor));

            GradientDrawable circleBlueGreyCompleteColor = (GradientDrawable) fragmentThemColorBinding.themeBlueGreyComplete.getBackground();
            circleBlueGreyCompleteColor.setColor(Color.parseColor(Theme.default_blueGrey_appBarColor));

            GradientDrawable circleIndigoCompleteColor = (GradientDrawable) fragmentThemColorBinding.themeIndigoComplete.getBackground();
            circleIndigoCompleteColor.setColor(Color.parseColor(Theme.default_indigo_appBarColor));

            GradientDrawable circleBrownCompleteColor = (GradientDrawable) fragmentThemColorBinding.themeBrownComplete.getBackground();
            circleBrownCompleteColor.setColor(Color.parseColor(Theme.default_brown_appBarColor));

            GradientDrawable circleTealCompleteColor = (GradientDrawable) fragmentThemColorBinding.themeTealComplete.getBackground();
            circleTealCompleteColor.setColor(Color.parseColor(Theme.default_teal_appBarColor));

            GradientDrawable circleGreyCompleteColor = (GradientDrawable) fragmentThemColorBinding.themeGreyComplete.getBackground();
            circleGreyCompleteColor.setColor(Color.parseColor(Theme.default_grey_appBarColor));


            switch (G.themeColor) {
                /*case Theme.CUSTOM:
                    fragmentThemColorBinding.iconCustom.setVisibility(View.VISIBLE);
                    break;*/
                case Theme.DEFAULT:

                    fragmentThemColorBinding.iconDefault.setVisibility(View.VISIBLE);
                    break;
                case Theme.DARK:
                    fragmentThemColorBinding.iconDark.setVisibility(View.VISIBLE);
                    break;
                case Theme.RED:
                    fragmentThemColorBinding.iconRed.setVisibility(View.VISIBLE);
                    break;
                case Theme.PINK:
                    fragmentThemColorBinding.iconPink.setVisibility(View.VISIBLE);
                    break;
                case Theme.PURPLE:
                    fragmentThemColorBinding.iconPurple.setVisibility(View.VISIBLE);
                    break;
                /*case Theme.DEEPPURPLE:
                    fragmentThemColorBinding.iconDeepPurple.setVisibility(View.VISIBLE);
                    break;*/
                /*case Theme.INDIGO:
                    fragmentThemColorBinding.iconIndigo.setVisibility(View.VISIBLE);
                    break;*/
                case Theme.BLUE:
                    fragmentThemColorBinding.iconBlue.setVisibility(View.VISIBLE);
                    break;

                /*case Theme.LIGHT_BLUE:
                    fragmentThemColorBinding.iconLightBlue.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.CYAN:
                    fragmentThemColorBinding.iconCyan.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.TEAL:
                    fragmentThemColorBinding.iconTeal.setVisibility(View.VISIBLE);
                    break;*/

                case Theme.GREEN:
                    fragmentThemColorBinding.iconGreen.setVisibility(View.VISIBLE);
                    break;

                /*case Theme.LIGHT_GREEN:
                    fragmentThemColorBinding.iconLightGreen.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.LIME:
                    fragmentThemColorBinding.iconLime.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.YELLLOW:
                    fragmentThemColorBinding.iconYellow.setVisibility(View.VISIBLE);
                    break;*/
                case Theme.AMBER:
                    fragmentThemColorBinding.iconAmber.setVisibility(View.VISIBLE);
                    break;

                case Theme.ORANGE:
                    fragmentThemColorBinding.iconOrange.setVisibility(View.VISIBLE);
                    break;

                /*case Theme.DEEP_ORANGE:
                    fragmentThemColorBinding.iconDeepOrange.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.BROWN:
                    fragmentThemColorBinding.iconBrown.setVisibility(View.VISIBLE);

                    break;*/
                case Theme.GREY:

                    fragmentThemColorBinding.iconGrey.setVisibility(View.VISIBLE);
                    break;
                /*case Theme.BLUE_GREY:

                    fragmentThemColorBinding.iconBlueGrey.setVisibility(View.VISIBLE);
                    break;*/
                /*case Theme.BLUE_GREY_COMPLETE:

                    fragmentThemColorBinding.iconBlueGreyComplete.setVisibility(View.VISIBLE);
                    break;*/
                /*case Theme.INDIGO_COMPLETE:
                    fragmentThemColorBinding.iconIndigoComplete.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.BROWN_COMPLETE:
                    fragmentThemColorBinding.iconBrownComplete.setVisibility(View.VISIBLE);
                    break;*/
                /*case Theme.TEAL_COMPLETE:
                    fragmentThemColorBinding.iconTealComplete.setVisibility(View.VISIBLE);
                    break;*/

                /*case Theme.GREY_COMPLETE:
                    fragmentThemColorBinding.iconGreyComplete.setVisibility(View.VISIBLE);
                    break;*/
            }


        }

    }
}
