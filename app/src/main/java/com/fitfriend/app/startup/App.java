package com.fitfriend.app.startup;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.fitfriend.app.R;
import com.fitfriend.app.connect.Comment;
import com.fitfriend.app.connect.Group;
import com.fitfriend.app.connect.Post;
import com.fitfriend.app.exercise.types.Exercise;
import com.fitfriend.app.plan.Event;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.PushService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Daniel on 5/3/2014.
 */
public class App extends Application {

    public App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Exercise.class);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "6ndNVpRctpv0EB5awdLtiT1nEwg5WidUBSNyKRwo", "QeU6X4k0S1zJDtlMZhiZPoe59DKhhGJONMdhZEBN");
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

        PushService.setDefaultPushCallback(this, MainActivity.class);

        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.cantwellcode.fitfriend.app", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}