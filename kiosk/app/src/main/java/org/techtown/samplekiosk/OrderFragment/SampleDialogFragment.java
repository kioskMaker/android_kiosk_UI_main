package org.techtown.samplekiosk.OrderFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import android.widget.Toast;

import org.techtown.samplekiosk.TextToSpeechService;


public class SampleDialogFragment extends androidx.fragment.app.DialogFragment {

    Intent intentTTS;
    int pointer = 0;

    public SampleDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //어디다?
        intentTTS = new Intent(getActivity(), TextToSpeechService.class);

        OrderSheet activity = (OrderSheet) getActivity();

        //Dialog 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("안내");
        builder.setMessage("결제하시겠습니까?");

        intentTTS.putExtra("word", "결제하시겠습니까? 결제하시려면 5번을 눌러주세요");
        getActivity().startService(intentTTS);

        //listener 적용
        DialogListener listener = new DialogListener();

        builder.setPositiveButton("예",listener);
        builder.setNeutralButton("취소",listener);
        builder.setNegativeButton("아니오",listener);

        AlertDialog alert = builder.create();

        return alert;
    }//finish


    // 버튼 누르면 반응-> listener이용/  fragment내에선 onclick방식 못씀
    class DialogListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //어디다?
            OrderSheet orderSheet = (OrderSheet) getActivity();
            switch (which){
                case DialogInterface.BUTTON_POSITIVE :
                    Toast.makeText(getActivity(), "ok", Toast.LENGTH_LONG).show();
                    Intent LoopIntent = new Intent(orderSheet, OrderSheet.class);
                    LoopIntent.putExtra("Return", true);
                    LoopIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(LoopIntent);
                    break;

                case DialogInterface.BUTTON_NEUTRAL :
                    Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show();
                    ((OrderSheet) getActivity()).isDialog = false;
                    break;

                case DialogInterface.BUTTON_NEGATIVE :
                    Toast.makeText(getActivity(), "no", Toast.LENGTH_LONG).show();
                    ((OrderSheet) getActivity()).isDialog = false;
                    break;
            }
        }

    }//finish -> listener 적용 ㄱㄱ




}