/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener
{
  Button signInUpButton;

  TextView signUpText;
  EditText usernameEditText, passwordEditText;
  int signView;
  String username, password;
  ParseUser user;
  ParseObject parseObject;



  public void signInOrUp(View view){

    username = String.valueOf(usernameEditText.getText());
    password = String.valueOf(passwordEditText.getText());


    if(signView == 0){
      //Log in view
      user.logInInBackground(username, password, new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if(user != null){
            Log.i("AppInfo", "Log in successfully");
            showUserList();

          }else{
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
          }
        }
      });



    }else{
      // Sign Up view
      user = new ParseUser();
      user.setUsername(username);
      user.setPassword(password);
      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            Log.i("AppInfo", "SignUp OK");
            clearFields();
          } else {
            // e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
          }
        }
      });
    }


  }

  public void clearFields(){
    usernameEditText.setText("");
    passwordEditText.setText("");
  }
  public void switchSignUp(View view){
    clearFields();
    if (signView == 0){
      signInUpButton.setText("Sign Up");

      signUpText.setText("Log In");
      signView = 1;
    }else{
      signInUpButton.setText("Log In");

      signUpText.setText("Sign Up");
      signView = 0;
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    signInUpButton = (Button) findViewById(R.id.signInUpButton);
    signUpText = (TextView) findViewById(R.id.signUpText);
    usernameEditText = (EditText) findViewById(R.id.usernameEditText);
    passwordEditText = (EditText) findViewById(R.id.passwordEditText);

    signView = 0; // 0: Login view 1: Sign Up view

    usernameEditText.setOnKeyListener(this);
    passwordEditText.setOnKeyListener(this);

    if(ParseUser.getCurrentUser()!=null)
      showUserList();

   /* ParseObject score = new ParseObject("Score");
    score.put("name", "Tomi");
    score.put("score", 111);

    ParseQuery<ParseObject> query=ParseQuery.getQuery("Score");
    query.whereEqualTo("name","Tomi");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e==null){
          Log.i("findInBackground", "Retrived"+objects.size()+" results");
          for(ParseObject object : objects){
            Log.i("findInBackgroundUser", String.valueOf(object.get("score")));
            object.put("score", 50);
            object.saveInBackground();
          }
        }
      }
    }); */
    /* getInBackground works when id is known
    query.getInBackground("iQCo5e7hLi", new GetCallback<ParseObject>() {

      @Override
      public void done(ParseObject object, ParseException e) {
        if(e==null){
            object.put("score", 200);
            object.saveInBackground();
        }
        else
          Log.i("error", e.toString());
      }

    });*/

    /*score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null){
          Log.i("SaveInBackground", "Successful");
        }
        else{
          Log.i("SaveInBackground","Failed");
        }
      }
    });*/
    //score.saveEventually();
    //score.saveInBackground();

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

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

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if(v.getId() == R.id.passwordEditText && keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
      signInOrUp(v);
    }
    if(v.getId()== R.id.usernameEditText && keyCode == KeyEvent.KEYCODE_ENTER) {
      passwordEditText.requestFocus();
      //InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
      //imm.showSoftInput(passwordEditText, InputMethodManager.SHOW_IMPLICIT);
      return true;
    }


      return false;
  }

  public void showUserList(){
    Intent i = new Intent(getApplicationContext(), UserlistActivity.class);
    startActivity(i);
  }
}
