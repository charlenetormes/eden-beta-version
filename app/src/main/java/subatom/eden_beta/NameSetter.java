package subatom.eden_beta;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NameSetter extends Activity {

    public class Proceed implements View.OnClickListener{
        public void onClick(View v) {
            if(!(txtName.getText().toString().isEmpty())){
                user_name = txtName.getText().toString();
                startActivity(new Intent(NameSetter.this, MainActivity.class).putExtra("user_name", txtName.getText().toString()));
            }
            else{
                txtNameWarning.setVisibility(View.VISIBLE);
            }
        }
    }

    public static String user_name = null;
    EditText txtName;
    Button btnProceed;
    TextView txtNameWarning;
    private int brightness = 255;
    private ContentResolver cResolver;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);

        txtName = (EditText)findViewById(R.id.txtName);
        btnProceed = (Button)findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new Proceed());
        txtNameWarning = (TextView)findViewById(R.id.txtNameWarning);



    }
}
