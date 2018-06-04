package luis_vives.app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import luis_vives.app.R;

public class main_fragment extends Fragment {

    public main_fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_screen_fragment_layout, container, false);
    }

    public void onStart(){
        SetMainText();
        super.onStart();
    }

    private void SetMainText(){
        String htmlAsString = getString(R.string.home_description);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
        // set the html content on the TextView
        TextView textView = (TextView) getActivity().findViewById(R.id.home_description_text);
        textView.setText(htmlAsSpanned);
    }
}
