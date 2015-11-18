package com.example.sails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sails.response.NoContent;

import io.nucleos.sailsio.Callback;
import io.nucleos.sailsio.Response;
import io.nucleos.sailsio.SailsIO;
import io.nucleos.sailsio.event.RawEvent;

public class MainActivity extends AppCompatActivity
    implements RawEvent.OnConnect, View.OnClickListener {

    private SailsIO sails;
    private UserService userService;
    private int currentStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sails = new SailsIO.Builder()
                .autoConnect(true)
                .baseUrl("192.168.1.143:8443")
                .interceptor(new MyInterceptor(this))
                .build();

        this.sails.onConnect(this);
    }

    @Override
    public void onEvent(Object... args) {
        this.userService = this.sails.create(UserService.class);
        Toast.makeText(MainActivity.this, "Socket connected . . . ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_status:
                onClickChangeStatus();
                break;
        }
    }

    private void onClickChangeStatus() {
        this.currentStatus = this.currentStatus == 1 ? 0 : 1;
        changeStatus(this.currentStatus);
    }

    private void changeStatus(int status) {
        UserStatus userStatus = new UserStatus();
        userStatus.status = status;

        this.userService.updateStatus(userStatus)
                .enqueue(new Callback<NoContent>() {
                    @Override
                    public void onResponse(Response<NoContent> response) {
                        Toast.makeText(MainActivity.this, "Success on update status", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(MainActivity.this, "Error on update status", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
