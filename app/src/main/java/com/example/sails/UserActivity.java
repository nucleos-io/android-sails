package com.example.sails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sails.model.Hire;
import com.example.sails.model.Hustler;
import com.example.sails.services.UserService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.nucleos.sailsio.Callback;
import io.nucleos.sailsio.Response;

public class UserActivity extends SailsActivity {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Constants
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public final String TAG = "TAG_" + UserActivity.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Fields
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private UserService mUserService;
    private List<Hustler> mHustlers;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- UI references
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    RecyclerView mHustlersRecyclerView;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Methods
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mHustlersRecyclerView = (RecyclerView) findViewById(R.id.recycler_hustlers);
    }

    @Override
    protected void onConnect() {
        Log.d(TAG, "onConnect . . . . . ");
        initializeServices();
    }

    @OnItemClick(R.id.recycler_hustlers)
    public void onClickItem(int position) {
        hire(position);
    }

    private void initializeServices() {
        mUserService = mSails.create(UserService.class);
        mUserService.listHustlers()
                .enqueue(new Callback<List<Hustler>>() {
                    @Override
                    public void onResponse(Response<List<Hustler>> response) {
                        Log.d(TAG, "onResponse listServices" + response);
                        if (response.getStatusCode() == 200) {
                            //Toast.makeText(UserActivity.this, "Success on load list", Toast.LENGTH_SHORT).show();
                            mHustlers = response.getData();
                            Log.d(TAG, "Hustlers: " + mHustlers.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showList();
                                }
                            });

                        } else {
                            //Toast.makeText(UserActivity.this, "Error on load list", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Status code: " + response.getStatusCode());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        Log.e(TAG, t.getMessage());
                    }
                });

        mUserService.listenHire()
                .listen(new Callback<Hire>() {
                    @Override
                    public void onResponse(final Response<Hire> response) {
                        Log.d(TAG, "listenHire onResponse: " + response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showHireResponseDialog(response.getData());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        Log.d(TAG, "listenHire onFailure: " + t.getMessage());
                    }
                });
    }

    private void showList() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHustlersRecyclerView.setLayoutManager(linearLayoutManager);

        final HustlersAdapter adapter =  new HustlersAdapter(mHustlers);
        mHustlersRecyclerView.setAdapter(adapter);
    }

    private void hire(int hustlerPosition) {
        final Hustler hustler = mHustlers.get(hustlerPosition);
        final Hire hire = new Hire();

        hire.setIdHustler(hustler.getId());
        hire.setService(hustler.getServices().get(0));
        hire.setTimeTotal(10);

        mUserService.hire(hire)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Response<Void> response) {
                        Log.d(TAG, "onResponse hire: " + response);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "onFailure hire: " + t.getMessage());
                    }
                });
    }

    private void showHireResponseDialog(Hire hire) {
        HireResponseDialog hireResponseDialog = new HireResponseDialog();
        hireResponseDialog.setHire(hire);
        hireResponseDialog.show(getSupportFragmentManager(), TAG);
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Class
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    class HustlersAdapter extends RecyclerView.Adapter<HustlersAdapter.ViewHolder> {

        List<Hustler> mListHustler;

        public HustlersAdapter(List<Hustler> listHustler) {
            mListHustler = listHustler;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hustler, parent, false);
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mNameTextView.setText(mListHustler.get(position).getUser().getName());
            holder.mHireButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick on hire button");
                    hire(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListHustler.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            @Bind(R.id.text_name)
            TextView mNameTextView;

            @Bind(R.id.btn_hire)
            Button mHireButton;

            public ViewHolder(View rootView) {
                super(rootView);
                ButterKnife.bind(this, rootView);
            }
        }
    }


}