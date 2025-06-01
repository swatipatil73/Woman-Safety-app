package com.asmbcs.woman;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button selectDateButton, saveButton;
    private EditText cycleLengthInput;
    private TextView nextPeriodDate;
    private Calendar selectedDate = Calendar.getInstance(); // Store selected date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }

        selectDateButton = findViewById(R.id.selectDateButton);
        saveButton = findViewById(R.id.saveButton);
        cycleLengthInput = findViewById(R.id.cycleLengthInput);
        nextPeriodDate = findViewById(R.id.nextPeriodDate);

        // Date Picker
        selectDateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(MainActivity.this, (view, year, month, dayOfMonth) -> {
                selectedDate.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectDateButton.setText(dateFormat.format(selectedDate.getTime())); // Show selected date
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Save Button Logic
        saveButton.setOnClickListener(v -> {
            String cycleText = cycleLengthInput.getText().toString();
            if (cycleText.isEmpty()) {
                Toast.makeText(this, "Enter cycle length!", Toast.LENGTH_SHORT).show();
                return;
            }

            int cycleLength = Integer.parseInt(cycleText);
            selectedDate.add(Calendar.DAY_OF_MONTH, cycleLength); // Calculate next period date

            // Display Next Period Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String nextPeriod = dateFormat.format(selectedDate.getTime());
            nextPeriodDate.setText("Next Period Date: " + nextPeriod);

            // Set Alarm 1 Day Before
            selectedDate.add(Calendar.DAY_OF_MONTH, -1);
            setReminder(selectedDate);

            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setReminder(Calendar reminderDate) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Check if the app has permission to schedule exact alarms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Please allow exact alarms in settings.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }

        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pendingIntent);

        // Set the alarm
       // alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderDate.getTimeInMillis(), pendingIntent);
    }

}