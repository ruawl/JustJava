/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    // Global Variables.
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = 0;
        String priceMessage = null;

        price = calculatePrice(5);
        priceMessage = createOrderSummary(price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + getName());
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method increments the quantity on the screen.
     */
    public void increment(View view) {
        if (quantity == 30) {
            Toast.makeText(this, "You cannot have more than 30 coffees", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity += 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method decrements the quantity on the screen.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity -= 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param pricePerCup
     * @return the price
     */
    private int calculatePrice(int pricePerCup) {
        if(checkWhippedCream())
            pricePerCup += 1;
        if(checkChocolate())
            pricePerCup += 2;

        int price = quantity * pricePerCup;

        return price;
    }

    private boolean checkWhippedCream() {
        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return hasWhippedCream.isChecked();
    }

    private boolean checkChocolate() {
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return hasChocolate.isChecked();
    }

    private String getName() {
        EditText clientName = (EditText) findViewById(R.id.name_edit_text);
        return clientName.getText().toString();
    }

    private String createOrderSummary(int price) {
        String priceMessage =           "Name: " + getName();
        priceMessage += "\nAdd whipped cream? " + checkWhippedCream();
        priceMessage += "\nAdd chocolate? " + checkChocolate();
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: R$ " + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }
}