package com.sunmi.sunmit2demo.ble.listener;


import android.bluetooth.BluetoothDevice;

import com.sunmi.sunmit2demo.ble.data.model.BSDevice;
import com.sunmi.sunmit2demo.ble.enums.EnumStatus;

/**
 * Created by yaoh on 2018/11/16.
 */

public interface BSScanListener {

    public void onScanResult(EnumStatus status, BSDevice device, BluetoothDevice bluetoothDevice);

}
