// Generated by view binder compiler. Do not edit!
package com.moon.skywatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.moon.skywatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CarlisttempleteBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imgCarParking1;

  @NonNull
  public final ImageView imgCarParking2;

  @NonNull
  public final ImageView imgNumPlate;

  @NonNull
  public final TextView tvArea;

  @NonNull
  public final TextView tvCarNum;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvTime;

  private CarlisttempleteBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imgCarParking1, @NonNull ImageView imgCarParking2,
      @NonNull ImageView imgNumPlate, @NonNull TextView tvArea, @NonNull TextView tvCarNum,
      @NonNull TextView tvDate, @NonNull TextView tvTime) {
    this.rootView = rootView;
    this.imgCarParking1 = imgCarParking1;
    this.imgCarParking2 = imgCarParking2;
    this.imgNumPlate = imgNumPlate;
    this.tvArea = tvArea;
    this.tvCarNum = tvCarNum;
    this.tvDate = tvDate;
    this.tvTime = tvTime;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CarlisttempleteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CarlisttempleteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.carlisttemplete, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CarlisttempleteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgCarParking1;
      ImageView imgCarParking1 = ViewBindings.findChildViewById(rootView, id);
      if (imgCarParking1 == null) {
        break missingId;
      }

      id = R.id.imgCarParking2;
      ImageView imgCarParking2 = ViewBindings.findChildViewById(rootView, id);
      if (imgCarParking2 == null) {
        break missingId;
      }

      id = R.id.imgNumPlate;
      ImageView imgNumPlate = ViewBindings.findChildViewById(rootView, id);
      if (imgNumPlate == null) {
        break missingId;
      }

      id = R.id.tv_area;
      TextView tvArea = ViewBindings.findChildViewById(rootView, id);
      if (tvArea == null) {
        break missingId;
      }

      id = R.id.tv_carNum;
      TextView tvCarNum = ViewBindings.findChildViewById(rootView, id);
      if (tvCarNum == null) {
        break missingId;
      }

      id = R.id.tv_date;
      TextView tvDate = ViewBindings.findChildViewById(rootView, id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tv_time;
      TextView tvTime = ViewBindings.findChildViewById(rootView, id);
      if (tvTime == null) {
        break missingId;
      }

      return new CarlisttempleteBinding((ConstraintLayout) rootView, imgCarParking1, imgCarParking2,
          imgNumPlate, tvArea, tvCarNum, tvDate, tvTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
