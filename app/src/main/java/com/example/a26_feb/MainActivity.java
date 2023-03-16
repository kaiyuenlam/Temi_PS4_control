package com.example.a26_feb;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnMovementVelocityChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.util.ArrayList;

//import com.robotemi.sdk.Robot;
//import com.robotemi.sdk.listeners.OnRobotReadyListener;

public class MainActivity extends AppCompatActivity implements OnRobotReadyListener, OnMovementVelocityChangedListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static Robot robot;

    private Button buttonBluetooth;

    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> arrayListBlueToothDevice;
    IntentFilter intentFilter;

    TextView textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.tv1);
        textView2 = findViewById(R.id.tv2);
        textView3 = findViewById(R.id.tv3);
        robot = Robot.getInstance();
        //bindView();
    }

    /*
    private void bindView() {
        buttonBluetooth = findViewById(R.id.BluetoothBtn);

        buttonBluetooth.setOnClickListener(this);
    }

    private void bluetoothSetup() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        if (bluetoothAdapter == null) {
            //Device does not support Bluetooth
        } else {

            if (!bluetoothAdapter.isEnabled()) {
                // Bluetooth is not enabled, ask user to turn it on
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                someActivityResultLauncher.launch(enableBtIntent);
            }
            arrayListBlueToothDevice = new ArrayList<>();
        }
        registerReceiver(receiver, intentFilter);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Toast.makeText(getApplicationContext(), "BlueTooth is Enable", Toast.LENGTH_LONG).show();
                    }
                }
            });

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Log.e(TAG, device.getAddress());
                arrayListBlueToothDevice.add(device.getName() + "\n" + device.getAddress());

            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismiss progress dialog
            }
        }
    };

    private void callAlertDialog(ArrayList<String> deviceStrs) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.select_dialog_singlechoice,
                deviceStrs.toArray(new String[deviceStrs.size()]));

        builder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //selected bluetooth device
                dialog.dismiss();
                int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();


            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BluetoothBtn:
                bluetoothSetup();
                bluetoothAdapter.startDiscovery();
                callAlertDialog(arrayListBlueToothDevice);
                break;
        }
    }

     */
    private void makeTextView1(String text) {
        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        textView1.setText(text);
    }

    private void makeTextView2(String text) {
        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        textView2.setText(text);
    }

    private void makeTextView3(String text) {
        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        textView3.setText(text);
    }


    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                robot.onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnRobotReadyListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnRobotReadyListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = false;
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
                == InputDevice.SOURCE_GAMEPAD) {
            Toast.makeText(getApplicationContext(), Integer.toString(keyCode), Toast.LENGTH_SHORT).show();
            if (event.getRepeatCount() == 0) {

                if (keyCode == 23)
                    makeTextView1("Cross pressed");

                if (keyCode == 4)
                    makeTextView1("Circle pressed");

                if (keyCode == 67)
                    makeTextView1("Square pressed");

                if (keyCode == 62)
                    makeTextView1("Triangle pressed");

                if (keyCode == 102)
                    makeTextView1("L1 pressed");

                if (keyCode == 104)
                    makeTextView1("L2 pressed");

                if (keyCode == 103)
                    makeTextView1("R1 pressed");

                if (keyCode == 105)
                    makeTextView1("R2 pressed");

                if (keyCode == 82)
                    makeTextView1("SHARE pressed");


            }
            if (handled) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {


        // Check that the event came from a game controller
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
                InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE) {


            // Process all historical movement samples in the batch
            final int historySize = event.getHistorySize();

            // Process the movements starting from the
            // earliest historical position in the batch
            for (int i = 0; i < historySize; i++) {
                // Process the event at historical position i
                processJoystickInput(event, i);
            }

            // Process the current movement sample in the batch (position -1)
            processJoystickInput(event, -1);
            return true;
        }
        return super.onGenericMotionEvent(event);
    }


    private void processJoystickInput(MotionEvent event,
                                      int historyPos) {

        InputDevice mInputDevice = event.getDevice();

        // Calculate the horizontal distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat axis, or the right control stick.
        float lx = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_X, historyPos);

        float rx = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Z, historyPos);

        float ly = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Y, historyPos);

        float ry = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_RZ, historyPos);


        makeTextView2("LX:" + lx + "");
        makeTextView2("LY:" + ly + "");
        makeTextView3("RX:" + rx + "");
        makeTextView2("RY:" + ry + "");

        robot.skidJoy(-1 * ry, -1 * rx);
    }

    private static float getCenteredAxis(MotionEvent event,
                                         InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range =
                device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis) :
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }


    @Override
    public void onMovementVelocityChanged(float v) {
       // textView3.setText(Float.toString(v));
    }
}