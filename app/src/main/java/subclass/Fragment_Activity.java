package subclass;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.com.ysy.fragmentp.MyFragment;
import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/2/3.
 */
public class Fragment_Activity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup group = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_fragmentmain);

        group = (RadioGroup) findViewById(R.id.radiogroup);
        group.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.first:

                Intent intent = new Intent(Fragment_Activity.this, FragmentActivity2.class);
                startActivity(intent);
                break;

            case R.id.second:

                MyFragment fragment2 = new MyFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction beginTransation = fragmentManager.beginTransaction();
                beginTransation.add(R.id.frame, fragment2);
                beginTransation.addToBackStack(null);
                beginTransation.commit();

                break;

            case R.id.thrid:

                break;

            case R.id.fourth:

                break;


        }

    }
}
