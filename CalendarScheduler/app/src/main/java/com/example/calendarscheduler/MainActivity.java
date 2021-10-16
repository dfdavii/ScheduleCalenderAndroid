package com.example.calendarscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout;
    TableLayout t1;
    TableRow r1;
    EditText editMonth, date_input, title_input, amt_input, types;
    TextView date_view_res, title_res, deposit_reslt, withdraw_reslt, type_reslt;
    TextView total_income, total_expense, total_money;
    TextView startDate, endDate;
    String per_week = "weekly";
    String per_2_week = "biweekly";
    BudgetReport budgetReport = new BudgetReport();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMonth = findViewById(R.id.monthNumber);
        Button monthbtn = findViewById(R.id.mthBtn);
        layout = findViewById(R.id.layout1);
        t1 = findViewById(R.id.table_row);
        t1.setColumnStretchable(0, true);
        t1.setColumnStretchable(1, true);
        t1.setColumnStretchable(2, true);
        t1.setColumnStretchable(3, true);
        t1.setColumnStretchable(4, true);


        monthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String str = editMonth.getText().toString();
                if(str.isEmpty() || Integer.parseInt(str) < 1 || Integer.parseInt(str) > 12){
                    Toast.makeText(MainActivity.this, "Please, enter a valid month number", Toast.LENGTH_SHORT).show();
                    return;
                }
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Here is your chosen month.",
                                Toast.LENGTH_SHORT).show();
                    }
                };
                runnable.run();
                //String str = editMonth.getText().toString();
                ImageView monthImage = new ImageView(MainActivity.this);
                //monthImage.setImageResource(R.drawable.jan2);
                //addView(monthImage, 338, 251);
                int month = Integer.parseInt(str);
                //cal = new Calendar(month);
                //addView(cal.getImage(), 600, 550);
                switch (month) {
                    case 1:
                        monthImage.setImageResource(R.drawable.jan2);
                        addView(monthImage, 900, 700);
                        break;
                    case 2:
                        monthImage.setImageResource(R.drawable.feb);
                        addView(monthImage, 900, 700);
                        break;
                    case 3:
                        monthImage.setImageResource(R.drawable.mar);
                        addView(monthImage, 900, 700);
                        break;
                    case 4:
                        monthImage.setImageResource(R.drawable.apr);
                        addView(monthImage, 900, 700);
                        break;
                    case 5:
                        monthImage.setImageResource(R.drawable.may);
                        addView(monthImage, 900, 700);
                        break;
                    case 6:
                        monthImage.setImageResource(R.drawable.jun);
                        addView(monthImage, 900, 700);
                        break;
                    case 7:
                        monthImage.setImageResource(R.drawable.jul);
                        addView(monthImage, 900, 700);
                        break;
                    case 8:
                        monthImage.setImageResource(R.drawable.aug);
                        addView(monthImage, 900, 700);
                        break;
                    case 9:
                        monthImage.setImageResource(R.drawable.sep);
                        addView(monthImage, 900, 700);
                        break;
                    case 10:
                        monthImage.setImageResource(R.drawable.oct);
                        addView(monthImage, 900, 700);
                        break;
                    case 11:
                        monthImage.setImageResource(R.drawable.nov);
                        addView(monthImage, 900, 700);
                        break;
                    case 12:
                        monthImage.setImageResource(R.drawable.dec);
                        addView(monthImage, 900, 700);
                        break;
                    default:
                        break;
                }

            }
        });
        // date_input, title_input, amt_input, types
        date_input = findViewById(R.id.dateInput);
        title_input = findViewById(R.id.title2);
        amt_input = findViewById(R.id.amount);
        types = findViewById(R.id.types);

        date_view_res = findViewById(R.id.date_res);
        title_res = findViewById(R.id.title_res);
        deposit_reslt = findViewById(R.id.deposit_res);
        type_reslt = findViewById(R.id.type_res);
        withdraw_reslt = findViewById(R.id.withdraw_res);
        startDate = findViewById(R.id.start);
        endDate = findViewById(R.id.end);

        total_income = findViewById(R.id.total2);
        total_expense = findViewById(R.id.totalExp2);
        total_money = findViewById(R.id.totalmoney2);

        Button incomebtn;
        incomebtn = findViewById(R.id.income);
        incomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // get date/day
                String dayStr = date_input.getText().toString();
                String getTitle = title_input.getText().toString();
                String money_type = types.getText().toString();
                String amountStr = amt_input.getText().toString();
                if(getTitle.isEmpty() || money_type.isEmpty() || dayStr.isEmpty() || amountStr.isEmpty()
                || !range(getMonth(), Integer.parseInt(dayStr))){
                    Toast.makeText(MainActivity.this, "Populate all input boxes and check the month range", Toast.LENGTH_SHORT).show();
                    return;
                }

                final Handler handler = new Handler();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        synchronized (this){
                            try {
                                wait(1000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Putting input into table, update income, " +
                                        "expenses and balance", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();

                int day = Integer.parseInt(dayStr);

                //double amount1 = Double.parseDouble(amountStr);
                String date1 = getMonth() + "-" + day;

                String start_date = getMonth() + "-" + 1;
                String end_date;

                if (range(getMonth(), 31))
                    end_date = getMonth() + "-" + 31;
                else if (range(getMonth(), 30))
                    end_date = getMonth() + "-" + 30;
                else
                    end_date = getMonth() + "-" + 29;


                startDate.setText(start_date);
                endDate.setText(end_date);



                //double amount1 = Double.parseDouble(amt_input.getText().toString());
                //deposit_reslt.setText(String.valueOf(amount1));
                //BudgetReport budgetReport = new BudgetReport();
                if (date1 != null) {
                    double amount1 = Double.parseDouble(amountStr);
                    if (money_type.equals(per_week)) {
                        // add the income 4 times
                        double amount2 = amount1 * 4;
                        BudgetItem in = new Income(title_input.getText().toString(), amount2, money_type, date1);
                        date_view_res.setText(in.getDate());
                        title_res.setText(in.getTitle());
                        type_reslt.setText(in.getTransaction_type());
                        deposit_reslt.setText(String.valueOf(in.getAmount()));
                        budgetReport.addItem(in);

                        // checks biweekly
                    } else if (money_type.equals(per_2_week)) {
                        //recur = "BiWeekly";
                        // add the income 2 times
                        double amount2 = amount1 * 2;
                        BudgetItem in = new Income(title_input.getText().toString(), amount2, money_type, date1);
                        date_view_res.setText(in.getDate());
                        title_res.setText(in.getTitle());
                        type_reslt.setText(in.getTransaction_type());
                        deposit_reslt.setText(String.valueOf(in.getAmount()));
                        budgetReport.addItem(in);

                        // if just single add it
                    } else {
                        BudgetItem in = new Income(getTitle, amount1, money_type, date1);
                        date_view_res.setText(in.getDate());
                        title_res.setText(in.getTitle());
                        type_reslt.setText(in.getTransaction_type());
                        deposit_reslt.setText(String.valueOf(in.getAmount()));
                        budgetReport.addItem(in);
                    }

                    total_money.setText(String.valueOf(budgetReport.getTotalMon(budgetReport)));
                    total_income.setText(String.valueOf(budgetReport.getTotalIn()));
                    total_expense.setText(String.valueOf(budgetReport.getTotalEx()));
                }

                r1 = new TableRow(MainActivity.this);
                //date_view_res, title_res, deposit_reslt, withdraw_reslt, type_reslt
                date_view_res = new TextView(MainActivity.this);
                date_view_res.setText("0");
                date_view_res.setGravity(Gravity.CENTER);
                title_res = new TextView(MainActivity.this);
                title_res.setText("0");
                title_res.setGravity(Gravity.CENTER);
                deposit_reslt = new TextView(MainActivity.this);
                deposit_reslt.setText("0");
                deposit_reslt.setGravity(Gravity.CENTER);
                withdraw_reslt = new TextView(MainActivity.this);
                withdraw_reslt.setText("0");
                withdraw_reslt.setGravity(Gravity.CENTER);
                type_reslt = new TextView(MainActivity.this);
                type_reslt.setText("0");
                type_reslt.setGravity(Gravity.CENTER);
                r1.addView(date_view_res);
                r1.addView(title_res);
                r1.addView(deposit_reslt);
                r1.addView(withdraw_reslt);
                r1.addView(type_reslt);
                t1.addView(r1);

            }
        });
        Button expensebtn;
        expensebtn = findViewById(R.id.expense);
        expensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get date/day
