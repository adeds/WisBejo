package adeyds.noes.wisbejo.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;

import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.prefs.SharedPref;


import static adeyds.noes.wisbejo.prefs.SharedPref.SP_EMAIL;
import static adeyds.noes.wisbejo.prefs.SharedPref.SP_LOGED;
import static adeyds.noes.wisbejo.prefs.SharedPref.SP_PHOTO_URL;
import static adeyds.noes.wisbejo.util.AppVar.ADDRESS_LOC;
import static android.app.Activity.RESULT_CANCELED;
import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private ImageView imgAvatar;
    private TextView tvKeter;
    private LinearLayout containerLogin;
    private EditText edtLogin, edtKritSar;
    private Button btnSubmitKritSar;
    private SharedPref sharedPref;
    private ProgressBar progressBar;


    static final int RC_SIGN_IN = 100;
    SimpleDateFormat dateFormat;
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = new SharedPref(getActivity().getApplicationContext());
        imgAvatar = view.findViewById(R.id.img_ava);
        edtLogin = view.findViewById(R.id.edt_login);
        edtKritSar = view.findViewById(R.id.edt_kritsar);
        btnSubmitKritSar = view.findViewById(R.id.btn_submit_kritsar);
        progressBar = view.findViewById(R.id.pg_bar);
        progressBar.setVisibility(View.GONE);
        edtLogin.setFocusable(false);

        containerLogin = view.findViewById(R.id.container_login);
        containerLogin.setClickable(true);
        containerLogin.setOnClickListener(this);
        tvKeter = view.findViewById(R.id.tv_keterangan);

        edtLogin.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        btnSubmitKritSar.setOnClickListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sharedPref = new SharedPref(getActivity().getApplicationContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        AndroidNetworking.initialize(getActivity().getApplicationContext());


//        Log.e("LOGER ONCREATE", sharedPref.getSpLoged()+"");

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            sharedPref.saveSPBoolean(SP_LOGED, true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);


                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            edtLogin.setText(user.getEmail());
            tvKeter.setText("Click here to Logout");
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            Glide
                    .with(getActivity().getApplicationContext())
                    .load(user.getPhotoUrl())
                    .thumbnail(0.5f)
                    .apply(new RequestOptions()
                            .circleCrop()
                            .error(R.mipmap.ic_launcher)
                            .placeholder(R.mipmap.ic_launcher))
                    .into(imgAvatar);


        } else {
            sharedPref.saveSPBoolean(SP_LOGED, false);
            edtLogin.setText("HERE");
            tvKeter.setText("Click here to Login");
            Glide
                    .with(getActivity().getApplicationContext())
                    .load(R.mipmap.ic_launcher_round)
                    .thumbnail(0.5f)
                    .apply(new RequestOptions()
                            .circleCrop()
                            .error(R.mipmap.ic_launcher)
                            .placeholder(R.mipmap.ic_launcher))
                    .into(imgAvatar);


        }
        progressBar.setVisibility(View.GONE);
    }

    private void sendKritSar() {
        String tanggal = dateFormat.format(Calendar.getInstance().getTime());
        AndroidNetworking.post("http://" + ADDRESS_LOC + "/PHP/Pantai_API/procedural/feedback.php")
                .addBodyParameter("email", edtLogin.getText().toString())
                .addBodyParameter("pesan", edtKritSar.getText().toString())
                .addBodyParameter("tanggal", tanggal)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject respon = new JSONObject(response.toString());
                            Log.e("resultkritsar", response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.getMessage();


                    }
                });
        Log.e("tanggal", tanggal);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.edt_login:
            case R.id.container_login: {
                progressBar.setVisibility(View.VISIBLE);
                edtLogin.setError(null);
                Log.e("Loged", sharedPref.getSpLoged()+"");
                if (!sharedPref.getSpLoged()) {

                    signIn();
                } else {
                    //signOut();
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("Logout")
                            .setMessage("Are you sure you want to Logout ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   signOut();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    Log.e("negatif", "hore");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
            break;
            case R.id.btn_submit_kritsar: {
                Log.e("LogedKritSar", edtKritSar.getText()+"");
                edtLogin.setError(null);
                edtKritSar.setError(null);
                if (!sharedPref.getSpLoged()) {
                    edtLogin.setError("Login First");
                    Toast.makeText(getContext(), "Login Dahulu", Toast.LENGTH_SHORT).show();
                }else if (edtKritSar.getText().toString().isEmpty()){
                    edtKritSar.setError("Masukkan Pesan");
                    Toast.makeText(getContext(), "Masukkan Pesan yang ingin dikirim", Toast.LENGTH_LONG).show();
                }
                else {

                    sendKritSar();
                    Toast.makeText(getContext(), "Pesan Terkirim", Toast.LENGTH_SHORT).show();
                    edtKritSar.setText("");
                }
            }
            break;
        }

    }
}
