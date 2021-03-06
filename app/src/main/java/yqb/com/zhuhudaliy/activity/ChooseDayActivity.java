package yqb.com.zhuhudaliy.activity;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


import yqb.com.zhuhudaliy.R;
import yqb.com.zhuhudaliy.base.BaseActivity;

/**
 * create by yangzikang 2017/7/31
 */
public class ChooseDayActivity extends BaseActivity {

    private Button submit;
    private DatePicker chooseDay;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_day);
        submit = (Button) findViewById(R.id.button_submit);
        chooseDay = (DatePicker) findViewById(R.id.datepicker_choose_day);
    }

    @Override
    protected void initListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("date", getDate());
                setResult(RESULT_OK, intent);
                finish();//此处一定要调用finish()方法
            }
        });

    }

    private String getDate() {
        int year = chooseDay.getYear();
        int month = chooseDay.getMonth() + 1;
        int day = chooseDay.getDayOfMonth();
        String monthString = month < 10 ? "0" + month : "" + month;
        String dayString = day < 10 ? "0" + day : "" + day;
        String date = year + monthString + dayString;
        return date;
    }

}
