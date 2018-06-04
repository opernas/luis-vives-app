package luis_vives.app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import luis_vives.app.R;

public class fragmentPdfViewer extends Fragment{

    String pdfTitle;
    String pdfUrlToShow;
    WebView webview;
    ProgressBar progressbar;

    public fragmentPdfViewer() {
    }

    public static fragmentPdfViewer newInstance(String urlPdfToShow, String pdfTitle) {
        Bundle bundle = new Bundle();
        bundle.putString("pdfUrl", urlPdfToShow);
        bundle.putString("pdfTitle", pdfTitle);
        fragmentPdfViewer fragment = new fragmentPdfViewer();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            pdfUrlToShow = bundle.getString("pdfUrl");
            pdfTitle = bundle.getString("pdfTitle");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pdf_viewer, container, false);
        readBundle(getArguments());
        return v;
    }

    public void onStart() {
        super.onStart();
        webview = (WebView)getActivity().findViewById(R.id.webview);
        progressbar = (ProgressBar) getActivity().findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfUrlToShow);
        TextView textView = getActivity().findViewById(R.id.tv_header);
        textView.setText(pdfTitle);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }
}
