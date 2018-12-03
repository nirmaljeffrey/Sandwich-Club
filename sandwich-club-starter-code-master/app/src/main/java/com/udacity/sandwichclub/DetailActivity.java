package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView originTextView;
    private TextView alsoKnowTextView;
    private TextView ingredintTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTextView = (TextView) findViewById(R.id.origin_tv);
        alsoKnowTextView = (TextView) findViewById(R.id.also_known_tv);
        ingredintTextView = (TextView) findViewById(R.id.ingredients_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Poplulate the place of origin data into textview
        if(sandwich.getPlaceOfOrigin().isEmpty()){
        originTextView.setText("Unknown");
        }else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }
        if(sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnowTextView.setText("-");
        }else {
            // Display the list data as a multiline strings in a single text view.
            StringBuilder builder = new StringBuilder();
            for (String listdata : sandwich.getAlsoKnownAs()) {
                builder.append(listdata + "\n");
            }
            // Poplulate the also known as  data into textview
            alsoKnowTextView.setText(builder.toString());
        }
        // Display the list data as a multiline strings in a single text view.
        StringBuilder ingredientBuilder = new StringBuilder();
        for (String listdata : sandwich.getIngredients()) {
            ingredientBuilder.append( listdata+ "\n");
        }
        // Poplulate the ingredient  data into textview
        ingredintTextView.setText(ingredientBuilder.toString());
        // Poplulate the description  data into textview
        descriptionTextView.setText(sandwich.getDescription());
    }
}
