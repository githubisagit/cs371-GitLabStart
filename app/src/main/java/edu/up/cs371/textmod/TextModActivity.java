package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TextModActivity extends ActionBarActivity {


    private Button lowerCase;
    private Button noSpacesButton ;


    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image
    private TextView editText;
    private Button reverseButton;
    private Button copyButton;
    private int myPosition;


    private Button upperCase;
    private Button clear;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);


        lowerCase = (Button) findViewById(R.id.lowerButton);
        lowerCase.setOnClickListener(new lowerCaseButtonListener());


        // set instance variables for our widgets
        imageView = (ImageView) findViewById(R.id.imageView);
        copyButton = (Button) findViewById(R.id.copyButton);
        editText = (TextView) findViewById(R.id.editText);
        reverseButton = (Button) findViewById(R.id.reverseButton);
        noSpacesButton = (Button)findViewById(R.id.button);


        upperCase = (Button) findViewById(R.id.upperCaseButton);
        clear = (Button) findViewById(R.id.clearButton);
        editText = (TextView) findViewById(R.id.editText);
        upperCase.setOnClickListener(new upperCaseButtonListener());
        clear.setOnClickListener(new clearButtonListener());
        noSpacesButton.setOnClickListener(new noSpacesButtonListener());


        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // get array of strings
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i, 0);
            if (id == 0) id = imageIds2.getResourceId(0, 0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());
        reverseButton.setOnClickListener(new ReverseButtonListener());
        copyButton.setOnClickListener(new CopyButtonListener());

    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
            myPosition = position;
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            //String spinnerText = spinner.getSelectedItem().toString();

            // your code here
        }
    }

    private class lowerCaseButtonListener implements View.OnClickListener {
        public void onClick(View v) {

            editText.setText(editText.getText().toString().toLowerCase());
        }
    }

    private class CopyButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
            String textAtSpinnerPosition = spinnerNames[myPosition];

            editText.append(textAtSpinnerPosition);

        }
    }

    private class ReverseButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String orig = editText.getText().toString();
            String reversed = "";


            for (int i = (orig.length() - 1); i >= 0; i--) {
                reversed = reversed + orig.charAt(i);
            }

            editText.setText(reversed);

        }

    }

    private class upperCaseButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            editText.setText(editText.getText().toString().toUpperCase());
        }


    }

    private class clearButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            editText.setText(" ");
        }


    }

    private class noSpacesButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String noSpacesText = editText.getText().toString().replace(" ", "");
            editText.setText(noSpacesText) ;
        }
    }

}
