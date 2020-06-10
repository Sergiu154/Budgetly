package com.example.myapplication;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Objects;

/**
 * Fragment which is inflated into the main view then the option button "Account"
 * from the bottom menu is pressed
 * It displays basic information about the user.
 * The user has the option to change the colour theme to dark mode.
 * The user has the option to opt for notifications.
 * The user can log out from the app.
 * About the app button.
 */
public class AccountFragment extends Fragment {


    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account, container, false);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String emailCurrUSer = currentUser.getEmail();
        int index = emailCurrUSer.indexOf('@');
        String username = emailCurrUSer.substring(0, index);

        TextView nameTextView = view.findViewById(R.id.textView4);
        nameTextView.setText(username);

        TextView emailTextView = view.findViewById(R.id.textView5);
        emailTextView.setText(emailCurrUSer);

        final TextView about = view.findViewById(R.id.textView2);
        about.setVisibility(View.INVISIBLE);
        Button signOutBut = view.findViewById(R.id.signOutBut);
        final ToggleButton notificationToggle = view.findViewById(R.id.notificationToggle);
        Button darkModeButton = view.findViewById(R.id.darkModeButton);
        Button lightModeButton = view.findViewById(R.id.lightModeButton);
        Button aboutButton = view.findViewById(R.id.aboutButton);

        signOutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // default value for switch
        boolean value = false;

        final SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("isChecked", 0);

        // retrieve the selected value by user in application
        value = sharedPreferences.getBoolean("isChecked", value);

        // set the switch to the selected value by user
        notificationToggle.setChecked(value);

        notificationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.edit().putBoolean("isChecked", true).apply();

                    createNotificationChannel();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 20);
                    calendar.set(Calendar.MINUTE, 30);
                    calendar.set(Calendar.SECOND, 0);

                    Intent intent = new Intent(getActivity(), NotificationBroadcast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                    long timeAtButtonClick = System.currentTimeMillis();
                    long secondsInMills = 1000 * 10;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + secondsInMills, pendingIntent);

                } else {
                    sharedPreferences.edit().putBoolean("isChecked", false).apply();
                }
            }
        });

        darkModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        lightModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View view) {
                visible = !visible;
                about.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        return view;
    }

    // setting the communication channel for newer android versions
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description = "Channel for daily remainder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("notifyMe", name, importance);

            channel.setDescription(description);

            NotificationManager notificationManager = Objects.requireNonNull(getActivity()).getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static AccountFragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
