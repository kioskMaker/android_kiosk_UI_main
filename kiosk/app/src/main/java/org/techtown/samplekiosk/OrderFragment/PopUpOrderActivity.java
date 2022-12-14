package org.techtown.samplekiosk.OrderFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.samplekiosk.R;
import org.techtown.samplekiosk.TextToSpeechService;
import org.techtown.samplekiosk.Types.Board;
import org.techtown.samplekiosk.Types.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopUpOrderActivity extends AppCompatActivity {

    Intent intent;
    Intent intentTTS;
    RecyclerView recyclerView;
    List<Board> board_dataList;
    OrderAdapter orderAdapter;
    Map<String, Board> board_data = new HashMap<>();
    Map<String, Integer> rule = new HashMap<>();
    int mode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentTTS  = new Intent(this, TextToSpeechService.class);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        mode = bundle.getInt("mode");
        board_dataList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, board_dataList, board_data, rule, mode);
        order(intent);

        setContentView(R.layout.activity_pop_up_order);

        recyclerView = findViewById(R.id.recycleView3);

        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setCheckOrderSheet();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getExtras().getString("keyPadValue") == "5"){
            intentTTS.putExtra("word", "포장 여부를 선택해주세요");
            finish();
        }
    }

    public void order(Intent intent){
        if(intent == null) return;
        Bundle bundle = intent.getExtras();

        int size = bundle.getInt("getItemCount");

        for(int i = 0; i<size;i++){
            Data data = bundle.getParcelable("Order"+(i+1));
            if(data == null) return;
            orderAdapter.putInMap(data.name, Integer.toString(data.count), Integer.toString(data.cost), orderAdapter.getItemCount());
        }
    }

    private void setCheckOrderSheet(){
        TextView orderCost = findViewById(R.id.oldOrderCost);
        TextView saleCost = findViewById(R.id.oldSaleCost);
        TextView payCost = findViewById(R.id.oldPayCost);
        Button buttonCheck = findViewById(R.id.buttonCheck);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orderCost.setText(""+orderAdapter.totalcost);
        saleCost.setText("0");
        int payCostValue = orderAdapter.totalcost - Integer.parseInt(saleCost.getText().toString());
        payCost.setText(""+payCostValue);
    }

}