//                int day = Integer.parseInt(date_input.getText().toString());
//                String getTitle = title_input.getText().toString();
//                String money_type = types.getText().toString();

                String dayStr = date_input.getText().toString();
                String getTitle = title_input.getText().toString();
                String money_type = types.getText().toString();
                String amountStr = amt_input.getText().toString();
                if(getTitle.isEmpty() || money_type.isEmpty() || dayStr.isEmpty() || amountStr.isEmpty()
                        || !range(getMonth(), Integer.parseInt(dayStr))){
                    Toast.makeText(MainActivity.this, "Populate all input boxes and check the month range", Toast.LENGTH_SHORT).show();
                    return;
                }
                int day = Integer.parseInt(dayStr);
                String date1 = getMonth() + "-" + day;

                String start_date = getMonth() + "-" + 1;
                String end_date;

                if (range(getMonth(), 31))
                    end_date = getMonth() + "-" + 31;
                else if (range(getMonth(), 30))
                    end_date = getMonth() + "-" + 30;
                else
                    end_date = getMonth() + "-" + 29;


                startDate.setText(start_date);
                endDate.setText(end_date);

                //BudgetReport budgetReport = new BudgetReport();
                if (date1 != null) {
                    double amount1 = Double.parseDouble(amountStr);
                    if (money_type.equals(per_week)) {
                        // add the income 4 times
                        double amount2 = amount1 * 4;
                        BudgetItem ex = new Expense(title_input.getText().toString(), amount2, money_type, date1);
                        date_view_res.setText(ex.getDate());
                        title_res.setText(ex.getTitle());
                        type_reslt.setText(ex.getTransaction_type());
                        withdraw_reslt.setText(String.valueOf(ex.getAmount()));
                        budgetReport.addItem(ex);

                        // checks biweekly
                    } else if (money_type.equals(per_2_week)) {
                        //recur = "BiWeekly";
                        // add the income 2 times
                        double amount2 = amount1 * 2;
                        BudgetItem ex = new Expense(getTitle, amount2, money_type, date1);
                        date_view_res.setText(ex.getDate());
                        title_res.setText(ex.getTitle());
                        type_reslt.setText(ex.getTransaction_type());
                        withdraw_reslt.setText(String.valueOf(ex.getAmount()));
                        budgetReport.addItem(ex);

                        // if just single add it
                    } else {
                        BudgetItem ex = new Expense(title_input.getText().toString(), amount1, money_type, date1);
                        date_view_res.setText(ex.getDate());
                        title_res.setText(ex.getTitle());
                        type_reslt.setText(ex.getTransaction_type());
                        withdraw_reslt.setText(String.valueOf(ex.getAmount()));
                        budgetReport.addItem(ex);
                    }

                    total_money.setText(String.valueOf(budgetReport.getTotalMon(budgetReport)));
                    total_income.setText(String.valueOf(budgetReport.getTotalIn()));
                    total_expense.setText(String.valueOf(budgetReport.getTotalEx()));
                }
                r1 = new TableRow(MainActivity.this);
                //date_view_res, title_res, deposit_reslt, withdraw_reslt, type_reslt
                date_view_res = new TextView(MainActivity.this);
                date_view_res.setText("0");
                date_view_res.setGravity(Gravity.CENTER);
                title_res = new TextView(MainActivity.this);
                title_res.setText("0");
                title_res.setGravity(Gravity.CENTER);
                deposit_reslt = new TextView(MainActivity.this);
                deposit_reslt.setText("0");
                deposit_reslt.setGravity(Gravity.CENTER);
                withdraw_reslt = new TextView(MainActivity.this);
                withdraw_reslt.setText("0");
                withdraw_reslt.setGravity(Gravity.CENTER);
                type_reslt = new TextView(MainActivity.this);
                type_reslt.setText("0");
                type_reslt.setGravity(Gravity.CENTER);
                r1.addView(date_view_res);
                r1.addView(title_res);
                r1.addView(deposit_reslt);
                r1.addView(withdraw_reslt);
                r1.addView(type_reslt);
                t1.addView(r1);

            }
        });
        Button deletebtn;
        deletebtn = findViewById(R.id.delete);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = Integer.parseInt(date_input.getText().toString());
                String getTitle = title_input.getText().toString();
                String money_type = types.getText().toString();
                String date1 = getMonth() + "-" + day;
                // calls delete method
                budgetReport.delete(budgetReport, date1);
