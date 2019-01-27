/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dndboard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.PlaneRenderer;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseTransformableNode;
import com.google.ar.sceneform.ux.TransformableNode;

import static java.lang.Math.round;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class DnDBoardActivity extends AppCompatActivity {
  private static final String TAG = DnDBoardActivity.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private ArFragment arFragment;
  private Spinner modelSpinner;

  private ModelRenderable andyRenderable;
  private ViewRenderable textRenderable;

  private ModelRenderable orcRenderable;
  private ModelRenderable goblinsRenderable;
  private ModelRenderable zombieRenderable;
  private ModelRenderable skeletonRenderable;
  private ModelRenderable cultistRenderable;
  private ModelRenderable dragonRenderable;
  private ModelRenderable demonRenderable;
  private ModelRenderable rangerRenderable;
  private ModelRenderable paladinRenderable;
  private ModelRenderable clericRenderable;
  private ModelRenderable wizardRenderable;
  private ModelRenderable bardRenderable;
  private ModelRenderable druidRenderable;
  private ModelRenderable fighterRenderable;
  private ModelRenderable rogueRenderable;
  
  private Vector3 distanceStartVec = null;

  @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }

      Texture.Sampler sampler =
              Texture.Sampler.builder()
                      .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
                      .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
                      .build();

    // R.drawable.custom_texture is a .png file in src/main/res/drawable
      Texture.builder()
              .setSource(this, R.drawable.stone)
              .setSampler(sampler)
              .build()
              .thenAccept(texture -> {
                  arFragment.getArSceneView().getPlaneRenderer()
                          .getMaterial().thenAccept(material ->
                          material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture));
              });

    setContentView(R.layout.activity_ux);

    //monster list
    modelSpinner = (Spinner) findViewById(R.id.mon_spinner);
    ArrayAdapter<CharSequence> model_adapter = ArrayAdapter.createFromResource(this,
              R.array.monsters, android.R.layout.simple_spinner_item);
    model_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    modelSpinner.setAdapter(model_adapter);


    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    ModelRenderable.builder()
        .setSource(this, R.raw.andy)
        .build()
        .thenAccept(renderable -> andyRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.orc)
        .build()
        .thenAccept(renderable -> orcRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load orc renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.goblin)
        .build()
        .thenAccept(renderable -> goblinsRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load goblin renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.zombie)
        .build()
        .thenAccept(renderable -> zombieRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load zombie renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.skeleton)
        .build()
        .thenAccept(renderable -> skeletonRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load skeleton renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.cultist)
        .build()
        .thenAccept(renderable -> cultistRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load cultist renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.dragon)
        .build()
        .thenAccept(renderable -> dragonRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load dragon renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.demon)
        .build()
        .thenAccept(renderable -> demonRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load demon renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.ranger)
        .build()
        .thenAccept(renderable -> rangerRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load ranger renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.paladin)
        .build()
        .thenAccept(renderable -> paladinRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load paladin renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.cleric)
        .build()
        .thenAccept(renderable -> clericRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load cleric renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.wizard)
        .build()
        .thenAccept(renderable -> wizardRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load wizard renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.bard)
        .build()
        .thenAccept(renderable -> bardRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load bard renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.druid)
        .build()
        .thenAccept(renderable -> druidRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load druid renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.fighter)
        .build()
        .thenAccept(renderable -> fighterRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load fighter renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    ModelRenderable.builder()
        .setSource(this, R.raw.rogue)
        .build()
        .thenAccept(renderable -> rogueRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load rogue renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (andyRenderable == null) {
            return;
          }

          float scale = 0.5f;
            ModelRenderable figureRenderable = null;

          switch (modelSpinner.getSelectedItem().toString()) {
              case "Distance Tool":

//                  Pose distanceStartPose = distanceStartAnchor.getPose();

                  if (distanceStartVec != null) {
                      Pose endPose = hitResult.getHitPose();
                      // Compute the difference vector between the two hit locations.
                      float dx = distanceStartVec.x - endPose.tx();
                      float dy = distanceStartVec.y  - endPose.ty();
                      float dz = distanceStartVec.z  - endPose.tz();

                      // Compute the straight-line distance.
                      float distanceMeters = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
                      float distanceScale = 20f*3.28f;
                      Toast toast =
                              Toast.makeText(this, String.format("%d ft", round(distanceMeters*distanceScale)), Toast.LENGTH_LONG);
                      toast.setGravity(Gravity.CENTER, 0, 0);
                      toast.show();
                  }

                  break;

              case "Orc":
                  scale = 0.05f;
                  figureRenderable = orcRenderable;
                  break;
              case "Goblins":
                  scale = 0.03f;
                  figureRenderable = goblinsRenderable;
                  break;
              case "Zombie":
                  scale = 0.04f;
                  figureRenderable = zombieRenderable;
                  break;
              case "Skeleton":
                  scale = 0.05f;
                  figureRenderable = skeletonRenderable;
                  break;
              case "Cultist":
                  scale = 0.05f;
                  figureRenderable = cultistRenderable;
                  break;
              case "Dragon":
                  scale = 0.275f;
                  figureRenderable = dragonRenderable;
                  break;
              case "Demon":
                  scale = 0.15f;
                  figureRenderable = demonRenderable;
                  break;
              case "Ranger":
                  scale = 0.05f;
                  figureRenderable = rangerRenderable;
                  break;
              case "Paladin":
                  scale = 0.05f;
                  figureRenderable = paladinRenderable;
                  break;
              case "Cleric":
                  scale = 0.05f;
                  figureRenderable = clericRenderable;
                  break;
              case "Wizard":
                  scale = 0.05f;
                  figureRenderable = wizardRenderable;
                  break;
              case "Bard":
                  scale = 0.05f;
                  figureRenderable = bardRenderable;
                  break;
              case "Druid":
                  scale = 0.05f;
                  figureRenderable = druidRenderable;
                  break;
              case "Fighter":
                  scale = 0.05f;
                  figureRenderable = fighterRenderable;
                  break;
              case "Rogue":
                  scale = 0.05f;
                  figureRenderable = rogueRenderable;
                  break;
              default:
                  Toast errorToast =
                    Toast.makeText(this, "Unknown Selection", Toast.LENGTH_LONG);
                  errorToast.setGravity(Gravity.CENTER, 0, 0);
                  errorToast.show();
                  return;
          }

          if (figureRenderable != null) {
              // Create the Anchor.
              Anchor anchor = hitResult.createAnchor();
              AnchorNode anchorNode = new AnchorNode(anchor);
              anchorNode.setParent(arFragment.getArSceneView().getScene());

              // Create the transformable andy and add it to the anchor.
              TransformableNode figure = new TransformableNode(arFragment.getTransformationSystem());
              figure.getScaleController().setMinScale(0.01f);
              figure.getScaleController().setMaxScale(1f);
              figure.getScaleController().setSensitivity(0);
              figure.setRenderable(figureRenderable);

              // Handles start point for distance measurement
              figure.setOnTapListener( (nodeHitResult, nodeMotionEvent) -> {
                  Log.d(TAG, "Node tapped");
                  distanceStartVec = figure.getWorldPosition();
              });
              distanceStartVec = figure.getWorldPosition();

              figure.setLocalScale(new Vector3(scale, scale, scale));
              figure.select();
              figure.setParent(anchorNode);

          }



            // Distance measurement
//            Pose endPose = hitResult.getHitPose();
//            // Compute the difference vector between the two hit locations.
//            float dx = startPose.tx() - endPose.tx();
//            float dy = startPose.ty() - endPose.ty();
//            float dz = startPose.tz() - endPose.tz();
//
//            // Compute the straight-line distance.
//            float distanceMeters = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
//            float scale = 20f*3.28f;
//            Toast toast =
//                    Toast.makeText(this, Float.toString(distanceMeters*scale), Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();

        });
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
  public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
    if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
      activity.finish();
      return false;
    }
    String openGlVersionString =
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
          .show();
      activity.finish();
      return false;
    }
    return true;
  }
}