//
//                r1 = new TableRow(MainActivity.this);
//                //date_view_res, title_res, deposit_reslt, withdraw_reslt, type_reslt
//                date_view_res = new TextView(MainActivity.this);
//                //date_view_res.setText("0");
//                date_view_res.setGravity(Gravity.CENTER);
//                title_res = new TextView(MainActivity.this);
//                //title_res.setText("0");
//                title_res.setGravity(Gravity.CENTER);
//                deposit_reslt = new TextView(MainActivity.this);
//                //deposit_reslt.setText("0");
//                deposit_reslt.setGravity(Gravity.CENTER);
//                withdraw_reslt = new TextView(MainActivity.this);
//                //withdraw_reslt.setText("0");
//                withdraw_reslt.setGravity(Gravity.CENTER);
//                type_reslt = new TextView(MainActivity.this);
//                //type_reslt.setText("0");
//                type_reslt.setGravity(Gravity.CENTER);
//                r1.removeView(date_view_res);
//                r1.removeView(title_res);
//                r1.removeView(deposit_reslt);
//                r1.removeView(withdraw_reslt);
//                r1.removeView(type_reslt);
//                t1.removeView(r1);
            }
        });
        // WORKMANAGER
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(WorkerNode.class).build();
        Button worker;
        worker = findViewById(R.id.delete);
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager workManager = WorkManager.getInstance(MainActivity.this);
                workManager.enqueue(request);

            }
        });
        final TextView failure = findViewById(R.id.failure);
        WorkManager.getInstance(MainActivity.this)
        .getWorkInfoByIdLiveData(request.getId())
        .observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                String status = workInfo.getState().name();
                failure.append(status + "\n");
            }
        });



    }

    public void addView(ImageView imageView, int w, int ht){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, ht);
        layoutParams.setMargins(0, 2, 0, 2);

        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
    }
    public int getMonth() {
        String str = editMonth.getText().toString();
        int month = Integer.parseInt(str);
        return month;
    }
    public boolean range(int month, int day) {
        if (month > 0 && month <= 12 && day > 0 && day <= 31) {
            if (month == 2 && day > 29)
                return false;
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day > 30)
                    return false;
            }

            return true;
        } else
            return false;

    }
    public String getDay(){
        String str = editMonth.getText().toString();
        int month = Integer.parseInt(str);
        int day = Integer.parseInt(date_input.getText().toString());
        boolean flag = false;
        if (!range(month, day)) {
            Toast.makeText(MainActivity.this, "Check month range", Toast.LENGTH_SHORT).show();
            return "";
        }
        return String.valueOf(day);

    }

}